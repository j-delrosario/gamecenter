package fall2018.csc2017.slidingtiles.twozerofoureight;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import fall2018.csc2017.slidingtiles.GameCentre.GameLaunchCentreActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * A gridlayout for the 2048 Game.
 */
public class GameView2048 extends GridLayout{

    /**
     * An int keeping the width for each tile view.
     */
    private int Width;

    /**
     * A boolean for checking if any move was made.
     */
    private boolean isMoved = false;

    /**
     * An int keeping the current user of the player.
     */
    private int score = 0;

    /**
     * An int represents the number of row.
     */
    private int NUM_ROW = 4;

    /**
     * An int represents the number of colume.
     */
    private int NUM_COL = 4;

    /**
     * A nested list keeping the current state of the game.
     */
    private Tile2048[][] tiles = new Tile2048[NUM_COL][NUM_ROW];

    /**
     * A nested list keeping the buffer game state in order to check if the game
     * has changed.
     */
    private Tile2048[][] bufferTiles = new Tile2048[NUM_COL][NUM_ROW];

    /**
     * A list of integers represents the inital game state.
     */
    public int[] theInitial = new int[NUM_COL * NUM_ROW];

    /**
     * An database store the scores.
     */
    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child(GameLaunchCentreActivity.Email);

    /**
     * Returns the current score of the game.
     * @return score
     */
    public int getScore(){
        return score;
    }

    /**
     * Set the score to int i.
     * @param i
     */
    public void setScore(int i){
        int newInt = new Integer(i);
        score = newInt;
    }

    /**
     * Clear the current score to 0.
     */
    public void clearCurrentScore(){
        score = 0;
    }

    /**
     * Initialize a GameView2048.
     */
    public GameView2048(Context context, AttributeSet attr, int def) {
        super(context, attr, def);
        createGameView();
    }

    /**
     * Initialize a GameView2048.
     */
    public GameView2048(Context context, AttributeSet attr) {
        super(context, attr);
        createGameView();
    }

    /**
     * Initialize a GameView2048.
     */
    public GameView2048(Context context) {
        super(context);
        createGameView();
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    protected void loadFromFile(String fileName) {
        try {
            InputStream inputStream = getContext().openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                int[] intBoard = (int[]) input.readObject();
                updateTiles(intBoard);
                updateBufferTiles(intBoard);
                clearTheInitial();
                setTheInitial(tilesToString(tiles));
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
                    getContext().openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(tilesToString(tiles));
            outputStream.close();
            GameActivity2048.setLoadable();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Creates the GameView, sets up the onTouchListener and initializes the
     * tiles.
     */
    public void createGameView() {
        setOnTouchListener(new View.OnTouchListener() {

            private float x1;
            private float x2;
            private float y1;
            private float y2;

            @Override
            public boolean onTouch(View view, MotionEvent direction) {
                switch (direction.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = direction.getX();
                        y1 = direction.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = direction.getX() - x1;
                        y2 = direction.getY() - y1;

                        if (Math.abs(x2) > Math.abs(y2)) {
                            if (x2 < -3) {
                                leftSwipe();
                                if (isOver()){
                                    ReadWriteMark();
                                    gameOverListener();
                                }else if (isWon()){
                                    ReadWriteMark();
                                    gameWonListener();
                                }


                            } else if (x2 > 3) {
                                rightSwipe();
                                if (isOver()){
                                    ReadWriteMark();
                                    gameOverListener();
                                }
                                else if (isWon()){
                                    ReadWriteMark();
                                    gameWonListener();
                                }

                            }
                        } else {
                            if (y2 < -3) {
                                upSwipe();
                                if (isOver()) {
                                    ReadWriteMark();
                                    gameOverListener();
                                }
                                else if (isWon()){
                                    ReadWriteMark();
                                    gameWonListener();
                                }
                            } else if (y2 > 3) {
                                downSwipe();
                                if (isOver()) {
                                    ReadWriteMark();
                                    gameOverListener();
                                }
                                else if (isWon()){
                                    ReadWriteMark();
                                    gameWonListener();
                                }
                            }
                        }
                        break;
                }
                return true;
            }
        });
        this.setColumnCount(4);
        start();
    }

    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);
        int width = (Math.min(c.getWidth(), c.getHeight())) / 4;
        removeAllViews();
        pushTiles(width, width);
    }

    /**
     * Add views based on the current tiles
     * @param width
     * @param height
     */
    public void pushTiles(int width, int height) {
        this.Width = width;
        for (int row = 0; row< 4; row++) {
            for (int col = 0; col < 4; col++) {
                addView(tiles[row][col], width, height);
            }
        }
    }
    /**
     * Generate 2 random tiles with 2 as background
     * Called in the creation of the game.
     */
    public void generateRamBackground() {
        ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
        ArrayList<Integer> emptyTiles = getEmptyTiles();
        int x1 = -1;
        int y1 = -1;
        for (int i = 0; i < 16; i++) {
            randomNumbers.add(new Integer(i));
        }
        Collections.shuffle(randomNumbers);
        for (int i = 0; i < 16; i++) {
            if (emptyTiles.contains(randomNumbers.get(i))){
                x1 = randomNumbers.get(i) % 4;
                y1 = randomNumbers.get(i) / 4;
            }
        }
        if (x1 >= 0 && y1 >= 0){
            Tile2048 t = new Tile2048(getContext(), 2);
            tiles[y1][x1] = t;
        }
    }

    /**
     * Returns true if the tiles is changed, false if not.
     * @return boolean
     */
    private boolean isChanged(){
        for (int row = 0; row< 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (tiles[row][col].getbackground() != bufferTiles[row][col].getbackground()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Starts the game by making all tiles as 0,
     * Generates two tiles with 2
     * Sets the inital state of the game.
     */
    public void start() {
        clearCurrentScore();
        for (int row = 0; row< 4; row++) {
            for (int col = 0; col < 4; col++) {
                Tile2048 t = new Tile2048(ContextStatic.getAppContext(), 0);
                tiles[row][col] = t;

            }
        }
        generateRamBackground();
        generateRamBackground();
        for (int row = 0; row< 4; row++) {
            for (int col = 0; col < 4; col++) {
                Tile2048 t1 = new Tile2048(ContextStatic.getAppContext(), 0);
                bufferTiles[row][col] = t1;

            }
        }
        clearTheInitial();
        setTheInitial(tilesToString(tiles));
    }

    /**
     * Clears the inital game state int list.
     */
    public void clearTheInitial(){
        theInitial = new int[16];
    }

    /**
     * Sets the inital game state int list.
     * @param intList
     */
    public void setTheInitial(int[] intList){
        for (int i=0; i<intList.length; i++) {
            theInitial[i] = new Integer(intList[i]);
        }
    }

    /**
     * Returns the inital game state int list.
     * @return theInitial
     */
    public int[] getTheInitial() {
        return theInitial;
    }

    /**
     * Returns a flat list of int representing tiles backgrounds from a nested list of tiles.
     * @param t
     * @return s
     */
    public int[] tilesToString(Tile2048[][] t) {
        int[] s = new int[16];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Integer q = new Integer(t[row][col].getbackground());
                s[row*4+col] = q;
            }
        }
        return s;
    }

    /**
     * Update the current state of the game based on a flat list of integers.
     * @param tileNum
     */
    public void updateTiles(int[] tileNum) {
        Integer newBackground;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                newBackground = new Integer(tileNum[row * 4 + col]);
                tiles[row][col] = new Tile2048(getContext(), newBackground);
            }
        }
    }

