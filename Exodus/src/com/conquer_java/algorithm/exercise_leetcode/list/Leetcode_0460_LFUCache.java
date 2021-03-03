package com.conquer_java.algorithm.exercise_leetcode.list;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Leetcode_0460_LFUCache<K, V> {
    private class DoubleLinkedList {
        private class Node {
            K key;
            V value;
            int frequency;
            Node prev, next; // 前后指针

            public Node() {
            }

            public Node(K key, V value, int frequency) {
                this.key = key;
                this.value = value;
                this.frequency = frequency;
            }

            public Node(K key, V value, Node prev, Node next) {
                this.key = key;
                this.value = value;
                this.frequency = frequency;
                this.prev = prev;
                this.next = next;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder("");
                return sb.
                        append("Node{key=").append(key).
                        append(", value=").append(value).
                        append(", frequency=").append(frequency).
                        append('}').toString();
            }
        }

        private Node head, tail; // dummy节点，指向双向链表的首尾节点的指针
        private int size; // 用以维护节点数量（若是最小频次，则需根据是否(size == 1)判断删除最后一个节点还是撤销整个链表）

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
            size++;
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
            size--;
            return node;
        }

        public Node removeNode(Node node) {
            /* Begin - 删除节点node */
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = null;
            /* End - 删除节点node */
            size--;
            return node;
        }

        @Override
        public String toString() {
            int count = 0;
            StringBuilder sb = new StringBuilder("");
            sb.append("DoubleLinkedList{");
            Node pointer = head.next;
            while (pointer != tail) {
                count++;
                sb.append("ListNode-");
                /**
                 * 补齐位数。使之达到固定位数=4的数字展示效果。
                 * <==>等价实现</==>
                 * DecimalFormat format = new DecimalFormat("0000");
                 * int id = format.format(index);
                 */
                for (int i = 0; i < (4 - ("" + count).length()); i++) { // 整数固定占用位数为4
                    sb.append("0");
                }
                sb.append(count + ": ");
                sb.append(pointer); // 等效：sb.append(pointer.toString());
                pointer = pointer.next;
                if (pointer != tail) sb.append(", ");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    private final int capacity;
    private int size;
    private int leastFrequency;
    private HashMap<K, DoubleLinkedList.Node> dataMap; // K <--> Node
    private HashMap<Integer, DoubleLinkedList> frequencyMap; // Frequence <--> DoubleLinkedList

    public Leetcode_0460_LFUCache(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Illegal parameter: capacity should be larger than 0!");
        this.capacity = capacity;
        this.size = 0;
        this.leastFrequency = 0;
        this.dataMap = new HashMap<>();
        this.frequencyMap = new HashMap<>();
    }

    public V get(K key) {
        DoubleLinkedList.Node node = dataMap.get(key);
        if (node == null) return null;

        int frequency = node.frequency;
        node.frequency++;

        DoubleLinkedList preFrequencyList = frequencyMap.get(frequency); // frequency level list definitely exist
        preFrequencyList.removeNode(node);

        // 如若最小频次链表元素清空，显然minmumFrequency增1升级。
        if (leastFrequency == frequency && preFrequencyList.size == 0) {
            leastFrequency++;
            /**
             * 此处没有必要每次LFUNode所在链表清空节点以后销毁当前频次链表，
             * 因为随后某个时刻可能需要重新创建当前频次的frequencyList频次链表，
             * 因此此处不删可以避免来回删建的无谓开销。
             */
            //frequencyMap.remove(frequency); // 删或保留均可
        }

        if (!frequencyMap.containsKey(frequency + 1)) // (frequency + 1) level list maybe exist maybe not
            frequencyMap.put(frequency + 1, new DoubleLinkedList());
        frequencyMap.get(frequency + 1).addFirst(node);

        return node.value;
    }

    /**
     * 注意：put(K, V)在K已经存在的情况下，具有两种场景：
     * 1) 覆盖之后频率加一；
     * (1, 1) (1, 1, 1) ==> (1, 9) (1, 9, 2) ==> (1, 81) (1, 81, 3)
     * 2) 覆盖之后频率归一；
     * (1, 1) (1, 1, 1) ==> (1, 9) (1, 9, 1) ==> (1, 81) (1, 81, 1)
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        if (capacity <= 0 ) return;

        DoubleLinkedList.Node newNode = new DoubleLinkedList().new Node(key, value, 1); // new node always with frequency == 1
        if (dataMap.containsKey(key)) {
            /**
             * Begin —— 数据覆盖频率加一
             */
            dataMap.get(key).value = value;
            this.get(key);
            /**
             * End —— 数据覆盖频率加一
             */
            /**
             * Begin —— 数据覆盖频率归一
             */
            //DoubleLinkedList.Node oldNode = dataMap.remove(key);
            //frequencyMap.get(oldNode.frequency).removeNode(oldNode);
            //frequencyMap.getOrDefault(1, new DoubleLinkedList());
            //leastFrequency = 1;
            //frequencyMap.get(leastFrequency).addFirst(newNode);
            //dataMap.put(key, newNode);
            /**
             * End —— 数据覆盖频率归一
             */
        } else {
            /**
             * 当缓存容量到达上限时，map需要根据key删除对应节点地址引用，如果Node节点只存value信息，
             * 那么无法通过list.removeLast()的返回结果（Node对象）提取map的remove()操作所需key信息。
             */
            if (size >= capacity) {
                DoubleLinkedList.Node lfuNode = frequencyMap.get(leastFrequency).removeLast();
                dataMap.remove(lfuNode.key);
                size--;
            }
            if (!frequencyMap.containsKey(1)) frequencyMap.put(1, new DoubleLinkedList());
            frequencyMap.get(1).addFirst(newNode);
            dataMap.put(key, newNode); // 更新数据，同时频次重置为1
            leastFrequency = 1;
            size++;
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Leetcode_0460_LFUCache{" +
                "capacity=" + capacity +
                ", size=" + size +
                ", leastFrequency=" + leastFrequency +
                ", dataMap=" + dataMap +
                ", frequencyMap=" + frequencyMap +
                '}';
    }

    public static void main(String[] args) {
        DecimalFormat format = new DecimalFormat("0000"); // "000.000" "###.###"
        System.out.println(format.format(9));
        System.out.println(new DecimalFormat("###.###").format(12.34));
        System.out.println(new DecimalFormat("***.***").format(12.34));
        String string = new DecimalFormat("000.000").format(12.34);
        System.out.println(string);

        Leetcode_0460_LFUCache<Integer, String> cache = new Leetcode_0460_LFUCache<>(2);
        cache.put(1, "Alexander 温——开天辟地");
        System.out.println(cache); // 等效：System.out.println(cache.toString());
        cache.put(2, "Alex Wen——披荆斩棘");
        System.out.println(cache);
        cache.get(1);
        System.out.println(cache);
        cache.put(3, "Alexander00Win99——英俊潇洒");
        System.out.println(cache);
        cache.get(2);
        System.out.println(cache);
        cache.put(4, "ktpd00wen99——风流倜傥");
        System.out.println(cache);
        cache.get(3);
        System.out.println(cache);
        cache.get(4);
        System.out.println(cache);

        Leetcode_0460_LFUCache<Integer, Integer> demo = new Leetcode_0460_LFUCache<>(2);
        demo.put(2, 1);
        System.out.println(demo);
        demo.put(2, 2);
        System.out.println(demo);
        System.out.println(demo.get(2));
        System.out.println(demo);
        demo.put(1, 1);
        System.out.println(demo);
        demo.put(4, 1);
        System.out.println(demo);
        System.out.println(demo.get(2));
        System.out.println(demo);
    }
}
