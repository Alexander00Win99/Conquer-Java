package com.conquer_java.knowledge.thread.thread_communication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo08_BlockingQueue {
    private static Thread t1 = null;
    private static Thread t2 = null;

    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * 使用两个变量相互阻塞等待实现：ArrayBlockingQueue：take()方法(阻塞执行-取不出来) + put()方法(阻塞执行-放不进去)
     * 线程1  ——  打印  ->  先放  ->  后取；
     * 线程2  ——  先取  ->  打印  ->  后放；
     */
    //====Solution08-BlockingQueue====
    static BlockingQueue<String> q1 = new ArrayBlockingQueue<>(1);
    static BlockingQueue<String> q2 = new ArrayBlockingQueue<>(1);
    static volatile boolean t1Finished = false;
    static volatile boolean t2Finished = false;
    //====Solution08-BlockingQueue====
    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Solution08-BlockingQueue--------++++++++");
        t1 = new Thread(() -> {
            for (char c : arrInt) {
                System.out.println(c);

                try {
                    if (!t2Finished) {
                        q1.put("Thread01-Ready");
                        q2.take();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            t1Finished = true;
            if (q1.size() == 0 && t2.getState() == Thread.State.WAITING) {
                try {
                    q1.put("Thread01-Done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BlockingQueue-Thread-01");
        t2 = new Thread(() -> {
            for (char c : arrChar) {
                try {
                    if (!t1Finished)
                        q1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(c);

                try {
                    if (!t1Finished) {
                        q2.put("Thread02-Ready");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            t2Finished = true;
            if (q2.size() == 0 && t1.getState() == Thread.State.WAITING) {
                try {
                    q2.put("Thread02-Done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BlockingQueue-Thread-02");

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution08-BlockingQueue--------++++++++");
    }
}
