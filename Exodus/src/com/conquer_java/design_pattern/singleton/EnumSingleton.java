package com.conquer_java.design_pattern.singleton;

import java.lang.reflect.Constructor;

/**
 * 【枚举单例】
 * 1) 没有无参构造器；
 * 2) 枚举是线程安全的，解决线程安全问题；
 * 3) 枚举禁止序列化和反序列化；
 * 4) 枚举天然禁止反射破坏单例，使用反射生成单例导致"Cannot reflectively create enum objects"异常；
 *
 * if ((clazz.getModifiers() & Modifier.ENUM) != 0)
 *     throw new IllegalArgumentException("Cannot reflectively create enum objects");
 */
enum EnumSingleton {
    INSTANCE;
    public EnumSingleton getInstance() {
        return INSTANCE;
    }

    public void work() { // 单例类工作方法
        System.out.println("Here do something!" + hashCode());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("++++++++++++++++利用枚举类实现单例++++++++++++++++");
        EnumSingleton enumSingleton1 = EnumSingleton.INSTANCE;
        EnumSingleton enumSingleton2 = EnumSingleton.INSTANCE;
        System.out.println(enumSingleton1 == enumSingleton2);
        enumSingleton1.work();
        enumSingleton2.work();
        Constructor<EnumSingleton> constructor = EnumSingleton.class.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        EnumSingleton enumSingleton = constructor.newInstance("INSTANCE", 0);
        System.out.println("----------------利用枚举类实现单例----------------");
    }
}
