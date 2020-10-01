package com.conquer_java.algorithm.leetcode;

/**
 * 1) 链式；2) 环形；3) 树形；
 *
 * House Robber I(Easy)
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed,
 * the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and
 * it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3). Total amount you can rob = 1 + 3 = 4.
 *
 * Example 2:
 * Input: [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1). Total amount you can rob = 2 + 9 + 1 = 12.
 * dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i])
 *
 * 【分析】
 * 典型的动态规划问题，沿街的房屋依次决定每家打劫还是放弃视为一个阶段，用dp[i]表示前i家获取的最高金额，第i阶段的决策就是两种：打劫、放弃。如此，不难写出以下状态转移方程：rob(i) = Math.max(rob(i - 2) + nums[i], rob(i - 1))
 *
 * House Robber II(Medium)
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed.
 * All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile,
 * adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.
 *
 * Example 2:
 * Input: [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3). Total amount you can rob = 1 + 3 = 4.
 *
 * House Robber III(Medium)
 * The thief has found himself a new place for his thievery again. There is only one entrance to this area, called the "root".
 * Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that
 * "all houses in this place forms a binary tree". It will automatically contact the police if two directly-linked houses were broken into on the same night.
 *
 * Determine the maximum amount of money the thief can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: [3,2,3,null,3,null,1]
 *
 *      3
 *     / \
 *    2   3
 *     \   \
 *      3   1
 *
 * Output: 7
 * Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
 *
 * Example 2:
 * Input: [3,4,5,1,3,null,1]
 *
 *      3
 *     / \
 *    4   5
 *   / \   \
 *  1   3   1
 *
 * Output: 9
 * Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
 */
