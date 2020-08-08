package com.conque_java.algorithm.MaShibing_ZuoChengyun.class07;

/**
 * 【题目】
 * 给你一个随机函数f，以p概率返回0，以(1 - p)概率返回1，这是你唯一可以使用的随机机制，如何实现等概率返回0和1；
 *
 * 【分析】
 * 随机函数每次返回0或者1的概率，分别是p和1-p；
 * 随机函数各次返回之间相互独立；
 * 随机函数两次返回可以获得两个数字，4种组合，每种组合概率分布如下：00:p*p; 01:p*(1-p); 10:(1-p)*p; 11:(1-p)*(1-p)；
 * 观察规律：连续两次随机过程返回获得01和10的概率相同，都是p-p^2；
 * 制定规则：如若获得01和10，即刻返回0和1，如若获得00和11，此次作废，重新连续两次随机直至获得01或者10为止；
 */
public class DemoEqualProbabilityRandom {
    public static class RandomBox {
        private final double p;

        public RandomBox(double zeroProbability) {
            p = zeroProbability; // 0<p<1，p位于(0, 1)区间
        }

        public int random() {
            return Math.random() < p ? 0 : 1;
        }
    }

    public static int rand(RandomBox randomBox) {
        int high = -1, low = -1;

        do {
            high = randomBox.random();
            low = randomBox.random();
        } while ((high ^ low) != 1); // 强行剥夺00和11生存几率，重新均分，人为增加01和10出现几率。

        return high;
    }

    public static void main(String[] args) {
        double zeroProbability = 0.88;
        RandomBox randomBox = new RandomBox(zeroProbability);
        int rand, count0 = 0, count1 = 0;
        int loop = 1000 * 1000;
        for (int i = 0; i < loop; i++) {
            rand = rand(randomBox);
            if (rand == 0)
                count0++;
            if (rand == 1)
                count1++;
            if ((rand != 0) & (rand != 1)) {
                System.out.println(rand);
                System.out.println("OOPS!");
            }
            if (rand == -1)
                System.out.println("\"rand == -1\" : OOPS!");
        }
        System.out.println("随机获得0总计次数：" + count0);
        System.out.println("随机获得1总计次数：" + count1);
        System.out.println("随机获得0总计概率：" + (double)count0/(double)loop);
        System.out.println("随机获得1总计概率：" + (double)count1/(double)loop);
    }
}
