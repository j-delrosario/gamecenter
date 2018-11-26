package fall2018.csc2017.slidingtiles.SlidingTiles;

import fall2018.csc2017.slidingtiles.R;

public class TileST extends Tile{

    /**
     * The coordinates for the region of the background image this tile corresponds to.
     */
    private int[] backgroundCoordinates = new int[4];

    /**
     * A blank Tile with an id.
     */
    TileST() {
        super("");
    }

    /**
     * A tile with background coordinates (for a tile with custom set image) and an id.
     *
     * @param id the id of the tile
     * @param x the starting x position to cut this tile's image (from a background image)
     * @param y the starting y position to cut this tile's image (from a background image)
     * @param width the width of the tile, to cut this tile's image (from a background image)
     * @param height the height of the tile, to cut this tile's image (from a background image)
     */
    TileST(int id, int x, int y, int width, int height) {
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
    TileST(int backgroundId) {
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
