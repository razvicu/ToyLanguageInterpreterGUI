package Model.ADTs;

import Model.Utils.IdManager;

import java.util.Map;

public class Heap<K,V> extends IdManager implements IHeap<K, V>  {
    private IDictionary<K, V> heapTable;

    public Heap() {
        heapTable = new Dictionary<K, V>(){};
    }

    @Override
    public void setContent(Map<K, V> map) {
        heapTable.clear();
        for ( Map.Entry<K, V> item : map.entrySet() )
            heapTable.put(item.getKey(), item.getValue());
    }

    @Override
    public Integer getFreeAddressIndex() {
        return super.getFreeIdIndex();
    }

    public IDictionary<K, V> getContent() {
        return heapTable;
    }

    @Override
    public String toString() {
        return heapTable.toString();
    }
}
