package Model.ADTs;

import java.util.List;

public interface IList<T> {
    void add(T element);
    T get(int index);
    List<T> toList();
    boolean isEmpty();
}
