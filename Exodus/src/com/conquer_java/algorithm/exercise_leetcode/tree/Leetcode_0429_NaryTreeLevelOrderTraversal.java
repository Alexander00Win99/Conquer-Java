package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Leetcode_0429_NaryTreeLevelOrderTraversal {
    static class Node {
        public int val;
        public List<Node> children;

        Node() {}
        Node(int _val) {
            this.val = _val;
        }
        Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) return levels;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> curLevel = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                curLevel.add(node.val);
                if (node.children != null && node.children.size() != 0)
                for (Node child : node.children) queue.offer(child);
            }
            levels.add(curLevel);
        }
        return levels;
    }

    public static void main(String[] args) {
    }
}
