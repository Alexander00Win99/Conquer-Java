package com.conque_java.algorithm.leetcode.recursion_training;

import java.util.*;

/**
 * 简.博克.莱诺拉袁 - 《拖延心理学》:拖延归根结底是对恐惧感的逃避 -  - Procrastination - Perfectionism
 * 如何战胜拖延心理：牢记——“在这个世界上没有那么多的人在乎你，只有你自己在乎自己，只有你自己特别在乎你自己的表现！”。
 *
 * 1) 丑数：
 * 质因数只包含2、3、5的正整数。
 * Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.
 *
 * Example 1:
 * Input: 6
 * Output: true
 * Explanation: 6 = 2 × 3
 *
 * Example 2:
 * Input: 8
 * Output: true
 * Explanation: 8 = 2 × 2 × 2
 *
 * Example 3:
 * Input: 14
 * Output: false
 * Explanation: 14 is not ugly since it includes another prime factor 7.
 *
 * 2) 快乐数：
 * 对于一个正整数，每次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是无限循环但始终变不到 1。如果可以变为 1，那么这个数就是快乐数。
 * A happy number is a number defined by the following process: Starting with any positive integer, replace the number by the sum of the squares of its digits,
 * and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those numbers for which
 * this process ends in 1 are happy numbers.
 * Example:
 * Input: 19
 * Output: true
 * Explanation:
 * 12 + 92 = 82
 * 82 + 22 = 68
 * 62 + 82 = 100
 * 12 + 02 + 02 = 1
 *
 * 3) 完美数：
 * 所有正因数(除了自身以外)之和等于自身的正数。
 * Perfect Number is a positive integer that is equal to the sum of all its positive divisors except itself.
 * Example:
 * Input: 28
 * Output: True
 * Explanation: 28 = 1 + 2 + 4 + 7 + 14
 * 欧几里得-欧拉定理(完美数定理)：如果p是质数，并且(2^p-1)(梅森素数)也是质数，那么(2^p-1) * 2^(p-1)即是偶完美数。例如：
 * 2 2^2-1=3 3*2=6
 * 3 2^3-1=7 7*4=28
 * 5 2^5-1=31 31*16=496
 * 十一个最小质数是{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31}
 *
 * 完美数与梅森数和梅森素数之间存在神秘而永久的联系。
 * 完美数有各式各样的推广，例如：k阶完美数、超完美数和平方完美数。因为在密码学的密钥系统中有应用，梅森数和梅森素数也有推广，
 * http://www.kxhb.com/201508/58.html
 */
public class MyInteger {
    public static boolean isUglyNumber(int number) {
       if (number == 0) return false;
       while(number != 1) {
           if (number % 2 == 0) {
               number = number / 2;
               continue;
           }
           if (number % 3 == 0) {
               number = number / 3;
               continue;
           }
           if (number % 5 == 0) {
               number = number / 5;
               continue;
           }
           return false;
       }
       return true;
    }

    public static boolean isUglyNum(int num) {
        // 递归出口
        if (num == 0) return false;
        if (num == 1) return true;
        // 如若2、3、5其中之一能够整除该数，继续递归，直至为1，返回true
        if (num % 2 == 0) return isUglyNum(num / 2);
        if (num % 3 == 0) return isUglyNum(num / 3);
        if (num % 5 == 0) return isUglyNum(num / 5);
        // 如若不能，返回false
        return false;
    }

