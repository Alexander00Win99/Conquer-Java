package com.conquer_java.knowledge.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DemoRepeatable {
    /*
    // 方式一：没用@Repeatable注解
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
    public @interface ReviewRecord {
        String name();
        String birthdate();
        String careerInfo();
        String accountRole();
        String comment() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
    public @interface ReviewRecords {
        ReviewRecord[] value();
    }

    @ReviewRecords({
            @ReviewRecord(name = "Alexander00Win99", birthdate = "1981-01-01", careerInfo = "chairman", accountRole = "super user", comment = "dominant-开天辟地"),
            @ReviewRecord(name = "Alexander 温", birthdate = "2000-01-01", careerInfo = "scientist", accountRole = "system user", comment = "farsighted-洞若观火"),
            @ReviewRecord(name = "Alex Wen", birthdate = "2001-01-01", careerInfo = "chef", accountRole = "normal user", comment = "challenging-大国小鲜")
    })
    private static class Person {
        String name;
        String birthdate;
        String careerInfo;
        String accountRole;
        String comment;

        @ReviewRecords({
                @ReviewRecord(name = "Alexander00Win99", birthdate = "1981-01-01", careerInfo = "chairman", accountRole = "super user", comment = "dominant-开天辟地"),
                @ReviewRecord(name = "Alexander 温", birthdate = "2000-01-01", careerInfo = "scientist", accountRole = "system user", comment = "farsighted-洞若观火"),
                @ReviewRecord(name = "Alex Wen", birthdate = "2001-01-01", careerInfo = "chef", accountRole = "normal user", comment = "challenging-大国小鲜")
        })
        public Person(String name, String birthdate, String careerInfo, String accountRole, String comment) {
            this.name = name;
            this.birthdate = birthdate;
            this.careerInfo = careerInfo;
            this.accountRole = accountRole;
            this.comment = comment;
        }
    }
     */

    // 方式二：使用@Repeatable注解
    @Repeatable(ReviewRecords.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
    public @interface ReviewRecord {
        String name();
        String birthdate();
        String careerInfo();
        String accountRole(); // Linux用户角色分为三类：1) 超级用户root(UID=0); 2) 系统用户(或称虚假用户，例如，bin、sbin、adn、daemon等，UID [1, 999] - CentOS7 | UID [1, 499] - CentOS6); 3) 普通用户(UID [1000, 65535] - CentOS7 | UID >= 500 - CentOS6)；
        String comment() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
    public @interface ReviewRecords {
        ReviewRecord[] value();
    }

    private static class Person {
        String name;
        String birthdate;
        String careerInfo;
        String accountRole;
        String comment;

        // 使用@Repeatable元注解修饰的注解，无须数组形式使用，直接列举各个元素。
        @ReviewRecord(name = "Alexander00Win99", birthdate = "1981-01-01", careerInfo = "chairman", accountRole = "super user", comment = "dominant-开天辟地")
        @ReviewRecord(name = "Alexander 温", birthdate = "2000-01-01", careerInfo = "scientist", accountRole = "system user", comment = "farsighted-洞若观火")
        @ReviewRecord(name = "Alex Wen", birthdate = "2001-01-01", careerInfo = "chef", accountRole = "normal user", comment = "challenging-大国小鲜")
        public Person(String name, String birthdate, String careerInfo, String accountRole, String comment) {
            this.name = name;
            this.birthdate = birthdate;
            this.careerInfo = careerInfo;
            this.accountRole = accountRole;
            this.comment = comment;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", birthdate='" + birthdate + '\'' +
                    ", careerInfo='" + careerInfo + '\'' +
                    ", accountRole='" + accountRole + '\'' +
                    ", comment='" + comment + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        System.out.println("++++++++++++++++Begin 未用Repeatable++++++++++++++++");
        /**
         * 模块说明 —— 没用@Repeatable注解
         * 获取@ReviewRecords注解（视为数组）==>通过value()属性==>获取@ReviewRecord注解（视为元素）
         */
//        Person person = new Person();
//        ReviewRecords recordsAnno = Person.class.getAnnotation(ReviewRecords.class);
//        for (ReviewRecord recordAnno : recordsAnno.value()) {
//            System.out.println(recordAnno);
//            System.out.println(recordsAnno);
//        }
        System.out.println("----------------End 未用Repeatable----------------");


        System.out.println("++++++++++++++++Begin 使用Repeatable++++++++++++++++");
        /**
         * 模块说明 —— 使用@Repeatable注解
         * 使用@Repeatable元注解修饰的注解，无须数组形式使用，直接列举各个元素。
         * 各个注解元素，可以首先通过getAnnotationsByType()获取数组，然后选取指定元素即可。
         */
        Class clazz = Person.class;
        try {
            Constructor constructor = clazz.getConstructor(String.class, String.class, String.class, String.class, String.class);
            ReviewRecord[] annos = constructor.getAnnotationsByType(ReviewRecord.class);
            Person person = (Person) constructor.newInstance(annos[0].name(), annos[0].birthdate(), annos[0].careerInfo(), annos[0].accountRole(), annos[0].comment());
            System.out.println(person);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("----------------End 使用Repeatable----------------");
    }
}
