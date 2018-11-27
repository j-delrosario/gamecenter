package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc2017.slidingtiles.R;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    protected int visibleBackground;

    /**
     * The unique id.
     */
    protected int id;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return visibleBackground;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A blank Tile.
     */
    public Tile() { }

    /**
     * A blank Tile with an id.
     */
    public Tile(String x) {
        this.id = Board.NUM_COLS * Board.NUM_ROWS;
        this.visibleBackground = 25;
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
