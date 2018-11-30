package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.content.Context;
import android.widget.Toast;

/**
 * A movement controller for Sliding tiles.
 */
public class MovementControllerST {

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
     * Defines how a tap behaves.
     * @param context
     * @param position
     * @param t
     * @return boolean
     */
    public boolean processTapMovement(Context context, int position, boolean t) {
        hasWon = false;
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (((BoardManagerST)boardManager).puzzleSolved()) {
                hasWon = true;
            }
        } else {
            return false;
        }
        return true;
    }

}
