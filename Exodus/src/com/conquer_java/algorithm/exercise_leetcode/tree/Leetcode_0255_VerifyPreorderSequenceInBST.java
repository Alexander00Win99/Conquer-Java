package com.conquer_java.algorithm.exercise_leetcode.tree;

import java.util.Arrays;

public class Leetcode_0255_VerifyPreorderSequenceInBST {
    public static boolean verifyPreorder(int[] preorder) {
        if (preorder.length <= 2) return true;

        int rootValue = preorder[0];
        int leftIndex = Integer.MAX_VALUE;
        int rightIndex = Integer.MAX_VALUE;

        // 获取左右子树起始位置
        for (int i = 1; i <= preorder.length; i++) {
            if (leftIndex == Integer.MAX_VALUE && preorder[i] < rootValue) leftIndex = i;
            if (rightIndex == Integer.MAX_VALUE && preorder[i] > rootValue) rightIndex = i;
            if (leftIndex != Integer.MAX_VALUE && rightIndex != Integer.MAX_VALUE) break;
        }

        // 存在左子树，但是左子树起始位置非1，说明1位置大于根节点，显然false。
        if (leftIndex != Integer.MAX_VALUE && leftIndex != 1) return false;

        if (rightIndex != Integer.MAX_VALUE) { // 存在右子树
            if (leftIndex == 1) { // 左右子树同时存在
                int[] leftArray = Arrays.copyOfRange(preorder, 1, rightIndex);
                int[] rightArray = new int[preorder.length - rightIndex];
                System.arraycopy(preorder, rightIndex, rightArray, 0, preorder.length - rightIndex);
                for (int i : leftArray) { // 左子树中有值大于根，返回false
                    if (i > rootValue)
                        return false;
                }
                for (int j : rightArray) { // 右子树中有值小于根，返回false
                    if (j < rootValue)
                        return false;
                }
                //boolean result = verifyPreorder(leftArray) && verifyPreorder(rightArray);
                return verifyPreorder(leftArray) && verifyPreorder(rightArray);
            } else { // 只有右子树
                int[] rightArray = new int[preorder.length - rightIndex];
                System.arraycopy(preorder, rightIndex, rightArray, 0, preorder.length - rightIndex);
                for (int j : rightArray) { // 右子树中有值小于根，返回false
                    if (j < rootValue)
                        return false;
                }
                //boolean result = verifyPreorder(rightArray);
                return verifyPreorder(rightArray);
            }
        } else { // 只有左子树
            int[] leftArray = Arrays.copyOfRange(preorder, 1, rightIndex);
            for (int i : leftArray) { // 左子树中有值大于根，返回false
                if (i > rootValue)
                    return false;
            }
            //boolean result = verifyPreorder(leftArray);
            return verifyPreorder(leftArray);
        }
    }

    public static boolean oppositeSign(int l, int r) {
        return (l ^ r) >>> 31 == 1;
    }

    public static void main(String[] args) {
        System.out.println(oppositeSign(0, 1));
        int[] arr = null;
        arr = new int[] {5, 2, 1, 3, 6};
        System.out.println(verifyPreorder(arr));
        arr = new int[] {5, 2, 6, 1, 3};
        System.out.println(verifyPreorder(arr));
    }
}
