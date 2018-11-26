package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The sliding tiles board.
 */
class Board extends Observable implements Serializable, Iterable<Tile>, Cloneable {
    /**
     * The number of rows.
     */
    public static int NUM_ROWS = 4;

    /**
     * The number of rows.
     */
    public static int NUM_COLS = 4;

    /**
     * The tiles on the board in row-major order.
     */
    protected Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != NUM_ROWS; row++) {
            for (int col = 0; col != NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    @NonNull
    public Iterator<Tile> iterator() {
        Tile[] tiles_reduced = new Tile[NUM_ROWS*NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                int index = i * NUM_ROWS + j;
                tiles_reduced[index] = tiles[i][j];
            }
        }
        List<Tile> tiles_list = Arrays.asList(tiles_reduced);
        return tiles_list.iterator();
    }

    /**
     * Return a new tiles list as a new board's input.
     * @return t
     */
    public List<Tile> getTiles(){
        List<Tile> t = new ArrayList<>();
        for (int row = 0; row != NUM_ROWS; row++) {
            for (int col = 0; col != NUM_COLS; col++) {
                t.add(tiles[row][col]);
            }
        }
        return t;
    }

    /**
     * Return the number of tiles on the board.
     * @return the number of tiles on the board
     */
    int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Return whether the Board size was changed after updating the value
     *
     * @param newSize the new Board size
     * @return whether newSize and the previous Board size were different
     */
    static boolean updateSize(int newSize) {
        boolean changedSize = false;
        if (newSize != Board.NUM_COLS) {
            changedSize = true;
            Board.NUM_ROWS = Board.NUM_COLS = newSize;
        }
        return changedSize;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

}
