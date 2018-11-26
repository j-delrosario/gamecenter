package fall2018.csc2017.slidingtiles.MatchingTiles;

import android.os.Handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class BoardMatch extends Observable implements Serializable {

    private static int NUM_ROW = 4;
    private static int NUM_COL = 4;
    private static int score = 100;
    private TileMatch[][] tiles = new TileMatch[NUM_COL][NUM_ROW];

    public static int getScore() { return score; }

    private int moves = 0;
    private boolean complete;
    private int completedMatches = 0;
    private ArrayList<TileMatch> selectedTiles = new ArrayList<>();

    public BoardMatch(){
        complete = false;
    }

    private int moveCount() { return moves; }
    public boolean isComplete(){return complete;}
    public TileMatch[][] getTiles(){
        return tiles;
    }
    public TileMatch getTile(int row, int col) { return tiles[row][col]; }

    private void move() { moves += 1; }
    private void resetMoves() {
        moves = 0;
    }
    private void updateScore() {score -= 1;}
    public void resetScore() {score = 100;}
    private void clearSelectedTiles() {
        selectedTiles.clear();
    }

    private void addSelectedTile(TileMatch tile) {
        selectedTiles.add(tile);
    }
    public void setTile(TileMatch t, int row, int col) { tiles[row][col] = t; }

    public void refresh(){
        for (int i = 0; i < NUM_ROW; i++){
            for (int j = 0; j < NUM_COL; j++){
                tiles[i][j].refreshBackground();
            }
        }
    }

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

   private void updateMatchingTiles(TileMatch tile1, TileMatch tile2) {
        completedMatches += 2;
        if (completedMatches == (NUM_COL * NUM_ROW)){complete = true;}
    }

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
