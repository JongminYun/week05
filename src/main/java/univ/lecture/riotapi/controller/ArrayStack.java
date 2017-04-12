package univ.lecture.riotapi.controller;

/**
 * Created by Calvus on 2017-04-12.
 */
import java.util.EmptyStackException;

interface Stack {
    public boolean isEmpty();
    public Object peek();
    public void push(Object theObject);
    public Object pop();
}

public class ArrayStack implements Stack{
    int top;
    Object [] stack;

    public ArrayStack(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException ("initialCapacity must be >= 1");
        stack = new Object[initialCapacity];
        top = -1;
    }

    public ArrayStack() {
        this(10);
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public Object peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return stack[top];
    }

    public void push(Object theElement) {
        if(top == stack.length-1) ensureCapacity();
        stack[++top] = theElement;
    }

    public Object pop() {
        if (isEmpty())
            throw new EmptyStackException();
        Object topElement = stack[top];
        stack[top--] = null;
        return topElement;
    }

    private void ensureCapacity() {
        Object[] larger = new Object[stack.length*2];

        for (int index = 0; index < stack.length; index++)
            larger[index] = stack[index];

        stack = larger;
    }

    public String toString() {
        if(isEmpty())
            return "<empty stack>";
        String result = "<stack :";
        for(int i = top; i >= 0; i--)
            result += stack[i] + " ";
        return result + ">";
    }
}
