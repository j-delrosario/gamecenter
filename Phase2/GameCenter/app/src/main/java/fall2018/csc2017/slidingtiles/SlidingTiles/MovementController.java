package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.content.Context;

/**
 * A class defines Movements
 */
abstract class MovementController {

    /**
     * A bool shows if the game have won.
     */
    protected boolean hasWon = false;

    /**
     * Board manager of Sliding tiles.
     */
    protected BoardManagerST boardManager = null;

    /**
     * Check if the game is won.
     * @return Bool
     */
    public boolean hasWon() { return hasWon; }

    /**
     * Set the board manager.
     * @param boardManager
     */
    public void setBoardManager(BoardManagerST boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * A boolean see if a tap is valid.
     * @param context
     * @param position
     * @param t
     * @return
     */
    abstract boolean processTapMovement(Context context, int position, boolean t);

}
