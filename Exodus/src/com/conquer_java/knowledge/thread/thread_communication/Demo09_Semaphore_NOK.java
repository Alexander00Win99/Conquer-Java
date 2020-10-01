package com.conquer_java.knowledge.thread.thread_communication;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo09_Semaphore_NOK {
    private static Thread t1 = null;
    private static Thread t2 = null;

    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * 信号量Semaphore必须结合Thread.sleep(time)才能工作，不知何故！！！
     */
    //====Solution09-Semaphore====
    private static volatile Semaphore semaphore = new Semaphore(1, true);
    //====Solution09-Semaphore====
    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Solution09-Semaphore--------++++++++");
        new Thread(() -> {
            for (char c : arrInt) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(c);
                semaphore.release();
            }

        }, "Semaphore-Thread-01").start();
        new Thread(() -> {
            for (char c : arrChar) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(c);
                semaphore.release();
            }

        }, "Semaphore-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution09-Semaphore--------++++++++");
    }
}
