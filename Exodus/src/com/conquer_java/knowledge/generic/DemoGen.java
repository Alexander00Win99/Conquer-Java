package com.conquer_java.knowledge.generic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DemoGen<T> {
    public T getValue(Object o) {
        return (T)o;
    }

    public static <T> T getValue2(Object o, T another) {
        return (T) o;
    }

    public <ANYTYPE> List<ANYTYPE> func(Class<ANYTYPE> anytypeClass) {
        List<ANYTYPE> list = new ArrayList<>();
        return list;
    }
//    public <ANYTYPE> List<ANYTYPE> func(Class<ANYTYPE> anytypeClass, <? extends Number> num) {
//        List<ANYTYPE> list = new ArrayList<>();
//        return list;
//    }

//    public class Number<T extends Comparable & Serializable> {
//
//    }

    public <U,S> void test(){

    }


//    public static <ANYTYPE, K, V> List<ANYTYPE> func(<K extends Number> key, <V super Map> value) {
//
//    }

    public static void main(String[] args) {
        DemoGen<Integer> demo = new DemoGen<>();
        Integer value = demo.getValue(3);
        System.out.println(value);

        Double value2 = getValue2(3.1, 1.3);
        System.out.println(value2);
        System.out.println(value2.getClass());
    }
}
