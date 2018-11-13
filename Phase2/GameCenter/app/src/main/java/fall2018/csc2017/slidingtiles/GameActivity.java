package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.widget.Toast;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer{

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * User defined background image.
     */
    public static Bitmap background = null;

    /**
     * An int tracking timing score.
     */
    public static int intTimeScore;

    /**
     * An int counting number of moves.
     */
    public static int intNumMoves;

    /**
     * A handler for the autosave Thread.
     */
    final Handler autosaveTimer = new Handler();

    /**
     * A handler for the timing score.
     */
    final Handler timingScore = new Handler();


    public static void clearTimeScore(){
        intTimeScore = 0;
    }
    public static void clearNumMoves(){
        intNumMoves = 0;
    }

    /**
     * A Runnable varible saving the game every 5 seconds.
     */
    private Runnable autoSave = new Runnable(){
        @Override
        public void run() {
                autosaveTimer.postDelayed(this, 5000);
                saveToFile(StartingActivity.SAVE_FILENAME);
                saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
                makeToastSavedText();
        }
    };

    /**
     * A runnabele variable counting the time.
     */
    private Runnable timing = new Runnable(){
        @Override
        public void run() {
            timingScore.postDelayed(this, 5000);
            intTimeScore ++;
        }
    };

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Check whether there is a valid background image.
     */
    public static boolean hasBackground() {
        return background != null;
    }

    /**
     * Return the background image's width and height.
     *
     * @return the background image's width and height.
     */
    public static int[] getBackgroundDimensions() {
        return new int[] {background.getWidth(), background.getHeight()};
    }

    /**
     * Set the background image.
     *
     * @param background the background image.
     */
    public static void setBackground(Bitmap background) {
        GameActivity.background = background;
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));

    }

    /**
     * Update the stack in boardManager based on current board.
     */
    public void updateBoard() {

        Board b = new Board(boardManager.getBoard().getTiles());
        BoardManager.managerStack.push(b);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFromFile(StartingActivity.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main);

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Board.NUM_COLS;
                        columnHeight = displayHeight / Board.NUM_ROWS;

                        display();
                        autosaveTimer.postDelayed(autoSave, 5000
                        );
                        timingScore.postDelayed(timing, 5000);
                        StartingActivity.setBool();
                    }
                });
    }




    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        Drawable d;
        tileButtons = new ArrayList<>();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                Button tmp = new Button(context);
                if (board.getTile(row, col).getBackground() == 0) {
                    d = createDrawableBackground(board, row, col);
                    tmp.setBackground(d);
                } else {
                    tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                }
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        Drawable d;

        for (Button b : tileButtons) {
            int row = nextPos / Board.NUM_ROWS;
            int col = nextPos % Board.NUM_COLS;
            if (board.getTile(row, col).getBackground() == 0) {
                d = createDrawableBackground(board, row, col);
                b.setBackground(d);
            }
            else { b.setBackgroundResource(board.getTile(row, col).getBackground()); }
            nextPos++;
        }
    }
    /**
     * Return a Drawable version of a region of the background image's bitmap.
     */
    private Drawable createDrawableBackground(Board board, int row, int col) {
        int[] coordinates = board.getTile(row, col).getBackgroundCoordinates();
        Bitmap bitmap = Bitmap.createBitmap(background, coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
        return new BitmapDrawable(Resources.getSystem(), bitmap);
    }


    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        autosaveTimer.removeCallbacks(autoSave);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateBoard();
        intNumMoves++;
        display();
    }
}
