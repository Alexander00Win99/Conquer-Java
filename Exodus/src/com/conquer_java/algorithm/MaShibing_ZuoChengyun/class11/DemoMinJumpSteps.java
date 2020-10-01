package com.conquer_java.algorithm.MaShibing_ZuoChengyun.class11;

import java.util.*;

/**
 * 【题目】
 * 假设有1~N总共N个位置，更左或者更右的位置均不存在，给定一个长度为N的整型数组arr，已知其中没有负数，
 * 数组下标从0到N-1，规定arr[i]表示如果有人站在i+1位置，那么下步可以前往i+1-arr[i]位置或者i+1+arr[i]位置，
 * 如果，给你start位置和end位置，请返回从start到end至少需要几步？如果无法到达，返回-1。
 *
 * 【思路】
 * 暴力递归 ==> 动态规划
 * 1) 假设状态转移方程 int f(int var1, int var2)——可以实现：矩阵空间 - [1, range1] * [1, range2]
 * 2) 假设状态转移方程 int f(int var1, int var2, boolean[] map)——无法实现：map的O(n) = 2 ^ n
 * 3) 假设状态转移方程 int f(int var1, int var2, int pressedStatus)——可以实现：状态压缩
 *
 * 【注意】
 * 1) 某些位置可能无法到达，例如：arr = [1, 2, 1, 2]，其中pos1可以到达pos4，但是pos2永远无法到达pos3；
 * 2) 设置变量控制避免同一节点重复出现：a) 暴力递归里面，通过当前节点不能重复遍历map[i] != true实现；b) 动态规划里面，通过跳跃次数不能超过arr.length - 1实现；
 *
 * 【总结】——算法一般包括两种：清晰知道某个算法的具体步骤以及实现细节，可以精确求解；只是知道初始条件和最终目标，并不了解中间过程如何运作，可以动态规划；
 * 1) 所有动态规划均是源于暴力递归(参考递归的自顶向下过程，自底向上逆向推理，通过合并归纳进行规律总结，从而求取状态转移方程)；
 * 2) 在某个场景的动态规划的状态转移方程中，可能包含多个状态变量，若状态变量超过三个或者其中任一状态变量的空间复杂度超出整型量级（例如，数组级别），则无法实现递归算法改为动态规划，除非能够实现状态压缩方式的动态规划；
 * 3) 动态规划的本质是：穷举各种可能，选择最优方案。
 */
