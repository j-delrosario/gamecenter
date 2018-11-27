package fall2018.csc2017.slidingtiles.MatchingTiles;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.Serializable;

import fall2018.csc2017.slidingtiles.R;

/**
 * View for a tile of game matching tiles.
 */
public class TileMatch extends FrameLayout implements Serializable {

    /**
     * The textView for the tile.
     */
    private TextView id;

    /**
     * The background number.
     */
    private int background;

    /**
     * The number for the visible background.
     */
    private int visibleBackground = 0;

    /**
     * the int for the finished background.
     */
    private int finishedBackground = ContextCompat.getColor(getContext(), R.color.colorPrimaryDarkMT);

    /**
     * if the tiles are matched.
     */
    private boolean isMatched = false;

    /**
     * Initialization.
     * @param context
     */
    public TileMatch(Context context) {
        super(context);
        id = new TextView(getContext());
        Typeface font = Typeface.createFromAsset(context.getAssets(),
                "fonts/Audiowide-Regular.ttf");
        id.setTypeface(font);
        id.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkMT));
        id.setGravity(Gravity.CENTER);
        id.setTextSize(40);
        id.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        LayoutParams layout = new LayoutParams(-1, -1);
        layout.setMargins(10, 10, 10, 10);
        addView(id, layout);
        setBackground(0);
        refreshBackground();
    }

    /**
     * get the background number.
     * @return int
     */
    public int getbackground() {
        return background;
    }

    /**
     * Ge the visible background.
     * @return
     */
    public int getVisibleBackground() {
        return visibleBackground;
    }

    /**
     * Returns if the tiles are matched.
     * @return boolean
     */
    public boolean getIsMatched() {
        return isMatched;
    }

    /**
     * Returns if the tile number is visible.
     * @return
     */
    public boolean isVisible() {
        return visibleBackground != 0;
    }

    /**
     * set the background number to b.
     * @param b
     */
    public void setbackground(int b) {
        background = b;
    }

    /**
     * set the visible background to b.
     * @param b
     */
    public void setBackground(int b) {
        visibleBackground = b;
    }

    /**
     * Set the IsMatched boolean value.
     * @param matched
     */
    public void setIsMatched(boolean matched) {isMatched = matched;}

    /**
     * update the Background color.
     * @param color
     */
    public void updateBackgroundColor(int color) {
        id.setBackgroundColor(color);
    }

    /**
     * update the background with a delay.
     */
    public void updateWithDelay() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateBackgroundColor(finishedBackground);
            }
        };
        handler.postDelayed(runnable, 500);
    }

    /**
     * Refresh the background.
     */
    public void refreshBackground(){
        if (visibleBackground != 0) {
            id.setText(Integer.toString((Integer) visibleBackground));
        } else { id.setText(""); }
    }

    /**
     * refresh the background color.
     */
    public void refreshBackgroundColor(){
        if (isMatched == true) {
            updateBackgroundColor(finishedBackground);
        }
    }

    /**
     * set if the tiles should be visible.
     * @return
     */
    public boolean setVisible() {
        if (!isMatched) {
            int temp = visibleBackground;
            visibleBackground = background;
            background = temp;
            return true;
        } else {
            updateWithDelay();
            visibleBackground = 0;
        }
        return false;
    }

    /**
     * ToString method for a matching tiles tile.
     * @return
     */
    public String toString() {
        return String.valueOf(visibleBackground);
    }

    /**
     * check if two tiles are equal.
     * @param t
     * @return boolean
     */
    public boolean equals(TileMatch t) {
        return (getVisibleBackground() == t.getVisibleBackground());
    }
}
