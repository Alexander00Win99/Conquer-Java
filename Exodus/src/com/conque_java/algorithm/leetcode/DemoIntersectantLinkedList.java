package com.conque_java.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;
import static com.conque_java.algorithm.leetcode.DemoCircularLinkedList.hasCircle;
import static com.conque_java.algorithm.leetcode.DemoCircularLinkedList.hasCycle;

/**
 * Leetcode-160: 相交链表——！！！重点注意：两个链表->通过分解成为多个部分然后针对不同部分进行无重复互补性相加实现步伐统一的求同存异思想->弥合差异！！！
 * 1) 暴力循环法，时间复杂度O(m*n)
 * ——简单粗暴
 * 2) 容器承装法，时间复杂度O(m+n)，空间复杂度O(m)|O(n)——3个以及以上链表，同样有效
 * ——两个链表同时向容器中逐个添加节点，遇到重复节点即刻返回
 * 3) 双指针遍历法，时间复杂度O(m+n-Intersection(m,n))——3个以及以上链表，无法工作
 * ——假设：a，b两个链表不同部分的节点长度分别是aL和bL，相同部分长度为commonL，那么，两个指针分别从a、b链头触发，a后接b，b后接a，在走了aL+bL+commonL之后一定同时位于相交节点；
 * ——假设：a，b两个链表并不相交，也即没有共同部分，那么，pA指针移动aL+bL之后，pB指针同时移动bL+aL，两者此时均为null，跳出while (pA != pB)循环，返回null；
 */
public class DemoIntersectantLinkedList {
    public static <T> DemoCircularLinkedList.ListNode<T> generateLinkedList(T[] arr) {
        if (arr == null | arr.length == 0) return null;
        DemoCircularLinkedList.ListNode head = new DemoCircularLinkedList.ListNode(arr[0]);
        DemoCircularLinkedList.ListNode tmp = head;

        for (int i = 1; i < arr.length; i++) {
            DemoCircularLinkedList.ListNode newNode = new DemoCircularLinkedList.ListNode(arr[i]);
            tmp.next = newNode;
            tmp = newNode;
        }
        return head;
    }

    public static <T> DemoCircularLinkedList.ListNode<T> generateCircularLinkedList(T[] arr, int pos) {
        if (arr == null | arr.length == 0) return null;
        DemoCircularLinkedList.ListNode head = new DemoCircularLinkedList.ListNode(arr[0]);
        DemoCircularLinkedList.ListNode circleEntrance = null;
        DemoCircularLinkedList.ListNode p = head;

        for (int i = 1; i < arr.length; i++) {
            DemoCircularLinkedList.ListNode node = new DemoCircularLinkedList.ListNode(arr[i]);
            if (i == pos) circleEntrance = node;
            p.next = node;
            p = node;
        }
        p.next = circleEntrance;
        return head;
    }

    public static <T> DemoCircularLinkedList.ListNode<T> mergeLinkedList(DemoCircularLinkedList.ListNode<T> listA, DemoCircularLinkedList.ListNode<T> listB) {
        if (listA == null) return listB;
        if (listB == null) return listA;
        DemoCircularLinkedList.ListNode<T> p = listA;
        while (p.next != null) {
            p = p.next;
        }
        p.next = listB;
        return listA;
    }

    public static <T> DemoCircularLinkedList.ListNode<T> getIntersectionNode1(DemoCircularLinkedList.ListNode<T> headA, DemoCircularLinkedList.ListNode<T> headB) {
        if (headA == null || headB == null) return null;
        DemoCircularLinkedList.ListNode<T> p = null;
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

    public static <T> DemoCircularLinkedList.ListNode<T> getIntersectionNode2(DemoCircularLinkedList.ListNode<T> headA, DemoCircularLinkedList.ListNode<T> headB) {
        Set set = new HashSet<DemoCircularLinkedList.ListNode<T>>();
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

    public static <T> DemoCircularLinkedList.ListNode<T> getIntersectionNode3(DemoCircularLinkedList.ListNode<T> headA, DemoCircularLinkedList.ListNode<T> headB) {
        if (headA == null || headB == null) return null;
        DemoCircularLinkedList.ListNode<T> pA = headA;
        DemoCircularLinkedList.ListNode<T> pB = headB;
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
        DemoCircularLinkedList.ListNode<Integer> head = null;
        head = generateLinkedList(arr);
        System.out.println(hasCircle(head));
        head = generateCircularLinkedList(arr, 3);
        System.out.println(hasCycle(head));

        String[] arrA = new String[] {"a1"};
        String[] arrB = new String[] {"b1", "b2"};
        String[] arrC = new String[] {"c1", "c2", "c3"};

        DemoCircularLinkedList.ListNode<String> listA = generateLinkedList(arrA);
        System.out.println(hasCycle(listA));
        DemoCircularLinkedList.ListNode<String> listB = generateLinkedList(arrB);
        System.out.println(hasCycle(listB));
        DemoCircularLinkedList.ListNode<String> listC = generateLinkedList(arrC);
        System.out.println(hasCycle(listC));

        DemoCircularLinkedList.ListNode<String> list1 = mergeLinkedList(listA, listC);
        System.out.println(hasCycle(list1));
        DemoCircularLinkedList.ListNode<String> list2 = mergeLinkedList(listB, listC);
        System.out.println(hasCycle(list2));

        DemoCircularLinkedList.ListNode<String> jointNode = null;
        jointNode = getIntersectionNode1(listA, listB);
        System.out.println(jointNode);
        jointNode = getIntersectionNode2(listA, listB);
        System.out.println(jointNode);
        jointNode = getIntersectionNode3(listA, listB);
        System.out.println(jointNode);
    }

    static class ListNode <T> {
        T value;
        DemoCircularLinkedList.ListNode next;
        ListNode(T x) {
            value = x;
            next = null;
        }
    }
}
