package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fall2018.csc2017.slidingtiles.GameCentre.GameLaunchCentreActivity;

import static java.security.AccessController.getContext;

/**
 * Gestrue detecter for Sliding tiles.
 */
public class GestureDetectGridViewST extends GridView {

    /**
     * A default swipe minimum distance.
     */
    public static final int SWIPE_MIN_DISTANCE = 100;

    /**
     * gestureDetecter
     */
    protected GestureDetector gDetector;

    /**
     * The movement controller
     */
    protected MovementControllerST mController;

    /**
     * A boolean represents if the filing is confirmed.
     */
    private boolean mFlingConfirmed = false;

    /**
     * The x location
     */
    private float mTouchX;

    /**
     * The y location
     */
    private float mTouchY;

    /**
     * the Database reference.
     */
    public DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    /**
     * Initialization
     * @param context
     */
    public GestureDetectGridViewST(Context context) {
        super(context);
        init(context);
    }

    /**
     * Initialization
     * @param context
     * @param attrs
     */
    public GestureDetectGridViewST(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Initialization
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public GestureDetectGridViewST(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }
            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * Set the board manager.
     * @param boardManager
     */
    public void setBoardManager(BoardManagerST boardManager) {
        mController.setBoardManager(boardManager);
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
                    final String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Integer value = dataSnapshot.child(currentUid).child("mmsliding").getValue(Integer.class);
                            if (value == null){
                                value = 0;
                            }
                            if (value < score){
                                mRef.child(currentUid).child("mmsliding").setValue(score);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    ((GameActivity)parentActivity).makeToastText("You Win!");
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
