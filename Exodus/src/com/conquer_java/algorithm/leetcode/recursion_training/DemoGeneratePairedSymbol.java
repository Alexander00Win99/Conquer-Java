package com.conquer_java.algorithm.leetcode.recursion_training;

import java.util.*;

/**
 * LeetCode-22: 生成括号组合
 *  * 数字n代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且有效的括号组合。
 *  * 示例：
 *  *
 *  * 输入：n = 3
 *  * 输出：[
 *  *        "((()))",
 *  *        "(()())",
 *  *        "(())()",
 *  *        "()(())",
 *  *        "()()()"
 *  *       ]
 *  *
 *  * 【符号】
 *  * , comma 逗号
 *  * . period 句号
 *  * ; semicolon 分号
 *  * : colon 冒号
 *  * ! exclamation 叹号
 *  * ? question mark 问号
 *  * / slash 斜线
 *  * \ backslash 反斜线
 *  * - hyphen 连字符
 *  * ... apostrophe 省略号
 *  * — dash 破折号
 *  * '' single quotation marks 单引号
 *  * "" double quotation marks 双引号
 *  * <> angle brackets 尖括号
 *  * () parenthesis(parentheses) 圆括号
 *  * [] square brackets 方括号
 *  * {} braces 花括号
 * 【英语“成对”】
 * paired; symmetrical; geminate;
 * 【Java中Array、Set、List、Map之间相互转化】
 * 1) Array -> List(Arrays.asList()返回一个固定大小的列表。所以不能做add 、 remove等操作)
 *      String[] array = new String[]{"A", "B", "C"};
 *      List<String> list = Arrays.asList(array);
 *      list.add("D"); // UnsupportedOperationException
 *      list.remove(0); // UnsupportedOperationException
 *      // 如下方可：
 *      List<String> arrayList = new ArrayList<String>();
 *      for(String temp : array){
 *          arrayList.add(temp);
 *      }
 *      arrayList.add("D"); // ok
 *      arrayList.remove(0); // ok
 * 2) Array -> Set
 *      String[] array = new String[]{"A", "B", "C"};
 *      Set<String> set = new HashSet<>(Arrays.asList(array));
 *      set.add("D"); // ok
 *      set.remove("ABCD"); // ok
 * 3) List -> Array
 *      String[] array = new String[]{"A", "B", "C"};
 *      List<String> list = Arrays.asList(array);
 *      Object[] arr = list.toArray();
 * 4) List -> Set
 *      String[] array = new String[]{"A", "B", "C"};
 *      List<String> list = Arrays.asList(array);
 *      Set set = new HashSet(list);
 * 5) Set -> Array
 *      String[] array = new String[]{"A", "B", "C"};
 *      Set<String> set = new HashSet<>(Arrays.asList(array));
 *      Object[] arr = set.toArray();
 * 6) Set -> List
 *      String[] array = new String[]{"A", "B", "C"};
 *      Set<String> set = new HashSet<>(Arrays.asList(array));
 *      List<String> list = new ArrayList<>(set);
 */
public class DemoGeneratePairedSymbol {
    private static HashMap<Character, Character> map = new HashMap<>();

    static {
        map.put('<', '>');
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
    }

    private static Set<String> generateParenthesis(int n) {
        Set<String> resSet = new HashSet<>();
        // iterator用法 https://www.cnblogs.com/cfb513142804/p/5884412.html
        if (n == 0) resSet = null;
        if (n == 1) resSet.add("()");
        if (n > 1) {
            Iterator<String> iterator = generateParenthesis(n-1).iterator();
            while (iterator.hasNext()) {
                String origStr = iterator.next();
                String dstStr = "(" + origStr + ")";
                if (!resSet.contains(dstStr)) resSet.add(dstStr);
                dstStr = "()" + origStr;
                if (!resSet.contains(dstStr)) resSet.add(dstStr);
                dstStr = origStr + "()";
                if (!resSet.contains(dstStr)) resSet.add(dstStr);
            }
        }
        List<String> resList = new ArrayList<>(resSet);
        return resSet;
    }

    public static void main(String[] args) {
        Set<String> set = generateParenthesis(4);
        System.out.println(set);
    }
}
