package com.conquer_java.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1) 参数：红包总额=100；红包数量=10；
 * 2) 需求：尽量平均但有差异；
 * 3) 限制：最小额度：1分；最大额度：200元；平均额度：averMoney = totalPacket / count；差异倍数times=最大额度maxPacket/平均额度avgPacket
 */
public class DemoSendRedEnvelopes {
    public static final int MIN_PACKET = 1;
    public static final int MAX_PACKET = 200 * 100;
    public static final int TIMES = 2;

    public static List<Integer> sendRedEnvelopes(int money, int count) {
        if (!checkValidity(money, count)) return null;

        List<Integer> list =  new ArrayList<>(count);
        int max = money / count * TIMES;
        for (int i = 0; i < count; i++) {
            // 考虑场景(max > money)，需要更新max
            max = Math.min(money, max);
            // 最小数额各次循环保持不变(1分钱)，最大数额各次循环重新计算
            int randomPacket = generateRandomPacket(money, count - i, MIN_PACKET, max);
            list.add(randomPacket);
            money = money - randomPacket;
        }
        return list;
    }

    public static int generateRandomPacket(int money, int count, int min, int max) {
        // 递归出口如下
        if (count == 1) return money;
        if (min == max) return min;

        double random = min + (max - min) * Math.random();
        // Math.rint()参数double，返回值double，返回距离较近的整数，距离两端相同则返回偶数。极端情况如下：
        // 1) 参数本身是整数，返回本身；
        // 2) 参数本身是正负零，返回本身((-)0.0)；
        // 3) 参数本身是正负无穷大，返回本身((-)Infinity)；
        int randomPacket = (int) Math.rint(random);
        int remainedMoney = money - randomPacket;
        int remainedCount = --count;
        // 此次发放以后剩余红包依旧满足要求，则直接返回此次红包数额
        if (checkValidity(remainedMoney, remainedCount))
            return randomPacket;
        // 此次发放以后剩余红包依旧满足要求，则重新调整此次红包数额
        double avgRemained = remainedMoney / remainedCount;

        if (avgRemained < MIN_PACKET) { // 之前红包过大——5个红包4分钱，需要重新计算——适当减小max
            return generateRandomPacket(money, count, min, randomPacket);
        } else if (avgRemained > MAX_PACKET) { // 之前红包过小——5个红包1001元钱，需要重新计算——适当增大min
            return generateRandomPacket(money, count, randomPacket, max);
        }

        return randomPacket;
    }

    public static boolean checkValidity(int money, int count) {
        double avgPacket = money / count;
        if (avgPacket < MIN_PACKET || avgPacket > MAX_PACKET) return false;
        return true;
    }

    public static void main(String[] args) {
        List<Integer> list = sendRedEnvelopes(20000, 10);
        System.out.println(Arrays.toString(list.toArray()));

        int sum = 0;
        for (Integer integer : list) {
            sum += integer;
        }
        System.out.println(sum);
    }
}