public class DemoMinJumpSteps {
    private static List<String> minJumpStepByBFS(int[] arr, int origin, int target) {
        List<String> paths = new ArrayList<>();
        if (origin < 1 || origin > arr.length || target < 1 || target > arr.length)
            return  paths;

        boolean[] map = new boolean[arr.length + 1];
        Queue<Integer> nodeQueue = new LinkedList<>();
        Queue<String> pathQueue = new LinkedList<>();

        nodeQueue.offer(origin); // 起始位置作为根节点root压栈
        pathQueue.offer(""); // 指向起始位置的指针作为根节点的路径前缀path压栈

        /**
         * 设想二叉数按照BFS宽度优先遍历方式返回从根节点到叶子的各条路径场景（逐层压栈，遇到非叶子节点继续，遇到叶子节点返回）：
         * 1) 每当出栈一个节点，压栈左右孩子：出栈节点组成的链表即为压栈节点的祖先（路径前缀）;
         * 2) 遇到叶子节点即可视作完成一条路径，合并得到完整路径completePath = curPath + curNode + "->" + left|right，加入路径集合，并且返回上层重新遍历下一路径；
         * 3) 路径之中不能包含同一节点，因此，需要为每个节点设置遍历标记，出栈代表当前节点curNode已经遍历，置true，left|right为叶子节点，代表某条路径已经完成，当前节点curNode置false，可以参与新的路径。
         * 叶子节点（node.left == null && node.right == null）<==> 当前位置跳跃到达的位置=越界|目标位置；
         *
         *
         * <==>
         */
        while (!nodeQueue.isEmpty()) {
            int curNode = nodeQueue.poll();
            String curPath = pathQueue.poll();
            map[curNode] = true; // 当前节点在路径前缀中已经出现
            int left = curNode - arr[curNode - 1]; // 左孩子是当前位置向左跳跃到达的位置
            int right = curNode + arr[curNode - 1]; // 右孩子是当前位置向右跳跃到达的位置

            if (left < 1 || left > arr.length || map[left] == true) { // 叶子节点(位置无效-越界)：无须压栈，直接返回
                paths.add(curPath + curNode + "->" + left);
                map[curNode] = false;
            } else if (left == target) { // 叶子节点(位置有效-到达目标)：无须压栈，直接返回
                paths.add(curPath + curNode + "->" + right);
                map[target] = true;
                map[curNode] = false;
//                nodeQueue.offer(left);
//                pathQueue.offer(curPath + curNode + "->" + left);
            } else { // 路径中间节点(位置有效-路径前缀)：继续压栈——left作为左孩子入栈节点队列+当前节点加入祖先牌位链表合并形成新的路径前缀入栈路径队列
                nodeQueue.offer(left);
                pathQueue.offer(curPath + curNode + "->");
            }

            if (right < 1 || right > arr.length || map[right] == true) { // 叶子节点(位置无效-越界)：无须压栈，直接返回
                paths.add(curPath + curNode + "->" + right);
                map[curNode] = false;
            } else if (left == target) { // 叶子节点(位置有效-到达目标)：无须压栈，直接返回
                paths.add(curPath + curNode + "->" + right);
                map[target] = true;
                map[curNode] = false;
//                nodeQueue.offer(right);
//                pathQueue.offer(curPath + curNode + "->" + right);
            } else { // 路径中间节点(位置有效-路径前缀)：继续压栈——left作为左孩子入栈节点队列+当前节点加入祖先牌位链表合并形成新的路径前缀入栈路径队列
                nodeQueue.offer(right);
                pathQueue.offer(curPath + curNode + "->");
            }
        }

        return paths;
    }

