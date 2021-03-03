package com.conquer_java.algorithm.sort;

import java.util.Arrays;

/**
 * 【堆排序】
 * 堆Heqp两大要素：1) Complete Binary Tree; 2) Parent > Children（大顶堆，小顶堆要求：Parent < Children）
 *
 * 【堆的特性】
 * 1) 由于是完全二叉树，可以用数组进行表示。从上到下，从左到右，顺序添加节点 <==> 数组下标连续递增；
 * 2) 方便计算父子关系。对于某个节点node，假设其下标是i，那么parentIndex = (i - 1) / 2，lChildIndex = 2 * i + 1，rChildIndex = 2 *  + 2；
 */
public class DemoHeapSort {
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        buildHeap(arr); // 对于整棵树进行堆化调整
        swap(arr, 0, arr.length - 1);
        for (int i = arr.length - 2; i > 0; i--) {
            heapify_recursive(arr, 0, i);
            swap(arr, 0, i);
        }
    }

    /**
     * 对于arr数组中第n个元素之前部分构建堆（第n个之后不予处理）
     * @param arr
     * @param n
     */
    public static void buildHeap(int[] arr, int n) {
        if (arr == null || arr.length == 0 || n >= arr.length) return;
        int lastIndex = (n - 1) / 2; // 倒数第一个非叶子节点 == 倒数第一个叶子节点的父节点
        while (lastIndex > 0) {
            heapify_recursive(arr, lastIndex, n); // 递归方式调整树
            //heapify_iterative(heap, lastIndex, n); // 迭代方式调整树
            lastIndex--;
        }
    }

    public static void heapify_recursive(int[] heap, int i, int n) {
        if (heap == null || heap.length <= 1 || i > (heap.length - 2) / 2 || n >= heap.length) return;
        int lIndex = 2 * i + 1;
        int rIndex = 2 * i + 2;

        int maxIndex = i; // 父节点、左孩子节点、右孩子节点中最大者
        if (lIndex <= n && heap[maxIndex] < heap[lIndex]) maxIndex = lIndex; // lIndex位于n范围之内
        if (rIndex <= n && heap[maxIndex] < heap[rIndex]) maxIndex = rIndex; // rIndex位于n范围之内
        if (maxIndex != i) { // 父节点不是最大值，进行交换。
            int temp = heap[i];
            heap[i] = heap[maxIndex];
            heap[maxIndex] = temp;
            heapify_recursive(heap, maxIndex, n);
        }
    }

    public static void heapify_iterative(int[] heap, int i, int n) {
        if (heap == null || heap.length <= 1 || i > (heap.length - 2) / 2 || n >= heap.length) return;

        int candidate = heap[i]; // 父节点
        int childIndex = i * 2 + 1; // childIndex默认指向左孩子节点
        while (childIndex <= n) {
            if (childIndex + 1 < n && heap[childIndex] < heap[childIndex + 1]) { // 左孩子<右孩子
                childIndex++; // childIndex重置指向右孩子节点
            }
            if (candidate < heap[childIndex]) {
                heap[i] = heap[childIndex];
                heap[childIndex] = candidate; // childIndex可能左孩子可能右孩子，无论哪个，需要重新调整其下子树，另外一个兄弟节点不受影响，无须调整
                i = childIndex;
            } else {
                break;
            }
            childIndex = i * 2 + 1;
        }
    }

    /**
     * 对于整棵树进行堆化调整
     * @param heap
     */
    public static void buildHeap(int[] heap) {
        if (heap == null || heap.length == 0) return;
        int lastIndex = (heap.length - 2) / 2; // 倒数第一个非叶子节点 == 倒数第一个叶子节点的父节点
        while (lastIndex >= 0) {
            heapify_recursive(heap, lastIndex); // 递归方式调整树
            //heapify_iterative(heap, lastIndex); // 迭代方式调整树
            lastIndex--;
        }
    }

    /**
     * 采用递归方式对heap中第i个节点进行heapify堆调整操作
     * @param heap
     * @param i
     */
    public static void heapify_recursive(int[] heap, int i) {
        if (heap == null || heap.length <= 1 || i > (heap.length - 2) / 2) return;
        int lIndex = 2 * i + 1;
        int rIndex = 2 * i + 2;

        int maxIndex = i; // 父节点、左孩子节点、右孩子节点中最大者
        if (heap[maxIndex] < heap[lIndex]) maxIndex = lIndex;
        if (heap[maxIndex] < heap[rIndex]) maxIndex = rIndex;
        if (maxIndex != i) { // 父节点不是最大值，进行交换。
            int temp = heap[i];
            heap[i] = heap[maxIndex];
            heap[maxIndex] = temp;
            heapify_recursive(heap, maxIndex);
        }
    }

    /**
     * 采用迭代方式对heap中第i个节点进行heapify堆调整操作
     * @param heap
     * @param i
     */
    public static void heapify_iterative(int[] heap, int i) {
        if (heap == null || heap.length <= 1 || i > (heap.length - 2) / 2) return;

        int candidate = heap[i]; // 父节点
        int childIndex = i * 2 + 1; // childIndex默认指向左孩子节点
        while (childIndex < heap.length) {
            if (childIndex + 1 < heap.length && heap[childIndex] < heap[childIndex + 1]) { // 左孩子<右孩子
                childIndex++; // childIndex重置指向右孩子节点
            }
            if (candidate < heap[childIndex]) {
                heap[i] = heap[childIndex];
                heap[childIndex] = candidate; // childIndex可能左孩子可能右孩子，无论哪个，需要重新调整其下子树，另外一个兄弟节点不受影响，无须调整
                i = childIndex;
            } else {
                break;
            }
            childIndex = i * 2 + 1;
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
        int[] tree = null;
        tree = new int[] {4, 10, 3, 5, 1, 2};
        Arrays.stream(tree).forEach(element -> {
            System.out.printf(element + " ");
        });
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("堆化处理结果如下：");
        heapify_recursive(tree, 0);
        Arrays.stream(tree).forEach(element -> {
            System.out.printf(element + " ");
        });
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println();

        tree = new int[] {4, 16, 81, 25, 49, 9, 36, 64, 1, 100, 128};
        Arrays.stream(tree).forEach(element -> {
            System.out.printf(element + " ");
        });
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("堆排序结果如下：");
        //buildHeap(tree);
        heapSort(tree);
        Arrays.stream(tree).forEach(element -> {
            System.out.printf(element + " ");
        });
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
