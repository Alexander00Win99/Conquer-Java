package com.conquer_java.algorithm.MaShibing_ZuoChengyun;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Problem-01
 * 1) 给定一个有序数组，其元素值可能为正数、零、负数，返回数组中每个数平方之后的不同结果有多少种？
 * 2) 给定一个数组，先递增后递减，返回数组中总计有多少个不同的数字？
 *
 * Problem-02
 * 1) 给定一个正整数数组，把它想象成为一个直方图，如果这个直方图能够盛水，返回其能装多少水？
 *
 * 【结论】——首尾指针技巧
 * 1) 能用首尾指针技巧解决的问题，往往序列是有规律的，只是这种要求并不严格；
 * 2) 但是严格要求首尾指针根据制定的规则在由两端向中间移动的过程中，一定不能错过某些答案(遗漏某些场景)；
 * 3) 制定的规则与数据状况以及问题本身相关；
 */
public class DemoDoublePointer {
    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param arr
     * @return
     */
    public static int squareDiff1(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        HashSet<Integer> set = new HashSet<>();
        for (int cur : arr)
            //set.add(Math.pow(2, cur));
            set.add(cur * cur);
        return set.size();
    }

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * 考虑场景：[-3, -2, -1, 0, 1, 2, 3, 4, 5, 6]以及[-9, -9, -9, -9, -1, 1, 9, 9, 9, 9]；
     * 人为指定如下移动规则：
     * 1) 初始状态：指针left和right分别位于数组两端；
     * 2) 两端指针所指元素绝对值大者由外到内移动，如此，不会遗漏亦不会重复，同时如果绝对值小者由外到内移动，会导致某些绝对值重复计数；
     * 3) 指针移动之后如果所指元素依然等于当前本端绝对值，继续移动直至不等，如此可以提高效率；
     * 4) 跳出条件：left > right
     * @param arr
     * @return
     */
    public static int squareDiff2(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int len = arr.length;
        int left = 0;
        int right = len - 1;
        int leftAbs = 0;
        int rightAbs =0;
        int count = 0;

        while (left <= right) {
            count++;
            leftAbs = Math.abs(arr[left]);
            rightAbs = Math.abs(arr[right]);
            if (leftAbs < rightAbs)
                while (right >= 0 && Math.abs(arr[right]) == rightAbs)
                    right--;
            else if (leftAbs > rightAbs)
                while (left < len && Math.abs(arr[left]) == leftAbs)
                    left++;
            else {
                while (left < len && Math.abs(arr[left]) == leftAbs)
                    left++;
                while (right >= 0 && Math.abs(arr[right]) == rightAbs)
                    right--;
            }
        }
        return count;
    }

    public static int getWaterVolume1(int[] arr) {
        if (arr == null || arr.length <= 2)
            return 0;
        int len = arr.length;
        int waterVolume = 0;
        for (int i = 1; i < len - 1; i++) {
            int leftMax = Integer.MIN_VALUE;
            for (int j = 0; j < i; j++) {
                leftMax = Math.max(leftMax, arr[j]);
            }
            int rightMax = Integer.MIN_VALUE;
            for (int j = i + 1; j < len; j++) {
                rightMax = Math.max(rightMax, arr[j]);
            }
            waterVolume += Math.max(Math.min(leftMax, rightMax) - arr[i], 0);
        }
        return waterVolume;
    }

    public static int getWaterVolume2(int[] arr) { // 生成两个预处理数据：leftMaxs + rightMaxs
        if (arr == null || arr.length <= 2)
            return 0;
        int len = arr.length;
        int waterVolume = 0;

        int[] leftMaxs = new int[len];
        leftMaxs[0] = arr[0];
        for (int i = 1; i < len; i++) {
            leftMaxs[i] = Math.max(leftMaxs[i - 1], arr[i]);
        }
        int[] rightMaxs = new int[len];
        rightMaxs[len - 1] = arr[len - 1];
        for (int i = len - 2; i >= 0; i--) {
            rightMaxs[i] = Math.max(rightMaxs[i + 1], arr[i]);
        }

        for (int i = 1; i < len - 1; i++) {
            waterVolume += Math.max(Math.min(leftMaxs[i - 1], rightMaxs[i + 1]) - arr[i], 0);
        }
        return waterVolume;
    }

    public static int getWaterVolume3(int[] arr) { // 生成单个预处理数据：leftMax —— 从左到右遍历过程中实时更新 + rightMaxs | leftMaxs + rightMax —— 从右到左遍历过程中实时更新
        if (arr == null || arr.length <= 2)
            return 0;
        int len = arr.length;
        int waterVolume = 0;

        int[] rightMaxs = new int[len];
        rightMaxs[len - 1] = arr[len - 1];
        for (int i = len - 2; i >= 0; i--) {
            rightMaxs[i] = Math.max(rightMaxs[i + 1], arr[i]);
        }

        int leftMax = arr[0];
        for (int i = 1; i < len - 1; i++) {
            waterVolume += Math.max(Math.min(leftMax, rightMaxs[i + 1]) - arr[i], 0);
            leftMax = Math.max(leftMax, arr[i]);
        }
        return waterVolume;
    }

    public static int getWaterVolume4(int[] arr) {
        if (arr == null || arr.length <= 2)
            return 0;
        int len = arr.length;
        int waterVolume = 0;

        int left = 1;
        int right = len - 2;
        int leftMax = arr[0];
        int rightMax = arr[len - 1];

        /**
         * 1) 对于任意下标位置的某个元素arr[i]来说，volume[i] = Math.max(Math.min(leftMaxs[i - 1], rightMaxs[i + 1]) - arr[i], 0)，需要求得左边和右边的最大值；
         *
         * <==优化==>
         *
         * 2) 对于分别位于left和right位置的两个元素arr[left]和arr[right]来说，leftMax和rightMax两者往外的最大值，两值之中小者才是最终的决定因素，
         * 如果(leftMax <= rightMax)，对于arr[left]来说，leftMax就是其leftMaxs[i - 1]，此时完全没有必要继续求出rightMaxs[i + 1]，因为，rightMaxs[i + 1] >= rightMax >= leftMax = leftMax[i - 1]；
         * 同理，如果(leftMax >= rightMax)，对于arr[right]来说，rightMax就是其rightMaxs[i + 1]，此时完全没有必要继续求出leftMaxs[i - 1]，因为，leftMaxs[i - 1] >= leftMax >= rightMax = rightMax[i + 1]；
         */
        while (left <= right) {
            if (leftMax < rightMax) {
                waterVolume += Math.max(leftMax - arr[left], 0);
                leftMax = Math.max(leftMax, arr[left++]);
            } else {
                waterVolume += Math.max(rightMax - arr[right], 0);
                rightMax = Math.max(rightMax, arr[right--]);
            }
        }
        return waterVolume;
    }

    public static int[] generateRandomArray(int length, int limit) {
        int[] res = new int[(int)(Math.random() * length) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int)(Math.random() * limit) + 1;
        }
        return res;
    }

    public static int[] generateRandomSortedArray(int length, int limit) {
        int[] res = new int[(int)(Math.random() * length) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int)(Math.random() * limit) - (int)(Math.random() * limit);
        }
        Arrays.sort(res);
        return res;
    }

    public static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + "\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr;

        arr = new int[] {-3, -2, -1, 0, 1, 2, 3, 4, 5, 6};
        System.out.println(squareDiff1(arr));
        System.out.println(squareDiff2(arr));
        arr = new int[] {-9, -9, -9, -9, -1, 1, 9, 9, 9, 9};
        System.out.println(squareDiff1(arr));
        System.out.println(squareDiff2(arr));

        System.out.println("++++++++--------Begin: 绝对值Absolute--------++++++++");
        int len = 100;
        int peak = 10;
        int count = 10;
        System.out.println("Test begin:");
        for (int i = 0; i < count; i++) {
            System.out.println("Loop: " + (i + 1));
            int[] array = generateRandomSortedArray(len, peak);
            printArray(array);

            int res1 = squareDiff1(array);
            int res2 = squareDiff2(array);

            System.out.println("res1: " + res1);
            System.out.println("res2: " + res2);

            if (res1 != res2)
                System.out.println("OOPS!");
        }
        System.out.println("Test end!");
        System.out.println("++++++++--------End: 绝对值Absolute--------++++++++");

        arr = new int[] {1, 3, 5, 7, 3, 4, 8, 2, 1, 5};
        System.out.println(getWaterVolume4(arr));

        System.out.println("++++++++--------Begin: 直方图Histogram--------++++++++");
        int length = 10;
        int limit = 100;
        int loop = 10;
        System.out.println("Test begin:");
        for (int i = 0; i < loop; i++) {
            System.out.println("Loop: " + (i + 1));
            int[] array = generateRandomArray(length, limit);
            printArray(array);

            int res1 = getWaterVolume1(array);
            int res2 = getWaterVolume2(array);
            int res3 = getWaterVolume3(array);
            int res4 = getWaterVolume4(array);

            System.out.println("res1: " + res1);
            System.out.println("res2: " + res2);
            System.out.println("res3: " + res3);
            System.out.println("res4: " + res4);

            if (res1 != res2 || res2 != res3 || res3 != res4)
                System.out.println("OOPS!");
        }
        System.out.println("Test end!");
        System.out.println("++++++++--------End: 直方图Histogram--------++++++++");
    }
}
