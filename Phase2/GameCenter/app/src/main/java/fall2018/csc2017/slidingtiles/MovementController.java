package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;

/**
 * A class defines Movements
 */
public class MovementController {

    private boolean hasWon = false;
    private BoardManager boardManager = null;

    public boolean hasWon() { return hasWon; }

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean t) {
        hasWon = false;
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                hasWon = true;
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
