package com.conquer_java.algorithm.sort;

import java.util.Arrays;

public class DemoInsertSort {
    public static void insertSort(int[] arr) { // 自右向左，逐次移动(最后空位，放入新值)
        if (arr == null || arr.length <= 1) return;

        for (int i = 1; i < arr.length; i++) {
            int candidate = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] > candidate) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = candidate;
        }
    }

    public static void insertSortBySwap(int[] arr) { // 自右向左，逐次交换
        if (arr == null || arr.length <= 1) return;

        for (int i = 1; i < arr.length; i++)
            for (int j = i; j > 0 && arr[j - 1] > arr[j]; j--)
                swap(arr, j - 1, j);
    }

    public static void swap(int[] arr, int i, int j) {
        if (arr == null || arr.length == 0 || i < 0 || i >= arr.length || j < 0 || j >= arr.length) return;

        if (i != j) {
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    }

    public static void main(String[] args) {
        int[] arr = null;
        arr = new int[]{6, 1, 2, 5, 9, 3, 4, 7, 0, 8};
        System.out.println(Arrays.toString(arr));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //insertSort(arr);
        insertSortBySwap(arr);
        System.out.println(Arrays.toString(arr));
        Arrays.stream(arr).forEach(element -> {
            System.out.print(element + " ");
        });
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
