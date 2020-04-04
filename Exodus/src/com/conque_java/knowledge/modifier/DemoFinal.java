package com.conque_java.knowledge.modifier;

/**
 * final：“无法改变的”
 * 【使用目的】：效率；设计；
 * 【修饰内容】：1)类；2)属性；3)方法；
 * 【final + 类】：类不能被继承(当类被加以final修饰时，类中方法也被同时隐式设定为final，但是类中属性不受影响)。——使用场景：1)安全角度，不希望类有任何子类；2)设计角度，不希望类有任何变动，
 * 【final + 属性】：属性含义保持不变(编译时，设定恒定不变的常量；运行时，初始化后值不被改变)。——基本类型，数值不变；引用类型，地址不变。——使用场景：1)安全角度，不可变特性实现多线程并发环境下的数据共享；2)效率角度，JVM会对final变量进行缓存。
 * 【final + 方法】：显式禁止方法在子类中被重写(private方法，会被隐式设定为final方法)。——使用场景：1)锁定方法，防止类修改定义；2)提升效率，早期版本JVM会对final方法进行优化；
 * 【实战举例】：
 * 1) 常量类
 * public static final String ALEX = "ALEXANDER 温";
 * 2) 接口内常量属性
 * interface IConstant {
 *     String ALEX = "ALEXANDER 温";
 * }
 */
public class DemoFinal {
    public static void main(String[] args) {
        // final修饰的引用变量地址不变，对象属性可以改变
        final MyClass myClass = new MyClass();
        for (int i = 0; i < 10; i++) {
            System.out.println(myClass.myNum++);
        }
        // final类内部属性不受final关键字影响
        FinalClass finalClass = new FinalClass();
        for (int i = 0; i < 10; i++) {
            System.out.println(finalClass.prop++);
        }

        // str位于常量池
        String str = "Alex Wen is handsome!";
        // str1是常量
        final String str1 = "Alex Wen";
        // str2是变量
        String str2 = "Alex Wen";
        // 常量 + 常量(直接使用常量池内容)
        String str3 = str1 + " is handsome!";
        // 变量 + 常量(利用StringBuffer进行+操作)
        String str4 = str2 + " is handsome!";

        System.out.println(str == str3);
        System.out.println(str == str4);
        // intern()返回等值String在常量池中的引用地址(JVM会在常量池中查找是否存在等值的String常量，若存在则直接返回常量池中的引用；若不存在则在常量池中创建一个等值String，然后返回这个String在常量池中的引用)
        System.out.println(str == str4.intern());
    }
}

class MyClass {
    public int myNum = 0;
}

final class FinalClass {
    public static int prop;
    public static void print() {}
}
