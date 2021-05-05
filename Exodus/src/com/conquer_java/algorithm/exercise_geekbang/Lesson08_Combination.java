package com.conquer_java.algorithm.exercise_geekbang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Lesson08_Combination {
    public static final ArrayList<String> TEAMS = new ArrayList<>(Arrays.asList("t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9"));
    private static Set<ArrayList<String>> result;

    private static void initResult() {
        result = new HashSet<>();
    }

    private static Set getResult() {
        return result;
    }

    private static void setResult(Set result0) {
        result = result0;
    }

    public static Set combineTeam(ArrayList<String> teams, int n) {
        if (teams.size() < n) throw new IllegalArgumentException("参数异常：要求选出组数大于实际拥有组数！");

        initResult();

        if (0 == n) return result;
        if (teams.size() == n) {
            result.add(teams);
            return result;
        }

        combine(teams, new ArrayList<String>(), n);
        return result;
    }

    /**
     * 【重点注意】
     * ！！！如果，使用“if (teamPool.size() < n) return;”作为递归出口，那么最后n支球队将永远无法取到！！！
     *
     * @param teamPool
     * @param chosenTeams
     * @param n
     */
    public static void combine(ArrayList<String> teamPool, ArrayList<String> chosenTeams, int n) {
        if (chosenTeams.size() >= n) {
            System.out.println("选出球队：" + chosenTeams);
            result.add(chosenTeams);
            return;
        }

        for (int i = 0; i < teamPool.size(); i++) {
            ArrayList<String> newTeamPool = new ArrayList<String>(teamPool.subList(i + 1, teamPool.size()));
            ArrayList<String> newChosenTeams = (ArrayList<String>) chosenTeams.clone();
            newChosenTeams.add(teamPool.get(i));
            combine(newTeamPool, newChosenTeams, n);
        }
    }

    public static void main(String[] args) {
        System.out.println(TEAMS);
        combineTeam(TEAMS, 3);
        System.out.println(result);
    }
}
