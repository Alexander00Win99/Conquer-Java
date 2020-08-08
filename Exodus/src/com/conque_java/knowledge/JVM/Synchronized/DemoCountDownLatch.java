package com.conque_java.knowledge.JVM.Synchronized;

import java.util.concurrent.*;

/**
 * CountDownLatch锁存器工作场景(用于线程间同步或称线程间通信，而非线程互斥)：
 * 1) 某个线程需要等待其他线程完成之后才能运行，初始设置new CountDownLatch(n)锁存器计数器为n，线程await()阻塞等待其他线程完成任务，
 * 每个条件线程完成任务，调用countDown()减一，直至为0，阻塞线程唤醒执行。例如：某个服务的主线程需要等待所有组件加载完毕才能继续执行。
 * 2) 实现多个线程之间执行任务的最大并行性(注意，不是并发)，初始化共享变量new CountDownLatch(1)，多个线程同时await()阻塞等待锁存器，
 * 主线程调用countDown()，所有线程同时唤醒执行(类似跑步运动)。
 *
 * CountDownLatch锁存器缺点：
 * 锁存器初始值执行过程中无法修改；锁存器使用完毕，无法再次使用(CyclicBarrier可以重复利用)。
 */
public class DemoCountDownLatch {
    private static final int SUB_THREAD_NUM = 3;
    private static ExecutorService service = Executors.newFixedThreadPool(SUB_THREAD_NUM);
    private static final CountDownLatch latch = new CountDownLatch(SUB_THREAD_NUM);

    private static final int COMPETITOR_NUM = 8;
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static final CountDownLatch umpire = new CountDownLatch(1);
    private static final CountDownLatch athlete = new CountDownLatch(COMPETITOR_NUM);

    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Scenario01--------++++++++");
        for (int i = 0; i < SUB_THREAD_NUM; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                    try {
                        Thread.sleep((long)(1000 * 10 * Math.random()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("子线程" + Thread.currentThread().getName() + "执行结束");
                    latch.countDown();
                }
            };
            service.execute(runnable);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long begin = System.currentTimeMillis();
        System.out.println("主线程" + Thread.currentThread().getName() + "等待子线程执行完毕......");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程" + Thread.currentThread().getName() + "开始执行......");
        System.out.println("主线程" + Thread.currentThread().getName() + "当前状态：" + Thread.currentThread().getState());
        long end = System.currentTimeMillis();
        System.out.println("主线程等待子线程时间：" + (end - begin));
        service.shutdown();
        System.out.println("++++++++--------End: Scenario01--------++++++++");

        System.out.println("++++++++--------Begin: Scenario02--------++++++++");
        for (int i = 0; i < COMPETITOR_NUM; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发出口令");
                    try {
                        umpire.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("选手" + Thread.currentThread().getName() + "听到裁判发出口令，立刻起跑");
                    athlete.countDown();
                }
            };
            executorService.execute(runnable);
        }

        try {
            Thread.sleep((long)(1000 * 10 * Math.random()));
            System.out.println("裁判" + Thread.currentThread().getName() + "预备发出口令");
            umpire.countDown();
            System.out.println("裁判" + Thread.currentThread().getName() + "发出口令，等待选手跑完赛道");
            athlete.await();
            Thread.sleep(1000 * 10);
            System.out.println("所有选手均已触线，裁判" + Thread.currentThread().getName() + "宣布成绩");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("++++++++--------End: Scenario02--------++++++++");
    }
}
