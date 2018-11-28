package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.slidingtiles.LeaderBoardActivity;
import fall2018.csc2017.slidingtiles.R;

/**
 * The initial activity for the sliding puzzle tile game.
 */
abstract class StartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file.ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    /**
     * The board manager.
     */
    protected BoardManagerST boardManager;

    /**
     * The gameName string.
     */
    public static String gameName;

    public static boolean loadable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * set loadble to true.
     */
    public static void setBool(){
        loadable = true;
    }

    abstract void addStartButtonListener();

    /**
     * Activate the load button.
     */
    protected void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File testFile = new File(getApplicationContext().getFilesDir(), SAVE_FILENAME);
                if(testFile.exists()) {
                    loadFromFile(SAVE_FILENAME);
                    boardManager.clearStack();
                    GameActivity.clearTimeScore();
                    GameActivity.clearNumMoves();
                    makeToastText("Loaded Game");
                    switchToGame();
                }
                else {
                    makeToastText("No Available Save");
                }
            }
        });
    }

    /**
     * Activate the scoreBoard button.
     */
    protected void addScoreBoardButtonListener(){
        Button scoreboardButton = findViewById(R.id.ScoreBoardButton);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaderBoardActivity.GameName = "mmsliding";
                startActivity(new Intent(StartingActivity.this, LeaderBoardActivity.class));
            }
        });
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    protected void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    protected void loadFromFile(String fileName) {
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

    /**
     * Make toast based on the input string.
     * @param msg
     */
    public void makeToastText(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        View view = toast.getView();
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 64 );
        shape.setColor(ContextCompat.getColor(this, R.color.colorMagicPrimaryTransparent));
        view.setBackground(shape);
        TextView textview = (TextView) view.findViewById(android.R.id.message);
        Typeface font = Typeface.createFromAsset(this.getAssets(),
                "fonts/Audiowide-Regular.ttf");
        textview.setTypeface(font);
        textview.setTextSize(18);
        textview.setGravity(Gravity.CENTER);
        textview.setTextColor(ContextCompat.getColor(this, R.color.colorMagicAccent));
        toast.show();
    }

}
