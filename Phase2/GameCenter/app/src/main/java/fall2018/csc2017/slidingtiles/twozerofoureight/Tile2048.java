package fall2018.csc2017.slidingtiles.twozerofoureight;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import fall2018.csc2017.slidingtiles.R;

/**
 * FrameLayout representing the tiles of 2048.
 */
public class Tile2048 extends FrameLayout {

    /**
     * An TextView of the tile.
     */
    private TextView id;

    /**
     * The background id to find the tile number.
     */
    private int background = 0;

    /**
     * Initializes the FrameLayout representing a tile for 2048.
     *
     * @return the background id
     */
    public Tile2048(Context context, int backGround) {
        super(context);
        switch (backGround) {
            case 0:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x40002b44);
                LayoutParams layout = new LayoutParams(-1, -1);
                layout.setMargins(10, 10, 10, 10);
                addView(id, layout);
                setBackground(0);
                break;
            case 2:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_2);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x40002b44);
                LayoutParams layout2 = new LayoutParams(-1, -1);
                layout2.setMargins(10, 10, 10, 10);
                addView(id, layout2);
                setBackground(2);
                break;
            case 4:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_4);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x8070b28b);
                LayoutParams layout4 = new LayoutParams(-1, -1);
                layout4.setMargins(10, 10, 10, 10);
                addView(id, layout4);
                setBackground(4);
                break;
            case 8:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_8);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x80148f77);
                LayoutParams layout8 = new LayoutParams(-1, -1);
                layout8.setMargins(10, 10, 10, 10);
                addView(id, layout8);
                setBackground(8);
                break;
            case 16:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_16);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x800b5345);
                LayoutParams layout16 = new LayoutParams(-1, -1);
                layout16.setMargins(10, 10, 10, 10);
                addView(id, layout16);
                setBackground(16);
                break;
            case 32:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_32);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x80edcf72);
                LayoutParams layout32 = new LayoutParams(-1, -1);
                layout32.setMargins(10, 10, 10, 10);
                addView(id, layout32);
                setBackground(32);
                break;
            case 64:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_64);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x80f4d03f);
                LayoutParams layout64 = new LayoutParams(-1, -1);
                layout64.setMargins(10, 10, 10, 10);
                addView(id, layout64);
                setBackground(64);
                break;
            case 128:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_128);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x80f5b041);
                LayoutParams layout128 = new LayoutParams(-1, -1);
                layout128.setMargins(10, 10, 10, 10);
                addView(id, layout128);
                setBackground(128);
                break;
            case 256:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_256);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x80d68910);
                LayoutParams layout256 = new LayoutParams(-1, -1);
                layout256.setMargins(10, 10, 10, 10);
                addView(id, layout256);
                setBackground(256);
                break;
            case 512:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_512);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x80b9770e);
                LayoutParams layout512 = new LayoutParams(-1, -1);
                layout512.setMargins(10, 10, 10, 10);
                addView(id, layout512);
                setBackground(512);
                break;
            case 1024:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_1024);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x80e6b0aa);
                LayoutParams layout1024 = new LayoutParams(-1, -1);
                layout1024.setMargins(10, 10, 10, 10);
                addView(id, layout1024);
                setBackground(1024);
                break;
            case 2048:
                id = new TextView(getContext());
                id.setBackgroundResource(R.drawable.rounded_textview_2048);
                //id.setTextColor();
                id.setGravity(Gravity.CENTER);
                id.setTextSize(40);
                //id.setBackgroundColor(0x80c0392b);
                LayoutParams layout2048 = new LayoutParams(-1, -1);
                layout2048.setMargins(10, 10, 10, 10);
                addView(id, layout2048);
                setBackground(2048);
                break;
        }
    }

    /**
     * Get the background number of a tile.
     * @return background
     */
    public int getbackground() {
        return background;
    }

    /**
     * Set the background number of a tile.
     * @param newBackground
     */
    public void setBackground(int newBackground) {
        Integer i = new Integer(newBackground);
        background = i;
        if (background > 0){
            id.setText(Integer.toString(background));
        }
        else {
            id.setText("");
        }
    }

    /**
     * Define the toString method of a tile.
     * @return background
     */
    public String toString() {
        return String.valueOf(background);
    }

    /**
     * Return true if the tile's background is equal to another tiles' background,
     * false if not.
     * @param t
     * @return boolean
     */
    public boolean equals(Tile2048 t) {
        return (getbackground() == t.getbackground());
    }
}
