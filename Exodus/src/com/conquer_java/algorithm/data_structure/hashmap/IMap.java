package com.conquer_java.algorithm.data_structure.hashmap;

public interface IMap<K, V> {
    V put(K k, V v);
    V get(K k);
    int size();
}
