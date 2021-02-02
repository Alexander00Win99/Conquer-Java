package com.conquer_java.algorithm.exercise_leetcode.tree;

public class Leetcode_0106_ConstructBinaryTreeFromPostorderAndInorderTraversal {
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

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder.length != postorder.length) throw new IllegalArgumentException("后序数组和中序数组元素数目不同！");
        if (inorder == null || postorder == null || inorder.length == 0 || postorder.length == 0) return null;
        TreeNode root = buildTree(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1);

        return root;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder, int inStart, int inEnd, int postStart, int postEnd) {
        if (inStart > inEnd) return null;
        if (inStart == inEnd) return new TreeNode(inorder[inStart]);

        TreeNode root = new TreeNode(postorder[postEnd]);
        int rootIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == postorder[postEnd]) {
                rootIndex = i;
                break;
            }
        }

        root.left = buildTree(inorder, postorder, inStart, rootIndex - 1, postStart, postStart + rootIndex - inStart - 1);
        root.right = buildTree(inorder, postorder, rootIndex + 1, inEnd, postStart + rootIndex - inStart, postEnd - 1);

        return root;
    }

    public static void main(String[] args) {
        int[] arr = new int[0];
        System.out.println(arr.length);
    }
}
