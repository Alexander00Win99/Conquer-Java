package com.conquer_java.design_pattern.singleton;

/**
 *  实现原理：
 *  利用JVM内部类加载机制(类加载过程会对类自动加锁，因此是天然线程安全)实现线程之间对于指令重排序的隔离。
 *  JVM在加载InnerClassSingleton类时尚未加载内部类SingletonHolder，当某个线程首次调用getInstance方法时才会加载内部类，装载过程会对内部类加锁，
 *  其他线程只能等待，从而实现惰性加载，同时，内部类静态属性SingletonHolder.instance对象实例化完毕才会释放锁，从而隔离指令重排序过程。另外，后续
 *  调用getInstance方法直接返回，提高效率。
 */
class InnerClassSingleton { // 特点：结合懒汉模式懒加载+饿汉模式快加载的优点
    public static String author = "Alexander 温"; // 在main函数中打印InnerClassSingleton.author属性，1)能打印，2)不打印。
    static {
        System.out.println("Static block in InnerClassSingleton is triggered for initialization!"); // 1)语句
    }

    private static class SingletonHolder {
        static {
            System.out.println("Static block in SingletonHolder is triggered for initialization!"); // 2)语句
        }
        private static final InnerClassSingleton instance = new InnerClassSingleton();
    }

    private InnerClassSingleton() {}
    public static InnerClassSingleton getInstance() { // 在main函数中调用InnerClassSingleton.getInstance()方法，才会访问SingletonHolder.instance属性，触发类加载过程的初始化，结果返回生成单例。
        return SingletonHolder.instance;
    }

    public static void main(String[] args) {
        /**
         * 访问静态属性触发类加载过程中的初始化动作，完成饿汉模式实例化。
         * 单纯访问InnerClassSingleton.author不能触发private static class SingletonHolder返回单例对象
         * InnerClassSingleton <==相当==> HungrySingleton + LazySingleton
         */
        System.out.println("++++++++++++++++访问静态属性触发类加载过程中的初始化动作++++++++++++++++");
        System.out.println(InnerClassSingleton.author); // NOK
        InnerClassSingleton innerClassSingleton = InnerClassSingleton.getInstance(); // OK
        System.out.println(innerClassSingleton);
        System.out.println("----------------访问静态属性触发类加载过程中的初始化动作----------------");
    }
}
