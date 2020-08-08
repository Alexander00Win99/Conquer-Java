package com.conque_java.algorithm.MaShibing_ZuoChengyun.class07;

/**
 * 【题目】
 * 数组中可能有一个数字出现的次数超过数组长度的一半，请找出这个数字。要求：
 * 时间复杂度O(n)，额外空间复杂度O(1)。
 *
 * 【分析】——解题技巧：一次删除两个不同数字
 * 1) 如果最终不剩任何数字，说明根本不可能存在出现次数超过一半的数字；
 * 2) 如果最后剩下一个候选数字，需要继续通过单次遍历确认该数字确实出现次数超过半数；
 * 3) 如果最终确认无效，那么没有任何数字出现次数超过半数；
 * 4) 不能使用哈希方式，因为极端情况，当每个元素都不相等时，额外空间复杂度O(n)。
 */
public class DemoFindHalfMajority {
    public static int halfMajority(int[] arr) {
        int candidate = -1;
        int HP = 0;

        for (int i = 0; i < arr.length; i++) {
            if (HP == 0) {
                candidate = arr[i];
                HP++;
            } else if (arr[i] == candidate) {
                HP++;
            } else {
                HP--;
            }
        }

        if (HP == 0) return Integer.MIN_VALUE;

        HP = 0;
        for (int i = 0; i != arr.length; i++) {
            if (arr[i] == candidate)
                HP++;
        }
        return HP > arr.length / 2 ? candidate : Integer.MIN_VALUE;
    }

    private static int[] generateRandomArray(int length, int limit) {
        int[] arr = new int[(int)(Math.random() * length + 1)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * limit) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr;
        arr = new int[] {1, 3, 1, 4, 1};
        System.out.println(halfMajority(arr));
        arr = new int[] {1, 3, 1, 4, 5, 2, 0};
        System.out.println(halfMajority(arr));
        arr = new int[] {0, 0, 0, 0, 1, 1, 1};
        System.out.println(halfMajority(arr));
        arr = new int[] {0, 0, 0, 0, 1, 1, 1, 1};
        System.out.println(halfMajority(arr));
        arr = new int[] {0, 0, 0, 0, 1, 1, 1, 1, 1};
        System.out.println(halfMajority(arr));
    }
}
