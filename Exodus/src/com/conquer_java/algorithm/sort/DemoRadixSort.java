package com.conquer_java.algorithm.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 【基数排序】
 * 基数排序（Radix Sort），又称桶排序（Bin Sort，或者Bucket Sort）或者分配排序（Distribution Sort），是一种借助多关键字排序实现对单关键字排序的方法，
 * 通过“分配”和“收集”（而非“比较”和“交换”）过程实现排序。例如，扑克牌可以通过按照“花色”和“牌值”双关键字实现排序。
 *
 * 通常，待排序记录R[i]的关键字R[i].key是由n位数字组成，即K^(n-1)K^(n-2)...K^0，每位数字表示关键字的一位，
 * 其中K^(n-1)是最高位，K^0是最低位，关键字中的每位的值都是位于0<=K^i<r范围之内，其中r成为基数（十进制对应是10）。
 *
 * 基数排序，顾名思义，它是透过键值的部份资讯，将要排序的元素分配至某些“桶”中，藉以达到排序的作用，基数排序法是属于稳定性的排序，
 * 其时间复杂度为O (nlog(r)m)，其中r为所采取的基数，而m为堆数，在某些时候，基数排序法的效率高于其它的稳定性排序法。
 *
 * 【基数排序的历史】
 * 基数排序的发明可以追溯到1887年赫尔曼·何乐礼在打孔卡片制表机(Tabulation Machine)上的贡献。它是这样实现的：
 * 将所有待比较数值（正整数）统一为同样的数位长度，数位较短的数前面补零。然后，从最低位开始，依次进行一次排序。
 * 这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。
 *
 * 【基数排序两种方法】
 * 最低位优先（LSD，Least Significant Digital，低位先排，高位后排）；最高位优先（MSD，Most Significant Digital，高位先排，低位后排）。
 * 最低位优先过程如下：
 * 首先，按照最低位值进行排序；
 * 然后，由低到高，依次基于前次排序顺序按照本次关键字进行排序，直至最高位终止。
 *
 * 【MSD的独特排序方法】
 * 非1桶内部递归，直至所有桶内数量为1。
 *
 * 【基数排序性能分析】
 * 关键字数量：n
 * 关键字最大位数：m，例如：对于1234这个关键字来说，m=4
 * 关键字的基：10（对于十进制整数来说，radix=10，对应10个桶bucket，分别是bucket0, bucker1, ... bucket9）
 * 1) 空间复杂度：
 *     O(r)（需要生成radix个bucket）
 * 2) 时间复杂度：
 *     每个关键字需要进行最大位数m次“分配”+“收集”工作，每次“分配”需要将n个关键字入列到radix个桶队列中，
 * 每次“收集”需要将radix个桶队列遍历一遍，所以，全部m次的“分配”+“收集”工作的时间复杂度是O(m*(n+r))
 * 3) 算法稳定性：
 *     “分配”，各个关键字按序入列到某个桶中，是稳定的，“收集”，按序遍历各个桶，依次从中出列所存关键字，是稳定的。
 *     由此可见：基数排序是稳定的。
 *
 * 【基数排序的缺点】
 * 由于把关键字拆分为多位，分别排序，那么拆分过程可能导致整体语义的“丢失”或者“失真”。例如：基数排序无法处理负数正数混在一起的情况。
 */
public class DemoRadixSort {
    private static int RADIX = 10; // 对于每个关键字(数组元素)来说，基是10

    public static void lsdRadixSort(int[] arr) {
        if (arr == null || arr.length == 0) return;

        long begin = System.currentTimeMillis();
        int[] intermediate = new int[arr.length]; // 生成数组用于存储基数排序各个中间阶段的排序结果
        int max = arr[0];
        for (int i = 1; i < arr.length; i++)
            max = Math.max(max, arr[i]);
        int base = 1; // 起始是最低位个位，依次乘十，直至最高位为止
        while (max / base > 0) {
            int[] buckets = new int[RADIX];
            for (int bucket : buckets) {
                bucket = 0;
            }
            for (int i = 0; i < arr.length; i++) { // Step-01：“分配” —— 对于所有关键字(n个)，依次入桶(radix个)
                int index = Math.abs(arr[i] / base) % RADIX;
                buckets[index] = buckets[index] + 1; // bucket[i]不是存储具体哪些关键字，而是存储应放此处的所有关键字个数总和，大于1代表此处重叠
            }
            for (int i = 1; i < buckets.length; i++) { // Step-02：“收集” —— 遍历所有桶，如有数据，依次出桶
                buckets[i] += buckets[i - 1];
            }
            /**
             * 【算法难点】
             * 此处必须按照arr[i]原始数组倒序遍历：因为 buckets[i]位置 可能存放 多个多个多个 关键字，累加处理之后，
             * buckets[i]其值代表从左到右已经完成阶段排序任务的关键字个数（==包含所有位置重叠关键字所能到达最右下标位置+1），
             * 因此，按照arr[i]元素数组倒序遍历，可以保证最先遇到多个位置重叠的关键字中的最后一个，这个关键字在阶段有序数组中的位置，
             * 恰恰就是bucket[i]值-1。当然，为了后续在遇到其重叠位置其他关键字时计算正确，此处必须buckets[i]--，
             * 代表每遇到一个重叠关键字，使其从右往左移动一位存储。
             * 该种算法，可以巧妙避免数组重叠位置必须使用链表这种复杂数据结构才能完全保存所有重叠关键字信息的额外开销。
             * 那样就会演变成为HashMap数据结构，得不偿失。
             */
            for (int i = intermediate.length - 1; i >= 0; i--) { // Step-02：“收集” —— 遍历所有桶，如有数据，依次出桶
                intermediate[buckets[Math.abs(arr[i] / base) % RADIX] - 1] = arr[i];
                buckets[Math.abs(arr[i] / base) % RADIX]--;
            }
            for (int i = 0; i < arr.length; i++) {
                arr[i] = intermediate[i];
            }
            base *= RADIX;
        }
        long end = System.currentTimeMillis();
        System.out.println("本次基数排序占时：" + (end - begin));
    }

    public static void msdRadixSort(int[] arr) {}

    public static int[] genRandArr(int n, int min, int max) { // 生成[min, max]范围随机数n个
        int[] arr = new int[n];
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(max - min + 1) + min;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = null;

        arr = genRandArr(10000, -1000000, +1000000);
        System.out.println(Arrays.toString(arr));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        lsdRadixSort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        arr = genRandArr(10000, +1000000, +2000000);
        System.out.println(Arrays.toString(arr));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        lsdRadixSort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