public class DemoHouseRobber {
    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0; // leetcode提交报错：if (nums == null || nums.length == 0) return Integer.MIN_VALUE;
        if (nums.length == 1) return nums[0]; // 避免数组越界错误

//        return rob(nums, nums.length - 1);
//        int[] memory = new int[nums.length];
//        return robByMemory(nums, nums.length -1, memory);
        return robByDP(nums);
    }

    /**
     * 推导过程：
     * nums = int[]{2, 7, 9, 3, 1}
     * 从后往前递推，每个下标位置对应元素，可以并且必须做出是否打劫的选择：
     * 假设rob(i)代表到达i下标位置对应元素nums[i]时，最佳打劫，根据规则，如下反推：
     * rob(4)   (rob(2) + nums[4])    (rob(3))  选择大者
     * rob(3)   (rob(1) + nums[3])    (rob(2))  选择大者
     * rob(2)   (rob(0) + nums[2])    (rob(1))  选择大者
     * rob(1)   Math.max(nums[0], nums[1])      出口
     * rob(0)   nums[0]                         出口
     *
     * 递归规律(状态转移方程)：
     * rob(i) = Math.max(rob(i - 2) + nums[i], rob(i - 1))
     * 递归出口(边界状态)：
     * rob(0) = nums[0]; rob(1) = Math.max(nums[0], nums[1])
     * @param nums
     * @param i
     * @return
     */
    public static int rob(int[] nums, int i) { // 返回到达i下标位置的最优解
        if (i == 0) return nums[0];
        if (i == 1) return Math.max(nums[0], nums[1]);

        int yes = rob(nums, i - 2) + nums[i]; // 当前位置进行打劫
        int no = rob(nums, i - 1); // 当前位置不作打劫
        return Math.max(yes, no);
    }

    public static int robByMemory(int[] nums, int i, int[] memory) {
        if (i == 0) {
            memory[0] = nums[0];
            return memory[0];
        }
        if (i == 1) {
            memory[1] = Math.max(nums[0], nums[1]);
            return memory[1];
        }

        int yes = (memory[i - 2] == 0 ? robByMemory(nums, i - 2, memory): memory[i - 2]) + nums[i]; // 当前位置进行打劫
        int no = memory[i - 1] == 0 ? robByMemory(nums, i - 1, memory): memory[i - 1]; // 当前位置不作打劫
        memory[i] = Math.max(yes, no);
        return memory[i];
    }

    public static int robByDP(int[] nums) {
        int[] spoilsDP = new int[nums.length];
        spoilsDP[0] = nums[0];
        spoilsDP[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            spoilsDP[i] = Math.max(spoilsDP[i - 2] + nums[i], spoilsDP[i - 1]);
            System.out.println("i=" + i + "-spoilsDP[i]=" + spoilsDP[i]);
        }

        return spoilsDP[nums.length - 1];
    }

    /**
     * 【环形打家劫舍】
     * 本题重点在于当推导状态转移方程时下标映射不能出错，如图：
     * nums[0]  nums[1]     nums[2]     nums[3]     nums[4]     nums[5]     nums[6]     nums[7]
     * -no      dp1[0]      dp1[1]      dp1[2]      dp1[3]      dp1[4]      dp1[5]      dp1[6]
     * +yes     -no         dp2[0]      dp2[1]      dp2[2]      dp2[3]      dp2[4]      -no
     * @param nums
     * @return
     */
    public static int robII(int[] nums) { // 假设环形街道，各户住宅首尾相连。所以，nums[0]和nums[nums.length - 1]必然不能同时打劫，因此，可以将原问题分为俩个互斥的动态规划问题各自求解。
        if (nums == null || nums.length == 0) return 0; // leetcode提交报错：if (nums == null || nums.length == 0) return Integer.MIN_VALUE;
        if (nums.length == 1) return nums[0]; // 避免数组越界错误
        if (nums.length == 2) return Math.max(nums[0], nums[1]); // 避免数组越界错误
        if (nums.length == 3) return Math.max(Math.max(nums[0], nums[1]), nums[2]); // 避免数组越界错误

        int[] dp1 = new int[nums.length - 1]; // 第一家nums[0]不打劫，在区间[1, nums.length - 1]上利用动态规划求解最大赃物收益
        int[] dp2 = new int[nums.length - 1]; // 第一家nums[0]打劫了，在区间[2, nums.length - 2]上利用动态规划求解最大赃物收益 + nums[0]

        dp1[0] = nums[1]; // 对nums[0]不打劫，从nums[1]到nums[nums.length - 1]总计(nums.length - 1)户住宅利用动态规划求解最大值dp1[nums.length - 2]
        dp1[1] = Math.max(nums[1], nums[2]);
        for (int i = 2; i < nums.length - 1; i++) {
            dp1[i] = Math.max(dp1[i - 2] + nums[i + 1], dp1[i - 1]);
        }

        dp2[0] = nums[2]; // 对nums[0]打劫了，从nums[2]到nums[nums.length - 2]总计(nums.length - 3)户住宅利用动态规划求解最大值dp2[nums.length - 4]
        if (nums.length > 4) {
            dp2[1] = Math.max(nums[2], nums[3]);
            for (int i = 2; i < nums.length - 3; i++) {
                dp2[i] = Math.max(dp2[i - 2] + nums[i + 2], dp2[i - 1]);
            }
        }

        return Math.max(dp1[nums.length - 2], nums[0] + (nums.length >= 5 ? dp2[nums.length - 4] : dp2[0]));
    }

    public static int robIII(TreeNode root) {
        int[] result = robSub(root);
        return Math.max(result[0], result[1]);
    }

    private static int[] robSub(TreeNode root) {
        if (root == null)
            return new int[2];
        int[] left = robSub(root.left);
        int[] right = robSub(root.right);
        int[] result = new int[2];
        // 不选当前节点
        result[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        // 选择当前节点
        result[1] = root.val + left[0] + right[0];

        return result;
    }

    public static int robIIIByRecursion(TreeNode root) { // https://blog.csdn.net/qq_40963043/article/details/100765212 里面包含案例
        if (root == null) return 0;
        // rootSpoils是root予以打劫的结果(!!!+++关注选择root的拓扑，等价于选择root+选择root.left.left+root.left.right+root.right.left+root.right.right子树+++!!!)
        int rootSpoils = root.val;
        if (root.left != null) rootSpoils += robIIIByRecursion(root.left.left) + robIIIByRecursion(root.left.right);
        if (root.right != null) rootSpoils += robIIIByRecursion(root.right.left) + robIIIByRecursion(root.right.right);
        // nonRootSpoils是root不予打劫的结果(!!!+++关注不选root的拓扑，等价于放弃root+选择root.left子树+选择right子树+++!!!)
        int nonRootSpoils = 0;
        if (root.left != null) nonRootSpoils += robIIIByRecursion(root.left);
        if (root.right != null) nonRootSpoils += robIIIByRecursion(root.right);

        return Math.max(rootSpoils, nonRootSpoils);
    }

    public static int robElegant(TreeNode root) {
        Money rootMoney = getMoney(root);
        return Math.max(rootMoney.use, rootMoney.notUse);
    }

    private static Money getMoney(TreeNode root) {
        if (root == null)
            return new Money();
        Money leftMoney = getMoney(root.left);
        Money rightMoney = getMoney(root.right);

        Money rootMoney = new Money();
        rootMoney.use = root.val + leftMoney.notUse + rightMoney.notUse;
        rootMoney.notUse = Math.max(leftMoney.use, leftMoney.notUse) + Math.max(rightMoney.use, rightMoney.notUse);

        return rootMoney;
    }

    public static void main(String[] args) {
        System.out.println("++++++++++++++++Rob House I++++++++++++++++");
        int[] nums = new int[]{2, 7, 9, 3, 1};
        System.out.println(rob(nums));
        System.out.println("----------------Rob House I----------------");

        System.out.println("++++++++++++++++Rob House II++++++++++++++++");
        int[] numbers = new int[]{1, 2, 3, 1, 7, 5, 1, 3, 2};
        System.out.println(robII(numbers));
        System.out.println("----------------Rob House II----------------");

        System.out.println("++++++++++++++++Rob House III++++++++++++++++");
        TreeNode A = new TreeNode(4);
        TreeNode B = new TreeNode(99);
        TreeNode C = new TreeNode(16);
        TreeNode D = new TreeNode(1);
        TreeNode E = new TreeNode(100);
        TreeNode F = new TreeNode(9);
        TreeNode G = new TreeNode(81);
        A.left = B;
        A.right = C;
        B.left = D;
        C.right = E;
        D.right = F;
        E.left = G;
        TreeNode root = A;
        System.out.println(robIII(root));
        System.out.println(robIIIByRecursion(root));
        System.out.println(robElegant(root));
        System.out.println("----------------Rob House III----------------");
    }

    static class Money {
        int use;
        int notUse;

        Money() {
            use = 0;
            notUse = 0;
        }
    }
}
