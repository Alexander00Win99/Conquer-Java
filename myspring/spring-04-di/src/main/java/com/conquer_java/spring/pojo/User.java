package com.conquer_java.spring.pojo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User {
    private String name;
    private int age;
    private Date birthdate;
    private Calendar birthday;

    public User() {
    }

    public User(String name, int age, Date birthdate, Calendar birthday) {
        this.name = name;
        this.age = age;
        this.birthdate = birthdate;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public String getDateString(Date birthdate) {
        Long date = birthdate.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");
        return simpleDateFormat.format(date);
    }

    public String getDateString(Calendar birthday) {
        Date date = birthday.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");
        return simpleDateFormat.format(date);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthdate=" + getDateString(birthdate) +
                ", birthday=" + getDateString(birthday) +
                '}';
    }
}
