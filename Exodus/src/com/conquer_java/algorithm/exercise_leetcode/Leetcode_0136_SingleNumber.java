package com.conquer_java.algorithm.exercise_leetcode;

public class Leetcode_0136_SingleNumber {
    // 1) 使用map，暴力循环：O(2n)两次遍历，频繁读写map（空间复杂度：O(n)）
//    public static int singleNumber(int[] nums) {
//        // 利用map存储元素出现次数
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int i = 0; i < nums.length; i++) {
//            int num = nums[i];
//            int count = 0;
//            if (map.containsKey(num)) {
//                count = map.get(num);
//                map.put(num, ++count);
//                continue;
//            }
//            map.put(num, ++count);
//        }
//
//        for (Integer num : map.keySet()) {
//            if (map.get(num) == 1)
//                return num;
//        }
//        return Integer.MIN_VALUE;
//    }

    // 2) 使用set，遇重则减：一次遍历，遇到相同元素，直接删除（空间复杂度：O(n)）
    // {9}
    // {9, 0}
    // {9, 0, 1}
    // {9, 0}
    // {9}
//    public static int singleNumber(int[] nums) {
//        Set<Integer> set = new HashSet<>();
//        for (int i = 0; i < nums.length; i++) {
//            int num = nums[i];
//            if (set.contains(num)) {
//                set.remove(num);
//                continue;
//            }
//            set.add(num);
//        }
//        return set.iterator().next();
//    }

    // 3) 使用set，减少操作：O(n)
//    public static int singleNumber(int[] nums) {
//        Set<Integer> set = new HashSet<>();
//        int sum = 0;
//        for (int i = 0; i < nums.length; i++) {
//            int num = nums[i];
//            if (set.contains(num)) {
//                sum -= num;
//                continue;
//            }
//            set.add(num);
//            sum += num;
//        }
//        return sum;
//    }

    // 4) 单次遍历两数，减少遍历次数：O(n/2)
//    public static int singleNumber(int[] nums) {
//        Arrays.sort(nums);
//        for (int i = 0; i < nums.length - 2;) {
//            if (nums[i] == nums[i + 1]) {
//                i += 2;
//                continue;
//            }
//            return nums[i];
//        }
//        return nums[nums.length - 1];
//    }

    // 利用异或^位运算特性实现快速运算
    public static int singleNumber(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++)
            res ^= nums[i];
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{9, 0, 1, 1, 0};
        System.out.println(singleNumber(nums));

        int[] arr = new int[] {0, 1, 4, 9, 4, 1, 9};
        System.out.println(singleNumber(arr));
    }
}
