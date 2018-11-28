package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.twozerofoureight.GameActivity2048;

/**
 * Starting Activity for Sliding Tiles.
 */
public class StartingActivityST extends StartingActivity implements Runnable{

    /**
     * An int keeps the Size of the row of the current game.
     */
    public static int SizeRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameName = "SlidingTiles";
        Thread thread = new Thread(StartingActivityST.this);
        thread.setDaemon(true);
        thread.start();
        boardManager = new BoardManagerST();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_starting_st);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addSettingsButtonListener();
        addUndoButtonListener();
        addScoreBoardButtonListener();

    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        // load and parse the .xml file associated with SharedPreferences (as soon as possible)
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("size_list", "");
    }

    /**
     * Update the game state with currently selected settings.
     */
    private void updateGameSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String size = prefs.getString("size_list", "");
        if (size == ""){
            SizeRow = 4;
        }
        else{
            SizeRow = Integer.parseInt(size);
        }
        String undo = prefs.getString("undo_list", "");
        boolean use_default_image = prefs.getBoolean("use_default_image", true);
        if (!size.equals("")) {
            boardManager.getBoard().updateSize(Integer.parseInt(size));
        }
        if (!undo.equals("")) {
            BoardManagerST.updateUndo(Integer.parseInt(undo));
        }
        if (GameActivity.hasBackground() && !use_default_image) {
            int[] image_dimensions = GameActivity.getBackgroundDimensions();
            boardManager = new BoardManagerST(image_dimensions[0], image_dimensions[1]);
        } else { boardManager = new BoardManagerST(); }
    }

    /**
     * Activate the start button.
     */
    protected void addStartButtonListener() {
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
    @Override
    protected void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File testFile = new File(getApplicationContext().getFilesDir(), GameActivity2048.SAVE_FILENAME);
                if(testFile.exists()) {
                    updateGameSettings();
                    loadFromFile(SAVE_FILENAME);
                    if (boardManager.getNumRow() != SizeRow ){
                        makeToastText("Save file has board size of " + String.valueOf(boardManager.getNumRow()) + ", does not match the current size " + String.valueOf(SizeRow));
                    }
                    else {
                    boardManager.clearStack();
                    GameActivity.clearTimeScore();
                    GameActivity.clearNumMoves();
                    makeToastText("Loaded Game");
                    switchToGame();
                    }
                }
                else {
                    makeToastText("No Available Save");
                }
            }
        });
    }

    /**
     * Activate the save button.
     */
    protected void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
                saveToFile(StartingActivity.SAVE_FILENAME);
                makeToastText("Saved Game");
            }
        });
    }

    /**
     * Activate the settings button.
     */
    protected void addSettingsButtonListener() {
        Button settingsButton = findViewById(R.id.SettingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartingActivityST.this, SettingsActivity.class));
            }
        });
    }

    /**
     *  Activate the undo button.
     */
    protected void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGameSettings();
                if (!boardManager.managerStack.isEmpty()) {
                    if (boardManager.managerStack.getNumRowInStack() != SizeRow) {
                        boardManager.clearStack();
                    }
                }
                if (!boardManager.managerStack.isEmpty()) {
                    undo();
                    switchToGame();
                }
                else {
                    makeToastText("No More Undo");
                }
            }
        });
    }

    /**
     * An undo function that allows the game to undo a number of times based on the size of stack.
     */
    public void undo() {
        if (!boardManager.managerStack.isEmpty()) {
            boardManager.managerStack.pop();
            if (!boardManager.managerStack.isEmpty()) {
                boardManager = new BoardManagerST(boardManager.managerStack.peek());
            }
            else {
                if (boardManager.managerStack.thelast != null) {
                    boardManager = new BoardManagerST(boardManager.managerStack.thelast);
                }
                else {
                    boardManager = new BoardManagerST(boardManager.initial_1);
                }
            }
        }
    }
}

