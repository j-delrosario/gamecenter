package fall2018.csc2017.slidingtiles;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import fall2018.csc2017.slidingtiles.MatchingTiles.TileMatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TileMatchTest {
    /**
     * The tile from matching tiles game
     */
    TileMatch tile1 = new TileMatch(androidx.test.core.app.ApplicationProvider.getApplicationContext());


    /**
     * Test getting the background of a Tile
     */
    @Test
    public void testGetbackground() {
        assertTrue(tile1.getbackground() == 0);
    }

    /**
     * Test setting the background of a Tile
     */
    @Test
    public void testSetbackground() {
        tile1.setbackground(7);
        assertTrue(tile1.getbackground() == 7);

    }

    /**
     * Test getting the currently visible background of a Tile
     */
    @Test
    public void testGetBackground() {
        assertTrue(tile1.getVisibleBackground() == 0);
    }

    /**
     * Test setting the currently visible background of a Tile
     */
    @Test
    public void testSetVisibleBackground() {
        tile1.setBackground(12);
        assertTrue(tile1.getVisibleBackground() == 12);

    }

    /**
     * Test getting whether the Tile has already been matched
     */
    @Test
    public void testGetIsMatched() {
        assertFalse(tile1.getIsMatched());
    }

    /**
     * Test setting whether the Tile has already been matched
     */
    @Test
    public void testSetIsMatched() {
        tile1.setIsMatched(true);
        assertTrue(tile1.getIsMatched());
    }

    /**
     * Test the String representation of a tile
     */
    @Test
    public void testToString() {
        tile1.setBackground(12);
        assertTrue(tile1.toString(), tile1.toString().equals("12"));
    }

    /**
     * Test the equality operator of a tile
     */
    @Test
    public void testEquals() {
        TileMatch tile2 = new TileMatch(androidx.test.core.app.ApplicationProvider.getApplicationContext());
        tile1.setBackground(12);
        assertFalse(tile1.equals(tile2));
        tile2.setBackground(12);
        assertTrue(tile1.equals(tile2));
    }

    /**
     * Test if background TextView is properly updated
     */
    @Test
    public void testRefreshBackground() {
        tile1.setBackground(12);
        tile1.refreshBackground();
        assertTrue(tile1.getID().getText().equals("12"));
        tile1.setBackground(0);
        tile1.refreshBackground();
        assertTrue(tile1.getID().getText().equals(""));
    }

    /**
     * Test whether a tile is set to: invisible if visible and matched
     *                                visible if not-visible and not-matched
     *                                not-visible if visible and not-matched
     */
    @Test
    public void testSetVisible() {
        TileMatch tile3 = new TileMatch(androidx.test.core.app.ApplicationProvider.getApplicationContext());
        tile3.setbackground(1);
        assertTrue(tile3.setVisible());
        assertEquals(tile3.getVisibleBackground(), 1);
        assertEquals(tile3.getbackground(), 0);
        tile3.setIsMatched(true);
        assertFalse(tile3.setVisible());
        assertEquals(tile3.getVisibleBackground(), 0);
    }

}
