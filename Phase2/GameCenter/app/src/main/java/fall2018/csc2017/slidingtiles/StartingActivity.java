package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Process;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity implements Runnable{

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
    private BoardManager boardManager;

    /**
     * The gameName string.
     */
    public static String gameName;

    public static boolean loadable = false;

    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        // load and parse the .xml file associated with SharedPreferences (as soon as possible)
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("size_list", "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        gameName = "SlidingTiles";
        Thread thread = new Thread(StartingActivity.this);
        thread.setDaemon(true);
        thread.start();

        super.onCreate(savedInstanceState);
        boardManager = new BoardManager();
        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_starting_);

        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addSettingsButtonListener();
        addUndoButtonListener();
        addScoreBoardButtonListener();


    }

    /**
     * set loadble to true.
     */
    public static void setBool(){
        loadable = true;
    }
    /**
     * Update the game state with currently selected settings.
     */
    private void updateGameSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String size = prefs.getString("size_list", "");
        String undo = prefs.getString("undo_list", "");
        boolean use_default_image = prefs.getBoolean("use_default_image", true);

        if (!size.equals("")) {
            Board.updateSize(Integer.parseInt(size));
        }

        if (!undo.equals("")) {
            BoardManager.updateUndo(Integer.parseInt(undo));
        }

        if (GameActivity.hasBackground() && !use_default_image) {
            int[] image_dimensions = GameActivity.getBackgroundDimensions();
            boardManager = new BoardManager(image_dimensions[0], image_dimensions[1]);
        } else { boardManager = new BoardManager(); }
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadable = true;
                updateGameSettings();
                boardManager.clearStack();
                boardManager.initial_1 = boardManager.getBoard();
                GameActivity.clearTimeScore();
                GameActivity.clearNumMoves();
                switchToGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loadable) {
                    loadFromFile(SAVE_FILENAME);
                    boardManager.clearStack();
                    GameActivity.clearTimeScore();
                    GameActivity.clearNumMoves();
                    makeToastLoadedText();
                    switchToGame();
                }
                else {
                    makeToastCantLoadText();
                }
            }
        });
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
     * Display that a game couldn't load.
     */
    private void makeToastCantUndoText() {
        Toast.makeText(this, "No more Undo", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
            });
    }

    /**
     *  Activate the undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boardManager.managerStack.isEmpty()) {
                    undo();
                    switchToGame();
                }
                else {
                    makeToastCantUndoText();
                }
            }
        });
    }
    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the settings button.
     */
    private void addSettingsButtonListener() {
        Button settingsButton = findViewById(R.id.SettingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartingActivity.this, SettingsActivity.class));
            }
        });
    }

    /**
     * Activate the scoreBoard button.
     */
    private void addScoreBoardButtonListener(){
        Button scoreboardButton = findViewById(R.id.ScoreBoardButton);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartingActivity.this, ScoreBoardActivity.class));
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
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
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

    /**
     * An undo function that allows the game to undo a number of times based on the size of stack.
     */
    public void undo() {
        if (!boardManager.managerStack.isEmpty()) {
            boardManager.managerStack.pop();
            if (!boardManager.managerStack.isEmpty()) {
                boardManager = new BoardManager(boardManager.managerStack.peek());
            }
            else {
                if (boardManager.managerStack.thelast != null) {
                    boardManager = new BoardManager(boardManager.managerStack.thelast);
                }
                else {
                    boardManager = new BoardManager(boardManager.initial_1);
                }
            }
        }
    }

}
