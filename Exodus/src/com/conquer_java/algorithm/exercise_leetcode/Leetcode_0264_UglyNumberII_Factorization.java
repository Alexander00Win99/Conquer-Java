package com.conquer_java.algorithm.exercise_leetcode;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0264_UglyNumberII_Factorization {
    /**
     * 根据丑数定义：UglyNumber = 2^pow(2) * 3^pow(3) * 5^pow(5)，也即：任一丑数，必然只能包含2、3、5因子。
     * 假设集合之中，各个丑数元素按照从小到大顺序排列，可以定义3个指针：maxIndex_2、maxIndex_3、maxIndex_5，
     * 分别代表2、3、5这三个丑数因子在现有当前列表中最大临界元素位置。满足：这个位置的元素乘以丑数因子<=当前最大丑数。
     * 显然，后一位置乘以相同丑数因子必然超出当前最大丑数。
     * 如此，我们每次对于新的丑数的选择，只需使用 Math.min(list.get(indexN) * factorN)求取即可，因为，下一丑数必然
     * 是现有丑数基础上乘以各个丑数因子，绝无例外。同时，对于新加入的丑数，如果某个丑数因子能够整除，将其对应指针加一后移一位。
     * 因为，其对当前最新加入丑数有所贡献，也即原来位置的元素乘以因子即可达到当前最新丑数，下一最新丑数，显然只可能通过后一位置
     * 元素乘以相应因子获得。
     *
     * factorization——因数分解法
     *
     * dp——动态规划法
     */

    public static int nthUglyNumber_Factorization(int n) {
        int factor_2 = 2, factor_3 = 3, factor_5 = 5;
        List<Integer> list = new ArrayList<>();
        // 丑数集合初始加入1，1可以同时看做1 * 2^0 | 1 * 3^0 | 1 * 5^0，因此，初始通过三个因子均可到达当前最大丑数1
        // 对应各个丑数因子的当前最大元素位置分别是：maxIndex_2 = 0, maxIndex_3 = 0, maxIndex_5 = 0。
        int candidate_2 = (int) Math.pow(2, 0), candidate_3 = (int) Math.pow(3, 0), candidate_5 = (int) Math.pow(5, 0);
        int winner = Math.min(Math.min(candidate_2, candidate_3), candidate_5);
        int maxIndex_2 = 0, maxIndex_3 = 0, maxIndex_5 = 0;
        list.add(winner);

        // 从小到大依次加入丑数
        while (list.size() < n) {
            candidate_2 = list.get(maxIndex_2) * 2;
            System.out.println("candidate_2: " + candidate_2);

            candidate_3 = list.get(maxIndex_3) * 3;
            System.out.println("candidate_3: " + candidate_3);

            candidate_5 = list.get(maxIndex_5) * 5;
            System.out.println("candidate_5: " + candidate_5);

            winner = Math.min(Math.min(candidate_2, candidate_3), candidate_5);
            if (winner == candidate_2) {
                maxIndex_2++;
                System.out.println("这次是" + winner/2 + "乘以2放入！");
                System.out.println("因子2的最大指针后移为：" + maxIndex_2);
                System.out.println("maxIndex_2: " + maxIndex_2);
                System.out.println("maxIndex_3: " + maxIndex_3);
                System.out.println("maxIndex_5: " + maxIndex_5);
            }
            if (winner == candidate_3) {
                maxIndex_3++;
                System.out.println("这次是" + winner/3 + "乘以3放入！");
                System.out.println("因子3的最大指针后移为：" + maxIndex_3);
                System.out.println("maxIndex_2: " + maxIndex_2);
                System.out.println("maxIndex_3: " + maxIndex_3);
                System.out.println("maxIndex_5: " + maxIndex_5);
            }
            if (winner == candidate_5) {
                maxIndex_5++;
                System.out.println("这次是" + winner/5 + "乘以5放入！");
                System.out.println("因子5的最大指针后移为：" + maxIndex_5);
                System.out.println("maxIndex_2: " + maxIndex_2);
                System.out.println("maxIndex_3: " + maxIndex_3);
                System.out.println("maxIndex_5: " + maxIndex_5);
            }
            list.add(winner);
            System.out.println();
        }

        return list.get(n - 1);
    }

    public static int nthUglyNumber_Factorization(int n, int a, int b, int c) {
        int count = 0, factor_a = a, factor_b = b, factor_c = c;
        List<Integer> list = new ArrayList<>();
        // 丑数集合初始加入1，1可以同时看做1 * a^0 | 1 * b^0 | 1 * c^0，因此，初始通过三个因子均可到达当前最大丑数1
        // 对应各个丑数因子的当前最大元素位置分别是：maxIndex_a = 0, maxIndex_b = 0, maxIndex_c = 0。
        int candidate_a = (int) Math.pow(a, 0), candidate_b = (int) Math.pow(b, 0), candidate_c = (int) Math.pow(c, 0);
        int winner = Math.min(Math.min(candidate_a, candidate_b), candidate_c);
        int maxIndex_a = 0, maxIndex_b = 0, maxIndex_c = 0;
        list.add(winner);

        // 从小到大依次加入丑数
        while (list.size() < n) {
            candidate_a = list.get(maxIndex_a) * a;
            System.out.println("candidate_a: " + candidate_a);

            candidate_b = list.get(maxIndex_b) * b;
            System.out.println("candidate_b: " + candidate_b);

            candidate_c = list.get(maxIndex_c) * c;
            System.out.println("candidate_c: " + candidate_c);

            winner = Math.min(Math.min(candidate_a, candidate_b), candidate_c);
            if (winner == candidate_a) {
                maxIndex_a++;
                System.out.println("这次是" + winner/2 + "乘以a放入！");
                System.out.println("因子2的最大指针后移为：" + maxIndex_a);
                System.out.println("maxIndex_a: " + maxIndex_a);
                System.out.println("maxIndex_b: " + maxIndex_b);
                System.out.println("maxIndex_c: " + maxIndex_c);
            }
            if (winner == candidate_b) {
                maxIndex_b++;
                System.out.println("这次是" + winner/b + "乘以b放入！");
                System.out.println("因子3的最大指针后移为：" + maxIndex_b);
                System.out.println("maxIndex_a: " + maxIndex_a);
                System.out.println("maxIndex_b: " + maxIndex_b);
                System.out.println("maxIndex_c: " + maxIndex_c);
            }
            if (winner == candidate_c) {
                maxIndex_c++;
                System.out.println("这次是" + winner/c + "乘以c放入！");
                System.out.println("因子5的最大指针后移为：" + maxIndex_c);
                System.out.println("maxIndex_a: " + maxIndex_a);
                System.out.println("maxIndex_b: " + maxIndex_b);
                System.out.println("maxIndex_c: " + maxIndex_c);
            }
            list.add(winner);
            System.out.println("Step-" + ++count + "加入：" + winner);
        }

        return list.get(n - 1);
    }

    /**
     *
     */
    public int nthUglyNumber_DP(int n) {
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(nthUglyNumber_Factorization(10));
        System.out.println(nthUglyNumber_Factorization(13, 2, 3, 5));
    }
}
