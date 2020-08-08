package com.conque_java.algorithm.MaShibing_ZuoChengyun;

import java.util.HashSet;

/**
 * 如果两个字符串，所含字符种类完全相同，即可算作一类。
 * 只由小写字母[a-z]组成的一组字符串，置于数组String[] arr之中，请返回其中一共有多少类？
 * 举例：abcdedcba和abcde同为一类，均含(a, b, c, d, e)
 * 抽象：提取字符串中出现的所有未重复字符，本质是种摘要算法。
 *
 * 【数据结构选择解析】
 *
 */
public class DemoGetStringVarieties {
    public static int getKinds(String[] arr) {
        if (arr == null || arr.length == 0) return 0;
        HashSet<String> set = new HashSet<>();

        for (String s : arr) {
            char[] chars = s.toCharArray();
            /**
             * 注意：此时无法使用Map/Set承装一个字符串中出现的所有字符。建议使用boolean[]记录，
             * 因为，Map无序，不同字符先后次序不同即可形成不同组合，实际却是同一种类；
             */
            //Map<Character, Boolean> map = new HashMap<>();
            // 映射某个字符串中总计出现哪些字符
            boolean[] alphbetMapTable = new boolean[26];
            for (char c : chars) {
                alphbetMapTable[c - 'a'] = true;
            }
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < alphbetMapTable.length; i++) {
                if (alphbetMapTable[i]) sb.append(String.valueOf((char)('a' + i)));
            }
            set.add(new String(sb));
        }
        return set.size();
    }

    public static int getVarieties(String[] arr) {
        if (arr == null || arr.length == 0) return 0;
        HashSet<Integer> set = new HashSet<>();

        for (String s : arr) {
            char[] chars = s.toCharArray();
            Integer bitmap = 0;
            for (int i = 0; i < chars.length; i++) {
                // bit0-'a', bit1-'b', bit2-'c', ...... ,bit25-'z'，如此bitmap代表出现哪个小写字母。
                // 如果给点字符集合是[a-z|A-Z]，那么可以使用long类型变量bitmap映射各个字母，64位空间足够2 * 26个字符使用。
                bitmap |= 1 << (chars[i] - 'a'); // |或位操作：计算效率极高；一次或者多次命中相同字母，操作结果相同。
            }
            set.add(bitmap);
        }
        return set.size();
    }

    public static void main(String[] args) {
        String[] arr = new String[] {"abcdedcba", "ace", "abcde", "bdf"};
        System.out.println(getKinds(arr));
        System.out.println(getVarieties(arr));
    }
}
