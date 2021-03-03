package com.conquer_java.knowledge.stream;

import java.util.*;

/**
 * Object.stream().forEach(element->{});
 * Java8引入新的stream.forEach()循环方式，最大优点是支持并发执行：
 * 1） 非常适合循环内部业务处理比较复杂的场景，比如：数据库查询，第三方接口调用等耗时操作。
 * 2） 并不适于简单业务场景，比如：组装，赋值等（最好使用普通循环，效率更快）。因为在多线程高并发场景下调用线程池必然需要耗费一些时间，得不偿失。
 */
public class DemoStream {
    public static void main(String[] args) {
        Map<Integer, String> map;
        final List<Map> list01 = new ArrayList<>();
        final List<Map> list02 = new ArrayList<>();
        final List<List<Map>> lists = new ArrayList<>();

        map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");
        list01.add(map);
        System.out.println(list01);

        map = new HashMap<>();
        map.put(1, "x");
        map.put(2, "y");
        map.put(3, "z");
        list02.add(map);
        System.out.println(list02);

        lists.add(list01);
        lists.add(list02);

        System.out.println(lists);
        lists.stream().flatMap(Collection::stream).forEach(list -> {
            list.forEach((k, v) -> {
                System.out.println("K: " + k + " -- " + "V: " + v);
            });
        });
    }
}
