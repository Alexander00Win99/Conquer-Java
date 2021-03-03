package com.conquer_java.algorithm.exercise_leetcode.dynamic_programming;

/**
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * The robot can only move either down or right at any point in time.
 * The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * How many possible unique paths are there?
 *
 * Example 1:
 * Input: m = 3, n = 7
 * Output: 28
 *
 * Example 2:
 * Input: m = 3, n = 2
 * Output: 3
 * Explanation:
 * From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Down -> Down
 * 2. Down -> Down -> Right
 * 3. Down -> Right -> Down
 *
 * Example 3:
 * Input: m = 7, n = 3
 * Output: 28
 *
 * Example 4:
 * Input: m = 3, n = 3
 * Output: 6
 *
 *
 * Constraints:
 * 1 <= m, n <= 100
 * It's guaranteed that the answer will be less than or equal to 2 * 109.
 *
 * 【解题思路】
 * Start: [0, 0], End: [m - 1, n - 1]
 * 某个位置Point: [x, y]，只能左边右移和上边下移二者选一达到。
 * 状态转移方程：f(x, y) = f(x - 1, y) + f(x, y - 1)
 * 边界条件：x为0，只能右移；y为0只能下移。
 */
public class Leetcode_0062_UniquePaths {
    public static int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) return -1;
        if (m == 0 || n == 0) return 0;
        if (m == 1 || n == 1) return 1;
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < m; j++) {
            dp[0][j] = 1;
        }
        dp[0][0] = 0; // 修正dp[0][0]
        for (int i = 1; i < n; i++)
            for (int j = 1; j < m; j++)
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        return dp[n - 1][m - 1];
    }

    public static void main(String[] args) {
        System.out.println(uniquePaths(3, 7));
        System.out.println(uniquePaths(7, 3));
        System.out.println(uniquePaths(3, 3));
        System.out.println(uniquePaths(7, 7));
    }
}
