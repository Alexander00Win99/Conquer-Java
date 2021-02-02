package com.conquer_java.algorithm.exercise_leetcode.tree;

public class Leetcode_0124_BinaryTreeMaximumPathSum {
    private int maxPathSum = Integer.MIN_VALUE;
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

    public int maxPathSum(TreeNode root) {
        maxPathSumToRoot(root);
        return maxPathSum;
    }

    /**
     * maxPathSumOfRoot代表一条以当前树的root节点为终点结束的某个路径的最大路径和（显然路径不含root的父节点），
     * 显然，当前以root为根节点的拓扑范围内某条必经root节点的路径的最大路径和必然包括root节点（无论其值正负），
     * 我们可以根据root的左右子树的最大路径和（以左右孩子为终点的路径）的数值正决定是否让其参与构建root的最大路径和，
     * 此时，root只能挑选左右子树最大路径和中大且为正者（二取一或者全舍弃）作为自身最大路径的纵向延伸，将来可能向上“纵向扩展”到root的父节点，
     * 同时，在以root为根的局部范围内，必然存在一条经过root节点但是不包含其父节点的最大路径，依据左右子树的最大路径和正负进行“横向扩展”，
     * 伴随着，root节点自顶向下的递归，以及自底向上的回溯，我们可以遍历求得树中每个节点的包含其自身的横向最大路径和，
     * 显然，整个树的最大路径和（并不要求包含树的root根节点），必然是这些“横向”最大路径和其中之最大者。
     *
     * @param root
     * @return
     */
    private int maxPathSumToRoot(TreeNode root) {
        if (root == null) return 0;

        int maxPathSumToLeftChild = maxPathSumToRoot(root.left); // 可正可负
        int maxPathSumToRightChild = maxPathSumToRoot(root.right); // 可正可负
        // 横向扩展 —— 只要为正，将其加入（基于左右子树的最大路径和正负情况，root可能不是包含自身的横向最大路径的终点）。
        int curMaxPathSumOfRoot = root.val + Math.max(maxPathSumToLeftChild, 0) + Math.max(maxPathSumToRightChild, 0);
        maxPathSum = Math.max(maxPathSum, curMaxPathSumOfRoot);
        // root自身参与纵向扩展，将来作为父节点的左右子树的最大路径和构建因子参与父节点的横向最大路径和计算。
        return root.val + Math.max(0, Math.max(maxPathSumToLeftChild, maxPathSumToRightChild));
    }

    public static void main(String[] args) {

    }
}
