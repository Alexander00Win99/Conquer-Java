package com.conquer_java.algorithm.exercise_leetcode.tree;

public class Leetcode_0156_BinaryTreeUpsideDown {
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

    private static TreeNode rootUpsideDown = null;

    public static TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null) return null;
        dfsTraversal(root);
        return rootUpsideDown;
    }

    public static TreeNode dfsTraversal(TreeNode root) {
        if (root == null) return null; // 递归出口：null空节点

        if (root.left == null && root.right == null) { // 递归出口：叶子节点
            if (rootUpsideDown == null) rootUpsideDown = root; // DFS-前中后序遍历-遇到的首个叶子节点即为颠倒树的根节点
            return root;
        }

        TreeNode left = dfsTraversal(root.left);
        TreeNode right = dfsTraversal(root.right);
        left.left = right;
        left.right = root;

//        if (root.left != null) { // (root.left != null & root.right !=null) + (root.left != null & root.right ==null)两种场景，根据题设，不存在左空右不空情形
//            TreeNode left = dfsTraversal(root.left);
//            TreeNode right = dfsTraversal(root.right);
//
//            left.left = right;
//            left.right = root;
//        }

        root.left = null;
        root.right = null;

        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        TreeNode upsideDownRoot = upsideDownBinaryTree(root);
    }
}
