package com.conquer_java.algorithm.sort;

import java.util.Arrays;

public class DemoQuickSort {
    public static void quickSort(int[] arr, int start, int end) {
        if (arr == null || arr.length <= 1 || start >= end || start < 0 || start >= arr.length || end < 0 || end >= arr.length) return;
        int candidate = arr[start];
        int i = start;
        int j = end;

        while (i < j) {
            while (arr[j] >= candidate && i < j)
                j--;
            while (arr[i] <= candidate && i < j)
                i++;
            if (i < j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }

        arr[start] = arr[i];
        arr[i] = candidate;

        // 以i为界限分割左右两块可以
        quickSort(arr, start, i - 1);
        quickSort(arr, i + 1, end);
        // 以j为界限分割左右两块也可
        //quickSort(arr, start, j - 1);
        //quickSort(arr, j + 1, end);
    }

    public static void quickSort_optimized(int[] arr, int start, int end) {
        if (arr == null || arr.length <= 1 || start >= end || start < 0 || start >= arr.length || end < 0 || end >= arr.length) return;
        int candidate = arr[start];
        int i = start;
        int j = end;

        while (i < j) {
            while (arr[j] > candidate && i < j)
                j--;
            while (arr[i] < candidate && i < j)
                i++;
            if (i < j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }

        // 以i为界限分割左右两块可以
        quickSort_optimized(arr, start, i - 1);
        quickSort_optimized(arr, i + 1, end);
        // 以j为界限分割左右两块也可
        //quickSort_optimized(arr, start, j - 1);
        //quickSort_optimized(arr, j + 1, end);
    }

    public static void main(String[] args) {
        int[] arr = new int[] {6, 1, 2, 5, 9, 3, 4, 7, 0, 8};
        int[] arr_optimized = new int[] {16, 11, 23, 25, 19, 33, 42, 17, 20, 18, 7, 0, 9, 81, 72};
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
        System.out.println("快速排序之前：");

        System.out.println(arr);
        System.out.println(Arrays.toString(arr));
        Arrays.stream(arr).forEach(element -> {
            System.out.print(element + " ");
        });
        System.out.println();

        System.out.println(arr_optimized);
        System.out.println(Arrays.toString(arr_optimized));
        Arrays.stream(arr_optimized).forEach(element -> {
            System.out.print(element + " ");
        });
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println("打印二维数组");
        System.out.println(Arrays.toString(arr2dimension));
        System.out.println(Arrays.deepToString(arr2dimension));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println("打印三维数组");
        System.out.println(Arrays.toString(arr3dimension));
        System.out.println(Arrays.deepToString(arr3dimension));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println("快速排序以后：");

        quickSort(arr, 0, arr.length - 1);
        System.out.println(arr);
        System.out.println(Arrays.toString(arr));

        quickSort_optimized(arr_optimized, 0, arr_optimized.length - 1);
        System.out.println(arr_optimized);
        System.out.println(Arrays.toString(arr_optimized));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
