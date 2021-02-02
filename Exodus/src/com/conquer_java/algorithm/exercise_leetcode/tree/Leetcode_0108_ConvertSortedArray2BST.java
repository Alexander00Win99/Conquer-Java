package com.conquer_java.algorithm.exercise_leetcode.tree;

public class Leetcode_0108_ConvertSortedArray2BST {
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

    public static TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        if (start < 0 || end >= nums.length) throw new IllegalArgumentException("Invalid parameter, out of array boundary!");
        if (start > end) return null;
        int mid = start + ((end - start) >>> 1);
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, start, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, end);
        return root;
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        TreeNode root = sortedArrayToBST(nums, 0, nums.length - 1);
        return root;
//        if (nums.length == 0) return null;
//        if (nums.length == 1) return new TreeNode(nums[0]);
//        TreeNode root = null;
//        if (nums.length == 2) {
//            root = new TreeNode(nums[1]);
//            root.left = new TreeNode(nums[0]);
//        }
//        if (nums.length >= 3) {
//            int rootIndex = nums.length / 2; // nums.length >= 3 => rootIndex >= 1
//            int[] leftNums = new int[rootIndex];
//            System.arraycopy(nums, 0, leftNums, 0, rootIndex);
//            int[] rightNums = new int[nums.length - rootIndex - 1];
//            System.arraycopy(nums, rootIndex + 1, rightNums, 0, nums.length - rootIndex - 1);
//            root = new TreeNode(nums[rootIndex]);
//            root.left = sortedArrayToBST(leftNums);
//            root.right = sortedArrayToBST(rightNums);
//        }
//        return root;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {-10, -3, 0, 5, 9};
        TreeNode root = sortedArrayToBST(nums);
        System.out.println(root);
    }
}
