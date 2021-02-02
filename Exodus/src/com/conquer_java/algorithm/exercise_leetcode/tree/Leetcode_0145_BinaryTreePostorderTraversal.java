package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Leetcode_0145_BinaryTreePostorderTraversal {
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

    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList();
        Stack<TreeNode> stack1 = new Stack();
        Stack<TreeNode> stack2 = new Stack();
        if (root == null) return list;

        stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            if (node.left != null) stack1.push(node.left);
            if (node.right != null) stack1.push(node.right);
        }
        while (!stack2.isEmpty())
            list.add(stack2.pop().val);
        return list;
    }

    public static void main(String[] args) {
        TreeNode root = null;
        System.out.println(postorderTraversal(root));
        root = new TreeNode(1);
        System.out.println(postorderTraversal(root));
        root.right = new TreeNode(2);
        System.out.println(postorderTraversal(root));
    }
}
