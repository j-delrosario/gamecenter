package fall2018.csc2017.slidingtiles.MatchingTiles;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import fall2018.csc2017.slidingtiles.R;

public class TileMatch extends FrameLayout {

    private TextView id;

    private int background;
    private int visibleBackground = 0;
    private int finishedBackground = ContextCompat.getColor(getContext(), R.color.colorPrimaryDarkMT);
    private boolean isMatched = false;

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

    public int getVisibleBackground() {
        return visibleBackground;
    }
    public boolean isVisible() {
        return visibleBackground != 0;
    }

    public void setBackground(int b) {
        visibleBackground = b;
    }
    public void setIsMatched(boolean matched) {isMatched = matched;}

    void updateBackgroundColor(int color) {
        id.setBackgroundColor(color);
    }

    void updateWithDelay() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateBackgroundColor(finishedBackground);
            }
        };
        handler.postDelayed(runnable, 500);
    }

    public void refreshBackground(){
        if (visibleBackground != 0) {
            id.setText(Integer.toString((Integer) visibleBackground));
        } else { id.setText(""); }
    }

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

    public String toString() {
        return String.valueOf(visibleBackground);
    }
    public boolean equals(TileMatch t) {
        return (getVisibleBackground() == t.getVisibleBackground());
    }
}
