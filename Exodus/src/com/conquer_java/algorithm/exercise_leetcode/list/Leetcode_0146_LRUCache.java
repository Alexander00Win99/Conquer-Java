package com.conquer_java.algorithm.exercise_leetcode.list;

import java.util.HashMap;

/**
 * 1) HashMap<K, Node>维护key到node地址的映射，实现查询O(1)，“以空间换时间”，避免List的查询O(n)弊端；
 * 2) List对于某个已知节点（HashMap提供node地址引用）的插入删除操作只需指针操作，本身时间复杂度是O(1)，
 * addFirst()/rmoveFirst()和addLast()/removeLast()等对于头尾节点和removeNode()对于特定节点的API操作，
 * 需要依赖head和tail头尾两个指针，才能实现O(1)；
 * 3) 综上所述：HashMap数据结构保证查询操作O(1)；List数据结构保证增删操作O(1)。那么，还有一个两者联动问题：
 * 对于List的增删操作需要相应修改HashMap中的key到node的映射关系，如果是增加节点，用户提供key和value，生成node，
 * Hashmap直接新增key到node的映射即可，但是如果是删除节点，HashMap必须能够根据node信息找到key,从而remove(K)删除，
 * 如此，显然这就要求Node类中必须同时存储key和value信息，仅仅存储vlaue无法满足需求。
 *
 * @param <K>
 * @param <V>
 */
public class Leetcode_0146_LRUCache<K, V> {
    private class DoubleLinkedList {
        private class Node {
            K key;
            V value;
            Node prev, next; // dummy节点，指向双向链表的首尾节点的指针

            public Node() {
            }

            public Node(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public Node(K key, V value, Node prev, Node next) {
                this.key = key;
                this.value = value;
                this.prev = prev;
                this.next = next;
            }
        }

        private Node head, tail;

        public DoubleLinkedList() {
            this.head = new Node();
            this.tail = new Node();
            head.next = tail;
            tail.prev = head;
        }

        public void addFirst(Node node) {
            /* Begin - node加入首位 */
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            /* End - node加入首位 */
        }

        public Node removeLast() {
            Node node = tail.prev;
            if (tail.prev == head) return null;
            /* Begin - 删除末位节点 */
            node.prev.next = tail;
            tail.prev = node.prev;
            node.prev = null;
            node.next = null;
            /* End - 删除末位节点 */
            return node;
        }

        public Node removeNode(Node node) {
            /* Begin - 删除节点node */
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = null;
            /* End - 删除节点node */
            return node;
        }
    }

    private final int capacity;
    private int size;
    private HashMap<K, DoubleLinkedList.Node> map;
    private DoubleLinkedList list;

    public Leetcode_0146_LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.map = new HashMap<>();
        list = new DoubleLinkedList();
    }

    public V get(K key) {
        DoubleLinkedList.Node node = map.get(key);
        if (node == null) return null;

        list.removeNode(node);
        list.addFirst(node);

        return node.value;
    }

    public void put(K key, V value) {
        DoubleLinkedList.Node node = new DoubleLinkedList().new Node(key, value);
        if (map.containsKey(key)) {
            list.removeNode(map.get(key));
            list.addFirst(node);
            map.put(key, node);
        } else {
            if (size >= capacity) { // 缓存容量到达上限之时，map需要根据key删除对应节点地址引用，如果Node节点只存value，那么list.removeLast()的返回结果就无法向map提供remove()操作所需参数key。
                DoubleLinkedList.Node last = list.removeLast();
                map.remove(last.key);
                size--;
            }
            list.addFirst(node);
            map.put(key, node);
            size++;
        }
    }

    public int getSize() {
        return size;
    }

    public static void main(String[] args) {
        Leetcode_0146_LRUCache<Integer, String> cache = new Leetcode_0146_LRUCache<>(2);
        cache.put(1, "Alexander 温——开天辟地");
        cache.put(2, "Alex Wen——披荆斩棘");
        cache.get(1);
        cache.put(3, "Alexander00Win99——英俊潇洒");
        cache.get(2);
        cache.put(4, "ktpd00wen99——风流倜傥");
        cache.get(3);
        cache.get(4);
    }
}
