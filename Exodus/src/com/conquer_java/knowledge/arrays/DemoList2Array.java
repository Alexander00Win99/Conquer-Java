package com.conquer_java.knowledge.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * toArray()无参：返回类型是Object[]，需要手动强转；
 * toArray()泛型：变量类型符合泛型要求即可；
 * 结合流式编程：对于String可以<String>直接处理，但是对于int，long，double，char等基本类型需要使用包装类处理或者stream转换
 */
public class DemoList2Array {
    private static List<Integer> intList = new ArrayList<>();
    private static List<String> stringList = new ArrayList<>();

    public static void main(String[] args) {
        intList.add(1);
        intList.add(2);
        intList.add(3);
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");

        // 对于String可以<String>直接处理，但是对于int，long，double，char等基本类型需要使用包装类处理或者stream转换
        String[] strings = stringList.toArray(new String[stringList.size()]);
        System.out.println(Arrays.toString(strings));

        Integer[] array = intList.toArray(new Integer[intList.size()]);
        System.out.println(Arrays.toString(array));

        int[] arr = intList.stream().mapToInt(Integer::valueOf).toArray();
        System.out.println(Arrays.toString(arr));
    }
}
