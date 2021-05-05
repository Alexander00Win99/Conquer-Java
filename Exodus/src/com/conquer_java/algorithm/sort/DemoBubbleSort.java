package com.conquer_java.algorithm.sort;

import java.util.Arrays;

public class DemoBubbleSort {
    public static void swap(int[] arr, int i, int j) {
        if (arr == null || arr.length == 0 || i < 0 || i >= arr.length || j < 0 || j >= arr.length) return;

        if (i != j) {
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    }

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - 1 - i; j++)
                if (arr[j] > arr[j + 1])
                    swap(arr, j, j + 1);
    }

    public static void main(String[] args) {
        int[] arr = null;
        arr = new int[]{6, 1, 2, 5, 9, 3, 4, 7, 0, 8};
        System.out.println(Arrays.toString(arr));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
        Arrays.stream(arr).forEach(element -> {
            System.out.print(element + " ");
        });
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

    }
}
