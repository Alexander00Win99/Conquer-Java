package com.conquer_java.algorithm.MaShibing_ZuoChengyun.class09;

import java.util.ArrayList;
import java.util.List;

/**
 * 【题目】
 * 给定一个只有0和1组成的二维数组matrix，每行可以保证0在左边，1在右边，请返回一个列表列出所有行中拥有最多数量1的行（可能多个并列第一）。
 *
 * 【思路】
 * [0, 0, 0, 1, 1, 1, 1, 1, 1]
 * [0, 0, 0, 1, 1, 1, 1, 1, 1]
 * [0, 0, 0, 0, 1, 1, 1, 1, 1]
 * [0, 0, 0, 0, 0, 1, 1, 1, 1]
 * [0, 1, 1, 1, 1, 1, 1, 1, 1]
 * [0, 1, 1, 1, 1, 1, 1, 1, 1]
 * [1, 1, 1, 1, 1, 1, 1, 1, 1]
 * [1, 1, 1, 1, 1, 1, 1, 1, 1]
 * [0, 0, 0, 0, 0, 0, 0, 0, 0]
 *
 * 1) 双重循环暴力计算：时间复杂度O(N*M)
 * 2) 从右上往左下移动：时间复杂度O(N+M)
 * 3) 每行二分查找获取最左位置：时间复杂度O(N*log(M))
 * 4) 从右上往左下移动+每行二分查找获取最左位置：时间复杂度O(N+log(M))
 */
public class DemoLongestOnes {
    private static final int LEFT_VALUE = 0;
    private static final int RIGHT_VALUE = 1;

    private static int mostLeft(int[] arr, int L, int R) { // 二分查找寻找最左的右值，如果返回结果index == (R + 1)，代表区间全是左值没有右值
        if (arr[L] == RIGHT_VALUE && arr[R] == LEFT_VALUE)
            throw new RuntimeException("Illegal array inputted!");
        if (L > R)
            throw new RuntimeException("Invalid boundary parameter!");

        int M;
        int index = R + 1;

        while (L <= R) {
            M = L + ((R - L) >> 1);
            if (arr[M] == RIGHT_VALUE) {
                R = M - 1;
                index = M;
            } else {
                L = M + 1;
            }
        }
        return index;
    }

    private static int mostRight(int[] arr, int L, int R) { // 二分查找寻找最右的左值，如果返回结果index == (L - 1)，代表区间全是右值没有左值
        if (arr[L] == RIGHT_VALUE && arr[R] == LEFT_VALUE)
            throw new RuntimeException("Illegal array inputted!");
        if (L <= R)
            throw new RuntimeException("Invalid boundary parameter!");

        int M;
        int index = L - 1;

        while (L <= R) {
            M = L + ((R - L) >> 1);
            if (arr[M] == LEFT_VALUE) {
                L = M + 1;
                index = M;
            } else {
                R = M - 1;
            }
        }
        return index;
    }

    /**
     * 双重循环暴力计算：时间复杂度O(N*M)。
     *
     * @param matrix
     * @return
     */
    public static List<Integer> longestOnes1(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        final int rows = matrix.length;
        final int cols = matrix[0].length;
        int maxLen = 0;

        for (int i = 0; i < rows; i++) { // 逐行处理
            int j = cols;
            while (j > 0 && matrix[i][j - 1] == 1) { // 每行从右到左找到最左位置的1
                j--;
            }
            if (maxLen == cols - j && maxLen != 0) { // 加入附加条件：maxLen != 0 ==> 避免极端情况：整个矩阵没有一个1，但是maxLen=0，每行都被加入结果列表。
                res.add(i);
            }
            if (maxLen < cols - j) {
                maxLen = cols - j;
                res.clear();
                res.add(i);
            }
        }
        return res;
    }

    /**
     * 从右上往左下移动：每行找到当行最左的1边界位置以后直接垂直进入下行，如果下行相同位置是1，继续探索下行最左位置，否则继续直接垂直进入下行，时间复杂度O(N+M)
     *
     * @param matrix
     * @return
     */
    public static List<Integer> longestOnes2(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        final int rows = matrix.length;
        final int cols = matrix[0].length;
        int maxLen = 0;
        int p = cols;

        for (int i = 0; i < rows; i++) { // 逐行处理
            if (p != cols && matrix[i][p] == 0) continue; // 上行最左位置的1垂直向下进入下行，如果本行进入位置元素为0，直接进入本行的下行
            while (p > 0 && matrix[i][p - 1] == 1) { // 上行最左位置的1垂直向下进入下行，如果下行相同位置左边不为1，没有必有向左更新最左边界
                p--;
            }
            if (maxLen == cols - p && maxLen != 0) { // 加入附加条件：maxLen != 0 ==> 避免极端情况：整个矩阵没有一个1，但是maxLen=0，每行都被加入结果列表。
                res.add(i);
            }
            if (maxLen < cols - p) {
                maxLen = cols - p;
                res.clear();
                res.add(i);
            }
        }
        return res;
    }

    /**
     * 每行二分查找获取最左位置：时间复杂度O(N*log(M))
     *
     * @param matrix
     * @return
     */
    public static List<Integer> longestOnes3(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        final int rows = matrix.length;
        final int cols = matrix[0].length;
        int maxLen = 0;

        for (int i = 0; i < rows; i++) { // 逐行处理
            int j = mostLeft(matrix[i], 0, cols - 1); // 二分查找求得最左的1，极端情况是，j == cols。
            if (maxLen == cols - j && maxLen != 0) { // 加入附加条件：maxLen != 0 ==> 避免极端情况：整个矩阵没有一个1，但是maxLen=0，每行都被加入结果列表。
                res.add(i);
            }
            if (maxLen < cols - j) {
                maxLen = cols - j;
                res.clear();
                res.add(i);
            }
        }
        return res;
    }

    /**
     * 从右上往左下移动+每行二分查找获取最左位置：时间复杂度O(N+log(M))
     *
     * @param matrix
     * @return
     */
    public static List<Integer> longestOnes4(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        final int rows = matrix.length;
        final int cols = matrix[0].length;
        int maxLen = 0;
        int p = cols;

        for (int i = 0; i < rows; i++) { // 逐行处理
            if (p != cols && matrix[i][p] == 0) continue;
            if (p > 0)
                p = mostLeft(matrix[i], 0, p - 1); // 二分查找求得最左的1，极端情况是，j == cols。
            if (maxLen == cols - p && maxLen != 0) { // 加入附加条件：maxLen != 0 ==> 避免极端情况：整个矩阵没有一个1，但是maxLen=0，每行都被加入结果列表。
                res.add(i);
            }
            if (maxLen < cols - p) {
                maxLen = cols - p;
                res.clear();
                res.add(i);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<Integer> result, result1, result2, result3, result4;
        int[][] testArr = { // 边界测试目的：矩阵之中没有一个1存在，maxLen != 0，但是每行都被加入结果列表返回。
                {0, 0},
                {0, 0}
        };
        result = longestOnes1(testArr);

        int[][] matrix = {
                {0, 0, 0, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        result1 = longestOnes1(matrix);
        result2 = longestOnes2(matrix);
        result3 = longestOnes3(matrix);
        result4 = longestOnes4(matrix);
    }
}
