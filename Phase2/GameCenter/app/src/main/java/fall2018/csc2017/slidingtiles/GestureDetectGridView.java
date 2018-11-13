package fall2018.csc2017.slidingtiles;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * A class for Gesture detection.
 */
public class GestureDetectGridView extends GridView {
    /**
     * a constant defining swipe miminum distance
     */
    public static final int SWIPE_MIN_DISTANCE = 100;

    /**
     * an gesture detector
     */
    private GestureDetector gDetector;

    /**
     * an movement controller
     */
    private MovementController mController;

    /**
     * an boolean represents the fling confirmed
     */
    private boolean mFlingConfirmed = false;

    /**
     * two floats represent touch locations.
     */
    private float mTouchX;
    private float mTouchY;
    private BoardManager boardManager;


    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        mController = new MovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                mController.processTapMovement(context, position, true);
                if (mController.hasWon()) {
                    int score = 15;// GameActivity.timeScore * GameActivity.numMoves;
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

                    int i = 0;
                    String highScoreKey = prefs.getString("highScoresKey" + "/" + StartingActivity.gameName + "/" + GameLaunchCentreActivity.emailString,"");
                    String lowestScoreID = highScoreKey.substring(ScoreActivity.SCORES - 1, ScoreActivity.SCORES);
                    while (i < ScoreActivity.SCORES) {
                        String highScoreID = highScoreKey.substring(i, i+1);
                        int highScore = Integer.parseInt(prefs.getString(GameLaunchCentreActivity.emailString + "/" + StartingActivity.gameName + "/" + "grade" + highScoreID,"0"));
                        if (highScore > score || highScore == 0 ) {
                            SharedPreferences.Editor editor = prefs.edit();
                            Log.i("WHY", Integer.toString(i) + "\t" + highScoreKey + "\t" + highScoreID);
                            editor.putString(GameLaunchCentreActivity.emailString + "/" + StartingActivity.gameName + "/" + "grade" + lowestScoreID, Integer.toString(score));
                            editor.putString("highScoresKey" + "/" + StartingActivity.gameName + "/" + GameLaunchCentreActivity.emailString, highScoreKey.substring(0, i) + lowestScoreID + highScoreKey.substring(i, highScoreKey.length()-1));
                            editor.apply();
                            i = ScoreActivity.SCORES;
                        }
                        i += 1;
                    }

                    int j = 0;
                    String highScoreKey2 = prefs.getString("globalHighScoresKey" + "/" + StartingActivity.gameName,"");
                    String lowestScoreID2 = highScoreKey2.substring(ScoreActivity.SCORES - 1, ScoreActivity.SCORES);
                    while (j < ScoreActivity.SCORES) {
                        String highScoreID2 = highScoreKey2.substring(j, j+1);
                        int highScore2 = Integer.parseInt(prefs.getString("globalHighScore" + "/" + StartingActivity.gameName + "/" + highScoreID2 ,"0").substring(0,1));
                        if (highScore2 > score || highScore2 == 0 ) {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString( "globalHighScore" + "/" + StartingActivity.gameName + "/" + lowestScoreID2, Integer.toString(score) + "/" + "StatusSave.getNickname(GameLaunchCentreActivity.emailString)" );
                            editor.putString("globalHighScoresKey" + "/" + StartingActivity.gameName, highScoreKey2.substring(0, j) + lowestScoreID2 + highScoreKey2.substring(j, highScoreKey2.length()-1));
                            editor.apply();
                            j = ScoreActivity.SCORES;
                        }
                        j += 1;
                    }

                }
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
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

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
}
