package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import fall2018.csc2017.slidingtiles.GameCentre.GameLaunchCentreActivity;

public class GestureDetectGridViewST extends GestureDetectGridView {

    public GestureDetectGridViewST(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridViewST(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridViewST(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        mController = new MovementControllerST();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridViewST.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                Activity parentActivity = (Activity)getContext();

                if (!mController.processTapMovement(context, position, true)) {
                    ((GameActivity)parentActivity).makeToastText("Invalid Tap");
                }
                if (mController.hasWon()) {
                    double score = GameActivity.getScore();
                    mRef.child(GameLaunchCentreActivity.Email).child("mmsliding").setValue(score);
                    ((GameActivity)parentActivity).makeToastText("You Win");
                }
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }
}
