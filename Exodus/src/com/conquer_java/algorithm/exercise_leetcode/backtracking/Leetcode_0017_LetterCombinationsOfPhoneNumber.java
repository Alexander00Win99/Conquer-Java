package com.conquer_java.algorithm.exercise_leetcode.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 【！！！注意！！！】
 * 千万不能使用属性作为接收结果的容器，建议使用方法内部局部变量承载结果，否则多次调用以后，结果累积导致错误。如果
 *
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 *
 * Example 1:
 * Input: digits = "23"
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * Example 2:
 * Input: digits = ""
 * Output: []
 *
 * Example 3:
 * Input: digits = "2"
 * Output: ["a","b","c"]
 *
 *
 * Constraints:
 * 0 <= digits.length <= 4
 * digits[i] is a digit in the range ['2', '9'].
 */
public class Leetcode_0017_LetterCombinationsOfPhoneNumber {
    private static char[][] map = new char[][]{{'a','b','c'},{'d','e','f'},{'g','h','i'},
            {'j','k','l'},{'m','n','o'},{'p','q','r','s'},{'t','u','v'},{'w','x','y','z'}
    };

    public static List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        if (digits == null || digits.length() == 0) return list;

        char[] chars = digits.toCharArray();
        dfs(new StringBuilder(""), list, chars, 0);
        return list;
    }

    private static void dfs(StringBuilder sb, List<String> list, char[] chars, int index) {
        if (index >= chars.length) {
            list.add(sb.toString());
            return;
        }
        // 获取当前数字对应的字母集合map二维数组中的某个元素——一维数组
        for (char c : map[chars[index] - '2']) {
            sb.append(c);
            dfs(sb, list, chars, index + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        String digits;
        List<String> list = new ArrayList<>();
        digits = "2";
        list = letterCombinations(digits);
        System.out.println(list);
        System.out.println(list.size());
        digits = "48";
        list = letterCombinations(digits);
        System.out.println(list);
        System.out.println(list.size());
        digits = "369";
        list = letterCombinations(digits);
        System.out.println(list);
        System.out.println(list.size());
    }
}
