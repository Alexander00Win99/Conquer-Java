package com.conquer_java.algorithm.exercise_interview;

import java.util.Scanner;

public class HW_DisassembleConsecutiveNumber {
    public static void disassembleConsecutiveNumber(int target) {
        System.out.println(target + "=" + target);
        int count = 1;
        int mid = target / 2;
        int start;
        StringBuilder sb;
        for (start = 1; start <= mid; start++) {
            sb = new StringBuilder("");
            sb.append(start);
            int sum = start;
            for (int i = start + 1; i < target; i++) {
                sb.append("+").append(i);
                sum += i;
                if (sum == target) {
                    count++;
                    System.out.println(target + "=" + sb.toString());
                    break;
                } else if (sum > target) {
                    break;
                }
            }
        }
        System.out.println("Result:" + count);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int target = scanner.nextInt();
            disassembleConsecutiveNumber(target);
        }
    }
}
