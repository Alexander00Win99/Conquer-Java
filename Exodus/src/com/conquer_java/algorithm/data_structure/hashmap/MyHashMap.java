package com.conquer_java.algorithm.data_structure.hashmap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 【实验目的】：摹写HashMap，理解原理。
 * 一、懒加载机制：
 *     首次put操作之时才会生成具体大小的HashMap集合，之前new构造函数只是初始化底层数组为null，如此可以避免声明之后不用浪费空间。
 * 二、数组长度为何必须是2的次方数？
 *     (table.length - 1)的结果，形如“0111,1111,...,1111,1111”，使用与操作计算元素在数组中的存储位置，可以充分利用hash值各位的随机性。
 *     设想，如果数组长度是个奇数，那么减一之后的结果的最后一位必然为0，此时两个不同对象的hash值与其按位与求取存储位置必然只会存储在偶数下标位，增加冲突且浪费空间。
 * 三、为何采用按位与操作？
 *     按位与效率高，并且(table.length - 1)按位与操作之后，可以抹除hash值高位，得出符合数组容量范围的存储位置。
 * 四、为何进行二次哈希（扰动函数）操作？
 *     (table.length - 1)按位与操作，只能利用hash值低位数字的随机性，为了保证存储位置的均匀性，必须充分利用hash值从高到低所有32位各位的随机性，因此，通过右移高位使其参与低位运算增加随机性。
 * 五、在多线程高并发环境下扩容可能形成死循环(JDK1.8采用尾插法避免此点)：
 *     两个线程同时执行到transfer()方法中的“node.next = newTable[index];”，线程1停止，线程2继续执行；
 *     此时，线程1的node和next指针分别指向：oldTable[i]和oldTable[i].next；
 *     线程2继续执行转移元素操作，将老数组oldTable[i]位置的旧桶oldBucket中前两元素转移值新数组后停止；
 *     此时，线程2转移操作的结果：在新数组newTable[index]位置的新桶newBucket中前两元素依次是oldTable[i].next和oldTable[i]，也即原来的next节点的next指针指向原来的node节点，两者先后顺序发生颠倒；
 *     线程1唤醒继续执行“node.next = newTable[index];”语句，操作导致：原来的node节点的next指针指向原来的next节点，形成死循环；
 *
 * @param <K>
 * @param <V>
 */
public class MyHashMap<K, V> implements IMyMap<K, V> {
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75F; // 缺省负载因子|加载因子
    final float loadFactor; // 负载因子|加载因子
    int threshold; // 扩容阈值
    transient Node<K, V>[] table = null;
    transient int size = 0;
    transient int modCount; // 记录结构变更(元素数量发生变化，例如，rehash操作)次数，用于Fail-Fast机制。

    public MyHashMap() { // 无参构造器——构造空HashMap数据结构(16, 0.75)：默认初始容量，默认加载因子，其他属性使用缺省值。
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
        //this.loadFactor = DEFAULT_LOAD_FACTOR; // JDK1.8
    }

