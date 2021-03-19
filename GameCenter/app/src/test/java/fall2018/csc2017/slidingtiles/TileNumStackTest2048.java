package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.lang.reflect.Array;

import fall2018.csc2017.slidingtiles.twozerofoureight.ScoreStack;
import fall2018.csc2017.slidingtiles.twozerofoureight.TileNumStack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TileNumStackTest2048 {
    /**
     * The board stack for testing.
     */
    TileNumStack tileNumStackTest2048;

    /**
     * The first board pushed in
     */
    int[] bottomBoard = new int[]{3};

    /**
     * The second board pushed in
     */
    int[] middleBoard = new int[]{2};

    /**
     * The last and most recent board pushed in
     */
    int[] topBoard = new int[]{1};

    /**
     * Create a new board stack
     */
    private void setUpCorrect() {
        tileNumStackTest2048 = new TileNumStack(3);
    }


    /**
     * Test pushing new boards onto an empty stack
     */
    @Test
    public void testPush() {
        setUpCorrect();
        tileNumStackTest2048.push(bottomBoard);
        tileNumStackTest2048.push(middleBoard);
        assertFalse(tileNumStackTest2048.isFull());
        tileNumStackTest2048.push(topBoard);
        assertTrue(tileNumStackTest2048.isFull());
        tileNumStackTest2048.push(middleBoard);
        assertNotEquals(topBoard, tileNumStackTest2048.peek());

    }


    /**
     * Test popping boards from the stack and checking if the stack is empty
     */
    @Test
    public void testPop() {
        setUpCorrect();
        tileNumStackTest2048.push(bottomBoard);
        assertEquals(bottomBoard, tileNumStackTest2048.pop());
        assertTrue(tileNumStackTest2048.isEmpty());
    }


    /**
     * Test whether stack is empty
     */
    @Test
    public void testIsEmpty() {
        setUpCorrect();
        assertTrue(tileNumStackTest2048.isEmpty());
        tileNumStackTest2048.push(bottomBoard);
        tileNumStackTest2048.pop();
        assertTrue(tileNumStackTest2048.isEmpty());
    }


    /**
     * Test whether peek returns the previous state of the board
     */
    @Test
    public void testPeek() {
        setUpCorrect();
        tileNumStackTest2048.push(bottomBoard);
        assertEquals(bottomBoard, tileNumStackTest2048.peek());
        assertTrue(!tileNumStackTest2048.isEmpty());
    }


    /**
     * Test whether the stack is full
     */
    @Test
    public void testIsFull() {
        setUpCorrect();
        tileNumStackTest2048.push(bottomBoard);
        tileNumStackTest2048.push(middleBoard);
        tileNumStackTest2048.push(topBoard);
        assertTrue(tileNumStackTest2048.isFull());
    }

}
