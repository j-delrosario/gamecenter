package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.content.Context;
import android.widget.Toast;

public class MovementControllerST extends MovementController {

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
