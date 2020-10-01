package com.conquer_java.knowledge.thread.thread_communication;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo07_AtomicInteger {
    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    //====Solution07-AtomicInteger====
    static AtomicInteger threadNo = new AtomicInteger(1);
    static volatile boolean bFinished = false; // 只要任一线程执行完毕即可置true
    //====Solution07-AtomicInteger====
    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Solution07-AtomicInteger--------++++++++");
        new Thread(() -> {
            for (char c : arrInt) {
                while (threadNo.get() != 1) {}
                System.out.println(c);
                if (!bFinished)
                    threadNo.incrementAndGet();
                //threadNo.set(2);
            }
            bFinished = true;
        }, "AtomicInteger-Thread-01").start();
        new Thread(() -> {
            for (char c : arrChar) {
                while (threadNo.get() != 2) {}
                System.out.println(c);
                if (!bFinished)
                    threadNo.decrementAndGet();
                //threadNo.set(1);
            }
            bFinished = true;
        }, "AtomicInteger-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution07-AtomicInteger--------++++++++");
    }
}
