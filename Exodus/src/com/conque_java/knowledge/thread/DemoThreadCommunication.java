package com.alex_java.algorithm.MaShibing_ZuoChengyun.DP;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程间通信：线程执行到某个阶段时或者处于某种状态时，需要使用某种方式通知另外一个或者一些线程。
 */
public class DemoThreadCommunication {
    //====Solution01-LockSupport====
    private static Thread t1 = null;
    private static Thread t2 = null;
    //====Solution01-LockSupport====

    //====Solution02-SpinLock====
    enum ReadyToRun {T1, T2};
    static volatile ReadyToRun ready = ReadyToRun.T1; // 思考：为何必须volatile
    //====Solution02-SpinLock====

    //====Solution03-AtomicInteger====
    static AtomicInteger threadNo = new AtomicInteger(1);
    //====Solution03-AtomicInteger====

    //====Solution04-BlockingQueue====
    /**
     * 使用两个变量相互阻塞等待实现：BlockingQueue：take()方法阻塞执行(取不出来) + put()方法阻塞执行(放不进去)
     */
    static BlockingQueue<String> q1 = new ArrayBlockingQueue<>(1);
    static BlockingQueue<String> q2 = new ArrayBlockingQueue<>(1);
    //====Solution04-BlockingQueue====

    //====Solution05-PipedInputStream+PipedOutputStream====
    /**
     * 利用read()或者write()的阻塞操作特性，效率很低！
     */
    //====Solution05-PipedInputStream+PipedOutputStream====

    //====Solution06-AtomicInteger====
    //====Solution06-AtomicInteger====

    //====Solution07-AtomicInteger====
    //====Solution07-AtomicInteger====

    //====Solution08-AtomicInteger====
    //====Solution08-AtomicInteger====

    public static void main(String[] args) throws IOException {
        char[] arrInt = "1234567".toCharArray();
        char[] arrChar = "abcdefg".toCharArray();
        System.out.println("++++++++--------Begin: Solution01-LockSupport的park()和unpark()--------++++++++");
        t1 = new Thread(() -> {
            for (char c : arrInt) {
                System.out.println(c);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "LockSupport-Thread-01");
        t2 = new Thread(() -> {
            for (char c : arrChar) {
                LockSupport.park();
                System.out.println(c);
                LockSupport.unpark(t1);
            }
        }, "LockSupport-Thread-02");

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution01-LockSupport的park()和unpark()--------++++++++");

        System.out.println("++++++++--------Begin: Solution02-SpinLock--------++++++++");
        /**
         * synchronized锁升级：偏向锁 -> 自旋锁 -> 重量级锁
         * 偏向锁：在对象头上记录线程ID；场景——
         * 自旋锁：线程不放弃CPU资源，空转；场景——并发线程数量较少，单个线程的任务代码CPU执行时间较短；
         * 重量级锁：场景——并发线程数量较多，一个执行，其他位于等待队列
         */
        new Thread(() -> {
            for (char c : arrInt) {
                while (ready != ReadyToRun.T1) {}
                System.out.println(c);
                ready = ReadyToRun.T2;
            }
        }, "SpinLock-Thread-01").start();
        new Thread(() -> {
            for (char c : arrChar) {
                while (ready != ReadyToRun.T2) {}
                System.out.println(c);
                ready = ReadyToRun.T1;
            }
        }, "SpinLock-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution02-SpinLock--------++++++++");

        System.out.println("++++++++--------Begin: Solution03-AtomicInteger--------++++++++");
        new Thread(() -> {
            for (char c : arrInt) {
                while (threadNo.get() != 1) {}
                System.out.println(c);
                threadNo.incrementAndGet();
                //threadNo.set(2);
            }
        }, "AtomicInteger-Thread-01").start();
        new Thread(() -> {
            for (char c : arrChar) {
                while (threadNo.get() != 2) {}
                System.out.println(c);
                threadNo.decrementAndGet();
                //threadNo.set(1);
            }
        }, "AtomicInteger-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution03-AtomicInteger--------++++++++");

        System.out.println("++++++++--------Begin: Solution04-BlockingQueue--------++++++++");
        new Thread(() -> {
            for (char c : arrInt) {
                System.out.println(c);
                try {
                    q1.put("Thread01-OK");
                    q2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BlockingQueue-Thread-01").start();
        new Thread(() -> {
            for (char c : arrChar) {
                try {
                    q1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(c);
                try {
                    q2.put("Thread02-OK");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BlockingQueue-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution04-BlockingQueue--------++++++++");

        System.out.println("++++++++--------Begin: Solution05-PipedStream--------++++++++");
        PipedInputStream input1 = new PipedInputStream();
        PipedInputStream input2 = new PipedInputStream();
        PipedOutputStream output1 = new PipedOutputStream();
        PipedOutputStream output2 = new PipedOutputStream();

        input1.connect(output2);
        input2.connect(output1);

        String msg = "Your turn:";

        new Thread(() -> {
            byte[] buffer = new byte[9];

            for (char c : arrInt) {
                while (ready != ReadyToRun.T1) {}
                System.out.println(c);
                ready = ReadyToRun.T2;
            }
        }, "PipedStream-Thread-01").start();
        new Thread(() -> {
            byte[] buffer = new byte[9];

            for (char c : arrChar) {
                try {
                    input1.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                if (new String) {}
            }
        }, "PipedStream-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution05-PipedStream--------++++++++");

        System.out.println("++++++++--------Begin: Solution01-LockSupport的park()和unpark()--------++++++++");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution01-LockSupport的park()和unpark()--------++++++++");

        System.out.println("++++++++--------Begin: Solution01-LockSupport的park()和unpark()--------++++++++");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution01-LockSupport的park()和unpark()--------++++++++");

        System.out.println("++++++++--------Begin: Solution01-LockSupport的park()和unpark()--------++++++++");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution01-LockSupport的park()和unpark()--------++++++++");
    }
}