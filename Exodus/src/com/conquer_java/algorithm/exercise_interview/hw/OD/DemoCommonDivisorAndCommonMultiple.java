package com.conquer_java.algorithm.exercise_interview.hw.OD;

public class DemoCommonDivisorAndCommonMultiple {
    public static int getMaxCommonDivisor(int a, int b) {
        if (a % b == 0 || b % a == 0)
            return a <= b ? a : b;
        int maxCommonDivisor = 1; // 最大公倍数初始化为1
        int min = Math.min(a, b);
        int factor = 2;
        while (factor <= Math.min(a, b)) {
            if (a % factor == 0 && b % factor == 0) {
                a /= factor;
                b /= factor;
                maxCommonDivisor *= factor;
                continue;
            }
            factor++;
        }
        return maxCommonDivisor;
    }

    public static int getMinCommonMultiple(int a, int b) {
        if (a % b == 0 || b % a == 0)
            return a >= b ? a : b;
        int minCommonMultiple = (a * b) / getMaxCommonDivisor(a, b);
//        if (a < b) { // 保证a总是大于b
//            a = a ^ b;
//            b = a ^ b;
//            a = a ^ b;
//        }
//        while (b != 0) {
//            int t = a % b;
//            a = b;
//            b = t;
//        }
        return minCommonMultiple;
    }

    public static void main(String[] args) {
        System.out.println(getMaxCommonDivisor(16, 8));
        System.out.println(getMinCommonMultiple(328, 7751));
        System.out.println(getMaxCommonDivisor(4, 8));
        System.out.println(getMaxCommonDivisor(14, 3));
        System.out.println(getMinCommonMultiple(28, 6));
    }
}
