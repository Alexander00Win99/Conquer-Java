package com.conquer_java.design_pattern.singleton;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

class HungrySingleton implements Serializable {
    public static String author = "Alexander 温"; // 访问HungrySingleton.auth静态属性，触发HungrySingleton.instance初始化。
    private static boolean flag = false;

    static {
        System.out.println("Static block in HungrySingleton is triggered for initialization!");
    }

    private static final long serialVersionUID = 1L;
    private static final HungrySingleton instance = new HungrySingleton();
    private HungrySingleton() {
        System.out.println("执行饿汉式单例！");
        synchronized (HungrySingleton.class) {
            if (instance != null)
                throw new RuntimeException("严重警告：禁止使用反射技术破坏单例！");
            if (!flag)
                flag = true;
            else
                throw new RuntimeException("单例模式禁止使用反射技术生成多个实例！");
        }
    }
    public static HungrySingleton getInstance() {
        return instance;
    }

    public static void main(String[] args) throws Exception {
        /**
         * 1) 用户通过HungrySingleton.getInstance();正常方式获取对象实例，黑客通过反射方式获取无参构造器生成对象实例。
         * 利用反射技术生成的单例对象和原有单例对象不同，破会了单例特性，可以在HungrySingleton类中加入异常阻止利用反射产生单例。
         * 原理在于：正常方式执行在先，将已无参构造器中intance置为非空，黑客访问抛出异常。
         * 局限之处：如果黑客执行在先，或者，正常用户也是通过反射方式生成单例对象，这种方法就无法工作了。
         *
         * 	2) 在无参构造器内，设置私有标志变量进行首次判断(boolean flag = false)。
         * 	原理在于：无论正常方式还是黑客访问，只要执行私有无参构造器，首次即可触发标志变量取反，无论是谁只要再次访问私有构造器，即可抛出异常。
         * 	局限之处：黑客完全可能通过解密方法获取私有标志变量名称，然后通过反射方法，重新还原值为初始值。
         */
        System.out.println("++++++++++++++++限制反射破坏饿汉式单例++++++++++++++++");
        // 调用静态方法触发类加载过程中的初始化行为
        HungrySingleton hungrySingleton = HungrySingleton.getInstance();

        // 使用反射方法访问类触发类加载过程中的初始化行为
        Class<HungrySingleton> clazz = HungrySingleton.class;
        Constructor<HungrySingleton> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        HungrySingleton reflectedInstance = constructor.newInstance();
        Field flag = clazz.getDeclaredField("flag");
        flag.setAccessible(true);
        flag.set(reflectedInstance, false);

        System.out.println("观察'饿汉式'和'懒汉式'的区别，可以得出结论：'private static HungrySingleton instance = new HungrySingleton();'加载即被执行！因此，抛出异常如下：");
        System.out.println(reflectedInstance == hungrySingleton);
        System.out.println("----------------限制反射破坏饿汉式单例----------------");
    }
}
