package com.conquer_java.algorithm.exercise_leetcode.tree;

public class Leetcode_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal {
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

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (inorder.length != preorder.length) throw new IllegalArgumentException("前序数组和中序数组元素数目不同！");
        if (inorder.length == 0) return null;
        if (inorder.length == 1) return new TreeNode(inorder[0]);

        TreeNode root = new TreeNode(preorder[0]);
        int rootIndex = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == preorder[0]) {
                rootIndex = i;
                break;
            }
        }
        int leftSize = rootIndex;
        int rightSize = inorder.length - rootIndex - 1;
        int[] leftPreorder = new int[rootIndex];
        System.arraycopy(preorder, 1, leftPreorder, 0, leftSize);
        int[] rightPreorder = new int[inorder.length - rootIndex - 1];
        System.arraycopy(preorder, rootIndex + 1, rightPreorder, 0, rightSize);
        int[] leftInorder = new int[rootIndex];
        System.arraycopy(inorder, 0, leftInorder, 0, leftSize);
        int[] rightInorder = new int[inorder.length - rootIndex - 1];
        System.arraycopy(inorder, rootIndex + 1, rightInorder, 0, rightSize);

        root.left = buildTree(leftPreorder, leftInorder);
        root.right = buildTree(rightPreorder, rightInorder);
        return root;
    }

    public static void main(String[] args) {
        int[] arr = new int[0];
        System.out.println(arr.length);
    }
}
