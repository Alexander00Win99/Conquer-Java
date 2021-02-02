package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Leetcode_0144_BinaryTreePreorderTraversal {
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

    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList();
        Stack<TreeNode> stack = new Stack();
        if (root == null) return list;

        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            list.add(node.val);
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        return list;
    }

    public static void main(String[] args) {
        TreeNode root = null;
        System.out.println(preorderTraversal(root));
        root = new TreeNode(1);
        System.out.println(preorderTraversal(root));
        root.right = new TreeNode(2);
        System.out.println(preorderTraversal(root));
    }
}
