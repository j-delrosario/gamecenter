package fall2018.csc2017.slidingtiles;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import fall2018.csc2017.slidingtiles.MatchingTiles.BoardMatch;
import fall2018.csc2017.slidingtiles.MatchingTiles.TileMatch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class BoardMatchTest {

    /**
     * Set up a new board before each test case
     */
    BoardMatch board = new BoardMatch();
    @Before
    public void testBefore(){
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                TileMatch t = new TileMatch(androidx.test.core.app.ApplicationProvider.getApplicationContext());
                board.setTile(t, row, col);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                TileMatch t = new TileMatch(androidx.test.core.app.ApplicationProvider.getApplicationContext());
                t.setBackground(i);
                t.refreshBackground();
                board.setTile(t, i, j);
            }
        }
    }

    /**
     * Testing selecting tiles, i.e. whether game updates after a match is found/not found
     */
    @Test
    public void testUpdateSelectedTiles() {
        assertTrue(board.moveCount() == 0);
        board.updateSelectedTiles(board.getTile(0,0));
        assertTrue(board.moveCount() == 1);
        board.updateSelectedTiles(board.getTile(1,0));
        try {
            Thread.sleep(500);
        } catch (Exception e) {e.printStackTrace();}
        assertTrue(board.moveCount() == 0);
        assertTrue(BoardMatch.getScore() == 99);

        board.resetScore();
    }

    /**
     * Testing isMatching, i.e. whether two tiles are properly identified as matching and
     * update the game state accordingly
     */
    @Test
    public void testIsMatching() {

        board.updateSelectedTiles(board.getTile(0,0));
        board.updateSelectedTiles(board.getTile(0,0));
        try {
            Thread.sleep(500);
        } catch (Exception e) {e.printStackTrace();}
        assertTrue(BoardMatch.getScore() == 99);
        assertFalse(board.getTile(0,0).getIsMatched());

        board.resetScore();

        board.updateSelectedTiles(board.getTile(0,0));
        board.updateSelectedTiles(board.getTile(0,1));
        try {
            Thread.sleep(500);
        } catch (Exception e) {e.printStackTrace();}
        assertTrue(BoardMatch.getScore() == 100);
        assertTrue(board.getTile(0,0).getIsMatched());
        assertTrue(board.getTile(0,1).getIsMatched());

    }

    /**
     * Testing save value function, whether board state is saved properly as an int[][][]
     */
    @Test
    public void testBoardSaveValue() {
        int[][][] save = board.boardSaveValue();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                assertTrue(save[i][j][0] == 0);
                assertTrue(save[i][j][1] == i);
                assertTrue(save[i][j][2] == 0);
            }
        }
        assertTrue(save[0][0][3] == 0);
        assertTrue(save[0][1][3] == 100);
    }

    /**
     * Testing refresh, i.e. whether the board updates the TextView of the individual tiles
     * after a tile has been changed in some way
     */
    @Test
    public void testRefresh() {
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                assertTrue(board.getTile(i, j).getVisibleBackground() == i);
            }
        }

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                board.getTile(i, j).setBackground(0);
            }
        }
        board.refresh();

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                assertTrue(board.getTile(i, j).getID().getText() == "");
            }
        }

    }

    /**
     * Testing updateCompletedMatches i.e. whether a match updates the total match count and
     * the game state is changed to complete once all matches have been found
     */
    @Test
    public void testUpdateCompletedMatches() {
        assertFalse(board.isComplete());

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j = j + 2){
                board.updateSelectedTiles(board.getTile(i,j));
                board.updateSelectedTiles(board.getTile(i,j+1));
            }
        }

        try {Thread.sleep(500);} catch (Exception e) {e.printStackTrace();}

        assertTrue(board.isComplete());

    }

    /**
     * Testing getting a BoardMatch from a save, i.e. whether a board state loads properly given
     * a save value
     */
    @Test
    public void testBoardFromSave() {
        int[][][] save = board.boardSaveValue();

        BoardMatch savedBoard = new BoardMatch();

        savedBoard = savedBoard.boardFromSave(androidx.test.core.app.ApplicationProvider.getApplicationContext(), save);

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                assertTrue(savedBoard.getTile(i,j).getVisibleBackground() == 0);
                assertTrue(savedBoard.getTile(i,j).getbackground() == i);
                assertFalse(savedBoard.getTile(i,j).getIsMatched());
            }
        }
        assertTrue(savedBoard.moveCount() == 0);
        assertTrue(BoardMatch.getScore() == 99);

    }

}
