package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0095_UniqueBinarySearchTreesII {
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

    public static List<TreeNode> generateTrees(int n) {
        List<TreeNode> list = new ArrayList<>();
        list = generateTrees(1, n);
        return list;
    }

    public static List<TreeNode> generateTrees(int l, int r) {
        List<TreeNode> list = new ArrayList<>();
        if (l > r) {
            list.add(null);
            return list;
            //throw new IllegalArgumentException("Illegal arguments: left boundary is larger than right boundary!");
        }

        if (l == r) {
            list.add(new TreeNode(l));
            return list;
        }

        for (int i = l; i <= r; i++) {
            List<TreeNode> leftList = generateTrees(l, i - 1);
            List<TreeNode> rightList = generateTrees(i + 1, r);
            for (TreeNode leftChild : leftList) {
                for (TreeNode righttChild : rightList) {
                    TreeNode root = new TreeNode(i);
                    root.left = leftChild;
                    root.right = righttChild;
                    list.add(root);
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        List<TreeNode> list = generateTrees(3);
        System.out.println(list);
    }
}
