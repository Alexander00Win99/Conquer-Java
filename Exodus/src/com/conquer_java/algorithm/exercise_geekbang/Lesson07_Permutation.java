package com.conquer_java.algorithm.exercise_geekbang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 排列(Permutation)：从n个不同的元素中选出m(1<=m<=n)个不同的元素按照一定的顺序排成一列。
 * 全排列(All Permutation)：m==n
 * 可重复排列(Permutation with Repetition) VS 不重复排列(Permutation without Repetition)：选出的m个元素是否可以重复
 *
 * 案例：
 * 1) 田忌赛马；
 * 2) 字符串全排列；
 */
public class Lesson07_Permutation {
    public static HashMap<String, Double> qiwangHorseTime = new HashMap<String, Double>() {{put("H1", 1.0);put("H2", 2.0);put("H3",3.0);}};
    public static HashMap<String, Double> tianjiHorseTime = new HashMap<>();
    public static ArrayList<String> qiwangHorses = new ArrayList<>(Arrays.asList("H1", "H2", "H3"));
    public static ArrayList<String> tianjiHorses = new ArrayList<>();

    static {
        qiwangHorseTime.put("H1", 1.0);
        qiwangHorseTime.put("H2", 2.0);
        qiwangHorseTime.put("H3", 3.0);

        tianjiHorseTime.put("h1", 1.5);
        tianjiHorseTime.put("h2", 2.5);
        tianjiHorseTime.put("h3", 3.5);
    }

    public static void permutate(ArrayList<String> restHorses, ArrayList<String> racedHorses) {
        if (restHorses.size() == 0) {
            System.out.println(racedHorses);
            compare(racedHorses, qiwangHorses);
            System.out.println("这场比赛结束！");
            System.out.println();
            return;
        }

        for (int i = 0; i < restHorses.size(); i++) { // 依次派出一匹赛马参赛
            ArrayList<String> newRestHorses = (ArrayList<String>) restHorses.clone();
            ArrayList<String> newRacedHorses = (ArrayList<String>) racedHorses.clone();

            // 未赛赛马减一
            newRestHorses.remove(restHorses.get(i));
            // 参赛赛马加一
            newRacedHorses.add(restHorses.get(i));
            // 递归
            permutate(newRestHorses, newRacedHorses);
        }
    }

    public static void compare(ArrayList<String> candidates, ArrayList<String> targets) {
        if (candidates.size() == 0 || candidates.size() != targets.size()) {
            System.out.println("数据不规范，无法比较！");
            return;
        }

        int winTimes = 0;
        for (int i = 0; i < candidates.size(); i++) {
            String candidate = candidates.get(i);
            String target = targets.get(i);
            System.out.println(candidate + " VS " + target + ": " + tianjiHorseTime.get(candidate) + " - " + qiwangHorseTime.get(target));

            if (tianjiHorseTime.get(candidate) < qiwangHorseTime.get(target)) winTimes++;
        }
        if (winTimes > candidates.size() / 2) System.out.println("候选人挑战成功！");
    }

    public static void main(String[] args) {
        tianjiHorses = new ArrayList<>(Arrays.asList("h1", "h2", "h3"));
        permutate(tianjiHorses, new ArrayList<>());
    }
}
