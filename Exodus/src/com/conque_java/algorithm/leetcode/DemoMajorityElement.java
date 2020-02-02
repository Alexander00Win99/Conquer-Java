package com.conque_java.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 【芝诺悖论(阿基里斯悖论)】
 * 阿基里斯和乌龟赛跑，开始时刻乌龟位于阿基里斯前面1000米处，假定阿基里斯的速度是乌龟的10倍。当比赛开始后，假设阿基里斯跑了1000米，
 * 所用的时间为t，此时乌龟便领先他100米；当阿基里斯跑完下一个100米时，他所用的时间为t/10，乌龟仍然领先他10米；当阿基里斯跑完下一个10米时，
 * 他所用的时间为t/100，乌龟仍然领先他1米……芝诺认为：阿基里斯能够继续逼近乌龟，但是决不可能追上乌龟。
 *
 * 【芝诺悖论的产生原因】
 * 芝诺认为，阿基里斯追乌龟有很多过程，每个过程都有很多时间，既然是无穷个过程，那么最终对应时间也是无穷大了。但是他忘记了时间是有限的，并非无限的。
 * 原因在于：无穷数量的数字相加的最终结果是有限大小的数字(1 + 0.1 + 0.01 + 0.001 + ......)，而非无穷大。
 *
 * 【芝诺悖论的哲学辨析】
 * 阿基里斯悖论割裂了运动与静止的关系，夸大了相对静止，否认了绝对运动，是形而上学说。
 *
 * Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.
 * You may assume that the array is non-empty and the majority element always exist in the array.
 *
 * Example 1:
 * Input: [3,2,3]
 * Output: 3
 *
 * Example 2:
 * Input: [2,2,1,1,1,2,2]
 * Output: 2
 *
 */
public class DemoMajorityElement {
    public static int majorityElement1(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int count = 1;
            if (map.containsKey(num)) {
                count = map.get(num);
                count++;
            }
            if (count > nums.length / 2) return num;
            map.put(num, count);
        }
        return -1;
    }

    /**
     * 排序法：
     * 排序以后中间位置的元素必然是众数
     * @param nums
     * @return
     */
    public static int majorityElement2(int[] nums) {
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        return nums[nums.length / 2];
    }

    /**
     * 正负相消法：
     * 众数：出现次数大于其他数字出现次数总和(众数出现次数不会被其他数字出现次数总和减完)
     * 操作：当前count==0，将当前元素置为候选人，count加1，下次比较候选人是否和当前元素相同，相同增一，不同减一，直至为零，重选候选人
     * @param nums
     * @return
     */
    public static int majorityElement3(int[] nums) {
        int count = 0;
        int candidate = -1;
        for (int i = 0; i < nums.length; i++) {
            if (count == 0) candidate = nums[i];
            int delta = candidate == nums[i] ? 1 : -1;
            count += delta;
        }
        return candidate;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {2, 2, 2, 1, 3, 1, 3, 1, 2, 2, 2};
        System.out.println(majorityElement1(nums));
        System.out.println(majorityElement2(nums));
        System.out.println(majorityElement3(nums));
    }
}
