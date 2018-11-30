package fall2018.csc2017.slidingtiles;

import android.content.Context;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import fall2018.csc2017.slidingtiles.MatchingTiles.BoardMatch;
import fall2018.csc2017.slidingtiles.twozerofoureight.*;
public class MatchingTilesTest {

    BoardMatch boardMatch;
    public void setUpCorrect() {
        boardMatch = new BoardMatch();


    }

    @Test
    public void testGetScore() {
        setUpCorrect();
        assertEquals(100, boardMatch.getScore());
    }

    @Test
    public void testSetScore() {
        setUpCorrect();
        boardMatch.setScore(200);
        assertEquals(200, boardMatch.getScore());
    }

    @Test
    public void testResetScore() {
        setUpCorrect();
        boardMatch.setScore(200);
        boardMatch.resetScore();
        assertEquals(100, boardMatch.getScore());
    }

    @Test
    public void moveCount() {
        setUpCorrect();
        assertFalse(boardMatch.isComplete());
    }
}
