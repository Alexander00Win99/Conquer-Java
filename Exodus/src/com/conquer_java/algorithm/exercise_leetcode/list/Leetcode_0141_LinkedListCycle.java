package com.conquer_java.algorithm.exercise_leetcode.list;

import java.util.HashSet;
import java.util.Set;

public class Leetcode_0141_LinkedListCycle {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static boolean hasCycle(ListNode head) {
        if (head == null) return false;

        ListNode slow = head;
        ListNode fast = head;
        int pos = 0;

        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            pos++;
            fast = fast.next.next;
            if (slow == fast) {
                System.out.println("Detect cycle at position: " + pos);
                return true;
            }
        }

        return false;
    }

    public static boolean hasLoop(ListNode head) {
        if (head == null) return false;
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (!set.add(head)) return true;
            head = head.next;
        }
        return false;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        node1.next = node2;
        node2.next = node1;

        System.out.println(hasCycle(node1));
    }
}
