package com.conquer_java.knowledge.generic;

import java.util.*;

public class Demo {
    private final <E, K, V, T> List<E> func(E e, Map<K, V> map, T t) {
        return new ArrayList<E>();
    }

    private static <ANYTYPE, E, K, V, T> List<Map<ANYTYPE, Map<K, V>>> func(ANYTYPE anyparameter, K k, V v, E e) {
        return null;
    }

    private <T, E extends T> void func11(List<T> list1, List<E> list2){};

    private <T> void func22(List<T> list1, List<? extends T> list2){};

    private <T> void func4(List<? extends T> list, T t) {}; // list只读（另外可见，?和泛型可以联合使用）
    private <T, E extends T> void func(List<E> list, T t){}; // list可以修改

    private <T> List<T> func1(List<? extends Number> para) {
        return null;
    }

//    private <T extends Number> List<T> func5(T para); {
//        return null;
//    }

//    private <T extends Number> List<T> func(T para); {
//        return null;
//    }

//    private <T, E> Map<T, E> func大(<T extends Number> parameter, E element); {
//        return null;
//    }
//
//    private <T super Number> Set<T> func点对点(T para); {
//
//        new HashMap<>();
//        return null;
//    }


    public static void main(String[] args) {

        System.out.println("++++++++++++++++Begin: 泛型由来++++++++++++++++");
        List arrayList = new ArrayList();
        arrayList.add("Alexander00Win99");
        arrayList.add(81);

//        for(int i = 0; i < arrayList.size(); i++){
//            String item = (String) arrayList.get(i);
//            System.out.println("泛型测试 —— item = " + item);
//        }
        System.out.println("----------------End: 泛型由来----------------");

        System.out.println("++++++++++++++++Begin: 泛型特点——编译时类型检测+运行时类型擦除++++++++++++++++");
        /**
         * 模块说明
         */
        List<String> stringArrayList = new ArrayList<String>();
        List<Integer> integerArrayList = new ArrayList<Integer>();

        Class classStringArrayList = stringArrayList.getClass();
        Class classIntegerArrayList = integerArrayList.getClass();

        if (classStringArrayList.equals(classIntegerArrayList)) System.out.println("泛型测试：类型相同");
        //System.out.println(stringArrayList instanceof List<String>); // NOK——报错：“illegal generic type for instanceof”
        System.out.println(stringArrayList instanceof List); // OK——泛型不具instanceof操作
        //System.out.println(integerArrayList instanceof List<Integer>); // NOK——报错：“illegal generic type for instanceof”
        System.out.println(integerArrayList instanceof List); // OK——泛型不具instanceof操作
        System.out.println("----------------End: 泛型特点——编译时类型检测+运行时类型擦除----------------");

        System.out.println("++++++++++++++++Begin: 泛型由来++++++++++++++++");
        /**
         * 模块说明
         */
        Random random = new Random();
        System.out.println(random.nextInt(4));
        System.out.println("----------------End: 泛型由来----------------");
    }
}

interface GenericInterface<T> {
    T func();
}

class GenericImpl4GenericInterface<T> implements GenericInterface<T> {

    @Override
    public T func() {
        return null;
    }
}

class NumberImpl implements GenericInterface<Number> {
    enum MyNumber{
        MYINTEGER(0, "整型"), MYLONG(1, "长整型"), MYFLOAT(2, "浮点"), MYDOUBLE(3, "双精度");
        private int index;
        private String type;

        MyNumber(int index, String type) {
            this.index = index;
            this.type = type;
        }

        public Class getRandomNumber() {
            switch (this) {
                case MYINTEGER:
                    return Integer.class;
                case MYLONG:
                    return Long.class;
                case MYFLOAT:
                    return Float.class;
                case MYDOUBLE:
                    return Double.class;
                default:
                    return null;
            }
        }

        public static Class getRandomNumber(int index) {
//            for (MyNumber myNumber : MyNumber.values()) {
//                if
//            }
            MyNumber value = MyNumber.values()[index];
            return value.getClass();
        }
    }
    @Override
    public Number func() {
        int limit = 10000;
        Class clazz = MyNumber.getRandomNumber(new Random(MyNumber.values().length).nextInt());
        try {
            clazz.newInstance();
            clazz.getConstructor(clazz.getClass());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}