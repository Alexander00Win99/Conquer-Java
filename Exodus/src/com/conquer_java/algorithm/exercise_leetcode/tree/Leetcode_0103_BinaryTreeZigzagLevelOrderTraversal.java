package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Leetcode_0103_BinaryTreeZigzagLevelOrderTraversal {
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

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) return levels;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean bReversed = false;
        while (!queue.isEmpty()) {
            LinkedList<Integer> curLevel = new LinkedList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                //!bReversed ? curLevel.add(node.val) : curLevel.push(node.val);
                if (!bReversed)
                    curLevel.add(node.val);
                else
                    curLevel.push(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            bReversed = !bReversed;
            levels.add(curLevel);
        }
        return levels;
    }

    public static void main(String[] args) {
        List<List<Integer>> levels = new ArrayList<>();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        levels = new Leetcode_0103_BinaryTreeZigzagLevelOrderTraversal().zigzagLevelOrder(root);
        System.out.println(levels);

        boolean flag = true;
        System.out.println(flag);
        flag = !flag;
        System.out.println(flag);
    }
}
