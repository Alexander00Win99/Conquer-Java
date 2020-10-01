package com.conquer_java.knowledge.JMM.Volatile;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    private static String str = "Hello World!";

    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        System.out.println(str);
//        Class<String> stringClass = (Class<String>) str.getClass();
//        Class clazz = String.class;
//        Field field = clazz.getDeclaredField("vlaue");
//        field.setAccessible(true);

        String className = args[0];
        Class clazz = Class.forName(className);
        Method main = clazz.getDeclaredMethod("main", String[].class);
        Constructor constructor = clazz.getConstructor();
        constructor.newInstance();
//        main.invoke((clazz)clazz.newInstance());
    }
}

class DemoReflect {
    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}