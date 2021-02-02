package com.conquer_java.spring.pojo;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.Calendar;
import java.util.Date;

public class MyCalendarFactoryBean extends AbstractFactoryBean {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public Class<?> getObjectType() {
        return Calendar.class;
    }

    @Override
    protected Object createInstance() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year, this.month, this.day, this.hour, this.minute, this.second);
        return calendar;
    }
}
