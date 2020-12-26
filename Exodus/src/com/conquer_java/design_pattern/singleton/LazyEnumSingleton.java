package com.conquer_java.design_pattern.singleton;

/**
 * 【枚举单例变种】
 * 枚举是通过静态代码块形成的饿汉式单例，那这样就失去了延迟加载的特性。
 * 如何解决呢？我们可以结合类的加载特点使用类似静态内部类的方式来实现：
 *
 *  优点：枚举单例是final类型，不会被继承；实现了真正的多线程安全，防止序列化反序列化和反射对于单例的破坏；内部类保证懒加载。
 *  缺点：需要使用文档单独说明单例获取方法
 */
class LazyEnumSingleton {
    private enum EnumSingletonHolder { // 内部枚举类，实现懒加载效果。
        INSTANCE;
        private LazyEnumSingleton instance;
        EnumSingletonHolder() {
            this.instance = new LazyEnumSingleton();
        }
        private LazyEnumSingleton getSingleton() {
            return this.instance;
        }
    }
    private LazyEnumSingleton() {}
    public static LazyEnumSingleton getInstance() {
        return EnumSingletonHolder.INSTANCE.getSingleton();
    }

    public static void main(String[] args) {
        LazyEnumSingleton instance1 = LazyEnumSingleton.getInstance();
        LazyEnumSingleton instance2 = LazyEnumSingleton.getInstance();
        System.out.println(System.identityHashCode(instance1));
        System.out.println(System.identityHashCode(instance2));
    }
}
