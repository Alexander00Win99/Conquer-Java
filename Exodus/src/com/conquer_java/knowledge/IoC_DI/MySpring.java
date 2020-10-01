package com.conquer_java.knowledge.IoC_DI;

import com.conquer_java.knowledge.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * DI是IoC的一小部分，实现对于利用IoC技术生成的对象进行属性赋值。
 */
public class MySpring {
    private String filename;
    public MySpring() {}

//    public MySpring(new File(filename)) {}

    public static Object getBean(String className) {
        Object o = null;
        Scanner input = new Scanner(System.in);
        System.out.println("下面将给" + className + "类对象赋值！");
        try {
            // 根据类名获取Class对象
            Class clazz = Class.forName(className);
            // 调用类的默认无参构造方法生成实例
            o = clazz.newInstance();
            // 获取类中所有属性
            Field[] fields = clazz.getDeclaredFields();
            // 对于每个属性使用对应的setField()方法进行赋值操作
            for (Field field : fields) {
                System.out.println("this is field: " + field);
                // 获取属性名称
                String fieldName = field.getName();
                // 获取属性类型
                Class fieldType = field.getType();
                System.out.println("this is fieldType: " + fieldType);
                // 获取属性对应setField()方法名
                String methodName = new StringBuilder("set").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1)).toString();
                // 获取对应setField()方法
                Method method = clazz.getDeclaredMethod(methodName, fieldType);
                // 获取用户输入|文件输入的String类型转为实际属性类型，例如：new Integer(123) | new Float(123.456)
                System.out.println("请给" + className + "类对象的" + fieldType.getName() + "类型的属性" + fieldName + "进行赋值：");
                String vlaue = input.nextLine();
                Constructor constructor = fieldType.getConstructor(String.class);

                // 执行setField()方法
                method.invoke(o, constructor.newInstance(vlaue));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void main(String[] args) {
        System.out.println(new Boolean("true"));
        Person person = (Person) getBean("com.conque_java.knowledge.Person");
        System.out.println(person);
        System.out.println();
    }
}
