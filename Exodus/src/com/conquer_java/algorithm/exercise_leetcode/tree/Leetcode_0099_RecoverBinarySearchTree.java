package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 【要求】
 * Follow up: A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?
 *
 * 【思路】
 * 根据题设条件，其中有且仅有一次出错，也就意味，只有两个节点出错，因此可以中序遍历同时，记录此两出错节点。
 *
 */
public class Leetcode_0099_RecoverBinarySearchTree {
    private TreeNode preNode = new TreeNode(Integer.MIN_VALUE);
    private TreeNode wrongNode1 = null, wrongNode2 = null;
    public static List<TreeNode> list = new ArrayList<>();

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

    public void recoverTreeIteration(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode preNode = new TreeNode(Integer.MIN_VALUE);
        TreeNode wrongNode1 = null, wrongNode2 = null, pointer = root;
        //List<TreeNode> list = new ArrayList<>();

        while (pointer != null || !stack.isEmpty()) {
            while (pointer != null) {
                stack.push(pointer);
                pointer = pointer.left;
            }
            pointer = stack.pop();
            list.add(pointer);
            if (wrongNode1 == null && preNode.val > pointer.val) wrongNode1 = preNode;
            if (wrongNode1 != null && preNode.val > pointer.val) wrongNode2 = pointer;
            preNode = pointer;
            pointer = pointer.right;
        }
        wrongNode1.val = wrongNode1.val ^ wrongNode2.val;
        wrongNode2.val = wrongNode1.val ^ wrongNode2.val;
        wrongNode1.val = wrongNode1.val ^ wrongNode2.val;
    }

    public void recoverTree(TreeNode root) {
        inorderTraversal(root);
        System.out.println(wrongNode1);
        System.out.println(wrongNode2);
        System.out.println(wrongNode1.val);
        System.out.println(wrongNode2.val);
        wrongNode1.val = wrongNode1.val ^ wrongNode2.val;
        wrongNode2.val = wrongNode1.val ^ wrongNode2.val;
        wrongNode1.val = wrongNode1.val ^ wrongNode2.val;
    }

    private void inorderTraversal(TreeNode root) {
        if (root == null) return;

        inorderTraversal(root.left);
        list.add(root);
        // 相邻两个节点调换：wrongNode2赋值一次；不相邻的两个节点调换：wrongNode2赋值两次；
        if (wrongNode1 == null && preNode.val > root.val)
            wrongNode1 = preNode;
        if (wrongNode1 != null && preNode.val > root.val)
            wrongNode2 = root;
        preNode = root;
        inorderTraversal(root.right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(2);

        Leetcode_0099_RecoverBinarySearchTree demo = new Leetcode_0099_RecoverBinarySearchTree();
        //demo.recoverTree(root);
        demo.recoverTreeIteration(root);
        for (TreeNode node : list) System.out.println(node.val);
    }
}
