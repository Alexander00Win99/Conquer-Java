package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.concurrent.TransferQueue;

public class Leetcode_0112_PathSum {
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

    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return targetSum == 0;
        if (root.left == null && root.right == null) return root.val == targetSum;
        if (root.left == null) return hasPathSum(root.right, targetSum - root.val);
        if (root.right == null) return hasPathSum(root.left, targetSum - root.val);
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(9);
        System.out.println(hasPathSum(root, 1));
        System.out.println(hasPathSum(root, 10));
    }
}
