package Model.ADTs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Stack<T> implements IStack<T> {

    private java.util.Stack<T> stack;

    public Stack() {
        stack = new java.util.Stack<>();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public List<T> toList() {
        ArrayList<T> arr = new ArrayList<>(stack);
        Collections.reverse(arr);
        return arr;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        if ( stack.isEmpty() )
            return "Empty stack\n";

        java.util.Stack<T> auxStack = new java.util.Stack<>();

        StringBuilder str = new StringBuilder();

        while (!stack.empty()) {
            auxStack.push(stack.pop());
            str.append(auxStack.peek().toString());
        }

        while (!auxStack.empty())
            stack.push(auxStack.pop());

        return str.toString();

    }
}
