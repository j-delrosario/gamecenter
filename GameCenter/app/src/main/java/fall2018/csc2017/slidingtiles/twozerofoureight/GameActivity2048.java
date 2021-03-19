package fall2018.csc2017.slidingtiles.twozerofoureight;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.slidingtiles.LeaderBoardActivity;
import fall2018.csc2017.slidingtiles.R;


/**
 * The Game activity for 2048.
 */
public class GameActivity2048 extends AppCompatActivity {

    /**
     * Current user's email
     */
    public static String emailFileName = "";

    /**
     * Game Score
     */
    public static String CURRENT_SCORE = "save_score.ser";

    /**
     * Game Score
     */
    public static int gameScore = 0;

    /**
     * The main save file.
     */
    public static String SAVE_FILENAME = "save_file_2048.ser";

    /**
     * A GameView2048 for the game view.
     */
    private GameView2048 gameView2048 = new GameView2048(ContextStatic.getAppContext());

    /**
     * A text view represents the current score.
     */
    private TextView currentScore;

    /**
     * A handler used to update the current score.
     */
    private Handler handler = new Handler();

    /**
     * An int represents the size of the stack.
     */
    private static int stackSize = 3;

    /**
     * a bool checks if it is loadable
     */
    private static boolean loadable = false;

    /**
     * A stack for the game states.
     */
    public static TileNumStack stack = new TileNumStack(stackSize);

    /**
     * A stack for the game scores.
     */
    public static ScoreStack scoreStack = new ScoreStack(stackSize);

    /**
     * return the score stack
     */
    public static ScoreStack getScoreStack(){
        return scoreStack;
    }

    /**
     * Returns the stack.
     * @return stack
     */
    public static TileNumStack getStack(){
        return stack;
    }

    /**
     * Update the file names
     */
    public void updateFileNames(String name){
        CURRENT_SCORE =  emailFileName + "_2048_SCORE.ser";
        SAVE_FILENAME =  emailFileName + "_2048.ser";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_o_four_eight_main);
        gameView2048 = findViewById(R.id.gameView2048);
        currentScore = (TextView) findViewById(R.id.score);
        currentScore.setTextSize(10);
        final String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        currentEmail.replace("@", "_");
        currentEmail.replace(".", "_");
        emailFileName = currentEmail;
        updateFileNames(emailFileName);
        File testScore = new File(getApplicationContext().getFilesDir(), CURRENT_SCORE);
        if (testScore.exists()){
            loadScoreFromFile(CURRENT_SCORE);
        }
        else{
            gameScore = 0;
        }
        currentScore.setText(String.valueOf(gameScore));
        gameView2048.setScore(gameScore);
        addUndoButtonListener();
        addNewGameButtonListener();
        addScoreBoardButtonListener();
        addLoadButtonListener();
        File testFile = new File(getApplicationContext().getFilesDir(), SAVE_FILENAME);
        if(testFile.exists()) {
            gameView2048.loadFromFile(SAVE_FILENAME);
            makeToastLoadedText();
        }
        handler.post(new Runnable(){
            @Override
            public void run(){
                showCurrentScore();
                handler.postDelayed(this, 1);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        showCurrentScore();
        //mGLView.onResume();
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    protected void loadScoreFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                int score = (int) input.readObject();
                gameScore = score;
            }
        } catch (FileNotFoundException e) {
            //Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            //Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            //Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public static void saveScoreToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    ContextStatic.getAppContext().openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(gameScore);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameScore = gameView2048.getScore();
        //clearStack();
        //mGLView.onPause();
    }

    /**
     * Show the current score based on the score in the game view.
     */
    public void showCurrentScore(){
        currentScore.setText(String.valueOf(gameView2048.getScore()));
    }

    /**
     * Clears the current stack.
     */
    public static void clearStack(){
        stack = new TileNumStack(3);
    }

    /**
     * An toast displayed when there are no more undo.
     */
    private void makeToastCantUndoText() {
        Toast.makeText(this, "No more Undo", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the load button.
     */
    protected void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.load_game_2048);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File testFile = new File(getApplicationContext().getFilesDir(), SAVE_FILENAME);
                if(testFile.exists() && loadable) {
                    gameView2048.loadFromFile(SAVE_FILENAME);
                    clearStack();
                    makeToastLoadedText();
                }
                else {
                    makeToastCantLoadText();
                }
            }
        });
    }

    /**
     * Set the bool to Unloadable.
     */
    public static void setUnLoadable(){
        loadable = false;
    }

    /**
     * Set the loadable boolean if there are files to load from.
     */
    public static void setLoadable(){
        loadable = true;
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game coudln't get loaded.
     */
    private void makeToastCantLoadText() {
        Toast.makeText(this, "No Load available", Toast.LENGTH_SHORT).show();
    }

    /**
     *  Activate the undo button.
     **/
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.undo_2048);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!stack.isEmpty()) {
                    undo();
                }
                else {
                    makeToastCantUndoText();
                }
            }
        });
    }

    /**
     * A listener for the new game button.
     */
    private void addNewGameButtonListener() {
        Button NewGameButton = findViewById(R.id.new_game_2048);
        NewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView2048.clearCurrentScore();
                gameScore = 0;
                clearStack();
                gameView2048.start();
            }
        });
    }

    /**
     * A listener for the ScoreBoard button.
     */
    private void addScoreBoardButtonListener(){
        Button ScoreBoardButton = findViewById(R.id.scoreboard_2048);
        ScoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaderBoardActivity.GameName = "mm2048";
                startActivity(new Intent(GameActivity2048.this, LeaderBoardActivity.class));
            }
        });
    }

    /**
     * update the scores in view and activity
     */
    public void updateScores(int sc){
        gameScore = sc;
        GameView2048.setScore(sc);
    }

    /**
     * An undo function that allows the game to undo a number of times based on the size of stack.
     */
    public void undo() {
        if (!stack.isEmpty()) {
            stack.pop();
            scoreStack.pop();
            if (!stack.isEmpty()) {
                gameView2048.updateTiles(stack.peek());
                updateScores(scoreStack.peek());
            }
            else {
                if (stack.thelast != null) {
                    gameView2048.updateTiles(stack.thelast);
                    updateScores(scoreStack.thelast);
                }
                else {
                    gameView2048.updateTiles(gameView2048.getTheInitial());
                    updateScores(0);
                }
            }
        }
        gameView2048.saveToFile(SAVE_FILENAME);
        saveScoreToFile(CURRENT_SCORE);
    }

}
