package com.conque_java.knowledge.JVM.ClassLoader;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;

import java.util.LinkedList;

/**
 * SelfDefinedClassLoader -> AppClassLoader应用程序类加载器 -> ExtClassLoader扩展类加载器 -> BootstrapClassLoader启动类加载器
 * 利用优先级
 */
public class DemoLoadClass {
    private void method() {

    }

    public static void main(String[] args) {
        /**
         * 1) 如果子类没有自有属性声明——public static int id;——SubClass.id实质上是SuperClass.id==(public static int id = 1;)
         * 调用静态变量触发类的初始化，但是不会触发子类的初始化——SubClass.id实质上是SuperClass.id；打印如下：
         * Initialization in super class!
         * 1
         * 2) 如果子类含有自有属性声明——public static int id;
         * 打印如下：
         * Initialization in super class!
         * Initialization in sub class!
         * 温
         * 0
         */
        System.out.println(SubClass.id);
        /**
         * SuperClass不是合法的类名，JDK底层使用同名类实现数组相关功能
         * SuperClass[] arr = new SuperClass[10];——new操作不会触发类的初始化
         * System.out.println(arr[0] instanceof SuperClass);——打印false
         */
        SuperClass[] arr = new SuperClass[10];
        System.out.println(arr[0] instanceof SuperClass);
        /**
         * 常量会被存储到方法区的常量池中，对于常量的引用不会触发类的初始化
         */
        System.out.println(SubClass.CONGRATULATION);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent()); // C++实现，返回null
    }

    class Inner {
        /**
         * 内部类可以看成外部类的非静态成员，因此内部类作为对象变量的初始化必须在外部类对象创建后发生，
         * 也即如需加载内部类必须首先实例化外部类生成对象：new Outer(); -> Inner.staticProp
         * 同时JVM虚拟机规范要求类的静态变量必须在类加载阶段完成内存分配及其初始化工作，然而Inner类无法加载，
         * 因为此时外部类尚未实例化，Inner内部类作为Outer外部类的实例属性是无法加载的(尚未分配内存)。
         * 但是，如果内部类的属性不是静态变量而是常量，常量
         */
        //static int wrong = 0; // 报错：Inner classes cannot have static declarations
        static final int right = 1; // 常量OK

//        static { // 报错：Inner classes cannot have static declarations
//            System.out.println("Inner classes cannot have static declarations");
//        }

        { // Non-static inner classes can only use non-static declarations or instance methods
            System.out.println("It's because an inner class is implicitly associated with an instance of its outer class, " +
                    "it cannot define any static methods itself. Since a static nested class cannot refer directly to " +
                    "instance variables or methods defined in its enclosing class, it can use them only through an object reference, " +
                    "it's safe to declare static methods in a static nested class.");
            method();
        }
    }
}

class SuperClass {
    static {
        System.out.println("Initialization in super class!");
    }

    public static int id = 1;
    public static final String CONGRATULATION = "Hello World!";
}

class SubClass extends SuperClass {
    static {
        System.out.println("Initialization in sub class!");
        //System.out.println(firstName); // 报错：Non-static field 'firstName' cannot be referenced from a static context
        /**
         * System.out.println(lastName);——静态属性不能提前访问，否则报错："Illegal forward reference"
         * lastName = "温";——静态属性可以提前赋值
         *
         * 静态块中可以提前赋值静态属性，但是不可提前使用静态属性，因为类的初始化本质上就是执行类构造器<clinit>()方法，
         * <clinit>()方法是由编译器自动收集类中的所有类属性的赋值动作和静态语句块(static{}块)中的语句合并产生的，
         * 编译器收集的顺序是由语句在源文件中出现的顺序所决定的。
         * 静态语句块中只能访问到定义在静态语句块之前的属性，定义在它之后的属性，在前面的静态语句块中可以赋值，但是不能访问。
         */
        //System.out.println(lastName);
        lastName = "温";
        //System.out.println(lastName); // 这里同样报错："Illegal forward reference"
    }

    public static int id;
    String firstName;
    static String lastName;

    /**
     * 静态块位于静态属性lastName之后，可以访问。
     */
    static {
        System.out.println(lastName);
    }
}
