package com.conquer_java.knowledge.time;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * 【目标】：掌握日期类及其常用方法
 *
 * 【结果】：完成
 *
 * 【历史】
 * 古巴比伦发明了万年历，世界通行的星期制是罗马皇帝君士坦丁大帝在公元321年3月7日正式确立的。
 * 古罗马人：Sun\'s-day（太阳神日），Moon\'s-day（月亮神日），Mars\'s-day（火星神日），Mercury\'s-day（水星神日），Jupiter\'s-day（木星神日），Venus\'-day（金星神日），Saturn\'s-day（土星神日）。
 * 英国盎格鲁-撒克逊人：Sunday, Monday, Tuesday(战神), Wednesday(Woden主神), Thursday(Thor雷神), Friday(Frigg爱神)
 * Sunday（太阳神日）
 * Monday（月亮神日）
 * Tuesday（战神日）
 * Wednesday（主神日）
 * Thursday（雷神日）
 * Friday（爱神日）
 * Saturday（土神日）。
 *
 * 【功能描述】
 * 接收输入某年某月，输出当月每天都是星期几
 *
 * 【计算方法】
 * 1900-01-01 星期一
 * 1) 计算上一年度距离起始纪元多少天数？
 * 2) 计算本年已过多少天数？
 * 3) 计算当月首日是星期几？
 * 4) 取模计算当日是星期几？
 */
public class DemoPerpetualCalendar {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("输入年月(例如：2020-01): ");

        String str = input.nextLine();
        String[] arr  = str.split("-");
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);

        System.out.println("++++++++++++++++Before Java8++++++++++++++++");
        printCalendar(year, month);
        System.out.println("----------------Before Java8----------------");

        System.out.println("++++++++++++++++After Java8++++++++++++++++");
        LocalDate localDate = LocalDate.of(year, month, 1);
        printPermanentCalendar(localDate);
        System.out.println("----------------After Java8----------------");
    }

    public static boolean isLeap(int year) { // 判断是否闰年
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }

    public static int getYearDays(int year) {
        return isLeap(year) ? 366 : 365;
    }

    public static int getYearDaysSum(int year) {
        int sum = 0;
        for (int i = 1900; i < year; i++)
            sum += getYearDays(i);
        return sum;
    }

    public static int getMonthDays(int year, int month) {
        int sum = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return isLeap(year) ? 29 : 28;
            default:
                System.out.println("Single month");
                return 30;
        }
    }

    public static int getMonthDaysSum(int year, int month) {
        int sum = 0;
        for (int i = 1; i < month; i++)
             sum += getMonthDays(year, i);
        return sum;
    }

    public static void printCalendar(int year, int month) {
        int passedDays = getYearDaysSum(year) + getMonthDaysSum(year, month);
        int days = getMonthDays(year, month);
        // 1900-01-01是星期一
        // 如果offset==0(时至今日，前面已经逝去天数对7取模为0)，那么今天是“星期一”->“留空一天”
        // 如果offset==6(时至今日，前面已经逝去天数对7取模为6)，那么今天是“星期日”->“留空零天”
        int offset = passedDays % 7;
        int weekOffset = (passedDays + 1) % 7;
        int weekRemaining = 7 - (passedDays + days) % 7;
        System.out.print("星期日\t星期一\t星期二\t星期三\t星期四\t星期五\t星期六\t\n");
        // 打印前驱空白
//        for (int i = 0; i < weekOffset; i++) {
//            System.out.print("\t\t");
//        }
        // 打印上月后继
        for (int i = weekOffset; i > 0; i--) {
            int lastMonthDays = month != 1 ? getMonthDays(year, month - 1) : getMonthDays(year - 1, 12);
            System.out.print((lastMonthDays - i + 1) + "\t\t");
        }
        // 打印本月日期
        for (int i = 1; i <= days; i++) {
            System.out.print(i + "\t\t");
            if ((i + weekOffset) % 7 == 0)
                System.out.println();
        }
        // 打印后继空白
//        for (int i = 1; i < weekRemaining; i++) {
//            System.out.print("\t\t");
//        }
        // 打印下月前驱
        for (int i = 1; i < weekRemaining; i++) {
            System.out.print(i + "\t\t");
        }

        System.out.println();
    }

    public static void printPermanentCalendar(LocalDate localDate) {
//        String[] dayOfWeek = {"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"}; // 字符位数不同，无法Tab对齐
        String[] dayOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        // 打印表头
        for (int i = 0; i < dayOfWeek.length; i++)
            System.out.printf("%s" + (i != dayOfWeek.length - 1 ? "\t" : "\n"), dayOfWeek[i]);
        // 打印空格
        int weekOffset = localDate.getDayOfWeek().getValue();
        for (int i = 0; i < weekOffset; i++)
            System.out.printf("\t");
        // 打印日期
        int month = localDate.getMonthValue(); // getMonth()返回JANUARY......DECENBER，getMonthValue()返回1......12
        while (month == localDate.getMonthValue()) {
            System.out.printf("%d" + (localDate.getDayOfWeek().getValue() != 6 ? "\t" : "\n"), localDate.getDayOfMonth());
            localDate = localDate.plusDays(1); // plusDays()|minusDays()后移|前移日期
        }

        System.out.println();
    }
}
