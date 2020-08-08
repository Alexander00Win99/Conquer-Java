package com.conque_java.knowledge.lock;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class DemoCountDownLatch {
    private static final int THREAD_NUM = 9;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDown = new CountDownLatch(THREAD_NUM);
        for(int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                System.out.println(String.format("%s\t%s\t%s", new Date(), Thread.currentThread().getName(), "start!"));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("%s\t%s\t%s", new Date(), Thread.currentThread().getName(), "end!"));
                countDown.countDown();
            }, "Thread " + i).start();
        }
        countDown.await();
        long end = System.currentTimeMillis();
        System.out.println(String.format("%s's duration: %sms",Thread.currentThread().getName() ,(end - start)));
    }
}