    public MyHashMap(int initialCapacity) { // 单参构造器——构造空HashMap数据结构(initialCapacity, 0.75)：指定初始容量，默认加载因子。
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactor) { // 双参构造器——构造空HashMap数据结构(initialCapacity, loadFactor)：指定初始容量，指定加载因子。
        if (initialCapacity < 0) throw new IllegalArgumentException("Illegal initial capacity: \n" + initialCapacity);
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) throw new IllegalArgumentException("Illegal load factor: \n" + loadFactor);
        if (initialCapacity > MAXIMUM_CAPACITY) initialCapacity = MAXIMUM_CAPACITY;
        this.loadFactor = loadFactor;
        this.threshold = initialCapacity; // 临时的扩容阈值，后续需要重新计算。
        //this.threshold = tableSizeFor(initialCapacity); // JDK1.8
        init(); // 空方法，用于未来子对象扩展
    }

    public MyHashMap(Map<? extends K, ? extends V> map) {
        this(Math.max((int)(map.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
        putAllForCreate(map); // 原有map中的所有元素挪入MyHashMap之中
    }

    public void _debugCapacity() {
        System.out.println("当前容器容量=数组长度table.length: " + table.length);
        System.out.println("当前扩容阈值threshold：" + this.threshold);
        System.out.println("当前元素数量size：" + this.size);
    }

    private void init() {
        // 空方法，用于未来子对象扩展
    }

    private void putAllForCreate(Map<? extends K, ? extends V> map) {

    }

    private void inflateTable(int assignedCapacity) {
        // JDK1.7计算扩容阈值：生成能够容纳指定容量assignedCapacity的最小2的次方数。
        int realCapacity = roundUpToPowerOf2(assignedCapacity);
        // 重新计算扩容阈值
        threshold = (int) Math.min(realCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
        // 分配底层数组存储空间
        table = new Node[realCapacity];
        //initHashSeedAsNeeded(realCapacity); // JDK1.8移除sun.misc.Hashing包
    }

    // JDK1.7: Returns a power of two size for the given target capacity.
    private int roundUpToPowerOf2(int assignedCapacity) {
        return assignedCapacity >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY : (assignedCapacity > 1)
                ? Integer.highestOneBit((assignedCapacity - 1) << 1) : 1;
        //return tableSizeFor(assignedCapacity); // JDK1.8
    }

    // JDK1.8: Returns a power of two size for the given target capacity.
    private int tableSizeFor(int assignedCapacity) { // JDK1.8计算数组容量：生成能够容纳指定容量assignedCapacity的最小2的次方数。
        int n = assignedCapacity - 1; // 开始先减一，结束再加一：保证假如指定2^m容量经过计算仍然得到2^m容量而非得到容量2^(m+1)
        n |= n >>> 1; // 保证从左端第一位非零位起高2位全1
        n |= n >>> 2; // 保证从左端第一位非零位起高4位全1
        n |= n >>> 4; // 保证从左端第一位非零位起高8位全1
        n |= n >>> 8; // 保证从左端第一位非零位起高16位全1
        n |= n >>> 16; // 保证从左端第一位非零位起高32位全1
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     * 对外接口：存储键值对数据——先在桶(单链表)中遍历，查看是否存在同键元素，有则替换，无则添加。
     * JDK1.7头插法——可能成环溢出；在遍历过程中遇到相同键即可替换返回，效率较高。
     * JDK1.8尾插法——避免成环溢出；在遍历过程中遇到相同键亦需遍历直至链表结尾，效率较低。
     * @param k
     * @param v
     * @return
     */
    @Override
    public V put(K k, V v) {
        // Step-01: 懒加载：首次put才会初始化底层数组table(此时threshold存储用户指定的initialCapacity初始容量)
        if (table == null) inflateTable(threshold); // 使用初始容量生成的2的次方数作为真实大小分配底层数组存储空间
        // Step-02: 若键为空值null，则置于table[0]位置，并且table[0]只会存在一个对象，后来赋值覆盖前面赋值。
        if (k == null) return putForNullKey(v);
        // Step-03: key通过hash()获取hash值——String类型键，可以利用String自身hash缓存实现快速处理
        int hash = hash(k);
        // Step-04: 计算数组下标
        int index = indexForHash(hash, table.length);
        // Step-05: 如果当前位置table[index]是包含多个元素的链表，遍历链表之中所有元素，遇到键值相同元素替换旧值为新值然后返回旧值。
        Node<K, V> node = table[index];
        while (node != null) {
            Object o;
            if (node.hash == hash && ((o = node.getKey()) == k || o.equals(k))) {
                V oldValue = node.getValue();
                node.value = v;
                node.recordAccess(this);
                return oldValue; // 遇到key相等，直接返回，无须继续遍历。
            }
            node = node.next;
        }
        modCount++;
        // Step-06: 添加元素节点(需做扩容判断)
        addNode(hash, k, v, index);
        return null;
    }

    /**
     * 添加元素之前，判断是否需要扩容，如需扩容，hash和index需要在扩容后重新计算。
     * 一、addNode()操作可能导致当前HashMap容量超限“阈值”，因此需要判断(size >= threshold)。
     * 例如：put操作无法肯定新增对象是否超出原有MyHashMap容量限制，需要进行超限判断。
     * 二、createNode()已经可以肯定当前HashMap容量足够容纳新增节点对象，
     * 例如：MyHashMap(Map<? extends K, ? extends V> m)构造函数将Map中的元素移至MyHashMap之中。
     * @param hash hash
     * @param key
     * @param value
     * @param bucketIndex 链/桶所在数组下标位置
     */
    private void addNode(int hash, K key, V value, int bucketIndex) {
        if ((size >= threshold) && (null != table[bucketIndex])) {
            resize(2 * table.length);
        }
        hash = (key == null) ? 0 : hash(key);
        bucketIndex = indexForHash(hash, table.length);
        createNode(hash, key, value, bucketIndex);
    }

    // 确保当前数组容量足够之后，创建一个新的键值对节点对象，采用头插法置于数组之中(链头位置)，更新size。
    private void createNode(int hash, K key, V value, int bucketIndex) {
        table[bucketIndex] = new Node<>(hash, key, value, table[bucketIndex]);
        size++;
    }

    private void resize(int newCapacity) {
        Node[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Node[] newTable = new Node[newCapacity];
        transfer(newTable);
        //transfer(newTable, initHashSeedAsNeeded(newCapacity)); // JDK1.8移除sun.misc.Hashing包
        table = newTable;
        threshold = (int) Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
    }

//    private final boolean initHashSeedAsNeeded(int capacity) {
//        boolean currentAltHashing = sun.misc.VM.isBooted() && (capacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
//        boolean switching = currentAltHashing ^ useAltHashing;
//        if (switching) {
//            hashSeed = useAltHashing ? sun.misc.Hashing.randomHashSeed(this) : 0;
//        }
//        return switching;
//    }

    /**
     * 在从旧容器转移节点到新容器过程中，各个节点hash不变，index可能改变。
     * 一、不能整体移动(链头带着整个链所有元素一起全部直接移到新的数组某个下标位置)，因为同一链中不同元素节点重新计算index下标以后可能不再位于同一位置。
     *     此点恰是扩容的理想目的——位于新的链中，减少哈希冲突，从而缩短链表，提升查询效率。
     * 二、各个链/桶中元素移动之后如果依然位于新数组的同一链上，那么先后顺序发生颠倒，前面变后面，后面变前面。
     * 三、在多线程高并发环境下扩容可能形成死循环(JDK1.8采用尾插法避免此点)。
     *
     * @param newTable
     * //@param rehash
     */
    private void transfer(Node[] newTable) {
        int newCapacity = newTable.length;
//        for (Node<K,V> node : table) {
//            int oldIndex = indexForHash(node.hash, table.length);
//            while (null != node) {
//                Node<K, V> next = node.next;
//                int index = indexForHash(node.hash, newCapacity);
//                node.next = newTable[index];
//                newTable[index] = node;
//                node = next;
//            }
//            table[oldIndex] = null; // 释放旧数组上各个下标的对象引用——保证for循环以后，旧数组置空。
//        }
        Node<K, V>[] oldTable = table;
        for (int i = 0; i < oldTable.length; i++) {
            Node<K, V> node = oldTable[i];
            if (node != null) {
                oldTable[i] = null; // 保证for循环完成之后，oldTable各位置空。
                do {
                    Node<K, V> next = node.next;
                    int index = indexForHash(node.hash, newCapacity);
                    node.next = newTable[index];
                    newTable[index] = node;
                    node = node.next;
                } while (node != null);
            }
        }
    }
//    private void transfer(Node[] newTable, boolean rehash) {
//        int newCapacity = newTable.length;
//        for (Node<K,V> node : table) {
//            while (null != node) {
//                Node<K, V> next = node.next;
//                if (rehash) {
//                    node.hash = (null == node.key) ? 0 : hash(node.key);
//                }
//                int index = indexForHash(node.hash, newCapacity);
//                node.next = newTable[index];
//                newTable[index] = node;
//                node = next;
//            }
//        }
//    }

    private V putForNullKey(V v) {
        for (Node<K, V> node = table[0]; node != null; node = node.next) {
            if (node.key == null) {
                V oldValue = node.value;
                node.value = v;
                node.recordAccess(this);
                return oldValue;
            }
        }
        addNode(0, null, v, 0);
        return null;
    }

    @Override
    public V get(K k) {
        if (size == 0) return null;
        // Step-01: key通过hash()计算得到hash作为数组下标index
        int hash = hash(k);
        // Step-02: 循环遍历数组下标位置的链表之中的每个元素（key值相同或者相等即为所需元素）
        Node<K, V> e = table[hash];
        while (e != null) {
            if (k == e.getKey() || k.equals(e.getKey())) {
                return e.getValue(); // ！！！！【重点注意】——如果上面Entry<K, V>泛型忘记书写，此处报错Object->V的类型匹配错误！！！！
            }
            e = e.next;
        }
        return null;
        // Step-03: 通过index存储对应Entry对象
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * 扰动函数——提高哈希值分布随机性，减少哈希冲突概率。
     * JDK1.7：比较复杂——4次位运算+5次异或操作
     * JDK1.8：进行简化——1次位运算+1次异或操作
     *
     * 【HashMap VS Hashtable】
     * Hashtable直接对key进行hashCode()运算，若键为null，则抛异常。
     * 结论：HashMap允许键key为空值null并且唯一(值value可以为空值null并且可以拥有多个)；Hashtable不允许键Key为空值null。
     *
     * @param o
     * @return
     */
    private int hash(Object o) {
        // JDK1.7
        //int h = hashSeed;
        //if (h != 0 && o instanceof String) return sun.misc.Hashing.StringHash32((String) o); // JDK1.8移除sun.misc.Hashing包
        //h ^= o.hashCode();
        //h ^= (h >>> 20) ^ (h >>> 12);
        //h ^= (h >>> 7) ^ (h >>> 4);
        //return h;

        // JDK1.8
        int h;
        return (o == null) ? 0 : (h = o.hashCode()) ^ (h >>> 16); // 高位右移16参与低位运算，增加位运算特征性
    }

    /**
     * 【为何不直接用hash作为下标存入数组】
     * 因为：hash是根据对象内存地址计算出来的一个int整形数值，取值范围[-2^31, 2^31 - 1]，超出HashMap容量范围。
     * @param hash
     * @param length
     * @return
     */
    private int indexForHash(int hash, int length) {
        return hash & (length - 1); // 使用按位与&位运算等价替换取模%运算，求取存储下标位置。
    }

    @Override
    public String toString() {
        return "MyHashMap{" +
                "table=" + Arrays.toString(table) +
                ", size=" + size +
                '}';
    }

    class Node<K, V> implements IMyMap.Entry<K,V> {
        //private final int hash; // 后续可能需要rehash，因此不能final修饰
        private int hash;
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(int hash, K key, V value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public final V getValue() {
            return value;
        }

        @Override
        public final V setValue(V newValue) {
            V oldValue = value;
            this.value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) { // 键值同时相等或者两者同一对象返回true，其他返回false。
            if (o == this) return true;
            if (o instanceof IMyMap.Entry) {
                IMyMap.Entry<?, ?> e = (IMyMap.Entry<?, ?>) o;
                if (((key == e.getKey()) || (key != null && key.equals(e.getKey()))) && ((value == e.getValue()) || (value != null && value.equals(e.getValue())))) return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }

        // Entry/Node的hashcode计算包括key和value两部分：key的hashcode异或value的hashcode：键和值任一部分发生改动，均会导致hashCode()不同结果。
        @Override
        public int hashCode() {
            return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
        }

        void recordAccess(MyHashMap<K, V> m) {
            // 当添加元素时（调用put()或者putForNullKey()），涉及替换已有元素，调用该方法进行处理，当前未作任何处理。
        }

        void recordRemoval(HashMap<K, V> m) {
            // 当删除元素时，调用该方法进行处理，当前未作任何处理。
        }
    }
}
