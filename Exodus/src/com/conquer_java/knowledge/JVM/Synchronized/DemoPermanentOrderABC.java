package com.conquer_java.knowledge.JVM.Synchronized;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DemoPermanentOrderABC implements Runnable {
    private String name;
    private Object pre;
    private Object self;
    private static final int COUNT = 10;
    private static final CountDownLatch latch = new CountDownLatch(COUNT * 3);

    public DemoPermanentOrderABC(String name, Object pre, Object self) {
        this.name = name;
        this.pre = pre;
        this.self = self;
    }

    @Override
    public void run() {
        int count = COUNT;
        while (count > 0) {
            synchronized (pre) {
                synchronized (self) {
                    System.out.println("loop: " + count + ", Thread-" + Thread.currentThread().getName() + ": " + name);
                    count--;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    self.notify();
                    latch.countDown();
                }
                try {
                    pre.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        Thread t1 = new Thread(new DemoPermanentOrderABC("A", c, a), "t1");
        Thread t2 = new Thread(new DemoPermanentOrderABC("B", a, b), "t2");
        Thread t3 = new Thread(new DemoPermanentOrderABC("C", b, c), "t3");
        Thread shutdown = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Before shutdown:");
            System.out.println(t1.getName() + ": " + t1.getState());
            System.out.println(t2.getName() + ": " + t2.getState());
            System.out.println(t3.getName() + ": " + t3.getState());
            /**
             * 需要synchronized (o) {}，否则报错“java.lang.IllegalMonitorStateException”
             */
            synchronized (a) {
                a.notify();
            }
            synchronized (b) {
                b.notify();
            }
            synchronized (c) {
                c.notify();
            }
            System.out.println("After shutdown:");
            System.out.println(t1.getName() + ": " + t1.getState());
            System.out.println(t2.getName() + ": " + t2.getState());
            System.out.println(t3.getName() + ": " + t3.getState());
        });

        shutdown.start();
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        t3.start();
        TimeUnit.SECONDS.sleep(1);
    }
}
