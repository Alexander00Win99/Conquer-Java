package com.conque_java.algorithm.leetcode;

/**
 * Blaise Pascal(1623-1662，法国数学家、文学家、哲学家)
 * Pascal's Triangle-1654(中国称作：杨辉三角|贾宪三角)神奇特性：
 * 1) 第n行的m个数可表示为 C(n-1，m-1)，即为从n-1个不同元素中取m-1个元素的组合数；
 * 2) 下一行的数字等于上一行的左右数字之和，第n+1行的第i个数字等于第n行的第i-1个数字和第i个数字之和(C(n+1,i)=C(n,i)+C(n,i-1)，组合数性质之一)；
 * 3) 第n行的第m个数和第n-m+1个数相等(组合数性质之一)；
 * 4) 将第2n+1行第1个数，第2n+2行第3个数，第2n+3行第5个数……连成一线，数字之和是第4n+1个斐波那契数；将第2n行第2个数(n>1)，第2n-1行第4个数，第2n-2行第6个数……连成一线，数字之和是第4n-2个斐波那契数；
 * 5) (a+b)^n的多项式展开式中的各项系数对应第(n+1)行的每个数字；
 * 6) 第n行数字之和为2^(n-1)；
 * 8) 第n行的数字分别乘以10^(m-1)，其中m为该数所在的列，各项加和为11^(n-1)；
 * 9) 各行数字左对齐以后，从右上到左下对角线数字之和等于斐波那契数列的对应数字；
 */
public class DemoPascalTriangle {
}
