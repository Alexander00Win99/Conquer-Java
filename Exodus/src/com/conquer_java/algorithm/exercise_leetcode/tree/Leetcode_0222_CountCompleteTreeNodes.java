package com.conquer_java.algorithm.exercise_leetcode.tree;

public class Leetcode_0222_CountCompleteTreeNodes {
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

    // 遍历方式计算普通二叉树的节点总数，时间复杂度是O(n)。
    public int countNormalTreeNodes(TreeNode root) {
        return root == null ? 0 : 1 + countNormalTreeNodes(root.left) + countNormalTreeNodes(root.right);
    }

    /**
     * 【解题思路】
     * 分别计算左右子树的最左深度：
     * 1) 相等，说明左子树是完美二叉树（较深），右子树待定（可能较浅，可能等深）；
     * 2) 不等，说明右子树是完美二叉树（较浅），左子树确定不完美（较深）；
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = getLeftMostDepth(root.left);
        int rightDepth = getLeftMostDepth(root.right);
        if (leftDepth == rightDepth) return (1 << leftDepth) + countNodes(root.right);
        else return (1 << rightDepth) + countNodes(root.left);
    }

    /**
     * 【解题思路】：
     * 完美二叉树拓扑特点：leftMostDepth = rightMostDepth（完满二叉树拓扑特点：leftMostDepth >= rightMostDepth）。
     * ==> 1) 使用最右深度作为已知完美二叉树的深度计算节点个数
     * 二叉树的二分特性：二叉树天然具有二分特性，适于二分查找。
     * ==> 2) 不断迭代计算curRoot.left的最右深度或者curRoot.right的最左深度，探测确定新的已知完美二叉树的边界。
     *
     * 【参考思路】
     * 有序二维数组寻找极值问题，选择左下（或者右上节点）作为始发点，向上或者向右移动（向下或者向左移动）。
     * @param root
     * @return
     */
    public static int countCompleteBinTree(TreeNode root) {
        TreeNode curRootCandidate = root;
        TreeNode curLeftCandidate;
        TreeNode curRightCandidate;
        int curLeftMostDepth = getLeftMostDepth(curRootCandidate);
        int curRightMostDepth = getRightMostDepth(curRootCandidate);
        int curCount = countPerfectBinTree(curRightMostDepth);

        while (curLeftMostDepth != curRightMostDepth && curRightMostDepth > 0) {
            curLeftCandidate = curRootCandidate.left;
            curRightCandidate = curRootCandidate.right;
            System.out.println("当前左右最深深度为：" + curLeftMostDepth + " 和 " + curRightMostDepth);

            // 纵向向下缩小检测范围
            // 优先选择左子树进行边界判定
            curRootCandidate = curLeftCandidate; // 如果左右最深深度不同，继续向下选择左子树作为候选人
            curLeftMostDepth = getLeftMostDepth(curRootCandidate);
            curRightMostDepth = getRightMostDepth(curRootCandidate);
            System.out.println("向下缩小检测返回之后，新的左右最深深度为：" + curLeftMostDepth + " 和 " + curRightMostDepth);
            // 横向向右平移检测范围
            // 如果左右最深深度相同，边界位于当前子树范围之外 —— 左子树确认已是完美二叉树，需要回退右子树继续检测边界
            // 如果左右最深深度不同，边界位于当前子树范围之内 —— continue循环，继续向下缩小检测范围
            if (curLeftMostDepth == curRightMostDepth) { // 如果左右最深深度相同，边界位于当前子树范围之外——左子树确认已是完美二叉树，需要回退右子树继续检测边界。
                curCount += 1 << (curRightMostDepth - 1);
                System.out.println("本次向下检测操作，发现左右最深深度相同，上步应该向右平移检测范围（考察当前节点的叔父节点）而非向下缩小检测范围，同时扩充本次向下操作覆盖范围内所有底层叶子节点个数：" + (1 << (curRightMostDepth - 1)));
                curRootCandidate = curRightCandidate; // 向右平移更新为原来保存右子树候选人
                curLeftMostDepth = getLeftMostDepth(curRootCandidate);
                curRightMostDepth = getRightMostDepth(curRootCandidate);
                System.out.println("上步操作若选叔父节点，相应左右最深深度为：" + curLeftMostDepth + " 和 " + curRightMostDepth);
            } // 如果左右最深深度不同，边界位于当前子树范围之内 —— continue循环，继续向下缩小检测范围
        }

        return curCount;
    }

    public static int countPerfectBinTree(int pefectDepth) {
        return (1 << pefectDepth) - 1; // 谨记：<<、>>、>>>操作要加()括号，否则出错，不易查找。
    }

    public static int getLeftMostDepth(TreeNode root) {
        int depth = 0;
        while (root != null) {
            depth++;
            root = root.left;
        }
        return depth;
        //return root == null ? 0 : 1 + getLeftMostDepth(root.left);
    }

    public static int getRightMostDepth(TreeNode root) {
        int depth = 0;
        while (root != null) {
            depth++;
            root = root.right;
        }
        return depth;
        //return root == null ? 0 : 1 + getRightMostDepth(root.right);
    }

    public static void main(String[] args) {
        System.out.println(countPerfectBinTree(3));
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        System.out.println(countCompleteBinTree(root));
    }
}
