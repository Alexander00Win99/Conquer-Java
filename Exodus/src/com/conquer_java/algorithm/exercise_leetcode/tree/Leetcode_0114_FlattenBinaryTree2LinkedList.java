package com.conquer_java.algorithm.exercise_leetcode.tree;


import java.util.*;

/**
 * Given the root of a binary tree, flatten the tree into a "linked list":
 * The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list and the left child pointer is always null.
 * The "linked list" should be in the same order as a pre-order traversal of the binary tree.
 *
 * Example 1:
 * Input: root = [1,2,5,3,4,null,6]
 * Output: [1,null,2,null,3,null,4,null,5,null,6]
 *
 * Example 2:
 * Input: root = []
 * Output: []
 *
 * Example 3:
 * Input: root = [0]
 * Output: [0]
 *
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 2000].
 * -100 <= Node.val <= 100
 *
 *
 * Follow up: Can you flatten the tree in-place (with O(1) extra space)?
 *
 * 【注意事项】
 * 当左右子树均有时，root先接左子树，后接右子树，因为无法知晓左子树深度（对应List长度），
 * 所以必须求得rightmost，然后对其right赋值，如果使用root.right.right则会出错（当且仅当左子树仅含一个节点）。
 */
public class Leetcode_0114_FlattenBinaryTree2LinkedList {
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

    public static void flatten(TreeNode root) {
        root = linkNode(root);
        return;
    }

    public static TreeNode linkNode(TreeNode root) {
        if (root == null) return null;
        if (root.left == null && root.right == null) {
            return root;
        }
        TreeNode left = null;
        TreeNode right = null;

        if (root.left == null) {
            right = linkNode(root.right);
            root.right = right;
            return root;
        }
        if (root.right == null) {
            left = linkNode(root.left);
            root.right = left;
            root.left = null;
            return root;
        }
        left = linkNode(root.left);
        right = linkNode(root.right);
        root.left = null;
        root.right = left;
        //root.right.right = right; // NOK，left的深度（对应链表长度）未知，不一定是1。
        TreeNode rightmost = left;
        while (rightmost.right != null) rightmost = rightmost.right;
        rightmost.right = right;
        return root;
    }

    interface Comparable<X> {
        public int compareTo(X that); // 编译器自动擦除泛型参数X，使用最左边界Object进行替换
    }

    public final class NumericValue implements Comparable<NumericValue> {
        private byte value;

        public NumericValue(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }

        @Override
        public int compareTo(NumericValue that) {
            return this.value - that.value;
        }
    }

    public static class Collections {
        public static <X extends Comparable<X>> X max(Collection<X> xs) {
            return (X) new Comparable<X>() {
                @Override
                public int compareTo(X that) {
                    return 0;
                }
            };
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
//        root.left = new TreeNode(2);
//        root.right = new TreeNode(3);
//        root.left.left = new TreeNode(4);
//        root.left.right = new TreeNode(5);
//        root.right.right = new TreeNode(6);
//        System.out.println(root);
//        flatten(root);
//        System.out.println(root);
//
//        Map<String, String> map = new HashMap<>();
//        map.put("name", "Alexander00Win99");
//        System.out.println(map.get("name"));
//        System.out.println((String) map.get("name"));
//
//        GenericStaticVar<Integer> gsv01 = new GenericStaticVar<>();
//        GenericStaticVar<Integer> gsv02 = new GenericStaticVar<>();
//        GenericStaticVar.var = 01;
//        System.out.println(gsv01.var);
//        System.out.println(gsv02.var);
//        gsv02.var = 02;
//        System.out.println(gsv01.var);
//        System.out.println(gsv02.var);
        int c = 0;
        for (int i = 0; i < 1000; i++) {
            if (i % 7 == 0 || i % 17 == 0){
                System.out.print(i + "\t");
            }
            c++;
            if (c % 5 == 0) System.out.println();
        }
    }
}

class GenericStaticVar<T> {
    public static int var = 0;
    public void doNothing(T t) {}
}

class GenericOverload<T> {
    public static void method(List<Integer> list) {}
    public static void method(List<String> list, Integer i) {}
}