package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.*;

public class Leetcode_0199_BinaryTreeRightSideView {
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

//    public List<Integer> rightSideView(TreeNode root) {
//        List<Integer> list = new ArrayList<>();
//        List<List<Integer>> levels = levelOrder(root);
//        for (List<Integer> level : levels) {
//            Stack<Integer> stack = new Stack();
//            for (Integer i : level) stack.push(i);
//            list.add(stack.pop());
//        }
//        return list;
//    }

//    public List<List<Integer>> levelOrder(TreeNode root) {
//        List<List<Integer>> levels = new ArrayList<>();
//        if (root == null) return levels;
//
//        Queue<TreeNode> queue = new LinkedList<>();
//        queue.offer(root);
//        while (!queue.isEmpty()) {
//            List<Integer> curLevel = new ArrayList<>();
//            int size = queue.size();
//            for (int i = 0; i < size; i++) {
//                TreeNode node = queue.poll();
//                curLevel.add(node.val);
//                if (node.left != null) queue.offer(node.left);
//                if (node.right != null) queue.offer(node.right);
//            }
//            levels.add(curLevel);
//        }
//        return levels;
//    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) return list;

        List<List<Integer>> levels = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> curLevel = new ArrayList<>();
            Stack<Integer> stack = new Stack<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                curLevel.add(node.val);
                stack.push(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            levels.add(curLevel);
            list.add(stack.pop()); // rightest node of each level
        }
        //return levels;
        return list;
    }

    public static void main(String[] args) {

    }
}
