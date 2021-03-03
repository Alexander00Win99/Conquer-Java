package com.conquer_java.algorithm.exercise_leetcode.dynamic_programming;

/**
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 *
 * Example 1:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 *
 * Example 2:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 *
 *
 * Constraints:
 * 1 <= prices.length <= 105
 * 0 <= prices[i] <= 104
 */
public class Leetcode_0121_BestTime2BuyAndSellStock {
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) return 0;
        int[] dp = new int[prices.length];
        dp[0] = prices[0];
        int lowest = prices[0];
        int answer = 0; // 初始设置为0，代表不买，也即，股价一直下跌，任何时候买入都会亏损，此种情况应该一直空仓

        for (int i = 1; i < dp.length; i++) {
            dp[i] = prices[i] - lowest;
            answer = Math.max(answer, dp[i]);
            lowest = Math.min(lowest, prices[i]);
        }
        return answer;
    }

    public static int maxProfit_dp01(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int length = prices.length;
        int[][] dp = new int[length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[length - 1][0];
    }

    public static int maxProfit_dp02(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int profit0 = 0, profit1 = -prices[0];
        int length = prices.length;
        for (int i = 1; i < length; i++) {
            profit0 = Math.max(profit0, profit1 + prices[i]);
            profit1 = Math.max(profit1, -prices[i]);
        }
        return profit0;
    }

    public static void main(String[] args) {
        int[] prices;
        prices = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit(prices));
        System.out.println(maxProfit_dp01(prices));
        System.out.println(maxProfit_dp02(prices));
        prices = new int[]{7, 6, 4, 3, 1};
        System.out.println(maxProfit(prices));
        System.out.println(maxProfit_dp01(prices));
        System.out.println(maxProfit_dp02(prices));
    }
}
