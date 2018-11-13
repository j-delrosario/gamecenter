package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;


/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The coordinates for the region of the background image this tile corresponds to.
     */
    private int[] backgroundCoordinates = new int[4];

    /**
     * The unique id.
     */
    private int id;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the coordinates for the region of the background image that this tile corresponds to.
     *
     * @return the background image coordinates.
     */
    public int[] getBackgroundCoordinates() {
        return backgroundCoordinates;
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
     * A tile with background coordinates (for a tile with custom set image) and an id.
     *
     * @param id the id of the tile
     * @param x the starting x position to cut this tile's image (from a background image)
     * @param y the starting y position to cut this tile's image (from a background image)
     * @param width the width of the tile, to cut this tile's image (from a background image)
     * @param height the height of the tile, to cut this tile's image (from a background image)
     */
    public Tile(int id, int x, int y, int width, int height) {
        this.id = id;
        this.backgroundCoordinates[0] = x;
        this.backgroundCoordinates[1] = y;
        this.backgroundCoordinates[2] = width;
        this.backgroundCoordinates[3] = height;
    }

    /**
     * A blank Tile with an id.
     */
    public Tile() {
        this.id = Board.NUM_COLS * Board.NUM_ROWS;
        this.background = R.drawable.tile_25;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId
     */
    public Tile(int backgroundId) {
        id = backgroundId + 1;
        // This looks so ugly.
        switch (backgroundId + 1) {
            case 1:
                background = R.drawable.tile_1;
                break;
            case 2:
                background = R.drawable.tile_2;
                break;
            case 3:
                background = R.drawable.tile_3;
                break;
            case 4:
                background = R.drawable.tile_4;
                break;
            case 5:
                background = R.drawable.tile_5;
                break;
            case 6:
                background = R.drawable.tile_6;
                break;
            case 7:
                background = R.drawable.tile_7;
                break;
            case 8:
                background = R.drawable.tile_8;
                break;
            case 9:
                background = R.drawable.tile_9;
                break;
            case 10:
                background = R.drawable.tile_10;
                break;
            case 11:
                background = R.drawable.tile_11;
                break;
            case 12:
                background = R.drawable.tile_12;
                break;
            case 13:
                background = R.drawable.tile_13;
                break;
            case 14:
                background = R.drawable.tile_14;
                break;
            case 15:
                background = R.drawable.tile_15;
                break;
            case 16:
                background = R.drawable.tile_16;
                break;
            case 17:
                background = R.drawable.tile_17;
                break;
            case 18:
                background = R.drawable.tile_18;
                break;
            case 19:
                background = R.drawable.tile_19;
                break;
            case 20:
                background = R.drawable.tile_20;
                break;
            case 21:
                background = R.drawable.tile_21;
                break;
            case 22:
                background = R.drawable.tile_22;
                break;
            case 23:
                background = R.drawable.tile_23;
                break;
            case 24:
                background = R.drawable.tile_24;
                break;
            case 25:
                background = R.drawable.tile_25;
                break;
            default:
                background = R.drawable.tile_25;
        }
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
