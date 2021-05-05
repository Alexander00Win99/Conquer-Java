package com.conquer_java.algorithm.sort;

import java.util.Arrays;

/**
 * 【希尔排序】——增量缩小排序
 * 希尔排序，也称递减增量排序算法，是基于插入排序的一种高速、稳定的改进版本。该方法因DL．Shell于1959年提出而得名。
 * 希尔排序是把记录按下标的一定增量分组，对于每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的元素越来越多，
 * 当增量减至为1时，整个数组或者文件恰被分成一组，算法便可终止。
 * 基于插入排序的以下两点特性，希尔排序提出改进方法：
 * 	插入排序一般来说是低效的， 因为插入排序每次只能移动一位数据。
 * 	插入排序在对几乎已经完成排序的数据操作时，效率很高，可以达到线性排序的效率。
 *
 * 【注意事项】
 * 1) 每隔固定步长的元素属于一组，实际算法，并非一组排完再拍下组，而是移动指针或者下标，各组交替进行各自的插入排序；
 * 2) 整个shell sort体现“先宏观后微观”的指导思想，前面的“宏观调控”阶段，虽然混乱无序，但是组多人少，插入排序可以使用较少代价完成组内排序，从而使得后面的“微观聚焦”即使组少人多，但是因为已经大体有序，插入排序也可快速完成；
 * 3) 插入排序都是先从step下标元素开始逐次往后进行各组内部插入排序（从右向左推进），具体两种方法：“交换法”（自右向左若左大于右则左右交换否则停止）和“移动法”（自右向左若大于标的则右取值左否则赋值标的停止），后者性能高于前者；
 * 4) 步长可以任意选择，通常选择数组长度一半，然后依次折半直至为1结束；
 */
public class DemoShellSort {
    public static void shellSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        int step = arr.length / 2; // 初始步长可以任意选择，通常首选数组长度的一半作为初始步长，往后
        while (step > 0) {
            for (int i = step; i < arr.length; i++) {
                int j = i;
                // “交换法”
                //while (j - step >= 0 && arr[j - step] > arr[j]) { // 自右向左：若左大右下，则左右交换
                    //swap(arr, j - step, j);
                    //j -= step;
                //}
                // “移动法”
                int candidate = arr[j];
                while (j - step >= 0 && arr[j - step] > candidate) {
                    arr[j] = arr[j - step];
                    j -= step;
                }
                arr[j] = candidate; // 自右向左：若左大右下，则右取左值。停下只是，左边arr[j - step] < candidate，是首个小于标的的值，当前元素赋值标的，找到组织。
            }
            step /= 2;
        }
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
        int[] arr = new int[]{6, 1, 2, 5, 9, 3, 4, 7, 0, 8};
        System.out.println(Arrays.toString(arr));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
        Arrays.stream(arr).forEach(element -> {
            System.out.print(element + " ");
        });
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
