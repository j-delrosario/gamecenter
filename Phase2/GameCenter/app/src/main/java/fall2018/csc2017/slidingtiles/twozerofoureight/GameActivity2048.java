package fall2018.csc2017.slidingtiles.twozerofoureight;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import fall2018.csc2017.slidingtiles.LeaderBoardActivity;
import fall2018.csc2017.slidingtiles.R;


/**
 * The Game activity for 2048.
 */
public class GameActivity2048 extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file_2048.ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_2048_tmp.ser";

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
     * Returns the stack.
     * @return stack
     */
    public static TileNumStack getStack(){
        return stack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_o_four_eight_main);
        gameView2048 = findViewById(R.id.gameView2048);
        currentScore = (TextView) findViewById(R.id.score);
        currentScore.setTextSize(10);
        addUndoButtonListener();
        addNewGameButtonListener();
        addScoreBoardButtonListener();
        addLoadButtonListener();
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
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        clearStack();
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
                if(loadable) {
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
     * An undo function that allows the game to undo a number of times based on the size of stack.
     */
    public void undo() {
        if (!stack.isEmpty()) {
            stack.pop();
            if (!stack.isEmpty()) {
                gameView2048.updateTiles(stack.peek());
            }
            else {
                if (stack.thelast != null) {
                    gameView2048.updateTiles(stack.thelast);
                }
                else {
                    gameView2048.updateTiles(gameView2048.getTheInitial());
                }
            }
        }
    }

}
