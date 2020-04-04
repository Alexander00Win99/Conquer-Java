package com.conque_java.knowledge.thread;

import java.util.concurrent.locks.Condition;

/**
 * 【线程的五种状态】
 * 新建NEW 就绪RUNNABLE 运行RUNNING 阻塞BLOCKED 等待WAITING 限时等待TIMED_WAITING 终结TERMINATED
 *
 * 注：WAITING状态触发动作：Object.wait(无参) | Thread.join(无参) | LockSupport.park()。例如：ThreadA调用某个对象的Object.wait()方法，
 * 那么其会一直处于等待WAITING状态直至另外一个线程ThreadB调用相同对象的Object.notify()/notifyALL()方法为止。
 *
 * [TIMED_WAITING定义]
 * A thread that is waiting for another thread to perform an action for up to a specified waiting time is in this state.
 * Thread state for a waiting thread with a specified waiting time. A thread is in the timed waiting state due to calling one of the following methods with a specified positive waiting time:
 *
 * Thread.sleep
 * Object.wait with timeout
 * Thread.join with timeout
 * LockSupport.parkNanos
 * LockSupport.parkUntil
 *
 * 注：TIMED_WAITING状态触发动作：Object.wait(有参) | Thread.join(有参) | Thread.sleep(有参) | LockSupport.parkNanos(有参) | LockSupport.parkUntil(有参)。
 */
public class DemoThreadControl {
    private static Object lock = new Object();

    public static void main(String[] args) {
        System.out.println("++++++++++++++++Begin-NEW状态++++++++++++++++");
        Thread thread = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE / 1000000; i++) {
                System.out.println(i);;
            }
        });
        System.out.println(thread.getState());
        System.out.println("----------------End-NEW状态----------------");

        System.out.println("++++++++++++++++Begin-RUNNABLE状态++++++++++++++++");
        thread.start();
        System.out.println(thread.getState());
        System.out.println("----------------End-RUNNABLE状态----------------");

        System.out.println("++++++++++++++++Begin-BLOCKED状态++++++++++++++++");
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " invoke:");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ThreadA-4-test-BLOCKED");
        Thread threadB = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " invoke:");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ThreadB-4-test-BLOCKED");
        /**
         * jstack观察得到：一个处于TIMED_WAITING状态，一个处于BLOCKED状态。
         */
        threadA.start();
        threadB.start();
        System.out.println("----------------End-BLOCKED状态----------------");

        System.out.println("++++++++++++++++Begin-WAITING状态++++++++++++++++");
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(Thread.currentThread().getName() + "wait over!");
                    System.out.println(Thread.currentThread().getName() + " invoke:");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread1-4-test-WAITING");
        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(10000);
                    System.out.println(Thread.currentThread().getName() + " invoke:");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.notifyAll();
            }
        }, "Thread2-4-test-WAITING");
        /**
         *
         */
        thread1.start();
        thread2.start();
        /**
         * 线程启动Thread.start()以后，调用Thread.join()方法 -> WAITING状态
         */
        System.out.println("----------------End-WAITING状态----------------");

        System.out.println("++++++++++++++++Begin-TIMED_WAITING状态++++++++++++++++");
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " accomplish!");
        });
        t.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread.getState());
        System.out.println("----------------End-TIMED_WAITING状态----------------");

        System.out.println("++++++++++++++++Begin-TERMINATED状态++++++++++++++++");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread.getState());
        System.out.println("----------------End-TERMINATED状态----------------");
        System.out.println("++++++++++++++++Begin-通过反射打印继承体系以及实现接口++++++++++++++++");
        System.out.println("主线程名：" + Thread.currentThread().getName());
    }
}
