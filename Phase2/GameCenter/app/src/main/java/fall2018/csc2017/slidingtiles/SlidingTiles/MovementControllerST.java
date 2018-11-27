package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.content.Context;
import android.widget.Toast;

/**
 * A movement controller for Sliding tiles.
 */
public class MovementControllerST extends MovementController {

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
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                hasWon = true;
            }
        } else {
            return false;
        }
        return true;
    }

}
