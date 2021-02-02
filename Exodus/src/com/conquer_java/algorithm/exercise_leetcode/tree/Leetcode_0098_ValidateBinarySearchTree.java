package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0098_ValidateBinarySearchTree {
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

    public boolean isValidBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inorderTraversal(root, list);

        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            if (list.get(i) >= list.get(i + 1)) return false;
        }
        return true;
    }

    private void inorderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) return;

        inorderTraversal(root.left, list);
        list.add(root.val);
        inorderTraversal(root.right, list);
    }

    public static void main(String[] args) {

    }
}
