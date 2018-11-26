package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.content.Context;

/**
 * A class defines Movements
 */
abstract class MovementController {

    protected boolean hasWon = false;
    protected BoardManager boardManager = null;

    public boolean hasWon() { return hasWon; }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    abstract boolean processTapMovement(Context context, int position, boolean t);

}
