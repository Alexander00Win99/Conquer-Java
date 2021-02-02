package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0113_PathSumII {
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

    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> targetList = new ArrayList<>();
        if (root == null) return targetList;

        List<Integer> inheritance = new ArrayList<>();
        helper(root, targetSum, targetList, inheritance);

        return targetList;
    }

    public static void helper(TreeNode root, int targetSum, List<List<Integer>> targetList, List<Integer> inheritance) {
        if (root == null) {
            if (targetSum == 0) {
                targetList.add(inheritance);
                return;
            }
    }
        List<Integer> candidate = new ArrayList<>();
        candidate.addAll(inheritance);
        candidate.add(root.val);

        if (root.left == null && root.right == null) {
            if (root.val == targetSum) {
                targetList.add(candidate);
            }
            return;
        }
        if (root.left == null) {
            helper(root.right, targetSum - root.val, targetList, candidate);
            return;
        }
        if (root.right == null) {
            helper(root.left, targetSum - root.val, targetList, candidate);
            return;
        }
        helper(root.left, targetSum - root.val, targetList, candidate);
        helper(root.right, targetSum - root.val, targetList, candidate);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        List<List<Integer>> targetList = pathSum(root, 4);
        System.out.println(targetList);

        TreeNode node = new TreeNode(5);
        TreeNode node01 = new TreeNode(4);
        TreeNode node02 = new TreeNode(8);
        TreeNode node03 = new TreeNode(11);
        TreeNode node04 = new TreeNode(13);
        TreeNode node05 = new TreeNode(4);
        TreeNode node06 = new TreeNode(7);
        TreeNode node07 = new TreeNode(2);
        TreeNode node08 = new TreeNode(5);
        TreeNode node09 = new TreeNode(1);
        node.left = node01;
        node.right = node02;
        node01.left = node03;
        node02.left = node04;
        node02.right = node05;
        node03.left = node06;
        node03.right = node07;
        node05.left = node08;
        node05.right = node09;

        System.out.println(pathSum(node, 22));
    }
}
