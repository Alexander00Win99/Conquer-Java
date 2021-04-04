package com.conquer_java.algorithm.exercise_interview.OD;

import java.util.*;
import java.util.stream.Collectors;

public class Divide {
    // 输入数据
    static List<Integer> list = null;
    // 所有可能结果
    static List<Integer> result = null;
    // 输入数据总和
    static int totalSum = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            // 积木数量
            int n = sc.nextInt();
            // 初始化，重置
            list = new ArrayList<>(n);
            result = new ArrayList<>();
            totalSum = 0;

            for (int i = 0; i < n; i++) {
                int input = sc.nextInt();
                list.add(input);
                totalSum += input;
            }

            // 不能取全部数 最少取一个数
            // 列举出每种取值数量所对应的组合
            for (int i = 1; i <= n/2; i++) {
                int[] output = new int[i];
                dfs(n, output, 0, 0);
            }
            if (result.size() == 0) {
                System.out.println(0);
            } else {
                // java.util.NoSuchElementException
                System.out.println(Collections.max(result));
            }
        }
    }

    /**
     * 获取指定数量的所有组合
     * @param num 元素数量（积木数量）
     * @param output 输出数组
     * @param index 要填充的output当前下标
     * @param start 从input第几个下标开始选择
     */
    public static void dfs(int num, int[] output, int index, int start) {
        if (index == output.length) {
            compareEquals(output);
            return;
        } else {
            for (int j = start; j < num; j++) {
                output[index] = j; // 记录选取的元素的下标
                dfs(num, output, index + 1, j + 1);//选取下一个元素，可选下标区间为[j+1,input.length]
            }
        }
    }

    /**
     * 二进制累加求和, 舍掉进位，即异或运算 （同为0，异为1）
     * 比较分隔的两部分积分重量是否相等
     */
    public static void compareEquals(int[] output) {
        // int数组转list，list不能存基本数据类型
        List<Integer> outputList = Arrays.stream(output).boxed().collect(Collectors.toList());
        int a = 0;
        int b = 0;
        // System.out.println("output: " + Arrays.toString(output));
        for (int i = 0; i < list.size(); i++) {
            // output里面存的是下标
            if (outputList.contains(i)) {
                // 选中
                a = a^list.get(i);
            } else {
                b = b^list.get(i);
            }
        }
        if (a == b && a > 0){
            int sum = getSum(output);
            result.add(sum);
            result.add(totalSum - sum);
        }
    }

    /**
     * 选中元素之和
     */
    public static int getSum(int[] output){
        int sum = 0;
        for(int i = 0; i < output.length; i++){
            sum += list.get(output[i]);
        }
        return sum;
    }
}
