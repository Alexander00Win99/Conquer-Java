package com.conquer_java.knowledge.arrays;

import java.util.Arrays;

public class DemoArrays {
    public static void main(String[] args) {
        int[] arr = new int[] {6, 1, 2, 5, 9, 3, 4, 7, 0, 8};
        int[][] arr2dimension = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][][] arr3dimension = {{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}, {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}}, {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}};
        /**
         * Arrays.toString()只能打印一维数组
         * Arrays.deepToString()用于打印多维数组
         *
         * System.out.println(Arrays.toString(arr2dimension))打印效果如下：
         * [[I@7699a589, [I@58372a00, [I@4dd8dc3]
         * 而非
         * [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
         *
         * System.out.println(Arrays.toString(arr3dimension))打印效果如下：
         * [[[I@568db2f2, [[I@378bf509, [[I@5fd0d5ae]
         * 而非
         * [[[1, 1, 1], [2, 2, 2], [3, 3, 3]], [[4, 4, 4], [5, 5, 5], [6, 6, 6]], [[7, 7, 7], [8, 8, 8], [9, 9, 9]]]
         */
        System.out.println(Arrays.toString(arr));
        Arrays.stream(arr).forEach(System.out::println);
        System.out.println(Arrays.toString(arr2dimension));
        System.out.println(Arrays.deepToString(arr2dimension));
        System.out.println(Arrays.toString(arr3dimension));
        System.out.println(Arrays.deepToString(arr3dimension));
    }
}
