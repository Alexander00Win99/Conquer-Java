package com.conquer_java.knowledge.lock;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;

public class DemoCyclicBarrier {
    private static final int THREAD_NUM = 9;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(THREAD_NUM);

        for(int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                System.out.println(String.format("%s\t%s\t%s", new Date(), Thread.currentThread().getName(), "start!"));
                try {
                    // 每个线程都会等待所有线程都已执行await()方法，然后所有等待线程都被唤醒从而继续执行。
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + "is doing......");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("%s\t%s\t%s", new Date(), Thread.currentThread().getName(), "end!"));
            }, "Thread-" + i).start();
        }
    }
}
