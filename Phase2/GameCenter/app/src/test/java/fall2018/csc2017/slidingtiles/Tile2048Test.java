package fall2018.csc2017.slidingtiles;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import fall2018.csc2017.slidingtiles.twozerofoureight.Tile2048;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class Tile2048Test {

    /**
     * Make Tile2048's.
     */
    Tile2048 tile = new Tile2048(androidx.test.core.app.ApplicationProvider.getApplicationContext(), 0);
    Tile2048 tile2 = new Tile2048(androidx.test.core.app.ApplicationProvider.getApplicationContext(), 0);
    Tile2048 tile3 = new Tile2048(androidx.test.core.app.ApplicationProvider.getApplicationContext(), 64);

    /**
     * Test the equality operator of a Tile2048
     */
    @Test
    public void testEquals() {
        assertTrue(tile.equals(tile2));
        assertFalse(tile.equals(tile3));

    }

    /**
     * Test the String representation of a Tile2048
     */
    @Test
    public void testToString() {
        assertTrue(tile.toString().equals("0"));

    }

    /**
     * Test the equality operator of a tile
     */
    @Test
    public void testGetBackground() {
        assertTrue(tile3.getbackground() == 64);

    }

    /**
     * Test setting a new background value for a Tile2048
     */
    @Test
    public void testSetBackground() {
        tile.setBackground(16);
        assertTrue(tile.getbackground() == 16);
        assertTrue(tile.getID().getText().equals("16"));

    }
}
