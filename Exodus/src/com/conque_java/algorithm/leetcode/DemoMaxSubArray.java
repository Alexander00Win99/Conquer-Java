package com.conque_java.algorithm.leetcode;

public class DemoMaxSubArray {
    public static int maxSubArrayByViolent(int[] nums) { // 时间复杂度：O(N^2)；空间复杂度O(1)。
        int max = nums[0];
        for (int i = 0; i < nums.length; i++) { // 以各个元素nums[i]作为连续子序列起点进行全覆盖
            int sum = 0;
            for (int j = i; j < nums.length; j++) { // 以各个元素nums[j]作为子序列终点求取连续子序列之和
                sum += nums[j];
                if (sum >= max) max = sum;
                System.out.println(i + "-" + j + " sum: " + sum + " max: " + max);
            }
        }
        return max;
    }

    public static int maxSubArrayByDP(int[] nums) { // 时间复杂度：O(N)；空间复杂度O(N)。
        int[] dp = new int[nums.length]; // dp[i]表示以第i个元素作为终点的连续子序列和(根据定义，dp[i]所代表的最大连续子序列必然包括num[i]元素，同时，显然dp[i]不一定是[0, i]区间内的最大连续子序列)
        dp[0] = nums[0];
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] > 0) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];
            }
            if (dp[i] > max) max = dp[i];
            System.out.println(i + " - nums[i]: " + nums[i] + " - dp[i]: " + dp[i] + " max: " + max);
        }
        return max;
    }

    public static int maxSubArray(int[] nums) { // 时间复杂度：O(N)；空间复杂度O(1)。
        int max = nums[0];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (sum >= 0) {
                sum = sum + nums[i];
            } else {
                sum = nums[i];
            }
            max = Math.max(max, sum);
            System.out.println(i + " - nums[i]: " + nums[i] + " - sum: " + sum + " max: " + max);
        }
        return max;
    }

    public static int maxSubArrayByRecursion(int[] nums, int begin, int end) { // 剥离法——后续补充
        if (begin < end) return Integer.MIN_VALUE;
        if (nums[begin] <= 0) return maxSubArrayByRecursion(nums, begin + 1, end);
        if (nums[end] <= 0) return maxSubArrayByRecursion(nums, begin, end - 1);

        return -1;
    }

    public static int maxSubArrayByDC(int[] nums) { // 分治法——后续补充
        return -1;
    }

    public static void main(String[] args) {
//        System.out.println(maxSubArrayByViolent(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
//        System.out.println(maxSubArrayByDP(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArray(new int[]{-2, 1, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArray(new int[]{-2, 1, 1, -3, 4, -1, 2, 1, -5, 4, 3, -9}));
    }
}
