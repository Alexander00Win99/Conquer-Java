package com.conquer_java.algorithm.exercise_leetcode.dynamic_programming;

/**
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 *
 * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 *
 * Example 2:
 * Input: nums = [1]
 * Output: 1
 *
 * Example 3:
 * Input: nums = [0]
 * Output: 0
 *
 * Example 4:
 * Input: nums = [-1]
 * Output: -1
 *
 * Example 5:
 * Input: nums = [-100000]
 * Output: -100000
 *
 *
 * Constraints:
 * 1 <= nums.length <= 3 * 104
 * -105 <= nums[i] <= 105
 */
public class Leetcode_0053_MaximumSubarray {
    /**
     * 【解题思路】
     * 假设：
     * maxSubSum表示从左到右到达当前下标位置，包含当前数组元素num[i]的这段子数组局部范围（连续子空间）内的最大子序列和；
     * maxArrSum表示从左到右整个数组范围内的最大子序列和，显然，其为从右边界依次到达每个数组元素位置的所有局部最大子序列和中的最大值；
     * 【规律总结】
     * 如果，num[i]位置的最大子序列和是maxSubSum[i]，那么，num[i+1]位置的依据前值maxSubSum[i]正负，即可计算得出。
     * 由于本题特殊题设，“最大连续子数组”之连续条件，每个位置的最大子序列，只与前一位置的最大子序列有关，无须考虑更靠前的最大子序列（否则断连），因此，可以省略dp[]，只用一个变量代替即可，因此可以进一步优化空间复杂度。
     * 【注意事项】
     * 初始值必须设置为nums[0]，然后从1开始遍历直至末尾。否则，如若初始值设置为0，然后从0开始往后遍历，无法覆盖数组仅含一个元素nums[0]并且nums[0]<0的场景。
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int maxSubSum = nums[0]; // int maxSubSum = 0;错误：无法处理仅含一个元素nums[0]<0的场景
        int maxArrSum = nums[0]; // int maxArrSum = 0;错误：无法处理仅含一个元素nums[0]<0的场景
        for (int i = 0; i < nums.length; i++) {
            if (maxSubSum < 0)
                maxSubSum = nums[i];
            else
                maxSubSum += nums[i];
            maxArrSum = Math.max(maxArrSum, maxSubSum);
        }
        return maxArrSum;

//        int[] dp = new int[nums.length];
//        dp[0] = nums[0];
//        int ans = nums[0];
//        for (int i = 1; i < dp.length; i++) {
//            dp[i] = Math.max(dp[i - 1], 0) + nums[i];
//            ans = Math.max(ans, dp[i]);
//        }
//        return ans;
    }

    public static void main(String[] args) {

    }
}
