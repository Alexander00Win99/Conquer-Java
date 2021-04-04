package com.conquer_java.algorithm.exercise_interview.OD;

public class DemoReverseString {
    public static String reverseString(String origin) {
        int len = origin.length();
        StringBuilder target = new StringBuilder("");
        for (int i = len - 1; i >= 0; i--) {
            target.append(origin.charAt(i));
        }
        return target.toString();
    }

    public static void main(String[] args) {
        String demo = "I am a student!";
        System.out.println(reverseString(demo));
    }
}
