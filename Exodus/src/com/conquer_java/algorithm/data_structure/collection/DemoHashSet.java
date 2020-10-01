package com.conquer_java.algorithm.data_structure.collection;

/**
 * set: 无序不重复(读出顺序 != 写入顺序；相同元素只有一份)
 * HashSet的去重判断依据：equals() + hashCode()。因此，对于引用类型，如需HashSet去重，需要重写类的equals() & hashCode()。
 */

import java.util.HashSet;

public class DemoHashSet {
    public static HashSet<String> stringSet = new HashSet();
    public static HashSet<People> peopleSet = new HashSet();

    public static void main(String[] args) {
        stringSet.add("Alexander 温");
        stringSet.add("Alexander 温");
        stringSet.add("Alexander 温");
        stringSet.add("Alexander 温");
        System.out.println("stringSet的大小是：" + stringSet.size());

        peopleSet.add(new People("Alexander 温"));
        peopleSet.add(new People("Alexander 温"));
        peopleSet.add(new People("Alexander 温"));
        peopleSet.add(new People("Alexander 温"));
        System.out.println("peopleSet的大小是：" + peopleSet.size());
    }
}
