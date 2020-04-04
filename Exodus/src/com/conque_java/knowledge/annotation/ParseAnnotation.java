package com.conque_java.knowledge.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ParseAnnotation {
    static Object obj;
    public static <T> T getBean(Class clazz) {
        try {
            // 类的默认构造器
            Constructor constructor = clazz.getConstructor();
            obj = constructor.newInstance();
            System.out.println("obj对象的类型：" + obj.getClass());
            // 默认构造函数上的MyAnnotation对象
            MyAnnotation myAnnotation = (MyAnnotation) constructor.getAnnotation(MyAnnotation.class); // 可能同时存在多个注解，因此需要指定注解类型
            //String[] values = myAnnotation.value();
            // 注解类的Class
            Class myAnnotationClass = myAnnotation.getClass();
            // 注解类的value()方法
            Method myAnnotationMethod = myAnnotationClass.getDeclaredMethod("value");
            String[] values = (String[]) myAnnotationMethod.invoke(myAnnotation);
            // 类的所有属性
            Field[] fields = clazz.getDeclaredFields();
            // 各个属性依次赋值
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                fields[i].set(obj, values[i]);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    public static void main(String[] args) {
        Class clazz = Person.class;
        Person p = ParseAnnotation.getBean(clazz);
        System.out.println(p.toString());
    }
}
