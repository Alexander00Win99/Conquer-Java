package com.conquer_java.knowledge.thread.thread_communication;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo03_Synchronized_CountDownLatch {
    private static Thread t1 = null;
    private static Thread t2 = null;

    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    //====Solution03-Synchronized====
    private static final Object o = new Object();
    private static CountDownLatch latch = new CountDownLatch(1);
    //====Solution03-Synchronized====

    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Solution03-Synchronized--------++++++++");
        t1 = new Thread(() -> {
            latch.countDown();
            synchronized (o) { // 此处synchronized(this)出错：this指代各个线程本身，显然无法达到同步目的。
                for (char c : arrInt) {
                    System.out.println(c);
                    if (t2.getState() != Thread.State.TERMINATED) {
                        try {
                            o.notify();
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                o.notify(); // 必须存在，否则最终两个线程总有一个最终处于等待队列之中永远无人唤醒自己，程序无法结束。
            }
        }, "Synchronized-Thread-01");
        t2 = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o) { // 此处synchronized(this)出错：this指代各个线程本身，显然无法达到同步目的。
                for (char c : arrChar) {
                    System.out.println(c);
                    if (t1.getState() != Thread.State.TERMINATED) {
                        try {
                            o.notify();
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                o.notify(); // 必须存在，否则最终两个线程总有一个最终处于等待队列之中永远无人唤醒自己，程序无法结束。
            }
        }, "Synchronized-Thread-02");

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution03-Synchronized--------++++++++");
    }
}
