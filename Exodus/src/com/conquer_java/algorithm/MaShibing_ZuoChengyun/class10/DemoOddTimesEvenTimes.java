package com.conquer_java.algorithm.MaShibing_ZuoChengyun.class10;

/**
 * 【题目】
 * 1) 在一个数组中，只有一个数字出现了奇数次，其他数字都是出现了偶数次，如何使用有限几个变量，找出这个数字？
 * 2) 在一个数组中，只有两个数字出现了奇数次，其他数字都是出现了偶数次，如何使用有限几个变量，找出这两数字？
 *
 * 【思路】
 * 1) num ^ num = 0；
 * 2) 任何一个二进制数字最右边的1位置可以通过取反加一获取：~num + 1——num的补码形式；
 * 3) 任何一个二进制数字最右边的1单独构成的二进制数值：num & (-num)
 *
 * 异或Exclusive OR，缩写XOR/EOR/EX-OR
 *
 * 【加法使用位操作实现】
 * 使用^实现无进位加法；
 * 进位使用& + <<代替；
 */
public class DemoOddTimesEvenTimes {
    public static void printUniqueNumWithOddTimes(int[] arr) {
        int xor = 0;
        for (int i : arr) {
            xor ^= i;
        }
        System.out.println(xor);
    }

    public static void printOnlyTwoNumsWithOddTimes(int[] arr) {
        int xor = 0; // 所有数字异或累加结果
        for (int i : arr) {
            xor ^= i;
        }
        System.out.println("出现奇数次的两个数字的异或结果：" + xor); // 其他出现偶数次的数字全部自行抵消，因此，所有数字异或结果就是这两个数字的异或结果。
        /**
         * 所有数字异或累加结果得到一个二进制数字，只留其中最右的1，其他数字全部置零，
         * 这个特殊数字（天选之子）含有31个0和1个1，所以，所有数字按照这个bit位，可以区分成为两类：
         * 一类，&天选之子=0；另外一类，&天选之子=1。我们希望从xor中分离出来的两个数一定分别位于两边。
         * 所有出现偶数次的数字必然全部属于其中一种，因此，我们按照各个天选之子chosenOne划分两类以后，
         * 两类内部所有数字各自进行累加异或操作，必然，两边只剩期望的数字。
         */
        int chosenOne = xor & (-xor);

        int one = 0, theother = 0;
        for (int i : arr) {
            if ((i & chosenOne) == 0)
                one ^= i;
            else
                theother ^= i;
        }

        if ((one ^ theother) != xor)
            throw new RuntimeException("计算错误！");

        System.out.println("出现奇数次的两个数字：" + one + "和" + theother);
    }

    private static int count_bit_1(int n) {
        int count = 0;
        while (n != 0) {
            int rightest1bit = n & -n;
            n -= rightest1bit;
            count++;
        }
        return count;
    }

    /**
     * 异或操作可以视为无进位加法
     * 25 + 28 = 43
     * 0001 1001 a
     * 0001 1100 b
     * 0011 0101 (a + b)
     *
     * (a ^ b) = 0000 0101 -> a
     * b = 0011 0000
     * (a ^ b) = 0011 0101 -> a
     * b = 0000 0000 -> jump loop
     * @param a
     * @param b
     * @return
     */
    private static int add(int a, int b) {
        int tmp = 0;
        while (b != 0) {
            tmp = a;
            a = a ^ b;
            b = ((tmp & b) << 1);
        }
        return a;
    }

    public static void main(String[] args) {
        int[] arr;
        arr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 0, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        printUniqueNumWithOddTimes(arr);
        arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 81};
        printOnlyTwoNumsWithOddTimes(arr);

        int a = 81, b = 99;
        System.out.println(add(a, b));
    }
}
