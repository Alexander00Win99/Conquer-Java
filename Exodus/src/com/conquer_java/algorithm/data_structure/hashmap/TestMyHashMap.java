package com.conquer_java.algorithm.data_structure.hashmap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class TestMyHashMap {
    public static void main(String[] args) {
        /**
        Map<String, String> map = new HashMap<>();
        System.out.println(map.put("Alex Wen", "清秀帅气"));
        System.out.println(map.size());
        System.out.println(map.put("Alex 温", "风流倜傥"));
        System.out.println(map.size());
        System.out.println(map.put("Alexander00Win99", "英明神武"));
        System.out.println(map.size());
        System.out.println(map.put("Alexander Wen", "披荆斩棘"));
        System.out.println(map.size());
        System.out.println(map.put("Alexander 温", "无往不胜"));
        System.out.println(map.size());
        System.out.println(map);
        // 重复加入
        System.out.println(map.put("Alexander00Win99", "英明神武"));
        System.out.println(map.size());
        System.out.println(map.put("Alexander00Win99", "仪表堂堂"));
        System.out.println(map.size());
        System.out.println(map.put("Alexander00Win99", "天神下凡"));
        System.out.println(map.size());
        System.out.println(map);
         */

//        MyHashMap<String, String> map = new MyHashMap<>();
//        System.out.println(map.put("Alex Wen", "清秀帅气"));
//        map._debugCapacity();
//        System.out.println(map.size());
//        System.out.println(map.put("Alex 温", "风流倜傥"));
//        map._debugCapacity();
//        System.out.println(map.size());
//        System.out.println(map.put("Alexander00Win99", "英明神武"));
//        map._debugCapacity();
//        System.out.println(map.size());
//        System.out.println(map.put("Alexander Wen", "披荆斩棘"));
//        map._debugCapacity();
//        System.out.println(map.size());
//        System.out.println(map.put("Alexander 温", "无往不胜"));
//        map._debugCapacity();
//        System.out.println(map.size());
//        System.out.println(map);
//        // 重复加入
//        System.out.println(map.put("Alexander00Win99", "英明神武"));
//        map._debugCapacity();
//        System.out.println(map.size());
//        System.out.println(map.put("Alexander00Win99", "仪表堂堂"));
//        map._debugCapacity();
//        System.out.println(map.size());
//        System.out.println(map.put("Alexander00Win99", "天神下凡"));
//        map._debugCapacity();
//        System.out.println(map.size());
//        System.out.println(map);
//        Map<String, String> m = new HashMap<String, String>();
//        System.out.println(m.size());

        List<Integer> list1 = new ArrayList<>(8);
        List<Integer> list2 = new ArrayList<>(8);
        System.out.println("list1" + list1.size());
        System.out.println("list2" + list2.size());
        list1.add(1);
        System.out.println("list1" + list1.size());
        list1.add(2);
        System.out.println("list1" + list1.size());
        list1.add(3);
        System.out.println("list1" + list1.size());
        System.out.println(list1);
        list2.add(null);
        System.out.println("list2" + list2.size());
        list2.add(4);
        System.out.println("list2" + list2.size());
        System.out.println(list2);
        Integer i = null;
        System.out.println("i: " + i);
        list1.addAll(list2);
        System.out.println(list1);

        Stack<Integer> stack = new Stack<>();
        System.out.println(stack.size());
        System.out.println(stack.isEmpty());
    }
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int value;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int value) { this.value = value; }
 *     TreeNode(int value, TreeNode left, TreeNode right) {
 *         this.value = value;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

