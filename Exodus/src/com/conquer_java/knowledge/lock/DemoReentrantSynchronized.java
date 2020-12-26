package com.conquer_java.knowledge.lock;

/**
 * 【目的】
 * 1) 验证synchronized锁是可重入锁；
 * 2) 验证静态内部类和非静态内部类对象的创建区别；
 *
 * 注：
 * 同一线程在调用本类内部其他synchronized方法/代码块或者调用父类的synchronized方法/代码块时，不会发生阻碍，可以直接获取锁对象。
 */
public class DemoReentrantSynchronized {
    private static class Parent {
        public synchronized void task() {
            basicTask();
            additionalTask();
        }

        public synchronized void basicTask() {
            System.out.println("Parent-BasicTask!");
        }

        public synchronized void additionalTask() {
            System.out.println("Parent-AdditionalTask!");
        }
    }

    private static class Child extends Parent {
        public final synchronized void task() {
            basicTask();
            additionalTask();
        }

        public final synchronized void basicTask() {
            System.out.println("Child-BasicTask!");
        }

        public final synchronized void additionalTask() {
            System.out.println("Child-AdditionalTask!");
            System.out.println("前人栽树后人乘凉，父辈的遗产子辈可以继承！");
            super.basicTask();
            super.additionalTask();
        }
    }

    /**
     * 1：静态内部类：无需外部类的对象实例引用即可直接创建；
     * 2：非静态内部类，必须拥有外部类的对象实例引用才能创建；
     * 3：外部类的静态方法：因为没有this指针，所以必须首先获得外部类的对象实例引用，然后方可创建非静态内部类；
     * 4：外部类的非静态方法：因为具有隐含的外部类的对象实例引用this指针，所以可以直接创建非静态内部类；
     * 5：静态内部类，不能直接访问外部类的非静态属性或方法；
     * 6：由此可见：非静态内部类之所以可以直接访问外部类的方法，是因为在创建非静态内部类时，已经隐含传入外部类的对象实例引用参数。
     * 注：
     * 如果Parent是non-static，Child是static，编译报错（因为，静态内部类不能访问外部类的非静态属性或方法。）：
     * Error:(xx, xx) java: no enclosing instance of type com.conquer_java.knowledge.lock.DemoReentrantSynchronized is in scope
     */
    public static void main(String[] args) {
        //Child child = new DemoReentrantSynchronized().new Child();
        Child c = new DemoReentrantSynchronized.Child();
        c.task();
    }
}
