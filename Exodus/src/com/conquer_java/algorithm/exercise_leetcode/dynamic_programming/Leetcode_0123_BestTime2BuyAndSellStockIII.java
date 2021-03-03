package com.conquer_java.algorithm.exercise_leetcode.dynamic_programming;

import java.util.ArrayList;
import java.util.List;

/**
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit. You may complete at most two transactions.
 * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 * Input: prices = [3,3,5,0,0,3,1,4]
 * Output: 6
 * Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 * Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
 *
 * Example 2:
 * Input: prices = [1,2,3,4,5]
 * Output: 4
 * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 * Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are engaging multiple transactions at the same time. You must sell before buying again.
 *
 * Example 3:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transaction is done, i.e. max profit = 0.
 *
 * Example 4:
 * Input: prices = [1]
 * Output: 0
 *
 *
 * Constraints:
 * 1 <= prices.length <= 105
 * 0 <= prices[i] <= 105
 *
 * 【思路分析】
 * Leetcode_0123“交易两次获取最大收益”无法基于Leetcode_122的各段交易进行排序，进而获取交易收益最大两次取和即可。
 * 因为，原先两次相邻交易完全可能合并成为一个新的交易，从而实现单次交易更大收益。例如：prices = new int[]{1, 2, 4, 2, 5, 7, 2, 4, 9, 0};
 * 按照Leetcode_122计算结果是12：总计三次交易，通过{4-1, 7-2, 9-2}取到5+7=12；实际应该合并前两次，从而{7-1, 9-2}得到6+7=13
 */
public class Leetcode_0123_BestTime2BuyAndSellStockIII {
    public static int maxProfit(int[] prices) {
        List<Integer> list = new ArrayList<>(128);
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
                if (profit > 0) list.add(profit);
                profit = 0;
                lowest = prices[i];
                highest = prices[i];
            }
        }
        if (profit > 0) list.add(profit);
        //System.out.println(list.size());

        switch (list.size()) {
            case 0:
                answer = 0;
                break;
            case 1:
                answer = list.get(0);
                break;
            case 2:
                answer = list.get(0) + list.get(1);
                break;
            default:
                int most = list.get(0);
                int second = most;
                for (int i = 1; i < list.size(); i++) {
                    if (list.get(i) > most) {
                        second = most;
                        most = list.get(i);
                    } else {
                        if (list.get(i) > second) {
                            second = list.get(i);
                        }
                    }
                }
                answer = most + second;
        }

        return answer;
    }

    public static int maxProfit_dp01(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int length = prices.length;
        int[][][] dp = new int[length][3][2];
        dp[0][1][0] = 0;
        dp[0][1][1] = -prices[0];
        dp[0][2][0] = 0;
        dp[0][2][1] = -prices[0];
        for (int i = 1; i < length; i++) {
            dp[i][2][0] = Math.max(dp[i - 1][2][0], dp[i - 1][2][1] + prices[i]);
            dp[i][2][1] = Math.max(dp[i - 1][2][1], dp[i - 1][1][0] - prices[i]);
            dp[i][1][0] = Math.max(dp[i - 1][1][0], dp[i - 1][1][1] + prices[i]);
            dp[i][1][1] = Math.max(dp[i - 1][1][1], dp[i - 1][0][0] - prices[i]);
        }
        return dp[length - 1][2][0];
    }

    public static int maxProfit_dp02(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int profitOne0 = 0, profitOne1 = -prices[0], profitTwo0 = 0, profitTwo1 = -prices[0];
        int length = prices.length;
        for (int i = 1; i < length; i++) {
            profitTwo0 = Math.max(profitTwo0, profitTwo1 + prices[i]);
            profitTwo1 = Math.max(profitTwo1, profitOne0 - prices[i]);
            profitOne0 = Math.max(profitOne0, profitOne1 + prices[i]);
            profitOne1 = Math.max(profitOne1, -prices[i]);
        }
        return profitTwo0;
    }

    public static void main(String[] args) {
        int[] prices;
//        prices = new int[]{1, 4, 2};
//        System.out.println(maxProfit(prices));
//        prices = new int[]{6, 1, 3, 2, 4, 7};
//        System.out.println(maxProfit(prices));
//        prices = new int[]{14, 9, 10, 12, 4, 8, 1, 16};
//        System.out.println(maxProfit(prices));
        prices = new int[]{1, 2, 4, 2, 5, 7, 2, 4, 9, 0};
        System.out.println(maxProfit(prices));
        System.out.println(maxProfit_dp01(prices));
        System.out.println(maxProfit_dp02(prices));
    }
}
