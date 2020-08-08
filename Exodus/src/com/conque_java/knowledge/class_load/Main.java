package com.conque_java.knowledge.class_load;

public class Main {
    /**
     * 1) 执行MyClass.class，打印：
     * “Hello word!”
     * 2) 执行Class.forName("com.conque_java.knowledge.class_load.MyClass")，打印：
     * “Static block is invoked!
     * Hello word!”
     */
    static {
        Class[] classArray = {
                MyClass.class // 引用该类必然导致JVM加载该类
        };
//        try {
//            Class[] classArray = {
//                    Class.forName("com.conque_java.knowledge.class_load.MyClass")
//            };
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
    public static void main(String[] args){
        System.out.println("Hello word!");
    }
}
