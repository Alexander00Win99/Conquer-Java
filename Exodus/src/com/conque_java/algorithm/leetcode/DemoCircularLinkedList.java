package com.conque_java.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Leetcode-141: 环形链表——！！！重点注意：|和||的细微差异可能导致前后两个表达式的空指针异常错误！！！
 */
public class DemoCircularLinkedList {
//    public static ListNode generateLinkedList(int[] arr) {
//        if (arr == null | arr.length == 0) return null;
//        ListNode head = new ListNode(arr[0]);
//        ListNode tmp = head;
//
//        for (int i = 1; i < arr.length; i++) {
//            ListNode newNode = new ListNode(arr[i]);
//            tmp.next = newNode;
//            tmp = newNode;
//        }
//        return head;
//    }

    public static <T> ListNode<T> generateLinkedList(T[] arr) {
        if (arr == null | arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode tmp = head;

        for (int i = 1; i < arr.length; i++) {
            ListNode newNode = new ListNode(arr[i]);
            tmp.next = newNode;
            tmp = newNode;
        }
        return head;
    }

    public static <T> ListNode<T> generateCircularLinkedList(T[] arr, int pos) {
        if (arr == null | arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode circleEntrance = null;
        ListNode p = head;

        for (int i = 1; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            if (i == pos) circleEntrance = node;
            p.next = node;
            p = node;
        }
        p.next = circleEntrance;
        return head;
    }

    public static void ringwise(ListNode head, int pos) {
        int index = 0;
        ListNode circleEntrance = null;
        while (true) {
            if (head.next == null) { // 如果只有一个节点，不能成环，index==0，head.next==null
                head.next = circleEntrance;
                return;
            }
            if (index == pos) {
                circleEntrance = head;
            }
            index ++;
            head = head.next;
        }
    }

    // 时间复杂度O(n)，空间复杂度O(n)
    // set存放已经出现的节点，如果成环，一定重复出现，否则无环
    public static boolean hasCircle(ListNode head) {
        if (head == null) return false;

        Set<ListNode> set = new HashSet<>();
        while (head.next != null) {
            if (set.contains(head)) {
                return true;
            } else {
                set.add(head);
            }
            head = head.next;
        }
        return false;
    }

    // 时间复杂度O(n)，空间复杂度O(1)
    // 两个指针slow和fast，如果成环，一定能够相遇，如果无环，其中之一一定能够遇到null
    public static boolean hasCycle(ListNode head) {
        if (head == null | head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head.next;

        while (slow != fast) {
            /**
             * fast一次移动两步，遍历更快，因此使用：if (fast == null || fast.next == null) return false;：
             * if (slow == null || fast == null) return false;不行，因为，不能保证fast = fast.next.next;语句避免java.lang.NullPointerException空指针异常；
             * if (fast == null | fast.next == null) return false;不行，因为，当(fast == null)时，继续(fast.next == null)判断会有java.lang.NullPointerException空指针异常；
             */
            if (fast == null || fast.next == null) return false;
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public static <T> ListNode<T> mergeLinkedList(ListNode<T> listA, ListNode<T> listB) {
        if (listA == null) return listB;
        if (listB == null) return listA;
        ListNode<T> p = listA;
        while (p.next != null) {
            p = p.next;
        }
        p.next = listB;
        return listA;
    }

    public static <T> ListNode<T> getIntersectionNode1(ListNode<T> headA, ListNode<T> headB) {
        if (headA == null || headB == null) return null;
        ListNode<T> p = null;
        while (headA.next != null) {
            p = headB;
            while (p.next != null) {
                if (p == headA)
                    return p;
                p = p.next;
            }
            headA = headA.next;
        }
        return null;
    }

    public static <T> ListNode<T> getIntersectionNode2(ListNode<T> headA, ListNode<T> headB) {
        Set set = new HashSet<ListNode<T>>();
        while (headA != null && headB != null) {
            if (headA == headB) return headA;
            if (set.contains(headA)) return headA;
            if (set.contains(headB)) return headB;
            set.add(headA);
            set.add(headB);
            headA = headA.next;
            headB = headB.next;
        }
        return null;
    }

    public static <T> ListNode<T> getIntersectionNode3(ListNode<T> headA, ListNode<T> headB) {
        if (headA == null || headB == null) return null;
        ListNode<T> pA = headA;
        ListNode<T> pB = headB;
        if (pA == pB) return pA;
        boolean isExtendedA = false;
        boolean isExtendedB = false;
        /**
         * 1) 假设headA和headB不相交；
         * 在经过num{headA+headB}次遍历之前，能够找出相交节点；
         * 2) 假设headA和headB不相交；
         * pA经过num{headA+headB}次遍历之后，等于null；
         * pB经过num{headB+headA}次遍历之后，等于null；
         * 在经过num{headA+headB}次遍历之后，while (pA == pB)跳出循环，返回null。
         */
        while (pA != pB) {
            if (pA == null) pA = headB;
            if (pB == null) pB = headA;
            pA = pA.next;
            pB = pB.next;
        }
        return pA;
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        ListNode<Integer> head = null;
        head = generateLinkedList(arr);
        System.out.println(hasCircle(head));
        head = generateCircularLinkedList(arr, 3);
        System.out.println(hasCycle(head));

        String[] arrA = new String[] {"a1"};
        String[] arrB = new String[] {"b1", "b2"};
        String[] arrC = new String[] {"c1", "c2", "c3"};

        ListNode<String> listA = generateLinkedList(arrA);
        System.out.println(hasCycle(listA));
        ListNode<String> listB = generateLinkedList(arrB);
        System.out.println(hasCycle(listB));
        ListNode<String> listC = generateLinkedList(arrC);
        System.out.println(hasCycle(listC));

        ListNode<String> list1 = mergeLinkedList(listA, listC);
        System.out.println(hasCycle(list1));
        ListNode<String> list2 = mergeLinkedList(listB, listC);
        System.out.println(hasCycle(list2));

        ListNode<String> jointNode = null;
        jointNode = getIntersectionNode1(listA, listB);
        System.out.println(jointNode);
        jointNode = getIntersectionNode2(listA, listB);
        System.out.println(jointNode);
        jointNode = getIntersectionNode3(listA, listB);
        System.out.println(jointNode);
    }

    static class ListNode <T> {
        T value;
        ListNode next;
        ListNode(T x) {
            value = x;
            next = null;
        }
    }
}
