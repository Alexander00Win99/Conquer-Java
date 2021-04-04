package com.conquer_java.algorithm.exercise_interview.OD;

import java.util.Scanner;

public class DemoArea {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            int total = scanner.nextInt();
            int endX = scanner.nextInt();
            int x = 0, y = 0, area = 0; // 原点开始；x代表上次横坐标，y代表上次纵坐标
            for (int i = 0; i < total; i++) {
                int coordinateX = scanner.nextInt(); // 本次横坐标
                int offsetY = scanner.nextInt();
                int coordinateY = y + offsetY; // 本次纵坐标
                if (y != 0) { // 观察图形得出规律：本次坐标和上次坐标以及横轴构成的方格面积 = 横坐标差值 * 上次纵坐标绝对值
                    area += (coordinateX - x) * Math.abs(y);
                }
                // 本次横纵坐标迭代成为上次横纵坐标
                x = coordinateX;
                y = coordinateY;
            }
            // 如果最后一个坐标点的横坐标尚未到达最初设置的横坐标终点，需要额外计算其到终点的包络面积
            if (x < endX)
                area += (endX - x) * Math.abs(y);
            System.out.println(area);
        }
    }
}
