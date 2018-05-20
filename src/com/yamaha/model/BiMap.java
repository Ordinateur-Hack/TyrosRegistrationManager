package com.yamaha.model;

import java.util.HashMap;

public class BiMap<K, V> {

    private HashMap<K, V> map = new HashMap<K, V>();
    private HashMap<V, K> inversedMap = new HashMap<V, K>();

    public void put(K k, V v) {
        map.put(k, v);
        inversedMap.put(v, k);
    }

    public V get(K k) {
        return map.get(k);
    }

    public K getKey(V v) {
        return inversedMap.get(v);
    }

}
