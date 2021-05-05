package com.conquer_java.algorithm.exercise_interview.tecent;

import java.util.ArrayList;

/**
 * 【测试用例设计】
 * 1) 字符串为空；            null
 * 2) 字符串长度小于3；       "", "9", "99"
 * 3) 字符串包含数字0；       "808", "088", "880", "909", "099", "990"
 * 4) 字符串包含非数字字符；   "123abc", "565@", "565*", "565#", "-12345678"...
 * 5) 长度为3n + 1字符串；    "5835", "58386268"
 * 6) 长度为3n + 2字符串；    "58385", "583862688"
 * 7) 一个不消的字符串；       "9999999"
 * 8) 前后扩展消除的字符串；   "35838538"
 * 9) 超长字符串；            "12818386536846344715767356517354666875713634145135415341557745137657713576..."
 * 10) for (100W)多次计算；
 */
public class Tecent_HappyElimination {
    private static final int SUM_TARGET = 16;
    public static int maxElimination = 0;

    private static void resetElimination() {
        maxElimination = 0;
    }

    public static int minLeftDigits(String digitStr) {
        if (digitStr == null) throw new IllegalArgumentException("参数异常");
        resetElimination();

        ArrayList<Integer> digits = new ArrayList<>();
        for (int i = 0; i < digitStr.length(); i++) {
            if (digitStr.charAt(i) < '1' || digitStr.charAt(i) > '9') throw new IllegalArgumentException("参数异常");
            digits.add(digitStr.charAt(i) - '0');
        }
        //System.out.println("得到整型ArrayList：" + digits);

        permutate(digits, 0);
        int result = digitStr.length() - maxElimination * 3;
        return result;
    }

    public static void permutate(ArrayList<Integer> restDigits, int eliminationTimes) {
        if (restDigits.size() < 3) {
            if (eliminationTimes > maxElimination) maxElimination = eliminationTimes;
            return;
        }

        // 从 [0, size() - 3] 之中挨个作为连消起始位尝试消除动作
        for (int i = 0; i <= restDigits.size() - 3; i++) {
            ArrayList<Integer> newRestDigits = new ArrayList<>();
            if (restDigits.get(i) + restDigits.get(i + 1) + restDigits.get(i + 2) == SUM_TARGET) { // 能够消除
                //System.out.println("成功消除：" + restDigits.get(i) + restDigits.get(i + 1) + restDigits.get(i + 2));
                newRestDigits = (ArrayList<Integer>) restDigits.clone();
                // 新的剩余数字之中减去 成功消除 的三个连续数字
                newRestDigits.remove(i);
                newRestDigits.remove(i);
                newRestDigits.remove(i);
                permutate(newRestDigits, eliminationTimes + 1);
            } else { // 不能消除
                for (int j = i + 3; j < restDigits.size(); j++) {
                    // 只需考虑后续部分
                    newRestDigits.add(restDigits.get(j));
                    permutate(newRestDigits, eliminationTimes);
                }
            }
        }
    }

    public static void permutate(ArrayList<Integer> restDigits, ArrayList<Integer> visitedDigits, int eliminationTimes) {
        if (restDigits.size() < 3) {
            if (eliminationTimes > maxElimination) maxElimination = eliminationTimes;
            System.out.println("本字符串最大有效消除次数：" + maxElimination);
            return;
        }

        // 从 [0, size() - 3] 之中挨个作为连消起始位尝试消除动作
        for (int i = 0; i <= restDigits.size() - 3; i++) {
            ArrayList<Integer> newRestDigits = new ArrayList<>();
            if (restDigits.get(i) + restDigits.get(i + 1) + restDigits.get(i + 2) == SUM_TARGET) {
                eliminationTimes++;
                System.out.println("成功消除：" + restDigits.get(i) + restDigits.get(i + 1) + restDigits.get(i + 2));
                newRestDigits = (ArrayList<Integer>) restDigits.clone();

                // 新的剩余数字之中减去 成功消除 的三个连续数字
                newRestDigits.remove(i);
                newRestDigits.remove(i);
                newRestDigits.remove(i);
            } else {
                for (int j = i + 3; j < restDigits.size(); j++) {
                    newRestDigits.add(restDigits.get(j));
                }
            }
            ArrayList<Integer> newVisitedDigits = (ArrayList<Integer>) visitedDigits.clone();
            // 新的已消数字之中加上 尝试消除 的三个连续数字
            newVisitedDigits.add(restDigits.get(i));
            newVisitedDigits.add(restDigits.get(i + 1));
            newVisitedDigits.add(restDigits.get(i + 2));
            permutate(newRestDigits, newVisitedDigits, eliminationTimes);
        }
    }

    public static void main(String[] args) {
        String digitStr;
        digitStr = "25";
        System.out.println("25 最小可以消为：" + minLeftDigits(digitStr) + "位");
        digitStr = "255655";
        System.out.println("255655 最小可以消为：" + minLeftDigits(digitStr) + "位");
        digitStr = "255655659";
        System.out.println("255655659 最小可以消为：" + minLeftDigits(digitStr) + "位");
    }
}
