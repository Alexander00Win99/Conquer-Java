package com.conquer_java.algorithm.leetcode.binary_tree;

/**
 * LeetCode-110: 判断平衡二叉树
 *
 * 【原始算法】：递归 + 递归 = 双层递归(一个递归里面调用另外一个递归)：效率低下，不平衡节点的树高重复计算多次。
 * isBalanced：自顶向下(树根节点root本身->左右子树节点->叶子leaf节点)依次判断各个节点是否平衡(左右子树高度差的绝对值是否小于等于1)，期间需要计算各个节点的树高；
 * getDepth：自底向上依次计算，返回树高。
 *
 * 【改进算法】：计算树高的同时，记忆树是否平衡：效率很高，不平衡节点自底向上方法一路返回-1，避免无谓的计算。
 * isBalanced：根据根节点root的树高是否-1直接判断；
 * getDepth：树高计算考虑平衡因素，如果平衡，计算树高，若不平衡，则返回-1作为树高代表不平衡。
 * 如果某个节点树高为-1，意味着以该节点为根节点的子树本身不平衡，并且，以该节点作为左右子树(子集)的树自然也不平衡，树高相应也是-1，
 * 如此，若任一节点的树高是-1，则自底向上包含该个节点的树高一律均直接返回-1，无须继续计算，显然能够节省大量计算；
 *
 * AVL: 苏联数学家 Georgy Adelson-Velsky and Evgenii Landis 发明的一种二叉平衡树
 * 【优化思路】
 */
public class DemoAVL {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    private static int getDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(getDepth(root.left), getDepth(root.right)) + 1;
    }

    public static boolean checkBalance(TreeNode root) {
        if (root == null) return true;
        if (Math.abs(getDepth(root.left) - getDepth(root.right)) > 1) return false;
        return checkBalance(root.left) && checkBalance(root.right);
    }

    // 先判断子树是否平衡(-1代表不平衡)，平衡再计算父节点树高。
    private static int depth(TreeNode root) {
        if (root == null) return 0;
        if (depth(root.left) == -1 || depth(root.left) == -1) return -1;
        if (Math.abs(depth(root.left) - depth(root.right)) > 1) return -1;
        return Math.max(depth(root.left), depth(root.right)) + 1;
    }

    // 根据节点树高是否-1进行判断
    public static boolean isBalanced(TreeNode root) {
        return depth(root) != -1;
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
        Integer[] array = new Integer[] {0, null, 1, null, 2, null, 3, null, 4};
        TreeNode root = generateBinaryTree(array, 0);
        System.out.println(getDepth(root));
        System.out.println(checkBalance(root));
        System.out.println(isBalanced(root));

        Integer[] arr = new Integer[] {3, 9, 20, null, null, 15, 7};
        TreeNode node = generateBinaryTree(arr, 0);
        System.out.println(getDepth(node));
        System.out.println(checkBalance(node));
        System.out.println(isBalanced(node));
    }
}
