package Model.ADTs;

import java.util.List;

public interface IStack<T> {
    void push(T v);
    T pop();
    T peek();
    List<T> toList();
    boolean isEmpty();
}
