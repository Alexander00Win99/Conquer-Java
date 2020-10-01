package com.conquer_java.knowledge.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 【目标】：掌握日期类及其常用方法
 *
 * 【结果】：完成
 *
 */
public class DemoDateCalendar {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date.toString());
        // Thu Jan 01 08:00:01 CST 1970 —— 格式：星期 月 日 时间 CST 年份
        // CST —— China Standard Time
        System.out.println(new Date(1000).toString()); // Date传参数值delta：单位毫秒ms

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime()); // 格式等同Date输出
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        System.out.printf("%d-%d-%d\n", year, month, day);

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        System.out.printf("%d年-%d月-%d日%n", localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        LocalDate dateExpected = LocalDate.of(2020, 2, 2);
        System.out.println(dateExpected);
        dateExpected = dateExpected.plus(1, ChronoUnit.WEEKS); // plus() | plusDays | plusHours...
        System.out.println(dateExpected);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        LocalDate localDateExpected = localDateTime.toLocalDate();
        System.out.println(localDateExpected);
        LocalTime localTimeExpected = localDateTime.toLocalTime();
        System.out.println(localTimeExpected);
    }
}
