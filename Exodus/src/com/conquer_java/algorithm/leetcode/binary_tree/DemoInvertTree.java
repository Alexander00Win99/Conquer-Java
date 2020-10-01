package com.conquer_java.algorithm.leetcode.binary_tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode-226
 *
 * Breath First Search(BFS) VS (DFS)Depth First Search
 * 广度优先遍历(搜索) + 深度优先遍历(搜索)
 * 深度优先遍历分为：前序DLR + 中序LDR + 后序LRD
 */
public class DemoInvertTree {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode invertTree(TreeNode root) {
        // 广度优先搜索方式处理

        // 深度优先搜索方式处理
        return invertTreeDFS(root);
        // 前序遍历方式处理
        //return invertTreeDLR(root);
        // 中序遍历方式处理
        //return invertTreeLDR(root);
        // 后序遍历方式处理
        //return invertTreeLRD(root);
    }

    public TreeNode invertTreeBFS(TreeNode root) {
        if (root == null) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        // 使用offer(队列满时返回false)而非add(队列满时抛出错误)
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 使用poll(取出队首元素并予删除)而非peek(取出队首元素但不删除)
            TreeNode head = queue.poll();
            // 交换队首元素的左右子树
            swapLeftRightChild(root);
            // 判断左右子树是否需要继续处理
            if (head.left != null) queue.offer(head.left);
            if (head.right != null) queue.offer(head.right);
        }

        return root;
    }

    public TreeNode invertTreeDFS(TreeNode root) {
        if (root == null) return null;
        TreeNode tmp = root.left;
        root.left = invertTreeDFS(root.right);
        root.right = invertTreeDFS(tmp);
        return root;
    }

    public TreeNode invertTreeDLR(TreeNode root) {
        if (root == null) return null;
        swapLeftRightChild(root);
        root.left = invertTreeDLR(root.left);
        root.right = invertTreeDLR(root.right);
        return root;
    }

    public TreeNode invertTreeLDR(TreeNode root) {
        if (root == null) return null;
        root.left = invertTreeLDR(root.left);
        swapLeftRightChild(root);
        root.left = invertTreeLDR(root.left);
        return root;
    }

    public TreeNode invertTreeLRD(TreeNode root) {
        if (root == null) return null;
        root.left = invertTreeLRD(root.left);
        root.right = invertTreeLRD(root.right);
        swapLeftRightChild(root);
        return root;
    }

    public void swapLeftRightChild(TreeNode root) {
        if (root != null) {
            TreeNode tmp = root.left;
            root.left = root.right;
            root.right = tmp;
        }
    }

    public static void main(String[] args) {

    }
}
