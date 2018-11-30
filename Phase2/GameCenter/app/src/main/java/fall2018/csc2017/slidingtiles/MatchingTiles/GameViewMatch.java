package fall2018.csc2017.slidingtiles.MatchingTiles;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SlidingTiles.GameActivity;


import static android.content.Context.MODE_PRIVATE;

/**
 * View for the game matching tile.
 */
public class GameViewMatch extends GridLayout {

    /**
     * An int represents number of rows.
     */
    private static int NUM_ROW = 4;

    /**
     * An int represents number of rows.
     */
    private static int NUM_COL = 4;

    /**
     * A firebase linked to the database.
     */
    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    /**
     * The board of the game view.
     */
    private BoardMatch board = new BoardMatch();

    /**
     * Initialize A game view for matching tiles.
     * @param context
     * @param attr
     * @param def
     */
    public GameViewMatch(Context context, AttributeSet attr, int def) {
        super(context, attr, def);
        createGameView();
    }

    /**
     * Initialize A game view for matching tiles.
     * @param context
     * @param attr
     */
    public GameViewMatch(Context context, AttributeSet attr) {
        super(context, attr);
        createGameView();
    }

    /**
     * Initialize A game view for matching tiles.
     * @param context
     */
    public GameViewMatch(Context context) {
        super(context);
        createGameView();
    }

