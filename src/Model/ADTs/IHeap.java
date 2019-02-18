package Model.ADTs;

import java.util.Map;

public interface IHeap<K,V> {
    void setContent(Map<K, V> map);
    Integer getFreeAddressIndex();
}
