package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import fall2018.csc2017.slidingtiles.GameCentre.GameLaunchCentreActivity;

/**
 * Gestrue detecter for Sliding tiles.
 */
public class GestureDetectGridViewST extends GestureDetectGridView {

    /**
     * Initialization.
     * @param context
     */
    public GestureDetectGridViewST(Context context) {
        super(context);
        init(context);
    }

    /**
     * Initialization.
     * @param context
     * @param attrs
     */

    public GestureDetectGridViewST(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Initialization.
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public GestureDetectGridViewST(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * defines how the gesture detector behaves.
     * @param context
     */
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
                    final double score = GameActivity.getScore();
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Integer value = dataSnapshot.child("mmsliding").getValue(Integer.class);
                            if (value < score){
                                mRef.child("mmsliding").setValue(score);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
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
