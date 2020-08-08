package com.conque_java.algorithm.MaShibing_ZuoChengyun.class10;

/**
 * 【题目】
 * 如何不用任何额外变量实现交换两个整数的值？
 *
 * 【思路】
 * 利用XOR异或运算的特点：
 * 1) num ^ 0 = num;——任意一个数和0异或等于其自身
 * 2) num ^ num = 0;——任意
 * 3) num1 ^ num2 = num2 ^ num1;——交换律：Commutative Law
 * 4) (num1 ^ num2) ^ num3 = num1 ^ num2 ^ num3 = num1 ^ (num2 ^ num3);——结合律：Associative Law
 * 注：异或运算没有分配率Distributive Law
 */
public class DemoSwapIntByXor {
    public static void swapInt(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
    }

    public static void main(String[] args) {
        int a = 0, b = 1;
        swapInt(a, b);
        System.out.println("(a, b): " + a + " " + b);
    }
}
