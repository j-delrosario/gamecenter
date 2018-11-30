package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A tile for sliding tiles.
 */
public class TileST implements Comparable<TileST>, Serializable {

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
    public TileST() {
        this.id = BoardST.NUM_COLS * BoardST.NUM_ROWS;
        this.visibleBackground = 25;
    }

    /**
     * return the comparing int.
     * @param o
     * @return
     */
    public int compareTo(@NonNull TileST o) {
        return o.id - this.id;
    }

    /**
     * The coordinates for the region of the background image this tile corresponds to.
     */
    private int[] backgroundCoordinates = new int[4];

    /**
     * A tile with background coordinates (for a tile with custom set image) and an id.
     *
     * @param id the id of the tile
     * @param x the starting x position to cut this tile's image (from a background image)
     * @param y the starting y position to cut this tile's image (from a background image)
     * @param width the width of the tile, to cut this tile's image (from a background image)
     * @param height the height of the tile, to cut this tile's image (from a background image)
     */
    public TileST(int id, int x, int y, int width, int height) {
        this.id = id;
        this.backgroundCoordinates[0] = x;
        this.backgroundCoordinates[1] = y;
        this.backgroundCoordinates[2] = width;
        this.backgroundCoordinates[3] = height;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId
     */
    public TileST(int backgroundId) {
        id = backgroundId + 1;
        visibleBackground = id;
    }

    /**
     * Return the coordinates for the region of the background image that this tile corresponds to.
     *
     * @return the background image coordinates.
     */
    public int[] getBackgroundCoordinates() {
        return backgroundCoordinates;
    }
}
