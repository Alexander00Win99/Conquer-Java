package com.conquer_java.algorithm.exercise_geekbang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * 【题目】
 * 总计n人参与抽奖，奖项设置为：一等奖n1人，二等奖n2人，三等奖n3人，请列出所有可能中奖情况
 * 1) 单人不能重复中奖；
 * 2) 单人可以重复中奖；
 * 3) 二等奖和三等奖可以重复中奖，一等奖只能中奖一次；
 *
 * 【场景一】——单人不能重复中奖：最为简单的场景，如下解法均可：
 * 1) n人之中抽取n1中一等奖；(n - n1)之中抽取n2人中二等奖；(n - n1 - n2)之中抽取n3人中三等奖；
 * 2) n人之中抽取n3中三等奖；(n - n3)之中抽取n2人中二等奖；(n - n3 - n2)之中抽取n1人中一等奖；
 * 3) n人之中抽取(n1 + n2 + n3)中一二三等奖（每人具体中奖情况待定），其中选取n1人中一等奖，选取n2人中二等奖，剩下自然中三等奖；
 * 注：
 * 显然，各种解法均能获得正确答案，虽然殊途同归，但是计算过程之中时空复杂度是不同的，现在第三种无论时间复杂度还是空间复杂度都是最低！
 */
public class Lesson08_Exercise_DrawLottery {
    public static final ArrayList<String> PERSONS = new ArrayList<>(Arrays.asList("p0", "p1", "p2", "p3", "p4", "p5", "p6", "p7"));
//    public static final ArrayList<String> PERSONS = new ArrayList<>(Arrays.asList("p0", "p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8", "p9"));
    public static final int COUNT_LOTTERY1 = 1;
    public static final int COUNT_LOTTERY2 = 2;
    public static final int COUNT_LOTTERY3 = 2;
    private static ArrayList<ArrayList<String>> set4Winners = new ArrayList<>();
    private static ArrayList<ArrayList<String>> set4RankedWinners = new ArrayList<>();
    private static Set<ArrayList<String>> lotteryWinners1;
    private static Set<ArrayList<String>> lotteryWinners2;
    private static Set<ArrayList<String>> lotteryWinners3;

    public static void combine(ArrayList<String> candidates, ArrayList<String> lotteryWinners, int winnerCount) {
        if (lotteryWinners.size() >= winnerCount) {
            System.out.println("当前全部中奖人员如下：" + lotteryWinners);
            set4Winners.add(lotteryWinners);
            ArrayList<String> originalCandidates = (ArrayList<String>) lotteryWinners.clone();
            stage1Combine(lotteryWinners, new ArrayList<String>(), COUNT_LOTTERY1, originalCandidates, new ArrayList<String>());
            return;
        }

        for (int i = 0; i < candidates.size(); i++) {
            ArrayList<String> newCandidates = new ArrayList<String>(candidates.subList(i + 1, candidates.size()));
            ArrayList<String> newLotteryWinners = (ArrayList<String>) lotteryWinners.clone();
            newLotteryWinners.add(candidates.get(i));
            combine(newCandidates, newLotteryWinners, winnerCount);
        }
    }

    public static void stage1Combine(ArrayList<String> currentCandidates, ArrayList<String> currentWinners, int stage1Count, ArrayList<String> stage1Candidates, ArrayList<String> finalWinners) {
        if (currentWinners.size() >= stage1Count) {
            ArrayList<String> nextCandidates = (ArrayList<String>) stage1Candidates.clone();
            // 排列：去除所有本轮中奖人员，进行下轮抽奖！
            for (String winners : currentWinners) {
                nextCandidates.remove(winners);
            }
            ArrayList<String> stage2Candidates = (ArrayList<String>) nextCandidates.clone();
            ArrayList<String> newFinalWinners = (ArrayList<String>) finalWinners.clone();
            newFinalWinners.addAll(currentWinners);
            stage2Combine(nextCandidates, new ArrayList<String>(), COUNT_LOTTERY2, stage2Candidates, newFinalWinners);
            return;
        }

        // 组合：本轮循环之中只取后续部分进行本轮的递归抽奖
        for (int i = 0; i < currentCandidates.size(); i++) {
            ArrayList<String> newCandidates = new ArrayList<String>(currentCandidates.subList(i + 1, currentCandidates.size()));
            ArrayList<String> newWinners = (ArrayList<String>) currentWinners.clone();
            newWinners.add(currentCandidates.get(i));
            stage1Combine(newCandidates, newWinners, stage1Count, stage1Candidates, finalWinners);
        }
    }

    public static void stage2Combine(ArrayList<String> currentCandidates, ArrayList<String> currentWinners, int stage2Count, ArrayList<String> stage2Candidates, ArrayList<String> finalWinners) {
        if (currentWinners.size() >= stage2Count) {
            ArrayList<String> nextCandidates = (ArrayList<String>) stage2Candidates.clone();
            // 排列：去除所有本轮中奖人员，进行下轮抽奖！
            for (String winners : currentWinners) {
                nextCandidates.remove(winners);
            }
            ArrayList<String> stage3Candidates = (ArrayList<String>) nextCandidates.clone();
            ArrayList<String> newFinalWinners = (ArrayList<String>) finalWinners.clone();
            newFinalWinners.addAll(currentWinners);
            stage3Combine(stage3Candidates, new ArrayList<String>(), COUNT_LOTTERY3, newFinalWinners);
            return;
        }

        // 组合：本轮循环之中只取后续部分进行本轮的递归抽奖
        for (int i = 0; i < currentCandidates.size(); i++) {
            ArrayList<String> newCandidates = new ArrayList<String>(currentCandidates.subList(i + 1, currentCandidates.size()));
            ArrayList<String> newWinners = (ArrayList<String>) currentWinners.clone();
            newWinners.add(currentCandidates.get(i));
            stage2Combine(newCandidates, newWinners, stage2Count, stage2Candidates, finalWinners);
        }
    }

    public static void stage3Combine(ArrayList<String> currentCandidates, ArrayList<String> currentWinners, int stage3Count, ArrayList<String> finalWinners) {
        // 最后一轮抽奖：所有legacyWinners依次作为每轮赢家一齐输出
        if (currentWinners.size() >= stage3Count) {
            ArrayList<String> newFinalWinners = (ArrayList<String>) finalWinners.clone();
            newFinalWinners.addAll(currentWinners);
            for (int i = 0; i < newFinalWinners.size(); i++) {
                if (i == 0) System.out.print("一等奖：");
                if (i == COUNT_LOTTERY1) System.out.print("二等奖：");
                if (i == COUNT_LOTTERY1 + COUNT_LOTTERY2) System.out.print("三等奖：");
                System.out.print(newFinalWinners.get(i) + "\t");
            }
            System.out.println();
            set4RankedWinners.add(newFinalWinners);
            return;
        }

        // 组合：本轮循环之中只取后续部分进行本轮的递归抽奖
        for (int i = 0; i < currentCandidates.size(); i++) {
            ArrayList<String> newCandidates = new ArrayList<String>(currentCandidates.subList(i + 1, currentCandidates.size()));
            ArrayList<String> newWinners = (ArrayList<String>) currentWinners.clone();
            newWinners.add(currentCandidates.get(i));
            stage3Combine(newCandidates, newWinners, stage3Count, finalWinners);
        }
    }

    public static void main(String[] args) {
        combine(PERSONS, new ArrayList<String>(), COUNT_LOTTERY1 + COUNT_LOTTERY2 + COUNT_LOTTERY3);
        System.out.println(set4Winners.size());
        System.out.println(set4RankedWinners);
        System.out.println(set4RankedWinners.size());
    }
}
