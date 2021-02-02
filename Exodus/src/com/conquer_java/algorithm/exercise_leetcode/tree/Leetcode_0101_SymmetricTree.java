package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 【重点】：利用双向队列Deque的First和Last两端对称操作判断Symmetric对称性
 * Iterable -> Collection -> Queue -> Deque
 *
 * 【Deque常见操作】：
 * 插入操作 —— 同时存在add() + offer()
 * addFirst()/addLast() VS offerFirst()/offerLast(): 队列已满，add报错"java.util.IllegalStateException"，offer返回fasle。
 * 删除操作 —— 同时存在remove() + poll()
 * removeFirst()/removeLast() VS pollFirst()/pollLast(): 队列为空，remove报错"java.util.NoSuchElementException"，poll返回null。
 * 获取操作 —— 并不存在get()但是存在peek()
 * getFirst()/getLast() VS peekFirst()/peekLast()
 * 栈操作
 * pop() + push()：报错"NoSuchElementException"；报错"IllegalStateException"。
 * 迭代操作
 * iterator()/descendingIterator()
 *
 * 【Deque常见场景】
 * FIFO：队列场景
 * LIFO：栈场景 —— Stack是线程同步的，所以推荐使用Deque。
 *
 * 【Deque常见实现】
 * ArrayDeque: 基于数组实现的线性双向队列
 * LinkedList: 基于链表实现的链式双向队列
 */
public class Leetcode_0101_SymmetricTree {
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

    /**
     * 【错误方法】
     * 下述方法，仅仅判断每层数字是否对称，但是存在严重问题：对称的数字没法保证原来属于对称的位置。
     * @param root
     * @return
     */
//    public static boolean isSymmetricNonRecursive(TreeNode root) {
//        if (root == null) return true;
//
//        Deque<TreeNode> deque = new LinkedList<>();
//        Deque<Integer> tempDeque = new LinkedList<>();
//        deque.offer(root);
//        while (!deque.isEmpty()) {
//            int size = deque.size();
//            for (int i = 0; i < size; i++) {
//                TreeNode node = deque.poll();
//                if (node.left != null) {
//                    deque.offer(node.left);
//                    tempDeque.offer(node.left.val);
//                }
//                if (node.right != null) {
//                    deque.offer(node.right);
//                    tempDeque.offer(node.right.val);
//                }
//            }
//            size = deque.size();
//            if (size % 2 != 0) return false; // 删除本层所有节点之后，剩余全是添加的下层节点。若其数量不是偶数，则返回false。
//            while (!tempDeque.isEmpty()) {
//                if (tempDeque.removeFirst() != tempDeque.removeLast()) return false; // 首尾不等，返回false；全部相等，清空进入下轮判断。
//            }
//        }
//        return true;
//    }

    public static boolean isSymmetricNonRecursive(TreeNode root) {
        if (root == null) return true;

        Deque<TreeNode> deque = new LinkedList<>();
        deque.addFirst(root.left);
        deque.addLast(root.right);
        TreeNode head = null;
        TreeNode tail = null;
        while (!deque.isEmpty()) {
            head = deque.pollFirst();
            tail = deque.pollLast();

            if (head == null & tail == null) continue;
            if (head == null ^ tail == null) return false;
            if (head.val != tail.val) return false;
            else {
                deque.addFirst(head.right);
                deque.addFirst(head.left);
                deque.addLast(tail.left);
                deque.addLast(tail.right);
            }
        }
        return true;
    }

    public static boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return checkSymmetry(root.left, root.right);
    }

    public static boolean checkSymmetry(TreeNode src, TreeNode dst) {
        if (src == null & dst == null) return true;
        if (src == null ^ dst == null) return false;
        if (src.val != dst.val) return false;
        return checkSymmetry(src.left, dst.right) && checkSymmetry(src.right, dst.left);
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
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(2);
        node.left.left = new TreeNode(3);
        node.right.left = new TreeNode(3);

        System.out.println(isSymmetric(root));
        System.out.println(isSymmetricNonRecursive(root));

        System.out.println(isSymmetric(node));
        System.out.println(isSymmetricNonRecursive(node));

        Integer[] array = new Integer[] {3, 9, 20, null, null, 15, 7};
        TreeNode newRoot = generateBinaryTree(array, 0);
        System.out.println(isSymmetric(newRoot));
    }
}