    private static <T> void printList(List<T> list) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext())
            System.out.println(iterator.next());
    }

    public static int minJumpStep(int[] arr, int start, int end) {
        if (start < 1 || start > arr.length || end < 1 || end > arr.length) return -1;
        // map[1...arr.length]表征[1...arr.length]各个位置以前是否来过，初始false
        boolean[] map = new boolean[arr.length + 1];
        return getSteps(arr, start, end, map);
    }

    /**
     * 在递归过程中，origin逐次改变，target保持不变，因此，每次递归调用均需判断origin是否合规；
     * 递归过程，本质是深度优先遍历，所以，在深度优先遍历过程中，可能某个分支出现重复遍历同一位置节点情形，也即产生死循环现象；
     * 如果没有判断当前节点以前是否来过，那么存在死循环可能，例如：arr = [1, 1, 2, 1]，在从1到4——无法实现——过程中，某个分支1-2-1-2-...；
     * 可以引入数组类型状态变量map[i]表征各个位置之前是否来过，如果来过，本条分支直接返回-1，提前终结继续向下遍历；
     * 到达某个新的位置，需要将当前位置map[position]置为true，左跳子树和右跳子树全部遍历完成以后，需要将其恢复置回false，否则，本条分支遍历完成以后，后续遍历其他分支会受影响；
     * @param arr 各个位置可选的左右跳跃步数
     * @param origin 当前位置
     * @param target 目标位置
     * @return 返回从origin到target所需的最小步数
     */
    private static int getSteps(int[] arr, int origin, int target, boolean[] map) {
        // 当前位置无效——递归出口：无法到达
        if (origin < 1 || origin > arr.length) return -1;
        // 当前位置有效并且以前来过——递归出口：无法到达
        if (map[origin] == true) return -1;
        // 当前位置有效并且到达目标——递归出口：可以到达
        if (origin == target) return 0;
        // 当前位置有效并且尚未到达目标，存在两种可能：要么左跳；要么右跳；
        // 到达最终目标的最少步数，只能是从当前位置的左跳位置到达最终目标的步数和动当前位置的右跳位置到达最终目标的步数之中小者加一。
        map[origin] = true;
        int leftSteps = getSteps(arr, origin - arr[origin - 1], target, map);
        int rightSteps = getSteps(arr, origin + arr[origin - 1], target, map);
        map[origin] = false;
        if (leftSteps == -1 && rightSteps == -1) return -1;
        if (leftSteps == -1) return rightSteps + 1;
        if (rightSteps == -1) return leftSteps + 1;
        return Math.min(leftSteps, rightSteps) + 1;
    }

    public static int minJumpStepByDP(int[] arr, int start, int end) {
        if (start < 1 || start > arr.length || end < 1 || end > arr.length) return -1;
        // map[1...arr.length]表征[1...arr.length]各个位置以前是否来过，初始false
        int[][] dp = new int[arr.length + 1][arr.length + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                dp[i][j] = Integer.MIN_VALUE; // 后续通过dp[i][j] == Integer.MIN_VALUE判断当前位置以前是否处理过
            }
        }
        return getStepsByDP(arr, start, end,0, dp);
    }

    /**
     * 假设int[] arr数组，长度len = arr.length，那么，从其中任一位置到另一位置的最小步数，永远都不可能超过len。
     * 因为，如果最小步数超过len，也就意味着必然出现某个节点位置重复遍历的情况，而这有悖于最小步数的前提假设。
     * 因此，可以使用一个状态参数passedSteps，表示从起始位置到当前位置origin已经经历的步数。
     * @param arr 记录各个位置能够左右跳跃的步数
     * @param origin 当前位置
     * @param target 目标位置
     * @param passedSteps 为到达当前位置，已经过的最少步数
     * @param dp dp[i][j]二维数组，作为动态规划的预处理数据结构，表示已经从起点位置init跳跃j步后到达当前位置i
     * @return 返回从当前位置origin到目标位置target所需最小跳跃步数
     */
    private static int getStepsByDP(int[] arr, int origin, int target, int passedSteps, int[][] dp) {
        // 当前位置无效：下标越界+深度越界
        if (origin < 1 || origin > arr.length || passedSteps > arr.length - 1) return -1;
        // 当前位置以前来过
        if (dp[origin][passedSteps] != Integer.MIN_VALUE) return dp[origin][passedSteps];
        // 当前位置有效
        if (origin == target) {
            dp[origin][passedSteps] = passedSteps;
            return passedSteps;
        }
        int leftSteps = getStepsByDP(arr, origin - arr[origin - 1], target, passedSteps + 1, dp);
        int rightSteps = getStepsByDP(arr, origin + arr[origin - 1], target, passedSteps + 1, dp);
        if (leftSteps == -1 && rightSteps == -1) return -1;
        if (leftSteps == -1) return rightSteps;
        if (rightSteps == -1) return leftSteps;
        dp[origin][passedSteps] = Math.min(dp[origin - arr[origin - 1]][passedSteps + 1], dp[origin + arr[origin - 1]][passedSteps + 1]);
        return Math.min(leftSteps, rightSteps);
    }

    // 生成测试数组
    public static int[] generateRandomArray(int L, int V) {
        return null;
    }

    public static void main(String[] args) {
        int[] arr;
//        arr = new int[] {1, 1, 2, 4, 1};
        arr = new int[] {2, 1, 1, 1, 3};
        printList(minJumpStepByBFS(arr, 1, 4));
        System.out.println(minJumpStep(arr, 1, 4));
        System.out.println(minJumpStepByDP(arr, 1, 4));
//        arr = new int[] {1, 2, 4, 3, 1, 2, 6};
//        System.out.println(minJumpStep(arr, 1, 7));
//        System.out.println(minJumpStepByDP(arr, 1, 7));

    }
}
