package com.conque_java.algorithm.MaShibing_ZuoChengyun.class07;

/**
 * 【题目一】
 * 给你一个随机函数f，等概率返回1-5中的一个数字，这是你唯一可以使用的随机机制，如何实现等概率返回1-7中的一个数字；
 *
 * 或者继续抽象如下：
 *
 * 【题目二】
 * 给你一个随机函数f，等概率返回a-b中的一个数字，这是你唯一可以使用的随机机制，如何实现等概率返回c-d中的一个数字；
 */
public class DemoRandom2Random {
    /**
     * 给定的随机函数，提供random()黑盒方法，可在[min, max]左闭右闭区间随机返回一个整数。
     */
    public static class RandomBox {
        private final int min;
        private final int max;

        public RandomBox(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int random() {
            return min + (int)(Math.random() * (max - min + 1)); // random()左闭右开，所以，使用(max - min + 1)保证能够取到max
        }

        public int min() {
            return min;
        }

        public int max() {
            return max;
        }
    }

    /**
     * 利用RandomBox.random()方法，随机产生一个比特，或0或1。
     * 注：需要区分当前给定随机函数的[min, max]区间包含奇数还是偶数个数。
     * @param randomBox
     * @return
     */
    public static int randomBit(RandomBox randomBox) {
        int min = randomBox.min();
        int max = randomBox.max();
        int size = max - min + 1;
        boolean isEven = ((size & 1) == 0); // 判断是否偶数个数
        int mid = size / 2;
        int res = 0;

        do {
            res = randomBox.random() - min;
        } while (!isEven & res == mid); // 如果奇数个数，并且随机得到的数刚好就是中值，那么再次随机获取新值直至新随机值不是中值为止

        return res > mid ? 1 : 0;
    }

    /**
     * 随机返回[from, to]区间的整数
     * STEP-1：首先确定需要多少个比特位用来覆盖[0, to - from]区间；
     * STEP-2：每次随机产生一个比特位；
     * STEP-3：如果最终产生的数位于[0, to - from]区间，直接返回；否则重新随机直至满足要求；
     * 注：
     * 1) 每个bit比特位的生成是完全随机的行为，因此，[0, 2 ^ bitNum]区间的所有数值的生成是完全的等概率随机事件；
     * 2) 如果随机产生新值位于(to - from, 2 ^ bitNum]区间，需要重新进行随机，直至最终结果位于[0, to - from]区间为止；
     * 3) 生成(to - from, 2 ^ bitNum]区间数字的概率，会被重新均摊赋予在[0, to - from]区间的每个数字身上；
     * @param randomBox
     * @param from
     * @param to
     * @return
     */
    public static int random(RandomBox randomBox, int from, int to) {
        if (from > to) return Integer.MIN_VALUE;
        if (from == to) return from;

        int range = to - from;
        int bitNum = 1;
        int res = 0;

        while (((1 << bitNum) - 1) < range)
            bitNum++; // 求出能够覆盖表示当前range = (to - from)个数的二进制比特位数

        do {
            res = 0;
            for (int i = 0; i < bitNum; i++) {
                res += randomBit(randomBox) << i; // rand(randomBox)随机产生一个比特位，或0或1；res最终产生的随机值，如果超出range=[from, to]区间，那么重新随机，直至满足要求。
            }
        } while (res > range); // 只要超出区间，就会重新随机。

        return res + from;
    }

    public static void main(String[] args) {
        RandomBox randomBox = new RandomBox(7, 13);
        int from = 49;
        int to = 81;
        int[] res = new int[to + 1];
        int loop = 1000 * 1000;
        int count = 0;
        int rand;
        for (int i = 0; i < loop; i++)
            res[random(randomBox, from, to)]++;

        System.out.println("++++++++++++++++Begin-Verification：打印各值出现次数++++++++++++++++");
        for (int i = 0; i < res.length; i++) {
            if (res[i] != 0) {
                if ((i < from) || (i > to))
                    System.out.println("OOPS!");
                count += res[i];
                System.out.println(i + " : " + res[i]);
            }
        }
        System.out.println("----------------End-Verification：打印各值出现次数----------------");

        System.out.println("总计次数：" + count);
        if (count != loop) System.out.println("OOPS!");
    }
}
