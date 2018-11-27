package fall2018.csc2017.slidingtiles.SlidingTiles;

import java.io.Serializable;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
abstract class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    protected Board board;

    /**
     * An int represent the number of row of current board.
     */
    public int intRow;

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
     * Default Constructor
     */
    BoardManager() {}

    /**
     * Manage a board that has been pre-populated.
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
        intRow = getRow();
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
     * Return the Row number of the board.
     * @return int
     */
    public int getRow(){
        return board.NUM_ROWS;
    }

    /**
     * Get the number of the row from the boardManager.
     * @return
     */
    public int getNumRow(){
        return intRow;
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
     * an boolean defines if the tap is valid.
     * @param position
     * @return
     */
    abstract boolean isValidTap(int position);

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    abstract boolean touchMove(int position);

}
