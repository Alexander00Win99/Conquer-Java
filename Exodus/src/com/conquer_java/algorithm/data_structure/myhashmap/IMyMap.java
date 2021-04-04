package com.conquer_java.algorithm.data_structure.myhashmap;

/**
 * 定义MyHashMap的接口规范
 * @param <K>
 * @param <V>
 */
public interface IMyMap<K, V> {
    V put(K k, V v);
    V get(K k);
    int size();

    interface Entry<K, V> {
        K getKey();
        V getValue();
        V setValue(V value);
        boolean equals(Object o);
    }
}
