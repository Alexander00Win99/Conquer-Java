package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Leetcode_0559_MaximumDepthOfNaryTree {
    private int maxDepth;

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

    public int maxDepth(Node root) {
        if (root == null) return 0;
        preorderTraversal(root, 1);
        return maxDepth;
    }

    private void preorderTraversal(Node root, int depth) {
        if (root.children != null) {
            int size = root.children.size();
            for (int i = 0; i < size; i++) {
                Node node = root.children.get(i);
                preorderTraversal(node, depth + 1);
            }
        } else {
            if (depth > maxDepth) maxDepth = depth;
            return;
        }
    }

    public int maxDepthDFS(Node root) {
        if (root == null) return 0;
        if (root.children == null || root.children.size() == 0) return 1;

        int maxChildrenDepth = 0;
        for (Node node : root.children) {
            maxChildrenDepth = Math.max(maxChildrenDepth, maxDepthDFS(node));
        }
        return maxChildrenDepth + 1;
    }

    /**
     * 利用Queue队列实现：每次抽出当层所有节点，同时加入当层所有节点的孩子节点。
     * @param root
     * @return
     */
    public int maxDepthBFS(Node root) {
        if (root == null) return 0;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if (node.children != null) {
                    for (Node child : node.children) {
                        queue.offer(child);
                    }
                }
            }
            depth++;
        }

        return depth;
    }

    public static void main(String[] args) {
        Node root = new Node(1, new ArrayList<>());
        Node child = new Node(25, new ArrayList<>());
        child.children.add(new Node(9));
        child.children.add(new Node(16));
        root.children.add(child);
        root.children.add(new Node(49));
        root.children.add(new Node(81));
        Leetcode_0559_MaximumDepthOfNaryTree demo = new Leetcode_0559_MaximumDepthOfNaryTree();
        demo.maxDepth(root);
        System.out.println(demo.maxDepth);
        System.out.println(demo.maxDepthDFS(root));
    }

    public static class Leetcode_0103_BinaryTreeZigzagLevelOrderTraversal {
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

        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> levels = new ArrayList<>();
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
            levels = new Leetcode_0103_BinaryTreeZigzagLevelOrderTraversal().levelOrder(root);
            System.out.println(levels);
        }
    }
}
