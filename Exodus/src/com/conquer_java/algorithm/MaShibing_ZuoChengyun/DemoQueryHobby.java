package com.conquer_java.algorithm.MaShibing_ZuoChengyun;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 【今日头条面试题目】
 * 给定数组[3, 2, 2, 3, 1]，查询(0, 3, 2)，代表在数组下标0-3范围上，查询出现几个2？返回2。
 * 假设给定一个数组array，对于该个数组的查询非常频繁，请返回所有查询的结果。
 *
 * 【图示解题思路】
 * 数组：[3, 1, 2, 2, 3, 1, 3, 2, 1]
 * Map：3 <--> [0, 4, 6]; 1 <--> [1, 5, 8]; 2 <--> [2, 3, 7];
 */
public class DemoQueryHobby {
    private static int[] arr;
    private static HashMap<Integer, ArrayList<Integer>> map;

    public int[] genQueryArray(int[] array) {
        int[] res = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i];
        }
        return res;
    }

    public HashMap<Integer, ArrayList<Integer>> genQueryMap(int[] array) {
        HashMap<Integer, ArrayList<Integer>> res = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (!res.containsKey(array[i]))
                res.put(array[i], new ArrayList<>());
            res.get(array[i]).add(i);
        }
        return res;
    }

    public DemoQueryHobby(int[] array) { // 构造函数生成预置数据
        arr = genQueryArray(array);
        map = genQueryMap(array);
    }

    public int query1(int l, int r, int v) { // 暴力查询，时间复杂度O(n)
        int ans = 0;
        for (; l <= r; l++)
            if (arr[l] == v)
                ans++;
//        while (l <= r) {
//            if (arr[l] == v)
//                ans++;
//            l++;
//        }
        return ans;
    }

    public int query2(int l, int r, int v) {
        if (!map.containsKey(v) || l > r)
            return 0;
        ArrayList<Integer> indexArray = map.get(v);
        return lessThan(indexArray,r + 1) - lessThan(indexArray, l);
    }

    /**
     * 采用“二分法”高效查找在一个有序列表中有多少个数小于指定值limit
     * @param arr
     * @param limit
     * @return
     */
    private int lessThan(ArrayList<Integer> arr, int limit) {
        int left = 0;
        int right = arr.size() - 1;
        int rightest = -1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if (arr.get(middle) < limit) {
                rightest = middle;
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return rightest + 1;
    }

    public static void main(String[] args) {
        int[] array = new int[] {3, 1, 2, 2, 3, 1, 3, 2, 1};
        DemoQueryHobby demoQueryHobby = new DemoQueryHobby(array);
        System.out.println(demoQueryHobby.query1(2, 4, 2));
        System.out.println(demoQueryHobby.query2(2, 6, 3));
    }
}
