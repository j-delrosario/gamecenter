package fall2018.csc2017.slidingtiles.SlidingTiles;

import java.util.EmptyStackException;

/**
 * A stack of Boards, used by the Undo function.
 */
public class BoardStack {

    /**
     * The maximum size of the stack.
     */
    private int maxSize;

    /**
     * The stack in the form of an array.
     */
    public BoardST[] stackList;

    /**
     * The top index used to monitor the top object in stack.
     */
    private int topIndex;

    /**
     * An int represents the number of rows for the boards in stack.
     */
    private int numRowInStack = 4;

    /**
     * The last board if there are more moves than maximum undo number taken.
     */
    public BoardST thelast;


    /**
     * Initialize the BoardStack
     * @param size
     */
    public BoardStack(int size) {
        maxSize = size;
        stackList = new BoardST[size+1];
        topIndex = -1;
    }

    /**
     * A push function that pushes Boards into the Stack.
     * @param b
     */
    public void push(BoardST b) {
        if (! isFull()){
            stackList[++topIndex] = b;
        }
        else {
            for (int s=0; s < maxSize-1; s++){
                if (s==0){
                    thelast = stackList[s];
                }
                stackList[s] = stackList[s+1];
            }
            stackList[topIndex] = b;
        }
        int bufferInt = new Integer(b.NUM_ROWS);
        numRowInStack = bufferInt;
    }

    /**
     * Returns the top object of the stack and pop it out.
     * @return a board
     */
    public BoardST pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackList[topIndex--];
    }

    /**
     * Return the number of rows of boards in stack.
     */
    public int getNumRowInStack(){
        return numRowInStack;
    }

    /**
     * Returns the top object and leave it inside.
     * @return a board
     */
    public BoardST peek() {
        return stackList[topIndex];
    }

    /**
     * Returns true if the stack if empty, false if not.
     * @return bool
     */
    public boolean isEmpty() {
        return (topIndex == -1);

    }
    /**
     * Returns true if the stack has Maxsize number of objects.
     * @return bool
     */
    public boolean isFull() {
        return (topIndex == maxSize - 1);
    }
}
