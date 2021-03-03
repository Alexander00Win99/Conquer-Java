package com.conquer_java.algorithm.sort;

import java.util.Arrays;
import java.util.Random;

public class DemoMergeSort {
    public static void mergeSort(int[] arr, int begin, int end) {
        if (arr == null || arr.length == 0 || begin < 0 || begin > arr.length - 1 || end < 0 || end > arr.length - 1 || begin >= end) return;

        int mid = (end - begin) / 2 + begin;
        mergeSort(arr, begin, mid);
        mergeSort(arr, mid + 1, end);
        int[] temp = new int[end - begin + 1];
        int i = begin, j = mid + 1, k = 0;
        while (i <= mid && j <= end) { // temp依次填充前后两段数组区间内的元素之中最小值，无论i++还是j++，k都会k++，因此最后无论i还是j到达边界退出while循环，k都会指向temp中下一尚未赋值下标位置
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        // 注意：如果右边数组尾部剩余元素，那么无须处理，因为已经有序
        if (i <= mid) { // 左边数组尾部剩余元素（右边数组元素已全取完)，只需将其全部移至末尾即可
            while (i <= mid)
                temp[k++] = arr[i++]; // k最终取值(end - begin + 1)
        }
//        for (int l = 0; l < k; l++) {
//            arr[begin + l] = temp[l];
//        }
        while (--k >= 0) {
            arr[begin + k] = temp[k];
        }
    }

    public static int[] genRandArr(int n, int min, int max) { // 生成[min, max]范围随机数n个
        int[] arr = new int[n];
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(max - min + 1) + min;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = null;
        arr = new int[]{6, 1, 2, 5, 9, 3, 4, 7, 0, 8};
        System.out.println(Arrays.toString(arr));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
        Arrays.stream(arr).forEach(element -> {
            System.out.print(element + " ");
        });
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        arr = genRandArr(30, 0, 100);
        System.out.println(Arrays.toString(arr));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
