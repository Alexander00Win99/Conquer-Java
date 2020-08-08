package com.conque_java.knowledge.class_load;

/**
 * ClassLoader的核心功能：动态加载 .class字节码文件 生成Class对象 —— 实现类（模块）的动态加载。如下四种方式可以触发类加载：
 * new | Class.farName(name) | Class.forName(name, classLoader) | ClassLoader.loadClass(name)
 *
 * 1) 启动类加载器(Bootstrap ClassLoader)
 * 负责加载JAVA_HOME/jre/lib/resources.jar|charsets.jar|rt.jar目录下的核心类库或者-Xbootclasspath指定目录下的类；
 * 2) 扩展类加载器(Extension ClassLoader)
 * 负责加载JAVA_HOME/jre/lib/ext目录下的扩展类库或者系统参数-Djava.ext.dirs指定目录下的类。开发者可以直接使用标准扩展类加载器；
 * 3) 系统类加载器(System ClassLoader)
 * 负责加载-classpath/系统参数-Djava.class.path/操作系统环境变量CLASSPATH指定目录下的类。一般来说，Java应用类都是由其完成加载。
 *
 * 注：
 * 类加载器 + 类本身 => 类在JVM虚拟机中的唯一性 <==> 也即同一虚拟机中的同一Class文件，被不同类加载器加载，产生不同的Class对象。
 */
public class TestClassLoader {
    public static void main(String[] args) {
        try {
            Class.forName("com.conque_java.knowledge.class_load.MyClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 通过getSystemClassLoader()获取AppClassLoader
        System.out.println(ClassLoader.getSystemClassLoader());
        // 获取加载路径
        String classPath = System.getProperty("java.class.path");
        System.out.println(classPath);
//        for (String path : classPath.split(";")) {
//            System.out.println(path);
//        }
        ClassLoader classLoader1 = TestClassLoader.class.getClassLoader();
        System.out.println(classLoader1);
        System.out.println(classLoader1.getParent());
        System.out.println(classLoader1.getParent().getParent());
        ClassLoader classLoader2 = MyClassLoader.class.getClassLoader();
        System.out.println(classLoader2);
        System.out.println(classLoader2.getParent());
        System.out.println(classLoader1.getParent().getParent());
        System.out.println("两个类加载器是否相同？");
        System.out.println(classLoader1 == classLoader2);

        MyClassLoader myClassLoader = new MyClassLoader("D:\\Workspace\\Java\\IdeaProjects\\Exodus\\out\\production\\Exodus\\com\\conque_java\\knowledge\\class_load", "random");
        System.out.println(myClassLoader);
        System.out.println(MyClassLoader.class);
        try {
            Class clazz = myClassLoader.loadClass("MyClass");
//            System.out.println("ClassLoader: " + clazz.getClassLoader());
//            System.out.println("生成class类对象的ClassLoader是否就是new出来的myClassLoader: " + (clazz.getClassLoader() == myClassLoader));
//            Object instance = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
        }


    }
}
