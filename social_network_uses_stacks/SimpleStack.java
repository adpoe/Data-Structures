
import java.io.Serializable;

/**
 * Created by tony on 9/18/15.
 */
public final class SimpleStack<T> implements SimpleStackInterface<T>, Serializable {

    // DEFAULT VARIABLES
    private T[] stack;
    private int top = 0;
    private static final int DEFAULT_CAPACITY = 25;
    private boolean initialized = false;

    //////////////////////////
    ////// CONSTRUCTORS //////
    /////////////////////////
    /** Creates an empty stack whose initial capacity is 25. */
    public SimpleStack() {
        this(DEFAULT_CAPACITY);
    } // end default constructor

    /**
     * Creates an empty stack having a given initial capacity.
     *
     * @param desiredCapacity The integer capacity desired.
     */
    public SimpleStack(int desiredCapacity) {
            // The cast is safe because the new array contains null entries.
            @SuppressWarnings("unchecked")
            T[] tempStack = (T[]) new Object[desiredCapacity]; // Unchecked cast
            stack = tempStack;
            top = 0;
            initialized = true;
    } // end constructor

    /** Throws an exception if this object is not initialized.
     *
     */
    private void checkInitialization()
    {
        if (!initialized)
            throw new SecurityException("ArrayBag object is not initialized " +
                    "properly.");
    }


    ////////////////////////////////////////
    ////// INTERFACE REQUIRED METHODS //////
    ////////////////////////////////////////
    /**
     * Adds a new T object to the top of the stack
     * @param item The item to be added
     * @return True if successful, false if there is no more room
     */
    public boolean add(T item) {
        boolean result = true;
        // make an array of T's and put item in it, within else if for success
        checkInitialization();
        if (isFull()) {
            result = false;
            // stackResize(); -> commented out for now
        } else { // Assertion: result is true here
            // Place number 0 in the stack is 0
            stack[top] = item;
            top ++;
        } // end if

        return result;
    }

    /**
     * Removes the top T object
     * @return The item that is removed, or null if no item can be removed
     */
    public T remove() {
        checkInitialization();

        T result = null;
        // Ensure TOP  CANNOT be less than zero
        // Otherwise, we'll have problems adding later
        int currentTop = top;
        if (currentTop - 1 < 0) {
            System.out.println("Stack is empty. Nothing else to remove.");
        } else {
            // Decrement top
            top --;
            // Store top item of Stack in result
            result = stack[top];
            // Set was removed, so replace it with null in the stack
            stack[top] = null;
        }

        return result;
    }

    /**
     * Returns an array of size n containing the top n items from the stack. The most recently-added
     * items should be listed first.
     * @param howMany The number of items to return
     * @return An array of the most recently-added items, or null if the stack does not contain
     * enough items
     */
    public Object[] topItems(int howMany) {
        Object[] contents = new Object[howMany];
        for(int i = (howMany-1); i>=0; i--){
            // If statement to ensure this DOES NOT yield ArrayOutOfBounds Exception
            if (top - (i+1) >= 0) {
                contents[i] = stack[top - (i + 1)];
            } else {
                contents[i] = null;
            }
        }
        return contents;
    }

    /**
     * Determines if the stack contains the specified item
     * @param item The item in question
     * @return True if the item is contained in the stack, false otherwise
     */
    public boolean contains(T item) {
        checkInitialization();
        return getIndexOf(item) > -1;
    }

    /**
     * Determines if the stack is empty
     * @return True if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return top == 0;
    }

    /**
     * Determines if the stack is full (at capacity)
     * @return True if the stack is full, false otherwise
     */
    public boolean isFull() {
        return top >= stack.length;
    }

    /**
     * Determines the size of the stack (the current number of objects in the stack, NOT the
     * capacity)
     * @return The number of objects in the stack
     */
    public int size() {
        return this.top;
    }

    /**
     * Finds the location of a given item in stack, if present.
     * If item is NOT found in the stack, return -1
     * @param item - The item to search for
     * @return int - location of the item in the stack
     */
    private int getIndexOf(T item) {
        int location = -1;
        boolean stillLooking = true;
        int index = 0;
        while (stillLooking && (index < top)) {
            if (item.equals(stack[index])) {
                stillLooking = false;
                location = index;
            } // end if
            index++;
        } // end for
        // Assertion:  if location > -1, item is in the stack.
        // If location == -1, stack does NOT contain the item
        return location;
    } // end getIndexOf

    /**
     * Doubles the size of the current stack, keeping the items in order.
     * Commented out for now.
     */
    /*
    private void stackResize() {
        int currentLength = stack.length;
        int newLength = currentLength * 2;
        T[] newStack = (T[]) new Object[newLength];
        // Manual array copy
        for (int i = 0; i < currentLength; i++) {
            newStack[i] = stack[i];
        }
        stack = newStack;
    } */
}
