package com.conquer_java.algorithm.MaShibing_ZuoChengyun;

/**
 * 给定一个只由小写字母组成的字符串str，返回其中最长无重复字符的子串长度。
 */
public class DemoMaxLength4SubString {
    /**
     * 对于每个字符，均需新建一个bitmap映射图，空间复杂度O(n)，双层循环O(n^2)
     * @param s
     * @return
     */
    public static int maxSubstringLength(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] chars = s.toCharArray();
        int len = s.length();
        int max = 0;
        int lastMaxSubtringIndex = -1;
        for (int i = 0; i < len; i++) {
            boolean[] alphabetMap = new boolean[26];
            for (int j = i; j < len; j++) {
                char aChar = chars[j];
                if (alphabetMap[chars[j] - 'a']) break;
                alphabetMap[chars[j] - 'a'] = true;
                max = Math.max(max, j - i + 1);
                if ((j - i + 1) >= max) lastMaxSubtringIndex = i;
            }
        }
        System.out.println("字符串中最长无重复子串长度为：" + max + "起始位置位于：" + lastMaxSubtringIndex);
        return max;
    }

    /**
     * 【动态规划思想抽象】
     * 寻找字符串中最长无重复子串长度 <=动态规划=> 1) 针对每个字符，求出以其结尾的最大无重复子串；2) 对于所有结果，求其最大值；
     * 【动态规划思想解析】
     * ----++++对于dp[i]，仅仅依赖dp[i-1]+++++----
     * 假设chars[i]位置字符为x，上次出现位置为pos，chars[i-1]位置字符的最长子串区间是[i - dp[i-1] - 2, i - 1]；
     * 对于chars[i]字符，其最长子串所能到达的最左位置，只能是(pos + 1)位置和dp[i-1]左端(i -  dp[i-1] - 2)位置两者之中近者；
     * 【动态规划优化思路】
     * 正常需要针对字符串新建一个数组dp[len](空间复杂度是O(n))，对于字符串中每个字符记录其能够向左推进包纳的最大无重复子串长度
     * @param s
     * @return
     */
    public static int maxSubstrLenByDP(String s) {
        if (s == null || s.length() == 0) return 0;
        int max = 0;

        // 根据ASCII码中26个小写字母顺序，记录字符串中的每个字符上次出现的位置。
        int[] lastPosition = new int[26];
        for (int i = 0; i < lastPosition.length; i++) {
            lastPosition[i] = -1;
        }

        char[] chars = s.toCharArray();
        int len = s.length();

        /**
         * 正统的动态规划：使用一个数组记录每个字符的最大子串长度
         */
//        // maxLengthDP以字符串中的每个字符结尾的子串的最大长度
//        int[] maxLengthDP = new int[len];
//        // 初始条件：首字符的最长无重复子串长度为1
//        maxLengthDP[0] = 1;
//        // 初始条件：首字符的上次出现位置为0
//        lastPosition[chars[0] - 'a'] = 0;
//        for (int i = 1; i < len; i++) {
//            // 如果当前字符chars[i]上次出现位置位于以上一字符char[i-1]为结尾的最长无重复子串左边，那么以当前字符为结尾的的最长无重复子串大小相对前者增一
//            if (lastPosition[chars[i] - 'a'] < i - maxLengthDP[i-1])
//                maxLengthDP[i] = maxLengthDP[i-1] + 1;
//            // 否则，以当前字符为结尾的的最长无重复子串大小为当前字符位置减去上次出现位置之差
//            else maxLengthDP[i] = i - lastPosition[chars[i] - 'a'];
//            // 更新位置数组
//            lastPosition[chars[i] - 'a'] = i;
//            max = Math.max(max, maxLengthDP[i]);
//        }
//        return max;

        /**
         * 优化思路：
         * 动态规划的每次迭代，都是maxLengthDP[i-1] -> maxLengthDP[i]，当前值只是唯一依赖前一值，并不依赖更加往前的值；
         * DP数组无需存在，完全可以使用一个变量不断迭代，进而可以等价实现maxLengthDP[i-1] -> maxLengthDP[i]的工作效果；
         */
        lastPosition[chars[0] - 'a'] = 0;
        int curMaxLen = 1;
        for (int i = 1; i < len; i++) {
            curMaxLen = Math.min(curMaxLen + 1, i - lastPosition[chars[i] - 'a']);
            lastPosition[chars[i] - 'a'] = i;
            max = Math.max(max, curMaxLen);
        }
        return max;
    }

    public static void main(String[] args) {
        String s = "dfahfhtrnretervvartpjnpba";
        System.out.println(maxSubstringLength(s));
        System.out.println(maxSubstrLenByDP(s));

        String str = "beabcdafgf";
        System.out.println(maxSubstringLength(str));
        System.out.println(maxSubstrLenByDP(str));
    }
}
