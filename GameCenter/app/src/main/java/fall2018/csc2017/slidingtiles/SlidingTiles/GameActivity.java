package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.slidingtiles.R;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer{

    /**
     * The board manager.
     */
    private BoardManagerST boardManager;

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
     * A handler for the timing score.
     */
    final Handler timingScore = new Handler();

    /**
     * clear the time score to 0
     */
    public static void clearTimeScore(){
        intTimeScore = 0;
    }

    /**
     * clear move score to 0
     */
    public static void clearNumMoves(){
        intNumMoves = 0;
    }

    /**
     * returns the total score.
     * @return
     */
    public static double getScore() {
        return Math.ceil((10000 - 10 * GameActivity.intNumMoves)/ 0.03 * GameActivity.intTimeScore);
    }

    /**
     * autosave the current state.
     */
    private void autosave(){
        saveToFile(StartingActivityST.SAVE_FILENAME);
        saveToFile(StartingActivityST.TEMP_SAVE_FILENAME);
        //makeToastText("Game Saved");
    }

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

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridViewST gridView;

    /**
     * column width and height int.
     */
    private static int columnWidth, columnHeight;

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
        BoardST b = new BoardST(boardManager.getBoard().getTiles());
        if (boardManager.getIsImage()){
            b.setIsImage(true);
        }
        BoardManagerST.managerStack.push(b);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFromFile(StartingActivityST.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        // Add View to activity
        setContentView(R.layout.activity_main_st);
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(BoardST.NUM_COLS);
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

                        columnWidth = displayWidth / BoardST.NUM_COLS;
                        columnHeight = displayHeight / BoardST.NUM_ROWS;

                        display();
                        timingScore.postDelayed(timing, 5000);
                        StartingActivityST.setBool();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        BoardST board = boardManager.getBoard();
        Drawable d;
        tileButtons = new ArrayList<>();

        for (int row = 0; row != BoardST.NUM_ROWS; row++) {
            for (int col = 0; col != BoardST.NUM_COLS; col++) {
                Button tmp = new Button(context);
                if (board.getTile(row, col).getBackground() == 0) {
                    d = createDrawableBackground(board, row, col);
                    tmp.setBackground(d);
                } else {
                    updateTileButton(tmp, board.getTile(row, col));
                }
                this.tileButtons.add(tmp);
            }
        }
    }

    private void updateTileButton(Button button, TileST tile) {
        if (tile.getId() == BoardST.NUM_COLS * BoardST.NUM_ROWS) {
            button.setText("");
        } else {
            button.setText(Integer.toString(tile.getId()));
        }
        button.setTextSize(40);
        Typeface font = Typeface.createFromAsset(this.getAssets(),
                "fonts/Audiowide-Regular.ttf");
        button.setTypeface(font);
        button.setTextColor(ContextCompat.getColor(this, R.color.colorMagicAccent));
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorMagicPrimary));
    }

    private ShapeDrawable buildBorder() {
        ShapeDrawable border = new ShapeDrawable();
        border.setShape(new RectShape());
        border.getPaint().setColor(Color.RED);
        border.getPaint().setStrokeWidth(10f);
        border.getPaint().setStyle(Paint.Style.STROKE);
        return border;
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        BoardST board = boardManager.getBoard();
        int nextPos = 0;
        Drawable d;

        for (Button b : tileButtons) {
            int row = nextPos / BoardST.NUM_ROWS;
            int col = nextPos % BoardST.NUM_COLS;
            if (board.getTile(row, col).getBackground() == 0) {
                d = createDrawableBackground(board, row, col);
                b.setBackground(d);
            }
            else { updateTileButton(b, board.getTile(row,col)); }
            nextPos++;
        }
    }

    /**
     * Return a Drawable version of a region of the background image's bitmap.
     */
    private Drawable createDrawableBackground(BoardST board, int row, int col) {
        int[] coordinates = ((TileST)board.getTile(row, col)).getBackgroundCoordinates();
        Bitmap bitmap = Bitmap.createBitmap(background, coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
        return new BitmapDrawable(Resources.getSystem(), bitmap);
    }


    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivityST.TEMP_SAVE_FILENAME);
        saveToFile(StartingActivityST.SAVE_FILENAME);
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
                boardManager = (BoardManagerST) input.readObject();
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
        autosave();
        display();
    }

    public void makeToastText(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        View view = toast.getView();
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 64 );
        shape.setColor(ContextCompat.getColor(this, R.color.colorMagicAccentTransparent));
        view.setBackground(shape);
        TextView textview = (TextView) view.findViewById(android.R.id.message);
        Typeface font = Typeface.createFromAsset(this.getAssets(),
                "fonts/Audiowide-Regular.ttf");
        textview.setTypeface(font);
        textview.setTextSize(18);
        textview.setGravity(Gravity.CENTER);
        textview.setTextColor(ContextCompat.getColor(this, R.color.colorMagicPrimary));
        toast.show();
    }
}
