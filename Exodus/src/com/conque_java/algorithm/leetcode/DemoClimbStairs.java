package com.conque_java.algorithm.leetcode;

/**
 * 动态规划DP(Dynamic Programming)的历史：
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
 * 2) 重叠子结构性质；
 * PS: 普遍方法——首先假设由问题的最优解导出的子问题的解不是最优的，然后设法证明在这个假设条件下可以构造出比原问题最优解更好的解，从而导致矛盾。
 *
 * 动态规划算法的求解步骤：
 * 1) 寻找最优解性质及其结构特征；
 * 2) 利用递归形式定义最优解；
 * 3) 自底向上计算最优解；
 * 4) 利用已有信息组成最优解；
 *
 * 动态规划 VS 分治思想：
 * 1) 相同点：待解问题 ——分解成为——> 若干个子问题；
 * 2) 异同点：动态规划——子问题之间存在重叠（多项式量级） + 分治思想——子问题之间相互独立（指数量级）；
 *
 */
public class DemoClimbStairs {
    public static int climbStairsByRecursion(int n) {
        return climbStairsByRecursion(0, n);
    }

    public static int climbStairsByRecursion(int i, int n) { // 从i层到n层的步数
        if (i == n) return 1;
        if (i > n) return 0;
        // 利用f(i, n) = f(i + 1, n) + f(i + 2, n)规律，进行暴力递归(Violent Recursion重复求解重叠子问题，效率低下)
        return climbStairsByRecursion(i + 1, n) + climbStairsByRecursion(i + 2, n);
    }

    public static int climbStairsByMemory(int n) {
        int[] stepArr = new int[n + 1]; // stepArr[i]代表i-n的走法数
        return climbStairsByMemory(0, n, stepArr);
    }

    public static int climbStairsByMemory(int i, int n, int[] stepArr) { // 新增数组，各个下标位置元素依次存储各层楼梯出发所需步数
        if (i == n) {
//            System.out.println(i + "-" + n + " need step: " + 1);
            return 1;
        }
        if (i > n) {
//            System.out.println(i + "-" + n + " need step: " + 0);
            return 0;
        }

        if (stepArr[i] != 0) { // 调取记忆
//            System.out.println(i + "-" + n + " already known: " + stepArr[i]);
            return stepArr[i];
        } else { // 存储记忆
            stepArr[i] = climbStairsByMemory(i + 1, n, stepArr) + climbStairsByMemory(i + 2, n, stepArr);
//            System.out.println(i + "-" + n + " after calculated: " + stepArr[i]);
            return stepArr[i];
        }
    }

    /**
     * 动态规划形象理解：
     * 1) 利用递归寻找规律(反证法)；
     * 2) 利用迭代计算结果(自底向上)；
     * @param i
     * @param n
     * @return
     */
    public static int climbStairsByDP(int n) {
        if (1 == n) return 1;
        int[] arr = new int[n + 1]; // 倒推方式，arr[i]代表，总共i层的所有走法，显然arr[i] = arr[i - 1] + arr[i - 2]。
        arr[1] = 1; // 1层只有一种
        arr[2] = 2; // 2层总计两种

        for (int i = 3; i < n + 1; i++) {
            arr[i] = arr[i - 1] + arr[i - 2]; // 利用递推规律
        }
        return arr[n];
    }

    public static void main(String[] args) {
        long begin;
        long end;

        System.out.println("++++++++++++++++Voilent Recursion++++++++++++++++");
        begin = System.currentTimeMillis();
        System.out.println(climbStairsByRecursion(30));
        end = System.currentTimeMillis();
        System.out.println("Voilent Recursion need time: " + (end - begin));
        System.out.println("----------------Voilent Recursion----------------");

        System.out.println("++++++++++++++++Memory Recursion++++++++++++++++");
        begin = System.currentTimeMillis();
        System.out.println(climbStairsByMemory(30));
        end = System.currentTimeMillis();
        System.out.println("Memory Recursion need time: " + (end - begin));
        System.out.println("----------------Memory Recursion----------------");

        System.out.println("++++++++++++++++DP++++++++++++++++");
        begin = System.currentTimeMillis();
        System.out.println(climbStairsByDP(30));
        end = System.currentTimeMillis();
        System.out.println("Voilent Recursion need time: " + (end - begin));
        System.out.println("----------------DP----------------");
    }
}
