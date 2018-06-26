package com.yamaha.model;

import java.util.HashMap;

// See https://stackoverflow.com/a/35219750
// and https://google.github.io/guava/releases/19.0/api/docs/com/google/common/collect/BiMap.html

/**
 * A BiMap is an associative array. It contains two {@link HashMap}s which allows
 * to get the mapped value for a given key, but also to get the key for a given mapped value.
 * <br>For example, it is used in the {@link com.yamaha.controller.StyleController} for
 * linking the StyleSections which the appropriate buttons in the GUI.
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class BiMap<K, V> {

    private HashMap<K, V> map = new HashMap<>();
    private HashMap<V, K> inversedMap = new HashMap<>();

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
