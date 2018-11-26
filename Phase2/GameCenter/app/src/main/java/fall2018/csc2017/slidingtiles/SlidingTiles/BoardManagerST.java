package fall2018.csc2017.slidingtiles.SlidingTiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardManagerST extends BoardManager {
    /**
     * Manage a board that has been pre-populated.
     * @param board the board
     */
    BoardManagerST(Board board) {
        super(board);
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManagerST() {

        List<Tile> tiles = new ArrayList<>();
        int numTiles = Board.NUM_COLS * Board.NUM_ROWS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            // blank tile, file name is tile_25
            if (tileNum == numTiles - 1) {
                tiles.add(new TileST());
            } else {
                tiles.add(new TileST(tileNum));
            }
        }
        Collections.shuffle(tiles);
        this.board = new BoardST(tiles);
        while (!isSolvable(tiles, Board.NUM_COLS)) {
            Collections.shuffle(tiles);
        }
        this.board = new BoardST(tiles);
    }

    /**
     * Manage a new shuffled board with user chosen image.
     */
    BoardManagerST(int image_width, int image_height) {

        List<Tile> tiles = new ArrayList<>();
        int tile_width = image_width / Board.NUM_ROWS;
        int tile_height = image_height / Board.NUM_COLS;

        for (int x = 0; x < Board.NUM_ROWS; x++) {
            for (int y = 0; y < Board.NUM_COLS; y++) {
                if (x == Board.NUM_ROWS - 1 && y == Board.NUM_COLS - 1) {
                    // blank tile, file name is tile_25
                    tiles.add(new TileST());
                } else {
                    tiles.add(new TileST(y* Board.NUM_COLS + x + 1,
                            x*tile_width, y*tile_height, tile_width, tile_height));
                }
            }
        }
        Collections.shuffle(tiles);
        this.board = new BoardST(tiles);
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
    boolean touchMove(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        List<Integer> coord = findBlankCoord();
        boolean isSuccessfulMove = false;
        int blank_row = coord.get(0), blank_col = coord.get(1);
        if ((row == blank_row && Math.abs(col - blank_col) == 1) || (col == blank_col) && (Math.abs(row - blank_row) == 1)) {
            ((BoardST)board).swapTiles(row, col, blank_row, blank_col);
            isSuccessfulMove = true;
        }
        return isSuccessfulMove;
    }

    /**
     * Return 2 element list of blank coordinate- row, column
     *
     * @return 2 element list of blank coordinate- row, column
     */
    List<Integer> findBlankCoord() {
        int blankId = board.numTiles();
        int blankRow = 0, blankColumn = 0;
        for (int i = 0; i < getBoard().NUM_ROWS; i++) {
            for (int j = 0; j < getBoard().NUM_COLS; j++) {
                Tile tile = board.getTile(i, j);
                if (tile.getId() == blankId) {
                    blankRow = i;
                    blankColumn = j;
                }
            }
        }
        return Arrays.asList(blankRow, blankColumn);
    }

    /**
     * Return whether the blank tile is on an odd row from the bottom
     *
     * @return whether the blank tile is on an odd row from the bottom
     */
    boolean findBlankOnOddRowFromBottom() {
        return ((getBoard().NUM_ROWS - findBlankCoord().get(0)) % 2 == 1);
    }

    /**
     * Return total number of inversions in a list of tiles for every single tile
     *
     * @param tiles the list of tiles
     * @return total number of inversions in a list of tiles for every single tile
     */
    int checkTotalInversions(List<Tile> tiles) {
        int inversions = 0;
        for (int i = 0; i < tiles.size(); i++) {
            List<Tile> sublist = tiles.subList(i, tiles.size());
            inversions += checkInversion(sublist); //
        }
        return inversions;
    }
    /**
     * Return number of inversions in a list of tiles for Tile at position 0
     *
     * @param tiles the list of tiles
     * @return number of inversions in a list of tiles for Tile at position 0
     */
    int checkInversion(List<Tile> tiles) {
        int counter = 0;
        if (tiles.size() != 0) {
            int number = tiles.get(0).getId();
            for (Tile i : tiles) {
                if (number > i.getId()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Return whether the board is solvable
     *
     * @param tiles the list of tiles
     * @param gridWidth the width of the board
     * @return whether the board is solvable
     */
    boolean isSolvable(List<Tile> tiles, int gridWidth) {
        //Check number of inversions and add up
        //( (grid width odd) && (#inversions even) )  ||  ( (grid width even) && ((blank on odd row from bottom) == (#inversions even)) )

        int inversions = checkTotalInversions(tiles);
        boolean evenInversion = (inversions % 2 == 0);
        return (((gridWidth % 2 == 1) && evenInversion) || ((gridWidth % 2 == 0) && ( findBlankOnOddRowFromBottom() == evenInversion)));
    }

}
