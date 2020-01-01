package com.conque_java.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 举例：
 * "abcd";
 * a-: abcd, abdc, acbd, acdb, adcb, adbc
 * b-: bacd, badc, bcad, bcda, bdca, bdac
 * c-: cbad, cbda, cabd, cadb, cdab, cdba
 * d-: dbca, dbac, dcba, dcab, dacb, dabc
 *
 * 数组拷贝的四种方法：
 * 1) obj.clone()
 * 2) System.arrayCopy(public static native void arraycopy(Object src, int srcPos, Object dest, int desPos, int length))
 * 3) Arrays.copyOf
 * 4) Arrays.copyOfRange
 *
 * public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
 *     @SuppressWarnings("unchecked")
 *     T[] copy = ((Object)newType == (Object)Object[].class)
 *         ? (T[]) new Object[newLength]
 *         : (T[]) Array.newInstance(newType.getComponentType(), newLength);
 *     System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
 *     return copy;
 * }
 *
 * public static <T,U> T[] copyOfRange(U[] original, int from, int to, Class<? extends T[]> newType) {
 *     int newLength = to - from;
 *     if (newLength < 0)
 *         throw new IllegalArgumentException(from + " > " + to);
 *     @SuppressWarnings("unchecked")
 *     T[] copy = ((Object)newType == (Object)Object[].class)
 *         ? (T[]) new Object[newLength]
 *         : (T[]) Array.newInstance(newType.getComponentType(), newLength);
 *     System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
 *     return copy;
 * }
 *
 * 深度拷贝的两种方法：
 * 1) 实现Cloneable接口(重写clone方法)；
 * 2) 实现Serializable接口(序列化+反序列化)；
 */
public class DemoStringPermutation {
    private static Set set = new HashSet<String>();
    public static void permutation(char[] arr, int begin, int end, int depth) {
        System.out.println("Depth - " + depth + ":");
        if (begin == end) {
            set.add(new String(arr));
            System.out.println(Arrays.toString(arr)); // arr.toString()方法返回地址；Arrays.toString()方法才能打印数组内容。
            return;
        }

        for (int i = begin; i <= end; i++) {
            swap(arr, begin, i);
            permutation(arr.clone(), begin + 1, end, depth + 1);
            swap(arr, begin, i);
        }
    }

    private static void swap(char[] arr, int i, int j) {
        if (i == j) return;
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        String str = "abcd";
        char[] arr = str.toCharArray();
        permutation(arr, 0, arr.length - 1, 1);
        System.out.println(DemoStringPermutation.set);
    }
}