    /**
     * 1, 2, 3, 4, 5, 6, 8, 10, 12, ......
     * num, 2 * num, 3 * num, 2 * 2 * num, 5 * num, 2 * 3 * num
     * @param n
     * @return
     */
    public static int nthUglyNumber(int n) {
        if (n <= 0 || n >= 1690) return -1;
//        List<Integer> list = new ArrayList<>(1690);
        Map<Integer, Integer> map = new HashMap<>(1690);

        for (int i = 1; i <= 6; i++) {
            map.put(i, i);
        }
        int uglyNumber = 1;
        int prev = 0;
        int next = 0;
        if (uglyNumber % 5 == 0) { // 含质因数5
            if (uglyNumber % 3 == 0) { // 含质因数5，3
                if (uglyNumber % 2 == 0) { // 1) 含质因数5、3、2 <==> + 5 + 3 + 2

                } else { // 2) 含质因数5和3，不含质因数2 <==> + 5 + 3 - 2
                    next = uglyNumber / 3 * 2 * 2;
                }
            } else if (uglyNumber % 2 == 0) { // 3) 含质因数5和2，不含质因数3 <==> + 5 - 3 + 2
                next = uglyNumber / 2 * 3;
            } else { // 4) 只含质因数5，不含质因数3和2 <==> + 5 - 3 - 2
                next = uglyNumber / 5 * 2 * 3;
            }
        } else if (uglyNumber % 3 == 0) { // 不含质因数5，含质因数3
            if (uglyNumber % 2 == 0) { // 5) 不含质因数5，含质因数3和2 <==> - 5 + 3 + 2
                next = uglyNumber / 3 * 2 * 2;
            } else { // 6) 不含质因数5和2，只含质因数3 <==> - 5 + 3 - 2
                if (uglyNumber % 9 == 0) next = uglyNumber / 9 * 10;
                next = uglyNumber / 3 * 4;
            }
        } else if (uglyNumber % 2 == 0) { // 7) 不含质因数5和3，只含质因数2 <==> - 5 - 3 + 2
            if (uglyNumber % 8 == 0) next = uglyNumber / 8 * 9;
            if (uglyNumber % 4 == 0) next = uglyNumber / 4 * 5;
            next = uglyNumber / 2 * 3;
        } else { // 8) 不含质因数5、3、2 <==> - 5 - 3 - 2，有悖假设

        }
//        list.add(1);

        return n;
    }



    public static int nthUglyNum(int n) {
        return n;
    }

    public static boolean isHappyNumber(int number) {
//        Set<Integer> set = new HashSet<>();
//        set.add(number);
//
//        while (number != 1) {
//            int sum = 0;
//            do {
//                int digit = number % 10;
//                sum += Math.pow(digit, 2);
//                number = number / 10;
//            } while (number / 10 != 0);
//            if (set.contains(sum)) return false;
//            set.add(sum);
//            number = sum;
//        }
//        return true;

        List<Integer> list = new ArrayList<>();

        while (number != 1) {
            int sum = 0;
            while (number > 0) {
                int digit = number % 10;
                sum += digit * digit;
                number = number / 10;
            }
            number = sum;
            if (list.contains(sum)) break;
            list.add(sum);
        }

        return number == 1;
    }

    /**
     * 运算结果有限-只有两种：1或者4
     * 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4
     * @param num
     * @return
     */
    public static boolean isHappyNum(int num) {
        if (num == 1) return true;
        if (num == 4) return false;

        int sum = 0;
        while (num > 0) {
            int digit = num % 10;
            sum += digit * digit;
            num = num / 10;
        }
        return isHappyNum(sum);
    }

    public static boolean isPerfectNumber(int number) {
        int sum = 1;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                sum += i + (number / i == i ? 0 : number / i);
            }
        }
        // sum包含number自身，需要去除以后再行判断，或者上述循环从2开始，sum从1开始。
        // return sum - number == number;(int sum = 0;)
        return  number != 1 && sum == number;
    }

    public static boolean isPerfectNum(int num) {
        int[] primeNums = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
        for (int i = 0; i < primeNums.length; i++) {
            int p = primeNums[i];
            int number = (int) ((Math.pow(2, p) - 1) * Math.pow(2, p - 1));
            if (number == num)
                return true;
        }
        return false;
    }

    public static boolean checkPerfectNumber(int number) {
        if (number <= 1) return false;

        int begin = 2;
        int end = number / 2;
        int sum = 1;

        for ( ; begin <= end; ) {
            System.out.println(begin + " - " + end);
            if (number % begin == 0) {
                end = number / begin;
                sum += begin + end--; // sum += begin++ + end--错误：因为，begin++和end--都是位于if语句之类，那么可能不作更新，导致死循环！
            }
            begin++;
        }

        return sum == number;
    }

    public static boolean checkPerfectNum(int num) {
        int[] perfectNumbers = {6, 28, 496, 8128, 33550336};
        for (int p : perfectNumbers) if (num == p) return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isUglyNumber(49));
        System.out.println(isUglyNum(81));

        System.out.println(isHappyNumber(19));
        System.out.println(isHappyNum(18));

        System.out.println(isPerfectNumber(28));
        System.out.println(isPerfectNum(496));

        System.out.println(checkPerfectNumber(496));
        System.out.println(checkPerfectNum(496));
    }
}
