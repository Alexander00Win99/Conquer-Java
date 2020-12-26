package com.conquer_java.knowledge.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MySpring {
    public Object getBean(String className) {
        Object obj = null;
        try {
            /**
             * IoC：Spring的BeanFactory自动生成对象，无须用户自己new生成。
             */
            Class clazz = Class.forName(className);
            Constructor constructor = clazz.getConstructor();
            obj = constructor.newInstance();

            /**
             * DI：属性赋值 —— 1) Scanner手动扫描输入；2) properties/xml文件读取；3) Annotation注解诸如；
             */
//            MyAnnotation myAnnotation = (MyAnnotation) constructor.getAnnotation(MyAnnotation.class);
//            Class<MyAnnotation> myAnnotationClass = MyAnnotation.class;
//            Method valueMethod = myAnnotationClass.getMethod("value");
            Annotation annotation = constructor.getAnnotation(MyAnnotation.class);
            Class<MyAnnotation> myAnnotationClass = MyAnnotation.class;
            Method valueMethod = myAnnotationClass.getMethod("value");
            String[] values = (String[]) valueMethod.invoke(annotation);

            Field[] fields = clazz.getDeclaredFields();
            if (fields.length != values.length)
                throw new RuntimeException("构造函数的注解之中属性传值不匹配！");

            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();
                String setterName = new StringBuilder("set").append(name.substring(0, 1).toUpperCase()).append(name.substring(1)).toString();
                Method setMethod = clazz.getMethod(setterName, fields[i].getType());
                // 特别注意：注解是String[]，属性类型并不一定是String类型，因此需要进行类型转换。此处要求：属性类型是八大基本类型对应的包装类（Character类没有包含String类型参数的构造方法）。
                // 如此方可保证它拥有Sting类型参数的构造方法，并能利用构造方法生成对应类型实例。如若是自定义类型，无法保证。
                // 除了Character以外的七大基本类型的包装类+String+自定义类（递归处理） 数组或者集合类型+Character须做额外处理
                // value[i] - String = "${Integer}" -> Integer - Method.invoke(parameter)
                Constructor ctor = fields[i].getType().getConstructor(String.class);
                setMethod.invoke(obj, ctor.newInstance(values[i]));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void main(String[] args) {
        Person person = (Person) new MySpring().getBean("com.conquer_java.knowledge.annotation.Person");
        System.out.println(person.toString());
    }
}
