package com.conquer_java.algorithm.exercise_leetcode.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 *
 * Example 1:
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 *
 * Example 2:
 * Input: n = 1
 * Output: ["()"]
 *
 *
 * Constraints:
 * 1 <= n <= 8
 */
public class Leetcode_0022_GenerateParentheses {
    private static List<String> list;

    public static List<String> generateParenthesis(int n) {
        list = new ArrayList<>();
        if (n <= 0) return null;

        dfs(new StringBuilder(), 0, 0, n);
        return list;
    }

    /**
     * 使用深度优先遍历构建合法的括号表达式
     * @param sb 用于保存当前字符串
     * @param left 当前字符串中的左括号个数
     * @param right 当前字符串中的右括号个数
     * @param pair 目标括号对数
     */
    private static void dfs(StringBuilder sb, int left, int right, int pair) {
        // StringBuilder字符串长度到达2n，既可返回
        if (sb.length() == 2 * pair) {
            //if (left == pair && right == pair)
                list.add(sb.toString());
            return;
        }
        // 左圆括号到达对数之前，先加'('
        if (left < pair) {
            sb.append('(');
            dfs(sb, left + 1, right, pair);
            sb.deleteCharAt(sb.length() - 1);
        }
        // 加完左圆括号，回溯删除再加右圆括号，如此规则保证生成字符串表达式符合题设要求
        if (right < left) {
            sb.append(')');
            dfs(sb, left, right + 1, pair);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        generateParenthesis(3);
        System.out.println(list);
        list.forEach(element -> System.out.println(element));
    }
}
