package com.conque_java.algorithm.MaShibing_ZuoChengyun.class09;

/**
 * 【题目】——LeetCode: Search a 2D Matrix II
 * 给定一个每行有序每列有序的二维数组matrix和数字num，返回matrix中是否存在num。
 *
 * 【思路】
 * 充分利用数据特征，优化算法。假设每行自左向右递增，每列自上而下递增。
 * 从左下角或者右上角开始遍历：
 * Bottom left——左下: 当前位置作为起点开始遍历，没有向左的选择，没有向下的选择，只能向右（增大）或者向上（减小）。假设当前位置元素的值小于目标值target，那么矩阵之中如果存在特定目标值target的话，只能向右（增大），不能向上（减小），当然也不可能向下（增大），下边元素已经遍历过了，肯定是当时大于target才会向上来到当前位置的，同理，如果前位置元素的值大于目标值target，只能向上（减小）。
 * Top right——右上: 当前位置作为起点开始遍历，没有向右的选择，没有向上的选择，只能向左（减小）或者向下（增大）。假设当前位置元素的值小于目标值target，那么矩阵之中如果存在特定目标值target的话，只能向下（增大），不能向左（减小），当然也不可能向右（增大），右边元素已经遍历过了，肯定是当时大于target才会向左来到当前位置的，同理，如果前位置元素的值大于目标值target，只能向左（减小）。
 */
public class DemoSearch2DMatrixII {
    public static boolean searchMatrix(int[][] matrix, int target) {
        boolean bAscending;
        int rows = matrix.length;
        int cols = matrix[0].length;
        if (matrix[0][0] < matrix[rows - 1][cols - 1]) bAscending = true;
        else if (matrix[0][0] > matrix[rows - 1][cols - 1]) bAscending = false;
        else throw new RuntimeException("请检查矩阵数据是否合规！");

        if (bAscending & (target < matrix[0][0] || target > matrix[rows - 1][cols - 1])) return false;
        if (!bAscending & (target > matrix[0][0] || target < matrix[rows - 1][cols - 1])) return false;

        int row = rows - 1;
        int col = 0;
        if (bAscending)
            while (row >= 0 && col <= cols - 1) {
                if (matrix[row][col] == target) return true;
                else if (matrix[row][col] < target) col++;
                else row--;
            }
        if (!bAscending)
            while (row >= 0 && col <= cols - 1) {
                if (matrix[row][col] == target) return true;
                else if (matrix[row][col] < target) row--;
                else col++;
            }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 5, 9},
                {2, 6, 10},
                {3, 7, 11}
        };
        System.out.println(searchMatrix(matrix, 9));
    }
}
