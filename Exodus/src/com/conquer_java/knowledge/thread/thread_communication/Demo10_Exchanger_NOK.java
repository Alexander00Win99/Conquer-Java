package com.conquer_java.knowledge.thread.thread_communication;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo10_Exchanger_NOK {
    private static Thread t1 = null;
    private static Thread t2 = null;

    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    //====Solution10-Exchanger====
    private static Exchanger<Character> exchanger = new Exchanger<>();
    //====Solution10-Exchanger====
    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Solution10-Exchanger--------++++++++");
        CountDownLatch latchNew = new CountDownLatch(1);
        new Thread(() -> {
            for (char c : arrInt) {
                try {
                    exchanger.exchange(c);
                    System.out.println(exchanger.exchange(c));
                    System.out.println("当前线程：" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "Exchanger-Thread-01").start();
        new Thread(() -> {
            for (char c : arrChar) {
                try {
                    System.out.println(exchanger.exchange(c));
                    System.out.println("当前线程：" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "Exchanger-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution10-Exchanger--------++++++++");
    }
}
