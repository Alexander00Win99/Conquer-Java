package com.conquer_java.algorithm.exercise_interview.OD;

import java.util.Scanner;

public class DemoIntSumInString {
    public static int getSumInString(String s) {
        char[] chars = s.toCharArray();
        boolean bNegative = false;
        int sum = 0;
        StringBuilder sb = new StringBuilder("-"); // 用于收集负数

        for (int i = 0; i < chars.length; i++) {
            // 正数一位一位单独加，负数一串数字一起减
            if (Character.isDigit(chars[i])) { // 数字字符
                if (!bNegative) sum += Character.getNumericValue(chars[i]);
                else sb.append(chars[i]);
            } else { // 非数字字符
                if (bNegative) { // 若遇到任意非数字字符，加上当前负数，重新收集新的负数
                    bNegative = false;
                    if (sb.length() > 1) {
                        int negativeNumber = Integer.parseInt(sb.toString());
                        sum += negativeNumber;
                    }
                    sb = new StringBuilder("-");
                }
                if (chars[i] == '-') {
                    bNegative = true;
                    sb = new StringBuilder("-");
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            System.out.println(getSumInString(s));
        }
    }
}
