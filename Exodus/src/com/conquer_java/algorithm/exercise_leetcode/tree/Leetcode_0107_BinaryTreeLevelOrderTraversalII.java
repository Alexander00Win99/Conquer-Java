package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.*;

public class Leetcode_0107_BinaryTreeLevelOrderTraversalII {
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

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> levels = new LinkedList<>();
        //List<List<Integer>> levels = new ArrayList<>();
        //Stack<List<Integer>> stack = new Stack<>();
        if (root == null) return levels;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> curLevel = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                curLevel.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            levels.push(curLevel);
            //stack.push(curLevel);
        }
        //while (!stack.isEmpty()) levels.add(stack.pop());
        return levels;
    }

    public static void main(String[] args) {
        List<List<Integer>> levels = new ArrayList<>();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        levels = new Leetcode_0107_BinaryTreeLevelOrderTraversalII().levelOrderBottom(root);
        System.out.println(levels);
    }
}
