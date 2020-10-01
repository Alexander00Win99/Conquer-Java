package com.conquer_java.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoMoveZeroes {
    // 时间复杂度O(N) 空间复杂度O(N)
    public static void moveZeroes1(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (0 != nums[i])
                list.add(nums[i]);
        }
        for (int i = 0; i < nums.length; i++) {
            if (i > list.size() - 1) {
                nums[i] = 0;
                continue;
            }
            nums[i] = list.get(i);
        }
    }
    // 时间复杂度O(N+M) 空间复杂度O(1) M==非零元素个数 指针记录截止目前所遇非零元素个数
    // cursor   index   {0, 1, 0, 3, 12}    operation
    // 0        1(1)    {1, 1, 0, 3, 12}    nums[0] = 1
    // 1        3(3)    {1, 3, 0, 3, 12}    nums[1] = 3
    // 2        12(4)   {1, 3, 12, 3, 12}   nums[2] = 12
    // 3                {1, 3, 12, 0, 0}
    public static void moveZeroes2(int[] nums) {
        // cursor指针初始为0，每当遇到非零元素逐次加一，并且置当前cursor位置元素为所遇非零元素
        int cursor = 0;
        for (int i = 0; i < nums.length; i++) {
            if (0 != nums[i]) {
                nums[cursor] = nums[i];
                cursor++;
            }
        }
        for (int i = cursor; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
    // 时间复杂度O(N) 空间复杂度O(1) 遍历+交换 遇到非零元素即和第一个零元素所在位置交换，然后第一个零元素位置后移一位
    // cursor   index   {0, 1, 0, 3, 12}
    // 0        1(1)    {1, 0, 0, 3, 12}
    // 1        3(3)    {1, 3, 0, 0, 12}
    // 2        12(4)   {1, 3, 12, 0, 0}
    // 3                {1, 3, 12, 0, 0}
    public static void moveZeroes3(int[] nums) {
        // cursor指针指向第一个零元素所在位置(初始为0)
        int cursor = 0;
        for (int i = 0; i < nums.length; i++) {
            if (0 != nums[i]) {
                if (cursor != i) {
                    nums[cursor] = nums[i];
                    nums[i] = 0;
                }
                // cursor后移一位
                cursor++;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 0, 3, 12};
        moveZeroes1(nums);
        Arrays.toString(nums);
    }
}
