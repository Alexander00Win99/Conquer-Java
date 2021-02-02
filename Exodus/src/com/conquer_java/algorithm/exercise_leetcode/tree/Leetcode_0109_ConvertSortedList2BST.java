package com.conquer_java.algorithm.exercise_leetcode.tree;

public class Leetcode_0109_ConvertSortedList2BST {
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

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

    public static TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);
        ListNode pointer = head;
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (fast != null && fast.next != null) { // 二者之一为null，代表fast指针触线到达终点，此时slow指针刚好位于中点。
            pointer = pointer.next;
            slow = slow.next;
            fast = fast.next.next;
        }
        pointer.next = null; // 截断链条前部
        ListNode newHead = slow.next; // 开启新的链条
        slow.next = null; // 截断链条后部
        TreeNode root = new TreeNode(slow.val);
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(newHead);
        return root;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(-10);
        ListNode node2 = new ListNode(-3);
        ListNode node3 = new ListNode(0);
        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(9);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        TreeNode root = sortedListToBST(node1);
        System.out.println(root);
    }
}
