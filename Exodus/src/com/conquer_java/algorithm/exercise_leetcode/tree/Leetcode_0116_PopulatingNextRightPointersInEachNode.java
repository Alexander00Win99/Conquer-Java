package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;

public class Leetcode_0116_PopulatingNextRightPointersInEachNode {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    public Node connect(Node root) {
        connectRecusive(root);
        //dfs(root, null);
        return root;
    }

    // preorder traversal will cover each node of the tree, the only thing for each node to do is populating next pointer of it's left child and right child, that is enough.
    public void connectRecusive(Node root) {
        if (root == null) return;
        if (root.left != null) root.left.next = root.right; // perfect binary tree
        if (root.right != null && root.next != null) root.right.next = root.next.left;
        connectRecusive(root.left);
        connectRecusive(root.right);
    }

    public void dfs(Node root, Node next) {
        if (root == null) return;
        root.next = next;
        dfs(root.left, root.right);
        dfs(root.right, root.next == null ? null : root.next.left);
    }

    public Node connectIterativeWithQueue(Node root) {
        if (root == null) return root;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) { // 逐层处理本层所有节点的next指针
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if (i < size - 1) node.next = queue.peek();
                if (node.left != null) queue.offer(node.left); // offer无法插入元素，返回false
                if (node.right != null) queue.add(node.right); // add无法插入元素，抛出@throws IllegalStateException
            }

        }
        return root;
    }

    public Node connectIterative(Node root) { // 纵横双指针，嵌套双循环 —— 逐层处理下层节点的next指针
        if (root == null) return root;
        Node leftestNode = root; // leftestNode始终指向本层最左的节点
        while (leftestNode.left != null) { // from top to bottom —— 指针自上而下逐层移动
            Node levelPointer = leftestNode; // from left to right —— 指针自左先右逐个移动
            while (levelPointer != null) {
                levelPointer.left.next = levelPointer.right;
                if (levelPointer.next != null) levelPointer.right.next = levelPointer.next.left;
                levelPointer = levelPointer.next;
            }
            leftestNode = leftestNode.left;
        }
        return root;
    }

    public static void main(String[] args) {

    }
}
