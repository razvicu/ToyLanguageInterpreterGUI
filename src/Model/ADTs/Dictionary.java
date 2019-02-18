package Model.ADTs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import Exception.*;

public class Dictionary<K, V> implements IDictionary<K, V> {

    private HashMap<K, V> hashMap;

    public Dictionary() {
        hashMap = new HashMap<>();
    }

    public Dictionary(HashMap<K,V> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public V put(K key, V value) {
        return hashMap.put(key, value);
    }

    @Override
    public V remove(K key) {
        return hashMap.remove(key);
    }

    @Override
    public V get(K key) {
        V value = hashMap.get(key);
        if ( value != null )
            return value;
        throw new UndefinedVariableException();
    }

    @Override
    public Set<K> keys() {
        return hashMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return hashMap.values();
    }

    @Override
    public HashMap<K, V> toMap() {
        return hashMap;
    }

    @Override
    public void setContent(Map<K, V> map) {
        hashMap.clear();
        for ( Map.Entry<K,V> item : map.entrySet() )
            hashMap.put(item.getKey(), item.getValue());
    }

    @Override
    public boolean isEmpty() {
        return hashMap.isEmpty();
    }

    @Override
    public IDictionary<K, V> duplicate() {
        HashMap<K,V> newHashMap = new HashMap<>(hashMap);
        return new Dictionary<>(newHashMap);
    }

    @Override
    public void clear() {
        hashMap.clear();
    }

    @Override
    public String toString() {
        if (hashMap.isEmpty())
            return "Empty table\n";

        StringBuilder res = new StringBuilder();
        for (K key : this.keys() )
            res.append(key.toString()).append(" -> ").append(hashMap.get(key).toString()).append("\n");

        return res.toString();
    }
}
