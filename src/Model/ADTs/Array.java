package Model.ADTs;

import java.util.ArrayList;
import java.util.List;

public class Array<T> implements IList<T> {

    private ArrayList<T> arrayList;

    public Array() {
        arrayList = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        arrayList.add(element);
    }

    @Override
    public T get(int index) {
        return arrayList.get(index);
    }

    @Override
    public List<T> toList() {
        return arrayList;
    }

    @Override
    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    @Override
    public String toString() {
        if ( arrayList.isEmpty() )
            return "Empty output list\n";
        StringBuilder res = new StringBuilder();
        for (T el : arrayList)
            res.append(el.toString()).append("\n");
        return res.toString() + "\n";
    }
}
