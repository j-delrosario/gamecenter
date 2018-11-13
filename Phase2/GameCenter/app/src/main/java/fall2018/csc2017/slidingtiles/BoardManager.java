package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The initial state of the Board in BoardManager
     */
    public static Board initial_1;

    /**
     * The number of Undo allowed.
     */
    public static int numUndo = 3;

    /**
     * The stack contains numUndo number of game States
     */
    public static BoardStack managerStack = new BoardStack(numUndo);

    /**
     * Manage a board that has been pre-populated.
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Clear managerStack.
     */
    public void clearStack(){
        managerStack = new BoardStack(numUndo);
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Return True if numUndo is changed.
     * @param num
     * @return changUndo
     */
    public static boolean updateUndo(int num){
        boolean changeUndo = false;
        if (num != numUndo) {
            numUndo = num;
            changeUndo = true;
        }
        return changeUndo;
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager() {

        List<Tile> tiles = new ArrayList<>();
        int numTiles = Board.NUM_COLS * Board.NUM_ROWS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
                // blank tile, file name is tile_25
                if (tileNum == numTiles - 1) {
                    tiles.add(new Tile());
                } else {
                    tiles.add(new Tile(tileNum));
                }
        }
        Collections.shuffle(tiles);
        this.board = new Board(tiles);
    }

    /**
     * Manage a new shuffled board with user chosen image.
     */
    BoardManager(int image_width, int image_height) {

        List<Tile> tiles = new ArrayList<>();
        int tile_width = image_width / Board.NUM_ROWS;
        int tile_height = image_height / Board.NUM_COLS;

        for (int x = 0; x < Board.NUM_ROWS; x++) {
            for (int y = 0; y < Board.NUM_COLS; y++) {
                if (x == Board.NUM_ROWS - 1 && y == Board.NUM_COLS - 1) {
                    // blank tile, file name is tile_25
                    tiles.add(new Tile());
                } else {
                    tiles.add(new Tile(y*Board.NUM_COLS + x + 1,
                                       x*tile_width, y*tile_height, tile_width, tile_height));
                }
            }
        }
        Collections.shuffle(tiles);
        this.board = new Board(tiles);
        initial_1 = board;

    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;
        int prev_tile = 0;
        for (Tile tile : board) {
            solved = solved && (prev_tile < tile.getId());
            prev_tile = tile.getId();
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();
        int blank_row = row, blank_col = col;

        // tiles is the blank tile, swap by calling Board's swap method.
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);

        if (above != null && above.getId() == blankId) { blank_row -= 1; }
        else if (below != null && below.getId() == blankId) { blank_row += 1; }
        else if (left != null && left.getId() == blankId) { blank_col -= 1; }
        else if(right != null && right.getId() == blankId) { blank_col += 1; }

        board.swapTiles(row, col, blank_row, blank_col);
    }

}
