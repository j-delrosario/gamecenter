package fall2018.csc2017.slidingtiles.MatchingTiles;

import android.content.Context;
import android.os.Handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Game Board for matching tiles.
 */
public class BoardMatch extends Observable implements Serializable {

    /**
     * A int of row number.
     */
    private static int NUM_ROW = 4;

    /**
     * An int of colume number.
     */
    private static int NUM_COL = 4;

    /**
     * An int of inital game score of 100.
     */
    private static int score = 100;

    /**
     * The default tiles of game board.
     */
    private TileMatch[][] tiles = new TileMatch[NUM_COL][NUM_ROW];

    /**
     * Return the current score.
     * @return int
     */
    public static int getScore() { return score; }

    /**
     * Set the current score based on an input.
     * @param score
     */
    public static void setScore(int score) {BoardMatch.score = score;}

    /**
     * An int keeps moves number.
     */
    private int moves = 0;

    /**
     * A boolean represents if the game is completed.
     */
    private boolean complete;

    /**
     * An int keep the number of completed matches
     */
    private int completedMatches = 0;

    /**
     * An array keep the selected Tiles.
     */
    private ArrayList<TileMatch> selectedTiles = new ArrayList<>();

    /**
     * Initialize a Board for Matching Tiles.
     */
    public BoardMatch(){
        complete = false;
    }

    /**
     * Return the number of moves.
     * @return int
     */
    private int moveCount() { return moves; }

    /**
     * Returns true if the game is completed.
     * @return
     */
    public boolean isComplete(){return complete;}

    /**
     * Return the current tiles.
     * @return tiles
     */
    public TileMatch[][] getTiles(){
        return tiles;
    }

    /**
     * Return a tile based on an Row and Column.
     * @param row
     * @param col
     * @return tile
     */
    public TileMatch getTile(int row, int col) { return tiles[row][col]; }

    /**
     * Set up the number of completed matches.
     * @param completedMatches
     */
    public void setCompletedMatches(int completedMatches) {this.completedMatches = completedMatches;}

    /**
     * Add an move number.
     */
    private void move() { moves += 1; }

    /**
     * Reset the number of moves to 0.
     */
    private void resetMoves() {
        moves = 0;
    }

    /**
     * Substract the score by 1.
     */
    private void updateScore() {score -= 1;}

    /**
     * Reset the current score back to 100.
     */
    public void resetScore() {score = 100;}

    /**
     * clear the selected Tiles.
     */
    private void clearSelectedTiles() {
        selectedTiles.clear();
    }

    /**
     * Add an selected tile.
     * @param tile
     */
    private void addSelectedTile(TileMatch tile) {
        selectedTiles.add(tile);
    }

    /**
     * Set a tile into the board at row/col.
     * @param t
     * @param row
     * @param col
     */
    public void setTile(TileMatch t, int row, int col) { tiles[row][col] = t; }

    /**
     * refresh the backgrounds.
     */
    public void refresh(){
        for (int i = 0; i < NUM_ROW; i++){
            for (int j = 0; j < NUM_COL; j++){
                tiles[i][j].refreshBackground();
                tiles[i][j].refreshBackgroundColor();            }
        }
    }

    /**
     * Refresh the tiles with delay of 0.5 sec.
     */
    private void refreshWithDelay() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        };
        handler.postDelayed(runnable, 500);
    }

    /**
     * update the tiles.
     * @param tile1
     * @param tile2
     */
   private void updateMatchingTiles(TileMatch tile1, TileMatch tile2) {
        completedMatches += 2;
        if (completedMatches == (NUM_COL * NUM_ROW)){complete = true;}
    }

    /**
     * updated selected tiles based on a board of Matching tiles.
     * @param tile
     */
    public void updateSelectedTiles(TileMatch tile) {
        addSelectedTile(tile);
        move();
        if (moveCount() == 2) {
            if (!isMatching()) {
                updateScore();
            }
            resetMoves();
        }
    }

    /**
     * check if 2 boards of tiles are matching.
     * @return
     */
    private boolean isMatching() {
        boolean isMatch;
        TileMatch tile1 = selectedTiles.get(0);
        TileMatch tile2 = selectedTiles.get(1);
        refresh();

        if (tile1.equals(tile2) && tile1 != tile2) {
            tile1.setIsMatched(true);
            tile2.setIsMatched(true);
            refreshWithDelay();
            updateMatchingTiles(tile1, tile2);
            tile1.setVisible();
            tile2.setVisible();
            isMatch = true;
        } else {
            tile1.setVisible();
            tile2.setVisible();
            refreshWithDelay();
            isMatch = false;
        }

        clearSelectedTiles();
        return isMatch;
    }

    /**
     * returns a triple list of board values.
     * @return saveValue
     */
    public int[][][] boardSaveValue() {
        int[][][] saveValue = new int[NUM_ROW][NUM_COL][4];
        saveValue[0][0][3] = completedMatches;
        saveValue[0][1][3] = score;
        for (int i = 0; i < NUM_ROW; i++){
            for (int j = 0; j < NUM_COL; j++){
                saveValue[i][j][0] = tiles[i][j].getbackground();
                saveValue[i][j][1] = tiles[i][j].getVisibleBackground();
                saveValue[i][j][2] = (tiles[i][j].getIsMatched() == true) ? 1 : 0;
            }
        }
        return saveValue;
    }

    /**
     * Return a board based on a save.
     * @param context
     * @param save
     * @return
     */
    public BoardMatch boardFromSave(Context context, int[][][] save) {
        BoardMatch board = new BoardMatch();
        int addScore = 0;
        for (int row = 0; row < NUM_ROW; row++){
            for (int col = 0; col < NUM_COL; col++){
                TileMatch t = new TileMatch(context);
                if (save[row][col][0] == 0) {
                    t.setbackground(save[row][col][1]);
                    addScore = -1;
                } else { t.setbackground(save[row][col][0]);}
                t.setBackground(0);
                t.setIsMatched(save[row][col][2] == 1);
                board.setTile(t, row, col);
            }
        }
        board.setCompletedMatches(save[0][0][3]);
        BoardMatch.setScore(save[0][1][3] + addScore);
        return board;
    }

    /**
     * String representation of board.
     * @return string.
     */
    public String toString() {
        String w = "";
        StringBuilder b = new StringBuilder(w);
        for (TileMatch[] t: tiles) {
            for (TileMatch s: t) {
                b.append(s.toString());
            }
        }
        return b.toString();
    }
}
