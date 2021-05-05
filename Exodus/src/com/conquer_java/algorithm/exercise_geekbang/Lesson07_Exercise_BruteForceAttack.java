package com.conquer_java.algorithm.exercise_geekbang;

import java.util.*;

/**
 * 【题目】
 * 假设存在一个4位字母密码，每个字符都是a-e之间的小写字母，请暴力求解所有可能的4位密码？
 */
public class Lesson07_Exercise_BruteForceAttack {
    private static final Set<Character> PASSWORD_CHARACTER_POOL = new HashSet<Character>() {
        {
            add('a');
            add('b');
            add('c');
            add('d');
            add('e');
        }
    };
    private static final int PASSWORD_LENGTH = 4;
    private static List<String> passwordDictionary = new ArrayList<>();

    public static void passwordCrack(StringBuilder password, Set<Character> chars, int length) {
        if (password.length() >= length) {
            passwordDictionary.add(password.toString());
            return;
        }

        Iterator<Character> iterator = PASSWORD_CHARACTER_POOL.iterator();
        while (iterator.hasNext()) {
            StringBuilder newPassword = new StringBuilder(password);
            newPassword.append(iterator.next());
            passwordCrack(newPassword, chars, length);
        }
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("");
        passwordCrack(sb, PASSWORD_CHARACTER_POOL, PASSWORD_LENGTH);
        System.out.println(passwordDictionary.size());
        System.out.println(passwordDictionary);
    }

    public void range(ArrayList<String> passwords, ArrayList<String> results) {
        if (passwords.isEmpty()) {
            String collect = String.join("", results);
            System.out.print(collect + "\t");
        }

        for (int i = 0; i < passwords.size(); i++) {
            String password = passwords.get(i);
            ArrayList<String> newPasswords = (ArrayList<String>) passwords.clone();
            newPasswords.remove(password);
            ArrayList<String> newResult = (ArrayList<String>) results.clone();
            newResult.add(password);
            range(newPasswords, newResult);
        }
    }
}
