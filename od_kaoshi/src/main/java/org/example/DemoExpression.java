package org.example;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class DemoExpression {
    public static void getSequentNumber(int target) {
        System.out.println(target + "=" + target);
        int count = 1;
        int mid = target / 2;
        int base;
        StringBuilder sb;
        for (base = 1; base <= mid; base++) {
            sb = new StringBuilder("");
            sb.append(base);
            int sum = base;
            for (int i = base + 1; i <= target; i++) {
                sb.append("+").append(i);
                sum += i;
                if (sum == target) {
                    count++;
                    System.out.print(target + "=");
                    System.out.println(sb.toString());
                    break;
                } else if (sum > target) {
                    break;
                }
            }
        }
        System.out.println("Result:" + count);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int target = sc.nextInt();
            getSequentNumber(target);
        }
    }
}