    /**
     * Update the bufferTiles of the game based on a flat list of integers.
     * @param tileNum
     */
    public void updateBufferTiles(int[] tileNum) {
        Integer newBackground;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                newBackground = new Integer(tileNum[row * 4 + col]);
                bufferTiles[row][col] = new Tile2048(getContext(), newBackground);
            }
        }
    }

    /**
     * Pushes a list of int represents current game state to the stack.
     */
    public void pushBoards(){
        int[] t = new int[NUM_ROW * NUM_COL];
        Integer i;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                i = new Integer(tiles[row][col].getbackground());
                t[row * 4 + col] = i;
            }
        }
        if (isChanged()){
            GameActivity2048.getStack().push(t);
        }
    }

    /**
     * Add sc to the current score.
     * @param sc
     */
    public void addScore(int sc){
        setScore(getScore() + sc);
    }


    /**
     * genereate a new 2 tile if moved.
     */
    private void generateIfMoved (){
        if (isMoved){
            generateRamBackground();
        }
        isMoved = false;
    }

    /**
     * Update the current state of game when a left swipe occurs.
     * pushes the current game state to the stack in the end.
     */
    public void leftSwipe() {

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Tile2048 t = new Tile2048(getContext(), tiles[row][col].getbackground());
                bufferTiles[row][col] = t;
            }
        }
        for (int row = 0; row < 4; row++) {
            for (int col= 0; col < 4; col++) {
                for (int y = col + 1; y < 4; y++) {
                    if (tiles[row][y].getbackground() > 0) {
                        if (tiles[row][col].getbackground() == 0) {
                            tiles[row][col] = new Tile2048(getContext(), tiles[row][y].getbackground());
                            tiles[row][y] = new Tile2048(getContext(), 0);
                            isMoved = true;
                            y--;
                        } else if (tiles[row][col].equals(tiles[row][y])) {
                            tiles[row][col] = new Tile2048(getContext(), tiles[row][col].getbackground()*2);
                            tiles[row][y] = new Tile2048(getContext(), 0);
                            isMoved = true;
                            addScore(tiles[row][col].getbackground());
                        }
                        break;
                    }
                }
            }
        }
        generateIfMoved();
        saveToFile(GameActivity2048.SAVE_FILENAME);
        pushBoards();
    }

    /**
     * Update the current state of game when a right swipe occurs.
     * pushes the current game state to the stack in the end.
     */
    public void rightSwipe() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Tile2048 t = new Tile2048(getContext(), tiles[row][col].getbackground());
                bufferTiles[row][col] = t;
            }
        }
        for (int row = 0; row < 4; row++) {
            for (int col = 3; col >= 0; col--) {
                for (int y = col - 1; y >= 0; y--) {
                    if (tiles[row][y].getbackground() > 0) {
                        if (tiles[row][col].getbackground() == 0) {
                            tiles[row][col] = new Tile2048(getContext(), tiles[row][y].getbackground());
                            tiles[row][y] = new Tile2048(getContext(), 0);
                            y++;
                            isMoved = true;
                        } else if (tiles[row][col].equals(tiles[row][y])) {
                            tiles[row][col] = new Tile2048(getContext(), tiles[row][col].getbackground()*2);
                            tiles[row][y] = new Tile2048(getContext(), 0);
                            isMoved = true;
                            addScore(tiles[row][col].getbackground());
                        }
                        break;
                    }
                }
            }
        }
        generateIfMoved();
        saveToFile(GameActivity2048.SAVE_FILENAME);
        pushBoards();
    }

    /**
     * Update the current state of game when a up swipe occurs.
     * pushes the current game state to the stack in the end.
     */
    private void upSwipe() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Tile2048 t = new Tile2048(getContext(), tiles[row][col].getbackground());
                bufferTiles[row][col] = t;
            }
        }
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                for (int x = row + 1; x < 4; x++) {
                    if (tiles[x][col].getbackground() > 0) {
                        if (tiles[row][col].getbackground() <= 0) {
                            tiles[row][col] = new Tile2048(getContext(), tiles[x][col].getbackground());
                            tiles[x][col] = new Tile2048(getContext(), 0);
                            x--;
                            isMoved = true;
                        } else if (tiles[row][col].equals(tiles[x][col])) {
                            tiles[row][col] = new Tile2048(getContext(), tiles[row][col].getbackground() * 2);
                            tiles[x][col] = new Tile2048(getContext(), 0);
                            isMoved = true;
                            addScore(tiles[row][col].getbackground());
                        }
                        break;
                    }
                }
            }
        }
        generateIfMoved();
        saveToFile(GameActivity2048.SAVE_FILENAME);
        pushBoards();
    }

    /**
     * Update the current state of game when a down swipe occurs.
     * pushes the current game state to the stack in the end.
     */
    private void downSwipe() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Tile2048 t = new Tile2048(getContext(), tiles[row][col].getbackground());
                bufferTiles[row][col] = t;
            }
        }
        for (int col = 0; col < 4; col++) {
            for (int row = 3; row >= 0; row--) {
                for (int x = row - 1; x >= 0; x--) {
                    if (tiles[x][col].getbackground() > 0) {
                        if (tiles[row][col].getbackground() == 0) {
                            tiles[row][col] = new Tile2048(getContext(), tiles[x][col].getbackground());
                            tiles[x][col] = new Tile2048(getContext(), 0);
                            x--;
                            isMoved = true;
                        } else if (tiles[row][col].equals(tiles[x][col])) {
                            tiles[row][col] = new Tile2048(getContext(), tiles[row][col].getbackground() * 2);
                            tiles[x][col] = new Tile2048(getContext(), 0);
                            isMoved = true;
                            addScore(tiles[row][col].getbackground());
                        }
                        break;
                    }
                }
            }
        }
        generateIfMoved();
        saveToFile(GameActivity2048.SAVE_FILENAME);
        pushBoards();
    }

    /**
     * Returns an Array of integer represents the positions of the empty tiles.
     * @return li
     */
    public ArrayList<Integer> getEmptyTiles() {

        ArrayList<Integer> li = new ArrayList<Integer>();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (tiles[row][col].getbackground() == 0) {
                    li.add(row * 4 + col);
                }
            }
        }
        return li;
    }

    /**
     * A listener which shows a message when the game is over, then lets
     * the user restart the game.
     */
    private void gameOverListener(){

        new AlertDialog.Builder(getContext()).setTitle(("Sorry")).setMessage("Game Over").setPositiveButton("Restart", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface d, int i ){
                GameActivity2048.clearStack();
                createGameView();
            }
        }).show();
    }

    /**
     * A listener which shows a message when the game is won, then lets
     * the user restart the game.
     */
    private void gameWonListener(){
        new AlertDialog.Builder(getContext()).setTitle(("Congratulations")).setMessage("You Won. Your Score is" + score).setPositiveButton("Restart", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface d, int i ){
                GameActivity2048.clearStack();
                createGameView();
            }
        }).show();
    }

    /**
     * Return true if the game is won, false if not.
     * @return boolean
     */
    public boolean isWon() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (tiles[row][col].getbackground() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return true if the game is over, false if not.
     * @return boolean
     */
    public boolean isOver() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if ( (tiles[row][col].getbackground() == 0) ||
                        (row>0 && tiles[row][col].equals(tiles[row-1][col])) ||
                        (row<3 && tiles[row][col].equals(tiles[row+1][col])) ||
                        (col>0&&tiles[row][col].equals(tiles[row][col-1])) ||
                        (col<3&&tiles[row][col].equals(tiles[row][col+1]))
                        ) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * rend / write the game score
     */
    private void ReadWriteMark(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.child("mm2048").getValue(Integer.class);

                if (value < score){
                    mRef.child("mm2048").setValue(score);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}