package com.conquer_java.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 举例：
 * "abcd"——字符串内各个字符互不相同
 * STEP-1：依次交换第1个字符和包括自身在内的各个位置的字符，如此保证交换以后"X---"中的第1个字符在各个结果之间相互唯一；
 * STEP-2：依次交换第1个字符和包括自身在内的各个位置的字符，如此保证交换以后"X"+"Y--"中的第1个字符在各个结果之间相互唯一；
 * STEP-3：依次交换第1个字符和包括自身在内的各个位置的字符，如此保证交换以后"XY"+"Z-"中的第1个字符在各个结果之间相互唯一；
 * STEP-4：依次交换第1个字符和包括自身在内的各个位置的字符，如此保证交换以后"XYZ"+"-"中的第1个字符在各个结果之间相互唯一；
 * a-: abcd, abdc, acbd, acdb, adcb, adbc // STEP-1："abcd"之中，0-a和0-a进行交换=>"a---"；STEP-2："bcd"之中，0-b和0-b进行交换=>"ab--"；STEP-3："cd"之中，0-c和0-c进行交换=>"abc-"；
 *                                                                                                                                   STEP-3："cd"之中，0-c和1-d进行交换=>"abd-"；
 *                                                                                        STEP-2："bcd"之中，0-b和1-c进行交换=>"ac--"；STEP-3："bd"之中，0-b和0-b进行交换=>"acb-"；
 *                                                                                                                                    STEP-3："bd"之中，0-b和1-d进行交换=>"acd-"；
 *                                                                                        STEP-2："bcd"之中，0-b和2-d进行交换=>"ad--"；STEP-3："cb"之中，0-c和0-c进行交换=>"adc-"；
 *                                                                                                                                    STEP-3："cb"之中，0-c和1-b进行交换=>"adb-"；
 * b-: bacd, badc, bcad, bcda, bdca, bdac // STEP-1："abcd"之中，0-a和1-b进行交换=>"b---"；STEP-2："acd"之中，0-a和0-a进行交换=>"ba--"；STEP-3："cd"之中，0-c和0-c进行交换=>"abc-"；
 *                                                                                        STEP-2："acd"之中，0-a和1-c进行交换=>"bc--"；STEP-3："cd"之中，0-c和0-c进行交换=>"abc-"；
 *                                                                                        STEP-2："acd"之中，0-a和2-d进行交换=>"bd--"；STEP-3："cd"之中，0-c和0-c进行交换=>"abc-"；
 * c-: cbad, cbda, cabd, cadb, cdab, cdba // STEP-1："abcd"之中，0-a和2-c进行交换=>"c---"；STEP-2："bad"之中，0-b和0-b进行交换=>"cb--"；STEP-3："cd"之中，0-c和0-c进行交换=>"abc-"；
 *                                                                                        STEP-2："bad"之中，0-b和1-a进行交换=>"ca--"；STEP-3："cd"之中，0-c和0-c进行交换=>"abc-"；
 *                                                                                        STEP-2："bad"之中，0-b和2-d进行交换=>"cd--"；STEP-3："cd"之中，0-c和0-c进行交换=>"abc-"；
 * d-: dbca, dbac, dcba, dcab, dacb, dabc // STEP-1："abcd"之中，0-a和3-d进行交换=>"d---"；STEP-2："bca"之中，0-b和0-b进行交换=>"db--"；STEP-3："cd"之中，0-c和0-c进行交换=>"abc-"；
 *                                                                                        STEP-2："bca"之中，0-b和1-c进行交换=>"dc--"；STEP-3："cd"之中，0-c和0-c进行交换=>"abc-"；
 *                                                                                        STEP-2："bca"之中，0-b和2-a进行交换=>"da--"；STEP-3："cd"之中，0-c和0-c进行交换=>"abc-"；
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

        /**
         * 从头到尾，首个字符和字符串中每个字符依次进行交换(基于原字符串各个字符不同的假设，如此交换依然可以确保新字符串的唯一性)
         */
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

    private static void printArray(String[] stringArray) {
        for (int i = 0; i < stringArray.length; i++) {
            String begin = i == 0 ? "[" : "";
            String end = i == stringArray.length - 1 ? "]\n" : ", ";
            System.out.print(begin + stringArray[i] + end);
        }
    }

    public static void main(String[] args) {
        String str = "abcd";
        char[] charArray = str.toCharArray();
//        permutation(charArray, 0, charArray.length - 2, 1);
//        permutation(charArray, 1, charArray.length - 1, 1);
        permutation(charArray, 0, charArray.length - 1, 1);
        System.out.println(DemoStringPermutation.set);
        // Object -> String : OK | Object[] -> String[] : NOK
        // String[] arr = (String[]) DemoStringPermutation.set.toArray(); // java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
        // Arrays.sort(arr);
        Object[] array = DemoStringPermutation.set.toArray();
        String[] stringArray = new String[array.length];
        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = (String) array[i];
        }
        // 排序之前各个元素
        System.out.println("排序之前各个元素：");
        printArray(stringArray);
        // 排序
        Arrays.sort(stringArray);
        // 排序之后各个元素
        System.out.println("排序之后各个元素：");
        printArray(stringArray);
    }
}

