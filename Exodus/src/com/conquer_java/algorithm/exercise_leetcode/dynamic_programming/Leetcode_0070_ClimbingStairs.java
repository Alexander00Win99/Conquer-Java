package com.conquer_java.algorithm.exercise_leetcode.dynamic_programming;

import java.util.HashMap;

/**
 * You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * Example 1:
 * Input: n = 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 *
 * Example 2:
 * Input: n = 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 * 1. 1 step + 1 step + 1 step
 * 2. 1 step + 2 steps
 * 3. 2 steps + 1 step
 *
 *
 * Constraints:
 * 1 <= n <= 45
 */
public class Leetcode_0070_ClimbingStairs {
    public int climbStairs_Recursive(int n) { // 递归正确但是超时：Time Limit Exceeded
        return (n == 0 || n == 1) ? 1 : climbStairs_Recursive(n - 1) + climbStairs_Recursive(n - 2);
    }

    public int climbStairs_DP(int n) { // 动态规划参考递归形式进行改编
        if (n == 0 || n == 1) return 1;

        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        for (int i = 2; i < dp.length; i++) {
            dp[i] = dp[i -2] + dp[i - 1];
        }
        return dp[n];
    }

    public static void main(String[] args) {

    }
}
