package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
 * An example is the root-to-leaf path 1->2->3 which represents the number 123.
 * Find the total sum of all root-to-leaf numbers.
 * Note: A leaf is a node with no children.
 *
 * Example:
 * Input: [1,2,3]
 *     1
 *    / \
 *   2   3
 * Output: 25
 * Explanation:
 * The root-to-leaf path 1->2 represents the number 12.
 * The root-to-leaf path 1->3 represents the number 13.
 * Therefore, sum = 12 + 13 = 25.
 *
 * Example 2:
 * Input: [4,9,0,5,1]
 *     4
 *    / \
 *   9   0
 *  / \
 * 5   1
 * Output: 1026
 * Explanation:
 * The root-to-leaf path 4->9->5 represents the number 495.
 * The root-to-leaf path 4->9->1 represents the number 491.
 * The root-to-leaf path 4->0 represents the number 40.
 * Therefore, sum = 495 + 491 + 40 = 1026.
 *
 * 【注意事项】
 * 1) Integer.valueof()返回的是Integer对象，Integer.paseInt()返回的是int数字基本类型。
 * 2) 若以(root == null)作为出口，进行相关计算，则需对于最终sum取半，因为所有数字都被加了两遍。
 */
public class Leetcode_0129_SumRoot2LeafNumbers {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private static List<String> numbers = new ArrayList<>();
    private static int sum;

    public static int sumNumbers(TreeNode root) {
        if (root == null) return sum;
        sumNumbers(root, 0);
        return sum;
//        int sum = 0;
//        StringBuilder sb = new StringBuilder("");
//        sumNumbers(root, sb);
//        for (String number : numbers) {
//            sum += Integer.parseInt(number);
//        }
//        //return sum / 2;
//        return sum;
    }

    public static void sumNumbers(TreeNode root, int num){
        if (root == null) return;
        if (root.left == null && root.right == null) {
            sum += 10 * num + root.val;
            return;
        }
        if (root.left == null) {
            sumNumbers(root.right, num * 10 + root.val);
            return;
        }
        if (root.right == null) {
            sumNumbers(root.left, num * 10 + root.val);
            return;
        }
        sumNumbers(root.left, num * 10 + root.val);
        sumNumbers(root.right, num * 10 + root.val);
    }

    public static void sumNumbers(TreeNode root, StringBuilder sb){
//        if (root == null) {
//            numbers.add(sb.toString());
//            return;
//        }

        if (root == null) return;
        if (root.left == null && root.right == null) {
            numbers.add(sb.append(root.val).toString());
            return;
        }
        if (root.left == null) {
            sumNumbers(root.right, new StringBuilder(sb).append(root.val));
            return;
        }
        if (root.right == null) {
            sumNumbers(root.left, new StringBuilder(sb).append(root.val));
            return;
        }
        sumNumbers(root.left, new StringBuilder(sb).append(root.val));
        sumNumbers(root.right, new StringBuilder(sb).append(root.val));
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        int result = sumNumbers(root);
        System.out.println(result);
    }
}
