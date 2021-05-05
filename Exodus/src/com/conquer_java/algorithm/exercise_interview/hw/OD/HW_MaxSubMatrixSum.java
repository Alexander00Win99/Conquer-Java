package com.conquer_java.algorithm.exercise_interview.hw.OD;

import java.util.Arrays;
import java.util.Scanner;

public class HW_MaxSubMatrixSum {
    public void getMaxMatrixSum(int[][] matrix) { // 以矩阵各点为左上顶点，向右下求取各个子矩阵和中最大值
        int max = Integer.MIN_VALUE;
        for (int row = 0; row < matrix.length; row ++) {
            for (int col = 0; col < matrix[0].length; col++) {
//                int sum = getSubAreaSum(matrix, row, col);
//                max = Math.max(sum, max);
            }
        }
    }

    public int getSubAreaSum(int[][] martrix, int startRow, int startCol, int endRow, int endCol) { // 求取指定点为左上顶点，向右下延展的各个子矩阵和中最大值
        if (startRow >= martrix.length || startCol >= martrix[0].length || endRow >= martrix.length || endCol >= martrix[0].length) return Integer.MIN_VALUE;
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = endRow; i < martrix.length; i++) {
            int[] ints = martrix[i];
            for (int j = endCol; j < ints.length; j++) {
                for (int row = startRow; row <= endRow; row++) {
                    for (int col = startCol; col < endCol; col++) {
                        sum += martrix[row][col];
                    }
                }
            }
        }
        max = Math.max(sum, max);
        return max;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = 0, m = 0;
        while (sc.hasNextInt()) {
            n = sc.nextInt();
            m = sc.nextInt();
        }
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            while (sc.hasNextLine()) {
                while (sc.hasNextInt()){
                    matrix[i][i] = sc.nextInt();
                }
            }
        }
        System.out.println(Arrays.deepToString(matrix));
    }
}
