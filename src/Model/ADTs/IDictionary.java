package Model.ADTs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IDictionary<K, V> {
    V put(K key, V value);
    V remove(K key);
    V get(K key);
    Set<K> keys();
    Collection<V> values();
    HashMap<K,V> toMap();
    void setContent(Map<K, V> map);
    boolean isEmpty();
    IDictionary<K,V> duplicate();
    void clear();
}
