package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.slidingtiles.SlidingTiles.BoardST;
import fall2018.csc2017.slidingtiles.SlidingTiles.BoardStack;
import fall2018.csc2017.slidingtiles.SlidingTiles.TileST;
import fall2018.csc2017.slidingtiles.twozerofoureight.ScoreStack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ScoreStackTest {
    /**
     * The board stack for testing.
     */
    ScoreStack scoreStack;

    /**
     * The first board pushed in
     */
    int bottomBoard = 3;

    /**
     * The second board pushed in
     */
    int middleBoard = 2;

    /**
     * The last and most recent board pushed in
     */
    int topBoard = 1;

    /**
     * Create a new board stack
     */
    private void setUpCorrect() {
        scoreStack = new ScoreStack(3);
    }


    /**
     * Test pushing new boards onto an empty stack
     */
    @Test
    public void testPush() {
        setUpCorrect();
        scoreStack.push(bottomBoard);
        scoreStack.push(middleBoard);
        assertFalse(scoreStack.isFull());
        scoreStack.push(topBoard);
        assertTrue(scoreStack.isFull());
        scoreStack.push(middleBoard);
        assertNotEquals(topBoard, scoreStack.peek());

    }


    /**
     * Test popping boards from the stack and checking if the stack is empty
     */
    @Test
    public void testPop() {
        setUpCorrect();
        scoreStack.push(bottomBoard);
        assertEquals(bottomBoard, scoreStack.pop());
        assertTrue(scoreStack.isEmpty());
    }


    /**
     * Test whether stack is empty
     */
    @Test
    public void testIsEmpty() {
        setUpCorrect();
        assertTrue(scoreStack.isEmpty());
        scoreStack.push(bottomBoard);
        scoreStack.pop();
        assertTrue(scoreStack.isEmpty());
    }


    /**
     * Test whether peek returns the previous state of the board
     */
    @Test
    public void testPeek() {
        setUpCorrect();
        scoreStack.push(bottomBoard);
        assertEquals(bottomBoard, scoreStack.peek());
        assertTrue(!scoreStack.isEmpty());
    }


    /**
     * Test whether the stack is full
     */
    @Test
    public void testIsFull() {
        setUpCorrect();
        scoreStack.push(bottomBoard);
        scoreStack.push(middleBoard);
        scoreStack.push(topBoard);
        assertTrue(scoreStack.isFull());
    }
    @Test(expected = Exception.class)
    public void testPopException() {
        setUpCorrect();
        scoreStack.pop();
    }


}
