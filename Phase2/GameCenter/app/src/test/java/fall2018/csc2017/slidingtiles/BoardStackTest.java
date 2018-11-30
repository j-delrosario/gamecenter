package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fall2018.csc2017.slidingtiles.SlidingTiles.*;
//import fall2018.csc2017.slidingtiles.SlidingTiles.BoardST;
//import fall2018.csc2017.slidingtiles.SlidingTiles.TileST;
//import fall2018.csc2017.slidingtiles.SlidingTiles.BoardStack;
//import fall2018.csc2017.slidingtiles.SlidingTiles.BoardST;
//import fall2018.csc2017.slidingtiles.SlidingTiles.BoardStack;
//import fall2018.csc2017.slidingtiles.SlidingTiles.TileST;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class BoardStackTest {

    /**
     * The board stack for testing.
     */
    BoardStack boardStack;

    /**
     * The solved board
     */
    List<Integer> solved = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

    /**
     * Unsolved board, 1 move away from victory
     */
    List<Integer> oneMoveBeforeSolved = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14);

    /**
     * Unsolved board, 2 moves away from victory
     */
    List<Integer> twoMoveBeforeSolved = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 15, 11, 12, 13, 10, 14);

    /**
     * The first board pushed in
     */
    BoardST bottomBoard = new BoardST(addTiles(twoMoveBeforeSolved));

    /**
     * The second board pushed in
     */
    BoardST middleBoard = new BoardST(addTiles(oneMoveBeforeSolved));

    /**
     * The last and most recent board pushed in
     */
    BoardST topBoard = new BoardST(addTiles(solved));

    /**
     * Create a new board stack
     */
    private void setUpCorrect() {
        boardStack = new BoardStack(3);
    }


    /**
     * Returns a list of tiles given a list of integer ids
     *
     * @return a list of tiles given a list of integer ids
     */
    public List<TileST> addTiles(List<Integer> ids) {
        List<TileST> a = new ArrayList<>();
        for (Integer i : ids) {
            a.add(new TileST(i));
        }
        return a;
    }


    /**
     * Test pushing new boards onto an empty stack
     */
    @Test
    public void testPush() {
        setUpCorrect();
        boardStack.push(bottomBoard);
        boardStack.push(middleBoard);
        assertFalse(boardStack.isFull());
        boardStack.push(topBoard);
        assertTrue(boardStack.isFull());
        boardStack.push(middleBoard);
        assertNotEquals(topBoard, boardStack.peek());

    }


    /**
     * Test popping boards from the stack and checking if the stack is empty
     */
    @Test
    public void testPop() {
        setUpCorrect();
        boardStack.push(bottomBoard);
        assertEquals(bottomBoard, boardStack.pop());
        assertTrue(boardStack.isEmpty());
    }

    @Test(expected = Exception.class)
    public void testPopException() {
        setUpCorrect();
        boardStack.pop();
    }


    /**
     * Test whether stack is empty
     */
    @Test
    public void testIsEmpty() {
        setUpCorrect();
        assertTrue(boardStack.isEmpty());
        boardStack.push(bottomBoard);
        boardStack.pop();
        assertTrue(boardStack.isEmpty());g
    }


    /**
     * Test whether peek returns the previous state of the board
     */
    @Test
    public void testPeek() {
        setUpCorrect();
        boardStack.push(bottomBoard);
        assertEquals(bottomBoard, boardStack.peek());
        assertTrue(!boardStack.isEmpty());
    }


    /**
     * Test whether the stack is full
     */
    @Test
    public void testIsFull() {
        setUpCorrect();
        boardStack.push(bottomBoard);
        boardStack.push(middleBoard);
        boardStack.push(topBoard);
        assertTrue(boardStack.isFull());
    }

    @Test
    public void testGetNumRowInStack() {
        setUpCorrect();
        assertEquals(4, boardStack.getNumRowInStack());
    }


}