    /**
     * Create a game view.
     */
    private void createGameView() {
        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent touch) {
                if(touch.getAction() == MotionEvent.ACTION_UP && !board.isComplete()) {
                    tap((int)touch.getX(),(int)touch.getY());
                    GameActivityMatch.showCurrentScore();
                }
                return true;
            }
        });
        this.setBackgroundColor(0xff959595);
        this.setColumnCount(4);
        start();
    }

    /**
     * Returns the current board.
     * @return board
     */
    public BoardMatch getBoard(){return board;}

    /**
     * Defines what happens during a tap.
     * @param x
     * @param y
     */
    private void tap(int x, int y) {
        int row = y / 220;
        int col = x / 220;
        if (row < NUM_ROW && col < NUM_COL) {
            TileMatch tile = board.getTile(row, col);
            if (tile.setVisible()) {
                tile.refreshBackground();
                board.updateSelectedTiles(tile);
                if (board.isComplete()) {
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Integer value = dataSnapshot.child(currentUid).child("mmmatching").getValue(Integer.class);
                            if (value == null){
                                value = 0;
                            }
                            if (value < BoardMatch.getScore()){
                                mRef.child(currentUid).child("mmmatching").setValue(BoardMatch.getScore());
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    endAnimation();
                    makeToastCompletedText();
                }
            }
            save(GameActivityMatch.SAVE_FILENAME);
        }
    }

    /**
     * Start the game and update the boards.
     */
    private void start() {
        for (int row = 0; row< NUM_ROW; row++) {
            for (int col = 0; col < NUM_COL; col++) {
                TileMatch t = new TileMatch(getContext());
                board.setTile(t, row, col);
            }
        }
        generateRamBackground();
    }

    /**
     * Save the game to a local file.
     * @param fileName
     */
    public void save(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getContext().openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(board.boardSaveValue());
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load a game from a file.
     * @param fileName
     * @return
     */
    public boolean load(String fileName) {
        try {
            InputStream inputStream = getContext().openFileInput(fileName);
            Log.i("ROFL", "rrra");
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                int[][][] save = (int[][][]) input.readObject();
                board = board.boardFromSave(getContext(), save);
                inputStream.close();
                return true;
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return false;
    }

    /**
     * Generate a ramdom Background.
     */
    private void generateRamBackground() {
        ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
        ArrayList<Integer> visibleNumbers = new ArrayList<Integer>();
        int x1;
        int y1;
        for (int i = 0; i < 16; i++) {
            randomNumbers.add(new Integer(i));
        }
        Collections.shuffle(randomNumbers);
        for (int tileNum = 0; tileNum != 8; tileNum++) {
            visibleNumbers.add(tileNum + 1);
            visibleNumbers.add(tileNum + 1);
        }
        for (int i = 0; i < 16; i++) {
            x1 = randomNumbers.get(i) % 4;
            y1 = randomNumbers.get(i) / 4;
            TileMatch t = board.getTile(x1, y1);
            t.setBackground(visibleNumbers.get(i));
            t.setVisible();
            t.refreshBackground();
        }
        board.resetScore();
    }

    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);
        int width = (Math.min(c.getWidth(), c.getHeight())) / 4;
        removeAllViews();
        pushTiles(width, width);
    }

    /**
     * Push tiles to the view.
     * @param width
     * @param height
     */
    private void pushTiles(int width, int height) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (((ViewGroup)board.getTile(row,col)).getParent() != null){
                    ((ViewGroup)board.getTile(row,col).getParent()).removeView(board.getTile(row,col));
                }
                addView(board.getTile(row,col), width, height);
            }
        }
    }

    /**
     * An handler for end game animation.
     */
    private Handler endAnimationHandler = new Handler();

    /**
     * Defines the end game animation.
     */
    private void endAnimation(){
        final ArrayList<int[]> animateOrder = new ArrayList<>();
        for (int col = 0; col < NUM_COL; col++) {
            animateOrder.add(new int[]{0, col});
        }
        for (int col = NUM_COL - 1; col >= 0; col--) {
            animateOrder.add(new int[]{1, col});
        }
        for (int col = 0; col < NUM_COL; col++) {
            animateOrder.add(new int[]{2, col});
        }
        for (int col = NUM_COL - 1; col >= 0; col--) {
            animateOrder.add(new int[]{3, col});
        }
        final Runnable endAnimationRunnable = new Runnable(){
            int[] posToDelete = new int[]{0,0};
            int[] pos = new int[]{0,0};
            int[] pos2 = new int[]{0,0};
            int[] pos3 = new int[]{0,0};
            int[] pos4 = new int[]{0,0};
            int[] safe = new int[]{0,0};
            int moveNext = 0;
            public void run() {
                try {
                    endAnimationHandler.postDelayed(this, 200);
                    if (!(posToDelete[0] == 3 && posToDelete[1] == 0) && moveNext < 6) {
                        if (!(pos[0] % 2 == 0 && pos[1] == 3) && !(pos[0] % 2 == 1 && pos[1] == 0)) {
                            pos = animateOrder.get(0);
                            animateOrder.remove(0);
                        } else if (pos[0] % 2 == 0 && pos[1] == 3) {
                            safe = new int[]{posToDelete[0] + 1,3};
                            moveNext += 1;
                        } else if (pos[0] % 2 == 1 && pos[1] == 0) {
                            safe = new int[]{posToDelete[0] + 1,0};
                            moveNext += 1;
                        }
                        if (moveNext < 5) {
                            board.getTile(posToDelete[0], posToDelete[1]).updateBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDarkMT));
                        }
                        if (moveNext < 4){
                            board.getTile(pos4[0], pos4[1]).updateBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary4));
                        }
                        if (moveNext < 3) {
                            board.getTile(pos3[0], pos3[1]).updateBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary3));
                        }
                        if (moveNext < 2) {
                            board.getTile(pos2[0], pos2[1]).updateBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary2));
                        }
                        if (moveNext < 1) {
                            board.getTile(pos[0], pos[1]).updateBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                        }
                        if (moveNext == 4) {
                            pos = pos2 = pos3 = pos4 = safe;
                            moveNext = 0;
                        }
                        posToDelete = pos4;
                        pos4 = pos3;
                        pos3 = pos2;
                        pos2 = pos;
                    } else {
                        board.getTile(posToDelete[0], posToDelete[1]).updateBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDarkMT));
                        endAnimationHandler.removeCallbacks(this);
                        Activity a = (Activity)getContext();
                        View v = ((GameActivityMatch)a).findViewById(R.id.gameViewMatch);
                        if (((GameViewMatch)v).getBoard().isComplete()) {
                            makeToastCorrectnessText();
                        }
                    }
                } catch(ClassCastException e) { e.printStackTrace();}
            }
        };

        endAnimationHandler.postDelayed(endAnimationRunnable, 1000);
    }

    /**
     * A toast when the game is completed.
     */
    private void makeToastCompletedText() {
        Toast toast = Toast.makeText(getContext(), "Game Completed", Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        TextView textview = (TextView) view.findViewById(android.R.id.message);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Audiowide-Regular.ttf");
        textview.setTypeface(font);
        textview.setTextSize(12);
        textview.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDarkMT));
        toast.show();
    }

    /**
     * A toast for the correctness text.
     */
    private void makeToastCorrectnessText() {
        Toast toast = Toast.makeText(getContext(), "MATCHED" + "\n in " + Integer.toString(8 + 100 - BoardMatch.getScore()) + " moves", Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        TextView textview = (TextView) view.findViewById(android.R.id.message);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Audiowide-Regular.ttf");
        textview.setTypeface(font);
        textview.setTextSize(36);
        textview.setGravity(Gravity.CENTER);
        textview.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDarkMT));
        toast.show();
    }
}