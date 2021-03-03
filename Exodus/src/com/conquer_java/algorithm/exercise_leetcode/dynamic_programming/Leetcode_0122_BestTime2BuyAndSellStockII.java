package com.conquer_java.algorithm.exercise_leetcode.dynamic_programming;

/**
 * Say you have an array prices for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times).
 * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 * Input: [7,1,5,3,6,4]
 * Output: 7
 * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
 *              Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
 *
 * Example 2:
 * Input: [1,2,3,4,5]
 * Output: 4
 * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 *              Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
 *              engaging multiple transactions at the same time. You must sell before buying again.
 *
 * Example 3:
 * Input: [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transaction is done, i.e. max profit = 0.
 *
 *
 * Constraints:
 * 1 <= prices.length <= 3 * 10 ^ 4
 * 0 <= prices[i] <= 10 ^ 4
 *
 * 【思路分析】
 * Leetcode_0122中的每个阶段交易可以按照Leetcode_0121进行求解，最后累加各次交易受益总和即可。不同交易阶段的划分依据，本次买入价格低于前次卖出价格。
 */
public class Leetcode_0122_BestTime2BuyAndSellStockII {
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) return 0;
        int[] dp = new int[prices.length];
        dp[0] = prices[0];
        int lowest = prices[0];
        int highest = prices[0];
        int profit = 0; // 每次买卖受益，初始设置为0，代表不买，也即，股价一直下跌，任何时候买入都会亏损，此种情况应该一直空仓
        int answer = 0;

        for (int i = 1; i < dp.length; i++) {
            dp[i] = prices[i] - lowest;
            if (prices[i] >= highest) {
                profit = Math.max(profit, dp[i]);
                lowest = Math.min(lowest, prices[i]);
                highest = Math.max(highest, prices[i]);
            } else {
                answer += profit;
                profit = 0;
                lowest = prices[i];
                highest = prices[i];
            }
        }
        answer += profit;

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
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
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
            int newProfit0 = Math.max(profit0, profit1 + prices[i]);
            int newProfit1 = Math.max(profit1, profit0 - prices[i]);
            profit0 = newProfit0;
            profit1 = newProfit1;
        }
        return profit0;
    }

    public static void main(String[] args) {

    }
}
