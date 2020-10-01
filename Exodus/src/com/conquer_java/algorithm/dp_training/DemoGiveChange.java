package com.conquer_java.algorithm.dp_training;

/**
 * 【目标】：掌握动态规划算法
 *
 * 【结果】：完成
 *
 * https://www.zhihu.com/question/23995189 —— 知乎 动态规划 入门讲解
 *
 * 动态规划DP(Dynamic Programming)概述：
 * 美国数学家R.E.Bellman提出的求解多阶段决策过程(Decision Process)最优化问题的一种方法。
 * 多阶段决策过程(Decision Process)：一个复杂问题按照时间(或者空间)分成若干阶段，每个阶段均需作出决策，以便得到过程的最优结局。
 *
 * 动态规划的概念：
 * 每个阶段采取的决策与时间有关，而且前一阶段采取的决策如何，不但影响前一阶段，还会影响以后各个阶段，是一个动态问题，因此，处理该类问题的方法称为动态规划方法。
 * 然而，动态规划也能处理一些本来与时间没有关系的静态模型，只要在静态模型中人为引入“时间”因素，分成多个时段，从而将其视作多阶段的动态模型，使用动态规划方法求解。
 * 简而言之：问题可以按照时间(或者空间)顺序分解成为若干相互联系的阶段，每一阶段都要做出决策，整个过程的决策是一个决策序列。
 *
 * 动态规划算法的基本要素：
 * 1) 最优子结构性质；
 * 2) 重叠子问题性质；
 * 3) 无后效性(例如：在找零最少问题中，当i=15时(当前阶段的状态)，f(15)|minCosts[15]只和f(14)|f(13)|f(10)|f(3)有关，当前阶段i=15之后的动态发展，比如f(16)不受之前阶段的各个状态影响)；
 * PS: 普遍方法——首先假设由问题的最优解导出的子问题的解不是最优的，然后设法证明在这个假设条件下可以构造出比原问题最优解更好的解，从而导致矛盾。
 *
 * 动态规划算法的求解步骤：
 * 1) 寻找最优解性质及其结构特征；
 * 2) 利用递归形式定义最优解(状态转移方程)；
 * 3) 自底向上计算最优解(最小规模问题<==>边界状态<==>递归出口)；
 * 4) 利用已有信息组成最终最优解；
 * PS: 利用递归寻找规律(找出状态转移方程) + 利用迭代计算结果(提升效率)
 * 如果一个问题的最优解可以划分为若干个子问题的最优解，那么，可以从规模最小的问题开始自底向上逐步推导出整个问题的最终最优解。
 *
 * 状态转移方程的两种类型：
 * 我从哪里来？——pull型转移(找零最少问题：f(n) = min{f(n-1), f(n-2), f(n-5), f(n-12)} + 1；DAG最短路径问题：f(p)=min{f(R)+w(R-p)}，R是有路到达p点的所有可能点)
 * 我到哪里去？——push型转移
 *
 * 动态规划 VS 分治思想：
 * 1) 相同点：待解问题 ——分解成为——> 若干个子问题；
 * 2) 异同点：动态规划——子问题之间存在重叠（多项式量级） + 分治思想——子问题之间相互独立（指数量级）。
 *
 * 动态规划 VS 贪心算法：
 * 1) 相同点：通过缩减问题规模，达到渐进解决问题目的；
 * 2) 异同点：贪心算法为了追求快速缩减问题规模，总是针对当前状态status选择一种极端行为action，因此只会考虑当前利益，不会考虑长远规划，可能导致后续状态需要付出更大代价，鼠目寸光，决策片面；动态规划则会针对当前状态选择多种可能最优规划策略，统筹考虑，全面决策。
 *
 * 动态规划 VS 暴力枚举：
 * 1) 相同点：均是通过枚举，在可能解空间内寻找最优解；
 * 2) 异同点：通过枚举所有可能解，获得最大可能解空间(指数级别O(n))；尽量缩小可能解，自带剪枝功能(多项式级别O(n))。
 */
public class DemoGiveChange implements IOfficialMoney {
    /**
     * for (int i = 1; i <= num; i++)的每个i值表征状态
     * 状态转移方程：f(n) = min{f(n-1), f(n-2), f(n-5), f(n-12)} + 1
     * @param num
     * @return
     */
    public static int getMinChange(int num) {
        if (num == 0) return 0;
        int[] minCosts = new int[num + 1];
        minCosts[0] = 0;
        for (int i = 1; i <= num; i++) {
            int minCost = Integer.MAX_VALUE;
            for (int chineseYuan : ChineseYuans) {
                if (i - chineseYuan >= 0)
                    minCost = Math.min(minCost, minCosts[i -chineseYuan] + 1);
            }
            minCosts[i] = minCost;
            System.out.println("minCosts[" + i + "]: " + minCosts[i]);
        }
        return minCosts[num];

//        if (num == 0) return 0;
//        int[] minCosts = new int[num + 1];
//        minCosts[0] = 0;
//        for (int i = 1; i <= num; i++) {
//            int minCost = Integer.MAX_VALUE;
//            if (i - 1 >= 0) minCost = Math.min(minCost, minCosts[i - 1] + 1);
//            if (i - 2 >= 0) minCost = Math.min(minCost, minCosts[i - 2] + 1);
//            if (i - 5 >= 0) minCost = Math.min(minCost, minCosts[i - 5] + 1);
//            if (i - 12 >= 0) minCost = Math.min(minCost, minCosts[i - 12] + 1);
//            minCosts[i] = minCost;
//            System.out.println("minCosts[" + i + "]: " + minCosts[i]);

            // DP动态规划使用迭代代替递归，如果包含递归，肯定错误，如下：
//            if (i - 1 >= 0) minCost = Math.min(minCost, getMinChange(i - 1) + 1);
//            if (i - 2 >= 0) minCost = Math.min(minCost, getMinChange(i - 2) + 1);
//            if (i - 5 >= 0) minCost = Math.min(minCost, getMinChange(i - 5) + 1);
//            if (i - 12 >= 0) minCost = Math.min(minCost, getMinChange(i - 12) + 1);
//            return minCost;
//        }
//        return minCosts[num];
    }

    public static void main(String[] args) {
        System.out.println(getMinChange(25));
    }
}
