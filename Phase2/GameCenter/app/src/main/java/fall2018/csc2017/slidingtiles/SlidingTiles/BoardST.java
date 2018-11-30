package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * Board for Sliding Tiles.
 */
public class BoardST extends Observable implements Serializable, Iterable<TileST>, Cloneable {

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
    protected TileST[][] tiles = new TileST[NUM_ROWS][NUM_COLS];

    /**
     * A boolean shows if the board is image.
     */
    private boolean isImage = false;

    /**
     *  set isImage boolean.
     * @param b
     */
    public void setIsImage(boolean b){
        isImage = b;
    }

    /**
     *  get isImage boolean.
     */
    public boolean getIsImage(){
        return isImage;
    }

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public BoardST(List<TileST> tiles) {
        Iterator<TileST> iter = tiles.iterator();

        for (int row = 0; row != NUM_ROWS; row++) {
            for (int col = 0; col != NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    @NonNull
    public Iterator<TileST> iterator() {
        TileST[] tiles_reduced = new TileST[NUM_ROWS*NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                int index = i * NUM_ROWS + j;
                tiles_reduced[index] = tiles[i][j];
            }
        }
        List<TileST> tiles_list = Arrays.asList(tiles_reduced);
        return tiles_list.iterator();
    }

    /**
     * Return a new tiles list as a new board's input.
     * @return t
     */
    public List<TileST> getTiles(){
        List<TileST> t = new ArrayList<>();
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
    public TileST getTile(int row, int col) {
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
        if (newSize != BoardST.NUM_COLS) {
            changedSize = true;
            BoardST.NUM_ROWS = BoardST.NUM_COLS = newSize;
        }
        return changedSize;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    public void swapTiles(int row1, int col1, int row2, int col2) {
        TileST temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;

        setChanged();
        notifyObservers();
    }

}
