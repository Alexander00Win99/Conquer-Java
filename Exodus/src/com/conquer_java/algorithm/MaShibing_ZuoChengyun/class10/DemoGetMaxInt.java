package com.conquer_java.algorithm.MaShibing_ZuoChengyun.class10;

/**
 * 【题目】
 * 如何不用任何比较判断，就能返回两数之中较大那个？
 *
 * 【思路】
 * 参考目标：return (a > b) ? a : b; => 需要达到：quotietyA * a + quotietyB * b，同时保证quotietyA和quotietyB互斥，若一个为零，则另一为一。
 * Step-1：两数正负符号相反（same=0, contrary=1），直接返回正数（和(a + b)不会溢出，差(a - b)可能溢出）；
 * Step-2：两数正负符号相同（same=1, contrary=0），(a - b)不会溢出，判定差值(a - b)符号正负，为1代表a大，为0代表b大；
 * Step-3：合并上述两式
 *
 * [20]原 = 0001 0100 1) 右移一位：20 >> 1 -> 0000 1010(+10)；2) 右移两位：20 >> 2 -> 0000 0101(+5)
 * [-20]补 = 1110 1100 1) 右移一位：-20 >> 1 -> 1111 0110(-10)；2) 右移两位：20 >> 2 -> 0000 0101(-5)
 *
 * 【变量名称】
 * quotient 商，商数
 * quotiety 率，系数
 * coefficent 率，程度，协同因素
 * modulus 系数，模数
 * ratio 比例，比率，系数
 */
public class DemoGetMaxInt {
    /**
     * 非负:0->1 负数:1->0
     * @param n 要么为0，要么为1
     * @return
     */
    private static int flip(int n) {
        return n ^ 1;
    }

    /**
     * 非负(正数或者零)返回0，负数返回1
     * Step-01: 一个int类型的数，>>31以后，要么32个bit全0，要么全1；
     * Step-02: 全0 & 1 -> 0，全1 & 1 -> 1；
     * Step-03: 0->1，1->0；
     * 最终：非负<==>1，负数<==>0
     * @param n 任意整数
     * @return
     */
    private static int sign(int n) {
        return flip((n >> 31) & 1); // 32位长的int带符号位右移31位，得到32位全0或者全1的数字。0000 0000 0000 0000 0000 0000 0000 0000或者1111 1111 1111 1111 1111 1111 1111 1111
    }

    /**
     * 并非精确实现，因为(a - b)可能溢出。
     * 若(a - b) >= 0，selectA为1，selectB为0，取a；
     * 若(a - b) < 0，selectA为0，selectB为1，取b；
     * @param a
     * @param b
     * @return
     */
    public static int getMaxRoughly(int a, int b) {
        int diff = a - b;
        int selectA = sign(diff);
        int selectB = flip(selectA);
        return selectA * a + selectB * b;
    }

    /**
     * 精确实现方式：无论(a - b)是否溢出，均可保证结果正确。
     * @param a
     * @param b
     * @return
     */
    public static int getMax(int a, int b) {
        int signA = sign(a);
        int signB = sign(b);
        int signDiff = sign(a - b);

        int contrary = signA ^ signB; // a和b符号相反为1；a和b符号相同为0——注意：此处0算作正数范畴
        int same = flip(contrary); // a和b符号相同为1；a和b符号相反为0——注意：此处0算作正数范畴

        // 当a和b符号相同时，same==1，contrary==0，(a + b)可能溢出，(a - b)不会溢出，因此可以基于signDiff返回大值：(a - b) >= 0，signDiff是1，返回a；(a - b) < 0，signDiff是0，返回b。
        int ret1 = same * signDiff * a + same * flip(signDiff) * b;
        // 当a和b符号相反时，same==0，contrary==1，(a + b)不会溢出，(a - b)可能溢出，直接根据a和b的符号返回其中为1者也即正数作为大值：signA=1，signB=0返回a；signA=0，signB=1是0，返回b。
        int ret2 = contrary * signA * a + contrary * signB * b;

        // 综合符号相同相反两种场景，参考最终期望quotietyA * a + quotietyB * b的表达式形式，合并系数如下：
        int quotietyA = flip(signA ^ signB) * sign(a - b) + (signA ^ signB) * signA;
        int quotietyB = flip(signA ^ signB) * sign(a - b) + (signA ^ signB) * signB;
        return quotietyA * a + quotietyB * b;
    }

    public static void main(String[] args) {
        int a = 81;
        int b = -9;
        int c = Integer.MAX_VALUE - 1;
        int d = Integer.MIN_VALUE + 1;

        System.out.println(getMaxRoughly(a, b));
        System.out.println(getMaxRoughly(c, d));
        System.out.println(getMax(a, b));
        System.out.println(getMax(c, d));
    }
}
