package com.conquer_java.algorithm.leetcode;

import java.util.HashMap;

public class DemoTwoSum {
    /**
     * 穷举法：暴力破解
     * 时间复杂度：O(n^2)
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if(nums[i] + nums[j] == target && i != j)
                    return new int[]{i, j};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 倒推法：已知当前数值，搜索补全数值(利用map数据结构，简化查询时间；先录入，后遍历)。
     * 时间复杂度：O(2*n)
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++)
            map.put(nums[i], i);
        for (int i = 0; i < nums.length; i++) {
            int expected = target - nums[i];
            if (map.containsKey(expected) && map.get(expected) != i)
                return new int[]{i, map.get(expected)};
        }
        return new int[]{-1, -1};
    }

    /**
     * 倒推法：已知当前数值，搜索补全数值(利用map数据结构，简化查询时间；边录入，边遍历)。
     * 时间复杂度：O(n)
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum3(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>(10);
        for (int i = 0; i < nums.length; i++) {
            int expected = target - nums[i];
            if (map.containsKey(expected) && map.get(expected) != i)
                return new int[]{i, map.get(expected)};
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    public static void printArr(int[] arr) {
        for (int num : arr) System.out.println(num);
    }
    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9, 20, 18, 16, 14, 12};
        printArr(twoSum1(nums, 10));
        printArr(twoSum2(nums, 23));
        printArr(twoSum3(nums, 49));
    }
}
