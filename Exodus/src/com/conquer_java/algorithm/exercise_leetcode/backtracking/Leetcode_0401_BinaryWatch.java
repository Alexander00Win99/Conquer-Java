package com.conquer_java.algorithm.exercise_leetcode.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A binary watch has 4 LEDs on the top which represent the hours (0-11), and the 6 LEDs on the bottom represent the minutes (0-59).
 * Each LED represents a zero or one, with the least significant bit on the right.
 *
 * For example, the above binary watch reads "3:25".
 * Given a non-negative integer n which represents the number of LEDs that are currently on, return all possible times the watch could represent.
 *
 * Example:
 * Input: n = 1
 * Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
 * Note:
 * The order of output does not matter.
 * The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
 * The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it should be "10:02".
 */
public class Leetcode_0401_BinaryWatch {
    private static List<String> list = new ArrayList<>();
    // 小时、分钟、秒钟二进制映射图
    private static int[][] hMap12 = new int[3][];
    private static int[][] hMap24 = new int[4][];
    private static int[][] mMap = new int[5][];
    private static int[][] sMap = new int[5][];

    public static List<String> readBinaryWatch(int num) {
        if (num <= 0 || num > 8) return new ArrayList<>();

        int h, m;
        int hCount, mCount;
        List<String> hList = new ArrayList<>();
        List<String> mList = new ArrayList<>();
        list = new ArrayList<>();
        if (num <= 6) {
            for (int i = 0; i < 3 && i < num; i++) {
                StringBuilder sb = new StringBuilder();
                List<String> temp = new ArrayList<>();
                hCount = i;
                mCount = (num - i) <= 6 ? num - i : -1;
                int[][] timeMap = new int[3][];
                timeMap[0] = hMap12[hCount];
                //timeMap[1] = new int[]{};
                timeMap[1] = mMap[mCount];
                dfs(sb, list, timeMap, list.size());
            }
        }

        return list;
    }

    private static void dfs(StringBuilder sb, List<String> list, int[][] map, int index) {
        if (index >= map.length) {
            list.add(sb.toString());
            return;
        }

        for (int n : map[index]) {
            sb.append(n);
            dfs(sb, list, map, index + 1);
            sb.deleteCharAt(sb.length() - 1);
            if (n >= 10) sb.deleteCharAt(sb.length() - 1);
        }
    }

    private static void constructTimeMap() {
        List<Integer> list_1 = new ArrayList<>();
        List<Integer> list_2 = new ArrayList<>();
        List<Integer> list_3 = new ArrayList<>();
        List<Integer> list_4 = new ArrayList<>();
        List<Integer> list_5 = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            int count = Integer.bitCount(i);
            switch (count) {
                case 1:
                    list_1.add(i);
                    break;
                case 2:
                    list_2.add(i);
                    break;
                case 3:
                    list_3.add(i);
                    break;
                default:
                    break;
            }
        }
        hMap12[0] = list_1.stream().mapToInt(Integer::intValue).toArray();
        hMap12[1] = list_2.stream().mapToInt(Integer::intValue).toArray();
        hMap12[2] = list_3.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.deepToString(hMap12));
        list_1 = new ArrayList<>();
        list_2 = new ArrayList<>();
        list_3 = new ArrayList<>();

        for (int i = 1; i <= 24; i++) {
            int count = Integer.bitCount(i);
            switch (count) {
                case 1:
                    list_1.add(i);
                    break;
                case 2:
                    list_2.add(i);
                    break;
                case 3:
                    list_3.add(i);
                    break;
                case 4:
                    list_4.add(i);
                    break;
                default:
                    break;
            }
        }
        hMap24[0] = list_1.stream().mapToInt(Integer::intValue).toArray();
        hMap24[1] = list_2.stream().mapToInt(Integer::intValue).toArray();
        hMap24[2] = list_3.stream().mapToInt(Integer::intValue).toArray();
        hMap24[3] = list_4.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.deepToString(hMap24));
        list_1 = new ArrayList<>();
        list_2 = new ArrayList<>();
        list_3 = new ArrayList<>();
        list_4 = new ArrayList<>();

        for (int i = 1; i <= 60; i++) {
            int count = Integer.bitCount(i);
            switch (count) {
                case 1:
                    list_1.add(i);
                    break;
                case 2:
                    list_2.add(i);
                    break;
                case 3:
                    list_3.add(i);
                    break;
                case 4:
                    list_4.add(i);
                    break;
                case 5:
                    list_5.add(i);
                    break;
                default:
                    break;
            }
        }
        mMap[0] = list_1.stream().mapToInt(Integer::intValue).toArray();
        mMap[1] = list_2.stream().mapToInt(Integer::intValue).toArray();
        mMap[2] = list_3.stream().mapToInt(Integer::intValue).toArray();
        mMap[3] = list_4.stream().mapToInt(Integer::intValue).toArray();
        mMap[4] = list_5.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.deepToString(mMap));
        sMap[0] = list_1.stream().mapToInt(Integer::intValue).toArray();
        sMap[1] = list_2.stream().mapToInt(Integer::intValue).toArray();
        sMap[2] = list_3.stream().mapToInt(Integer::intValue).toArray();
        sMap[3] = list_4.stream().mapToInt(Integer::intValue).toArray();
        sMap[4] = list_5.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.deepToString(mMap));
        list_1 = new ArrayList<>();
        list_2 = new ArrayList<>();
        list_3 = new ArrayList<>();
        list_4 = new ArrayList<>();
        list_5 = new ArrayList<>();
    }

    private static void printIntWithFixedLength(int len) {

    }

    public static void main(String[] args) {
        constructTimeMap();
    }
}
