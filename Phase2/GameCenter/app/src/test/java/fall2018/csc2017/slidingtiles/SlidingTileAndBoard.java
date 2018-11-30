package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fall2018.csc2017.slidingtiles.SlidingTiles.BoardManagerST;
import fall2018.csc2017.slidingtiles.SlidingTiles.BoardST;
import static org.junit.Assert.*;

import fall2018.csc2017.slidingtiles.SlidingTiles.TileST;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTileAndBoard {

    /**
     * The board manager for testing.
     */
    BoardManagerST boardManager;

    /**
     * Make a set of tiles that according to a list of integers
     *
     * @return a List<Tile>
     */
    public List<TileST> addTiles(List<Integer> ids) {
        List<TileST> a = new ArrayList<>();
        for (Integer i : ids) {
            a.add(new TileST(i));
        }
        return a;
    }


    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<TileST> makeTilesST() {
        List<TileST> tiles = new ArrayList<>();
        final int numTiles = 16;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new TileST(tileNum));
        }
        return tiles;
    }


    /**
     * Make a solved ST Board.
     */
    private void setUpCorrect() {
        List<TileST> tilesST = makeTilesST();
        BoardST board = new BoardST(tilesST);
        this.boardManager = new BoardManagerST(board);
    }


    /**
     * Test getting the background coordinates of a ST Tile
     */
    @Test
    public void testGetBackgroundCoordinates() {
        TileST tile = new TileST(24, 1, 1, 2, 2);
        assertArrayEquals(new int[]{1, 1, 2, 2}, tile.getBackgroundCoordinates());

    }


    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        ((BoardST) boardManager.getBoard()).swapTiles(0, 0, 0, 1);
    }


    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertEquals(true, boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertEquals(false, boardManager.puzzleSolved());
    }


    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        ((BoardST) boardManager.getBoard()).swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }


    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        ((BoardST) boardManager.getBoard()).swapTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());
    }


    /**
     * Test whether isValidTap works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, boardManager.isValidTap(11));
        assertEquals(true, boardManager.isValidTap(14));
        assertEquals(false, boardManager.isValidTap(10));
        ((BoardST) boardManager.getBoard()).swapTiles(0, 0, 0, 1);
        assertEquals(true, boardManager.isValidTap(11));
    }


    /**
     * Test whether blank tile is on an odd row from the bottom
     */
    @Test
    public void testFindBlankOnOddRowFromBottom() {
        setUpCorrect();
        assertEquals(true, boardManager.findBlankOnOddRowFromBottom());
        ((BoardST) boardManager.getBoard()).swapTiles(3, 3, 2, 3);
        assertFalse(boardManager.findBlankOnOddRowFromBottom());

    }


    /**
     * Test the constructor, and whether it gives back the appropriate id
     */
    @Test
    public void testCreation() {
        List<Integer> i = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);
        List<TileST> tiles = addTiles(i);
        for (int j = 0; j < i.size(); j++) {
            assertEquals(j, tiles.get(j).getId() - 1);

        }
    }


    /**
     * Test whether the compare method for the tiles work
     */
    @Test
    public void testCompareTo() {
        List<Integer> i = Arrays.asList(0, 1);
        List<Integer> m = Arrays.asList(0, 0);
        List<TileST> different = addTiles(i);
        List<TileST> same = addTiles(m);
        assertEquals(1, different.get(0).compareTo(different.get(1)));
        assertEquals(0, same.get(0).compareTo(same.get(1)));
    }


    /**
     * Test counting the number of inversions in a list of tiles
     */
    @Test
    public void checkInversion() {
        setUpCorrect();
        List<TileST> checkOne = addTiles(Arrays.asList(9, 8));
        List<TileST> checkZero = addTiles(Arrays.asList(8, 9));
        List<TileST> checkTwo = addTiles(Arrays.asList(9, 8, 7));
        assertEquals(1, boardManager.checkInversion(checkOne));
        assertEquals(0, boardManager.checkInversion(checkZero));
        assertEquals(2, boardManager.checkInversion(checkTwo));
    }


    /**
     * Test counting total inversions
     */
    @Test
    public void checkInversions() {
        List<TileST> checkOne = addTiles(Arrays.asList(9, 8, 10));
        List<TileST> checkThree = addTiles(Arrays.asList(9, 8, 7));
        setUpCorrect();
        assertEquals(1, boardManager.checkTotalInversions(checkOne));
        assertEquals(3, boardManager.checkTotalInversions(checkThree));
    }


    /**
     * Test solvability
     */
    @Test
    public void isSolvable() {
        setUpCorrect();
        assertEquals(true, boardManager.isSolvable(boardManager.getBoard().getTiles(), 4));
        List<TileST> notSolvable = addTiles(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 8, 7));
        assertFalse(boardManager.isSolvable(notSolvable, 3));

    }


    /**
     * Test sliding a tile
     */
    @Test
    public void testTouchMove() {
        setUpCorrect();
        assertEquals(true, boardManager.touchMove(14));
    }


}
