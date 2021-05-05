package com.conquer_java.algorithm.exercise_interview.hw;

public class HW_Palindrome {
    private static int maxPalindromeLength = -1;

    public static int getPalindromeLength(String str) {
        if (str == null) return 0;
        if (str.length() == 0 || str.length() == 1) return str.length();
        if (str.length() == 2) return str.charAt(0) != str.charAt(1) ? 1 : 2;

        char[] chars = str.toCharArray();
        for (int i = 1; i < chars.length - 1; i++) { // 奇数回文串，例如：“xyx”
            oddPalindrome(chars, i, i - 1, i + 1);
        }
        for (int i = 1; i < chars.length - 1; i++) { // 偶数回文串，例如：“xyyx”
            if (chars[i] == chars[i + 1]) evenPalindrome(chars, i, i + 1);
        }
        return maxPalindromeLength;
    }

    public static void oddPalindrome(char[] chars, int centerIndex, int left, int right) {
        if (left < 0 || right > chars.length - 1) {
            int len = right - left - 1;
            if (maxPalindromeLength < len) maxPalindromeLength = len;
            return;
        }
        if (chars[left] == chars[right]) {
            oddPalindrome(chars, centerIndex, left - 1, right + 1);
        } else {
            int len = right - left - 1;
            if (maxPalindromeLength < len) maxPalindromeLength = len;
            return;
        }
    }

    public static void evenPalindrome(char[] chars, int left, int right) {
        if (left < 0 || right > chars.length - 1) {
            int len = right - left - 1;
            if (maxPalindromeLength < len) maxPalindromeLength = len;
            return;
        }
        if (chars[left] == chars[right]) {
            evenPalindrome(chars, left - 1, right + 1);
        } else {
            int len = right - left + 1;
            if (maxPalindromeLength < len) maxPalindromeLength = len;
            return;
        }
    }

    public static void main(String[] args) {
        String str;
        str = "x";
        System.out.println(getPalindromeLength(str));
        str = "xyyx";
        System.out.println(getPalindromeLength(str));
        str = "xyxyx";
        System.out.println(getPalindromeLength(str));
        str = "abcbxyxyxabcb";
        System.out.println(getPalindromeLength(str));
    }
}
