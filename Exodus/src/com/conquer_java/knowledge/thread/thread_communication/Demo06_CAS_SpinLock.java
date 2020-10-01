package com.conquer_java.knowledge.thread.thread_communication;

import java.util.concurrent.TimeUnit;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo06_CAS_SpinLock {
    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    //====Solution06-CAS-SpinLock====
    enum ReadyToRun {T0, T1, T2};
    static volatile ReadyToRun ready = ReadyToRun.T0; // 思考：为何必须volatile
    enum FinishRunning {T0, T1, T2};
    static volatile  FinishRunning finish = FinishRunning.T0; // 思考：为何必须volatile
    //====Solution06-CAS-SpinLock====

    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Solution06-CAS-SpinLock--------++++++++");
        /**
         * synchronized锁升级：偏向锁 -> 自旋锁 -> 重量级锁
         * 偏向锁：在对象头上记录线程ID；场景——只有一个线程使用共享资源
         * 自旋锁：线程不放弃CPU资源，忙等；场景——并发线程数量较少，单个线程的任务代码CPU执行时间较短；
         * 重量级锁：锁竞争加剧，闲等；场景——并发线程数量很多，一个执行，其他位于等待队列
         */
        ready = ReadyToRun.T1;
        new Thread(() -> {
            for (char c : arrInt) {
                while (ready != ReadyToRun.T1) {}
                System.out.println(c);
                if (finish != FinishRunning.T2)
                    ready = ReadyToRun.T2;
            }
            finish = FinishRunning.T1;
        }, "SpinLock-Thread-01").start();
        new Thread(() -> {
            for (char c : arrChar) {
                while (ready != ReadyToRun.T2) {}
                System.out.println(c);
                if (finish != FinishRunning.T1)
                    ready = ReadyToRun.T1;
            }
            finish = FinishRunning.T2;
        }, "SpinLock-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution06-CAS-SpinLock--------++++++++");
    }
}
