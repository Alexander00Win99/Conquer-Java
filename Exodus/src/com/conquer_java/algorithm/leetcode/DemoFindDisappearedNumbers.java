package com.conquer_java.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 【三次数学危机】
 * 第一次数学危机：不可通约性的发现；
 * 第二次数学危机：无穷小量是否存在；
 * 第三次数学危机：罗素悖论。
 * 参考：
 * https://wenku.baidu.com/view/0d6c001a777f5acfa1c7aa00b52acfc789eb9f9f.html
 *
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 * Find all the elements of [1, n] inclusive that do not appear in this array.
 * Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
 *
 * Example:
 * Input:
 * [4,3,2,7,8,2,3,1]
 * Output:
 * [5,6]
 */
public class DemoFindDisappearedNumbers {
    public static List<Integer> findDisappearedNumbers1(int[] numbers) {
        List<Integer> list = new ArrayList<>();
        Set set = new HashSet<>();
        // 存放所有数字
        for (int num : numbers) {
            set.add(num);
        }
        // 找出[1, n]之内没有出现在set中的数字即可
        for (int i = 1; i <= numbers.length; i++) {
            if (set.contains(i)) continue;
            list.add(i);
        }
        return list;
    }

    /**
     * 利用特点：[1, n]的下标是0, ..., n-1，遇数标记对应下标位置元素，最后剩余未标元素即为缺失数字。
     * [1, 2, 4, 5, 1]举例如下：
     * 元素element        索引index     当前输入数组current     变化输出数组result      操作注释
     * 1                    0           [1, 2, 4, 5, 1]         [-1, 2, 4, 5, 1]        0号元素置负
     * 2                    1           [-1, 2, 4, 5, 1]        [-1, -2, 4, 5, 1]       1号元素置负
     * 4                    3           [-1, -2, 4, 5, 1]       [-1, -2, 4, -5, 1]      3号元素置负
     * 5                    4           [-1, -2, 4, -5, 1]      [-1, -2, 4, -5, -1]     4号元素置负
     * 1                    0           [-1, -2, 4, -5, -1]     [-1, -2, 4, -5, -1]     0号元素不变
     * @param numbers
     */
    public static List<Integer> findDisappearedNumbers2(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int num = Math.abs(numbers[i]);
            int index = num - 1;
            if (numbers[index] > 0) numbers[index] = - numbers[index];
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++)
            if (numbers[i] > 0)
                list.add(i + 1);
        return  list;
    }

    /**
     * 复原法：
     * 思想——值为n的正整数应该位于index=(n-1)下标位置
     * 行动——遍历每个元素，让其归还原位，然后返回异常位置下标即可
     * @param numbers
     * @return
     */
    // int[]{4, 3, 2, 7, 8, 2, 3, 1}
    public static List<Integer> findDisappearedNumbers3(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            // 获取当前元素理想下标位置
            int targetIndex = numbers[i] - 1;
            // 如果理想下标位置元素不是理想数值，交换当前元素和理想下标位置元素
            if (numbers[targetIndex] != numbers[i]) {
                int temp = numbers[targetIndex];
                numbers[targetIndex] = numbers[i];
                numbers[i] = temp;
                i--;
            }
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++)
            if (numbers[i] != i + 1)
                list.add(i + 1);
        return  list;
    }

    public static void main(String[] args) {
        System.out.println(findDisappearedNumbers1(new int[]{4, 3, 2, 7, 8, 2, 3, 1}));
        System.out.println(findDisappearedNumbers2(new int[]{4, 3, 2, 7, 8, 2, 3, 1}));
        System.out.println(findDisappearedNumbers3(new int[]{4, 3, 2, 7, 8, 2, 3, 1}));
    }
}
