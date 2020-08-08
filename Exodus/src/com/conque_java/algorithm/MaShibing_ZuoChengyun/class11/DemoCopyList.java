package com.conque_java.algorithm.MaShibing_ZuoChengyun.class11;

import java.util.HashMap;

/**
 * 【题目】
 * 已知一个复杂链表，链表的每个节点都有一个随机指针，随机指针可能指向链表中的任意一个节点或者为空。请返回这个链表的深拷贝。
 *
 * 【思路】
 * 1) 使用HashMap存储节点，建立从原有节点到克隆节点的一一映射关系；
 * 2) 省略额外存储空间，利用间隔插入方式，达到前后节点一一对应关系；
 */
public class DemoCopyList {
    static class Node {
        int value;
        Node next;
        Node rand;

        public Node(int value) {
            this.value = value;
            this.next = null;
            this.rand = null;
        }
    }

    public static Node copyListWithRand(Node head) {
        if (head == null) return null;

        HashMap<Node, Node> map = new HashMap<>();
        Node p = head;

        // 遍历主链不重不漏拷贝生成各个链表节点
        while (p != null) {
            map.put(p, new Node(p.value));
            p = p.next;
        }

        // 设置各个拷贝节点的next/rand指针，节点对应关系：p -> p.next/p.rand <==> map.get(p) -> map.get(p.next)/map.get(p.rand)
        p = head;
        while (p != null) {
            map.get(p).next = map.get(p.next);
            map.get(p).rand = map.get(p.rand);
            p = p.next;
        }

        return map.get(head);
    }

    public static Node cloneListWithRand(Node head) {
        if (head == null) return null;

        Node p = head;
        Node next = null;
        // 每个原始节点后面依次插入新的克隆节点，生成合并列表
        while (p != null) {
            next = p.next;
            p.next = new Node(p.value);
            p.next.next = next;
            p = next;
        }

        // 设置每个克隆节点的rand指针，节点对应关系 p->p.rand <==> p.next->p.rand.next
        p = head;
        Node clone = null;
        while (p != null) {
            clone = p.next;
            //clone.rand = p.rand == null ? null : p.rand.next;
            if (p.rand != null) clone.rand = p.rand.next;
            p = p.next.next;
        }

        // 分离合并链表：原始链表+克隆链表
        p = head;
        clone = p.next;
        while (p != null) {
            next = p.next;
            p.next = next.next;
            next.next = p.next == null ? null : p.next.next;
            p = p.next;
        }

        return clone;
    }

    public static void main(String[] args) {
        Node nodeA = new Node(1);
        Node nodeB = new Node(2);
        Node nodeC = new Node(3);
        Node nodeD = new Node(4);
        Node nodeE = new Node(5);
        Node nodeF = new Node(6);

        nodeA.next = nodeB;
        nodeB.next = nodeC;
        nodeC.next = nodeD;
        nodeD.next = nodeE;
        nodeE.next = nodeF;
        nodeA.rand = nodeC;
        nodeB.rand = nodeD;
        nodeC.rand = nodeF;
        nodeF.rand = nodeB;

        Node copy = copyListWithRand(nodeA);
        System.out.println(copy);

        Node clone = cloneListWithRand(nodeA);
        System.out.println(clone);
    }
}