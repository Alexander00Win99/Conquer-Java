package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0257_BinaryTreePaths {
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

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        preorderTraversal(root, null, paths);
        return paths;
    }

    private void preorderTraversal(TreeNode root, String prefix, List<String> paths) {
        if (root == null) return;
        String selfPrefix = (prefix == null) ? "" + root.val : new StringBuilder(prefix).append("->" + root.val).toString();
        if (root.left == null && root.right == null) {
            paths.add(selfPrefix);
        }
        preorderTraversal(root.left, selfPrefix, paths);
        preorderTraversal(root.right, selfPrefix, paths);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        System.out.println(new Leetcode_0257_BinaryTreePaths().binaryTreePaths(root));
    }
}
