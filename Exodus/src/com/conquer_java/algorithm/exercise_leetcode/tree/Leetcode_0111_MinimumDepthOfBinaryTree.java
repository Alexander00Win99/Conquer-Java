package com.conquer_java.algorithm.exercise_leetcode.tree;

public class Leetcode_0111_MinimumDepthOfBinaryTree {
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

    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null) return minDepth(root.right) + 1;
        if (root.right == null) return minDepth(root.left) + 1;

        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    public static void main(String[] args) {

    }
}
