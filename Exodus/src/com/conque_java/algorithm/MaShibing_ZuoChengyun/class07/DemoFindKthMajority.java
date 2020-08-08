package com.conque_java.algorithm.MaShibing_ZuoChengyun.class07;

import java.util.*;

/**
 * 【题目】
 * 给定一个长度为N的数组arr和一个大于1的正整数K，如果存在哪些数字出现次数大于N/K，即可将其放回，要求：
 * 时间复杂度O(N)，额外空间复杂度O(K)。
 *
 * 【分析】
 * 1) 候选人不可能超过(K-1)个；
 * 2) 当候选人个数不足(K-1)个时，遇到有别于当前所有候选人的数组元素，直接选为候选人；
 * 3) 当候选人个数已满(K-1)个时，遇到有别于当前所有候选人的数组元素，当前所有候选人均需选票减一(如有归零，剥夺候选人资格)；
 * 4) 当遇到数组元素为当前所有候选人中某个时，相应候选人选票加一；
 */
public class DemoFindKthMajority {
    public static List<Integer> kthMajority(int[] arr, int kth) {
        List<Integer> res = new ArrayList<>();
        if (kth <= 1) return res;

        HashMap<Integer, Integer> candidates = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (candidates.containsKey(arr[i])) {
                candidates.put(arr[i], candidates.get(arr[i]) + 1);
            } else {
                if (candidates.size() == kth - 1)
                    allDecreaseByOne(candidates);
                else
                    candidates.put(arr[i], 1);
            }
        }

        HashMap<Integer, Integer> qualifiers = getQualifiers(arr, candidates);
        for (Map.Entry<Integer, Integer> set : candidates.entrySet()) { // for (Map.Entry<Integer, Integer> set : qualifiers.entrySet())
            Integer key = set.getKey();
            if (qualifiers.get(key) > arr.length / kth)
                res.add(key);
        }
        return res;
    }

    private static void allDecreaseByOne(HashMap<Integer, Integer> map) {
        List<Integer> removeList = new LinkedList<>();
        for (Map.Entry<Integer, Integer> set : map.entrySet()) {
            Integer key = set.getKey();
            Integer value = set.getValue();
            if (value == 1)
                removeList.add(key);
            map.put(key, value - 1);
        }
        for (Integer removeKey : removeList) {
            map.remove(removeKey);
        }
    }

    private static HashMap<Integer, Integer> getQualifiers(int[] arr, HashMap<Integer, Integer> candidates) {
        HashMap<Integer, Integer> qualifiers = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int cur = arr[i];
            if (candidates.containsKey(cur))
                qualifiers.put(cur, qualifiers.containsKey(cur) ? qualifiers.get(cur) + 1 : 1);
        }
        return qualifiers;
    }

    private static int[] generateRandomArray(int length, int limit) {
        int[] arr = new int[(int)(Math.random() * length + 1)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * limit) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr;
        arr = new int[] {1, 3, 1, 4, 1};
        System.out.println(kthMajority(arr, 1));
        System.out.println(kthMajority(arr, 2));
        System.out.println(kthMajority(arr, 3));
        arr = new int[] {1, 3, 1, 4, 5, 2, 0};
        System.out.println(kthMajority(arr, 4));
        arr = new int[] {1, 3, 1, 4, 5, 2, 0, 5};
        System.out.println(kthMajority(arr, 4));
        System.out.println(kthMajority(arr, 5));
    }
}
