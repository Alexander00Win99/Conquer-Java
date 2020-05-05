package com.conque_java.algorithm.leetcode.binary_tree;

/**
 * LeetCode-101
 */
public class DemoSymmetric {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    public static boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return checkSymmetry(root.left, root.right);
    }

    private static boolean checkSymmetry(TreeNode node1, TreeNode node2) {
        if ((node1 == null) & (node2 == null)) return true;
        if ((node1 == null) ^ (node2 == null)) return false;
        if (node1.val != node2.val) return false;
        return checkSymmetry(node1.left, node2.right) & checkSymmetry(node1.right, node2.left);
    }

    // 根据数组生成二叉树
    private static TreeNode generateBinaryTree(Integer[] array, int index) {
        if (index >= array.length) return null;
        if (array[index] == null) return null;
        TreeNode root = new TreeNode(array[index]);
        // 观察数组得出规律，index索引位置节点的左子树节点位于(2 * index + 1)索引位置
        root.left = generateBinaryTree(array, 2 * index + 1);
        // 观察数组得出规律，index索引位置节点的右子树节点位于(2 * index + 2)索引位置
        root.right = generateBinaryTree(array, 2 * index + 2);
        return root;
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[] {3, 9, 20, null, null, 15, 7};
        TreeNode root = generateBinaryTree(array, 0);
        System.out.println(isSymmetric(root));
    }
}
