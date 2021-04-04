package com.conquer_java.algorithm.exercise_leetcode.stack;

import java.util.*;

/**
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 *
 * Example 1:
 * Input: s = "()"
 * Output: true
 *
 * Example 2:
 * Input: s = "()[]{}"
 * Output: true
 *
 * Example 3:
 * Input: s = "(]"
 * Output: false
 *
 * Example 4:
 * Input: s = "([)]"
 * Output: false
 *
 * Example 5:
 * Input: s = "{[]}"
 * Output: true
 *
 *
 * Constraints:
 * 1 <= s.length <= 104
 * s consists of parentheses only '()[]{}'.
 */
public class Leetcode_0020_ValidParentheses {
    private final static char LEFT_PARENTHESIS = '('; // 小括号|圆括号
    private final static char RIGHT_PARENTHESIS = ')';
    private final static char LEFT_BRACKET = '['; // 中括号|方括号
    private final static char RIGHT_BRACKET = ']';
    private final static char LEFT_BRACE = '{'; // 大括号|花括号
    private final static char RIGHT_BRACE = '}';
    private final static char OVERBAR = '_'; // 上划线
    private final static char HYPHEN = '-'; // 中划线|减号|负号|连字符
    private final static char UNDERSCORE = '_'; // 下划线
    private final static char DASH = '—'; // 破折号（比中划线长）
    private final static char COMMA = ',';
    private final static char SEMICOLON = ';';
    private final static char SINGLE_QUOTE = '\'';
    private final static char DOUBLE_QUOTE = '\"';
    private final static char EXCLAMATION = '!';

    private static int countParenthesis = 0;
    private static int countBracket = 0;
    private static int countBrace = 0;

    private static Map<Character, Character> map;
    private static List<Character> list;

    static {
        map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        list = new ArrayList<>();
    }

    public static boolean isValid_Stack(String s) {
        if (s == null || s.length() == 0) return true;

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // If the current character is a closing bracket, get the top element of the stack.
            // If the stack is empty, set a dummy value of '#' to be the result of pop stack.
            if (map.containsKey(c)) {
                char top = stack.empty() ? '#' : stack.pop();
                // If the mapping for this bracket doesn't match the stack's top element, return false.
                if (top != map.get(c)) return false;
            }
            // If the current character is a opening bracket, push it into the stack.
            else {
                stack.push(c);
            }
        }

        return stack.empty();
    }

    public static boolean isValid_List(String s) {
        if (s == null || s.length() == 0) return true;

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '(' || c == '[' || c == '{') list.add(c);
            else if (c == ')') {
                if (list.size() != 0 && list.get(list.size() - 1) == '(') list.remove(list.size() - 1);
                else return false;
            }
            else if (c == ']') {
                if (list.size() != 0 && list.get(list.size() - 1) == '[') list.remove(list.size() - 1);
                else return false;
            }
            else if (c == '}') {
                if (list.size() != 0 && list.get(list.size() - 1) == '{') list.remove(list.size() - 1);
                else return false;
            }
            else return false;
        }

        return list.size() == 0;
    }

    public static boolean validateParentheses(String s) {
        return validateParentheses(new StringBuilder(s));
    }

    public static boolean validateParentheses(StringBuilder sb) {
        if (sb == null || sb.length() == 0) return true;

        for (int i = 0; i < sb.length() - 1; i++) {
            if (sb.charAt(i) == '(' && sb.charAt(i + 1) == ')') {
                return validateParentheses(sb.delete(i, i + 2));
            }
            if (sb.charAt(i) == '[' && sb.charAt(i + 1) == ']') {
                return validateParentheses(sb.delete(i, i + 2));
            }
            if (sb.charAt(i) == '{' && sb.charAt(i + 1) == '}') {
                return validateParentheses(sb.delete(i, i + 2));
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String s;
        s = "{[()]}";
        System.out.println(isValid_Stack(s));
        s = "{([)]}";
        System.out.println(isValid_List(s));
        s = "";
        System.out.println(validateParentheses(s));
    }
}
