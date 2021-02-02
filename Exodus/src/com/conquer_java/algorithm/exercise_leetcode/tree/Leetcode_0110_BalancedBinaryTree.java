package com.conquer_java.algorithm.exercise_leetcode.tree;

public class Leetcode_0110_BalancedBinaryTree {
    private boolean isBalanced = true;

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

    public boolean isBalanced(TreeNode root) {
        getDepth(root);
        return isBalanced;
    }

    public int getDepth(TreeNode root) {
        if (root == null) return 0;
        int lDepth = getDepth(root.left);
        int rDepth = getDepth(root.right);
        if (Math.abs(lDepth - rDepth) > 1) isBalanced = false;
        return Math.max(lDepth, rDepth) + 1;
    }

    public static void main(String[] args) {

    }
}
