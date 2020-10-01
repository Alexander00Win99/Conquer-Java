package com.conquer_java.knowledge.JSR133;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * https://blog.csdn.net/u013967628/article/details/85291748
 * https://www.cnblogs.com/study-everyday/p/8618802.html
 * https://www.cnblogs.com/bcl88/p/11457422.html
 */
public class UnsafeInstance {
    public static Unsafe getUnsafeByReflect() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Unsafe unsafe = getUnsafeByReflect();
        int i = 0;
        unsafe.loadFence();
        unsafe.storeFence(); // 内存屏障(读屏障|写屏障|读写屏障)
        unsafe.fullFence();
        int j = 0;
    }
}
