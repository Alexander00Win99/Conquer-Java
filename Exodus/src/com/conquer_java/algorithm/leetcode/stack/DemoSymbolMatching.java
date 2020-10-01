package com.conquer_java.algorithm.leetcode.stack;

import java.util.*;

/**
 * LeetCode-20: 判断有效括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 * 1) 左括号必须用相同类型的右括号闭合；
 * 2) 左右括号必须以正确的顺序闭合。
 * 注意：空字符串可被认为是有效字符串
 *
 * 【为何使用栈】
 * 最后出现的左括号需要一一匹配最先出现右括号
 *
 * LeetCode-22: 生成括号组合
 * 数字n代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且有效的括号组合。
 * 示例：
 *
 * 输入：n = 3
 * 输出：[
 *        "((()))",
 *        "(()())",
 *        "(())()",
 *        "()(())",
 *        "()()()"
 *       ]
 *
 * 【符号】
 * , comma 逗号
 * . period 句号
 * ; semicolon 分号
 * : colon 冒号
 * ! exclamation 叹号
 * ? question mark 问号
 * / slash 斜线
 * \ backslash 反斜线
 * - hyphen 连字符
 * ... apostrophe 省略号
 * — dash 破折号
 * '' single quotation marks 单引号
 * "" double quotation marks 双引号
 * <> angle brackets 尖括号
 * () parenthesis(parentheses) 圆括号
 * [] square brackets 方括号
 * {} braces 花括号
 */
public class DemoSymbolMatching {
    private static HashMap<Character, Character> map = new HashMap<>();
    private static HashMap<Character, Character> backmap= new HashMap<>();

    static {
        map.put('<', '>');
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        backmap.put('>', '<');
        backmap.put(')', '(');
        backmap.put(']', '[');
        backmap.put('}', '{');
    }

    public static boolean checkValidity(String s) {
        Stack<Character> stack = new Stack<>();

        if (s == null || s.length() == 0) return true;
        if (s.length() % 2 == 1) return false;

        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (backmap.containsKey(c)) { // 当前字符是右括号
                if (stack.empty()) return false;
                if (stack.pop() == backmap.get(c)) continue;
                return false;
            } else { // 当前字符是左括号
                stack.push(c);
            }
        }

        return stack.empty();
    }

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        if (s == null || s.isEmpty()) return true;
        if (s.length() % 2 == 1) return false;

        for (char c : s.toCharArray()) {
            switch (c) {
                case '(':
                    stack.push(')');
                    break;
                case '[':
                    stack.push(']');
                    break;
                case '{':
                    stack.push('}');
                    break;
                default:
                    if (stack.empty() || c != stack.pop()) return false;
                    continue;
            }
        }

        return stack.empty();
    }

    public static List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        List<String> list = new LinkedList<>();
        Set set = new HashSet();
        if (n == 0) return null;
        if (n == 1) {
            res.add("()");
            return res;
        }
        for (int i = 2; i <= n; i++) {

        }
        return null;
    }

//    public static Set<String> generateParenthesis(int n) {
//        return null;
//    }

    public static void main(String[] args) {
        System.out.println("++++++++++++++++Begin leetcode-20 判断有效括号++++++++++++++++");
        String s = "{[()]}[()]()";
        System.out.println(checkValidity(s));
        System.out.println(isValid(s));
        System.out.println("----------------End leetcode-20 判断有效括号----------------");

        System.out.println("++++++++++++++++Begin leetcode-22 生成括号组合++++++++++++++++");

        System.out.println("----------------End leetcode-22 生成括号组合----------------");
    }
}
