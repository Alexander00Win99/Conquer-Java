package com.conquer_java.algorithm.exercise_interview.hw.OD;

import java.text.DecimalFormat;
import java.util.Scanner;

public class DemoCubeRoot {
    public static final double DELTA_THRESHOLD = 0.01;

    public static double getCubeRoot(double num) {
        if (num == 1.0 || num == -1.0 || num == 0.0) return num;

        double number = Math.abs(num);
        DecimalFormat df = new DecimalFormat("#.0");
        double min, max, mid = 0.0;
        double deltaThreshold = 0.01;
        if (number > 1) {
            min = 1.0;
            max = number;
        } else {
            min = number;
            max = 1.0;
        }
        while (min < max) {
            mid = min + (max - min) / 2;
            double cube = mid * mid * mid;
            double delta = Math.abs(cube - number);
            if (delta < DELTA_THRESHOLD) break;
            else {
                if (cube > number)
                    max = mid;
                else
                    min = mid;
            }
        }

        return num > 0 ? Double.valueOf(df.format(mid)) : (0 - Double.valueOf(df.format(mid)));
    }

    public static void main(String[] args) {
//        double num = -123.456;
//        System.out.println(getCubeRoot(num));
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextDouble()) {
            double num = sc.nextDouble();
            System.out.println(getCubeRoot(num));
        }
    }
}
