package com.conquer_java.algorithm.recursion_training;

/**
 * 裴波那契数列：
 * http://blog.sciencenet.cn/blog-566204-856417.html
 *
 * 1) 偶数项的平方比前后两项的乘积少1，奇数项的平方比前后两项的乘积多1；
 * 2) 第5n项和第12n项的裴波那契数与本项序号具有相似性(即可以整除)；
 * 3) 从数列的第9项开始，相邻两项之比接近黄金分割数，并且互为倒数，尤其是从第11项开始，前后相邻两项比值的小数部分均为0.6180......，无限接近于无理数-黄金分割比。
 *
 *  兔子数列：
 * M=1 1 A
 * M=2 1 A~
 * M=3 2 A->B
 * M=4 3 A->C B~
 * M=5 5 A->D B->E C~
 * M=6 8 A->F B->G C->H D~ E~
 * ...
 * M=n   F(n) = F(n-1） + F(n-2) // F(n-1)=1个月前的兔子数量，是当前存在的兔子数量，无须考虑兔子生长状态；F(n-2)=2个月前的兔子数量，无论当时处于什么生长阶段，当前已经能够生育，也是当前新近出生的兔子。
 */
public class DemoFibonacci {
    public static int fibonacci(int n) {
        if (n < 0) return -1;
        if (n <= 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static int fib(int n) {
        if (n < 0) return -1;
        int arr[] = new int[n + 1];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i < arr.length; i ++) {
            arr[i] = arr[i - 2] + arr[i - 1];
        }
        return arr[n];
    }

    public static void main(String[] args) {

    }
}
