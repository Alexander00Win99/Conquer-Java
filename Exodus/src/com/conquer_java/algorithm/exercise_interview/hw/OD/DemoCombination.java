package com.conquer_java.algorithm.exercise_interview.hw.OD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * permutation是有序的；combination是无序的
 *
 */
public class DemoCombination {
    private static List list = new ArrayList();

    /**
     * 从input数组中第start下标位置起往后取元素（可以连续可不连续）填满output数组
     * @param input
     * @param start
     * @param output
     * @param index
     */
    public static void fillCombination(int[] input, int start, int[] output, int index) {
        if (index == output.length) { // 如果output数组已经填满溢出，代表组合填充完毕，那么直接输出output组合内容不再向下递归
            System.out.println(Arrays.toString(output));
            list.add(output);
            return;
        }
        for (int i = start; i < input.length; i++) {
            output[index] = input[i];
            fillCombination(input, i + 1, output, index + 1);
        }
    }

    public static void main(String[] args) {
//        int[] input = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] input = new int[]{1, 2, 3, 4, 5};
        int length = 3;
        int[] output = new int[length];
        System.out.println(Arrays.toString(input));
        System.out.println("input是否int[]类型？");
        System.out.println(input instanceof int[]);

        fillCombination(input, 0, output, 0);
        list.forEach(System.out::println);
//        int combination
//        List<Integer[]> output = new ArrayList();
//        //output = combination(input, 0, 10);
//        combination(input, output, 0, 10);
//        for (Integer[] arr : output) {
//            //Arrays.stream(o).forEach(element -> System.out.println(element));
//            System.out.println(Arrays.deepToString(arr));
//        }
//        System.out.println(output);
    }
}
