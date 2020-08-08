package com.conque_java.knowledge.JVM.ClassLoader;

/**
 * 参考：https://www.cnblogs.com/alip8/p/9810027.html
 * 【为何使用内部类】
 * 每个内部类都能独立地继承一个（接口的）实现，所以无论外围类是否已经继承了某个（接口的）实现，对于内部类都是毫无影响。
 */
public class DemoOuterInnerClass {
    public static void main(String[] args) {

//        OuterClass.InnerClass obj = new OuterClass().new InnerClass();
//        obj.setName();
    }
}

class OuterClass {
    String name;

    OuterClass(String name) {
        this.name = name;
    }

    class InnerClass {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            // 内部类如何访问外部类的属性
//            this.name = new StringBuilder(name).append(super()).toString();
        }
    }
}
