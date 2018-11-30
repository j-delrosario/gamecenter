package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
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
import fall2018.csc2017.slidingtiles.twozerofoureight.GameActivity2048;

/**
 * Starting Activity for Sliding Tiles.
 */
public class StartingActivityST extends AppCompatActivity implements Runnable{

    /**
     * Current user's email
     */
    public static String emailFileName = "";

    /**
     * The main save file.
     */
    public static String SAVE_FILENAME =  "SAVE_FILE.ser";
    /**
     * A temporary save file.
     */
    public static String TEMP_SAVE_FILENAME = "SAVE_FILE_tmp.ser";

    /**
     * The board manager.
     */
    protected BoardManagerST boardManager;

    /**
     * The gameName string.
     */
    public static String gameName;

    /**
     * The boolean represents if a file is loadable.
     */
    public static boolean loadable = false;

    /**
     * set loadble to true.
     */
    public static void setBool(){
        loadable = true;
    }

    /**
     * Update the file names
     */
    public void updateFileNames(String name){
        SAVE_FILENAME =  emailFileName + "_ST.ser";
        TEMP_SAVE_FILENAME = emailFileName + "_ST_tmp.ser";
    }

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
                    updateGameSettings();
                    loadFromFile(SAVE_FILENAME);
                    boardManager.initial_1 = boardManager.getBoard();
                    saveToFile(TEMP_SAVE_FILENAME);
                    if (boardManager.getNumRow() != SizeRow ){
                        makeToastText("Save file has board size of " + String.valueOf(boardManager.getNumRow()) + ", does not match the current size " + String.valueOf(SizeRow));
                    }
                    else if (isDefaultImage() && boardManager.getIsImage() && !GameActivity.hasBackground()) {
                        makeToastText("Image is not loaded");
                    }
                    else if (!isDefaultImage() && boardManager.getIsImage()){
                        makeToastText("Save file has image background, please uncheck use default background in settings.");
                    }
                    else if (isDefaultImage() && ! boardManager.getIsImage()){
                        makeToastText("Save file has default background, please check use default background in settings.");
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
     * Activate the scoreBoard button.
     */
    protected void addScoreBoardButtonListener(){
        Button scoreboardButton = findViewById(R.id.ScoreBoardButton);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaderBoardActivity.GameName = "mmsliding";
                startActivity(new Intent(getApplicationContext(), LeaderBoardActivity.class));
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
        final String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        currentEmail.replace("@", "_");
        currentEmail.replace(".", "_");
        emailFileName = currentEmail;
        updateFileNames(emailFileName);
        saveToFile(this.TEMP_SAVE_FILENAME);
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
     * get if the use default image is selected
     */
    public boolean isDefaultImage(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean use_default_image = prefs.getBoolean("use_default_image", true);
        if (!use_default_image){
            return true;
        }
        else{
            return false;
        }
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
                if (isDefaultImage() && !GameActivity.hasBackground()){
                    makeToastText("Image is not loaded");
                }
                else {
                    loadable = true;
                    updateGameSettings();
                    boardManager.clearStack();
                    boardManager.initial_1 = boardManager.getBoard();
                    GameActivity.clearTimeScore();
                    GameActivity.clearNumMoves();
                    switchToGame();
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
                saveToFile(TEMP_SAVE_FILENAME);
                saveToFile(SAVE_FILENAME);
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
                    saveToFile(SAVE_FILENAME);
                    saveToFile(TEMP_SAVE_FILENAME);
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
                if (boardManager.managerStack.peek().getIsImage()){
                    boardManager.setIsImage(true);
                }
            }
            else {
                if (boardManager.managerStack.thelast != null) {
                    boardManager = new BoardManagerST(boardManager.managerStack.thelast);
                    if (boardManager.managerStack.thelast.getIsImage()){
                        boardManager.setIsImage(true);
                    }
                }
                else {
                    boardManager = new BoardManagerST(boardManager.initial_1);
                    if (boardManager.initial_1.getIsImage()){
                        boardManager.setIsImage(true);
                    }
                }
            }
        }
    }
}

