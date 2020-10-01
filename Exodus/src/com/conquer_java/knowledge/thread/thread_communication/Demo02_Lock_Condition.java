package com.conquer_java.knowledge.thread.thread_communication;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo02_Lock_Condition {
    private static Thread t1 = null;
    private static Thread t2 = null;

    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * 【Condition VS synchronized】
     * Condition是一把Lock锁的两个或者多个状态(多个队列)，synchronized锁只有单个状态(阻塞队列)；
     * Condition可以精确控制其中处于某个状态(某个队列)的线程，synchronized无法在等待队列中唤醒指定线程；
     *
     * 【场景说明】
     * 考虑生产者消费者模型：
     * 1) 产品队列填满以后，生产者线程condition.await()置于队列conditionProducer，产品队列不满以后，直接condition.signal()通知队列中的生成者；产品队列取空以后，消费者线程condition.await()置于队列conditionConsumer，产品队列不空以后，直接condition.signal()通知队列中的消费者。
     * 2) 产品队列填满以后，生产者线程o.wait()置于等待队列，产品队列不满以后，o.notify()无法保证唤醒线程是生产者线程；产品队列取空以后，消费者线程o.wait()置于等待队列，产品队列不空以后，o.notify()无法保证唤醒线程是消费者线程。
     */
    //====Solution02-Lock+Condition====
    // 本质：使用 [同一把锁Lock + 两个队列Condition] 实现。每个线程获取Lock资源以后，执行打印->唤醒对方->休眠自己
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition1 = lock.newCondition();
    private static Condition condition2 = lock.newCondition();
    //====Solution02-Lock+Condition====

    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Solution02-Lock+Condition--------++++++++");
        CountDownLatch latch = new CountDownLatch(1);
        t1 = new Thread(() -> {
            lock.lock();
            latch.countDown();
            try {
                for (char c : arrInt) {
                    System.out.println(c);
                    System.out.println("线程1 >>>> 持有当前可重入锁的线程数量：" + lock.getHoldCount());
                    if (t2.getState() != Thread.State.TERMINATED) {
                        condition2.signal();
                        condition1.await();
                    }
                }
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "Condition-Thread-01");
        t2 = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                for (char c : arrChar) {
                    System.out.println(c);
                    System.out.println("线程2 >>>> 持有当前可重入锁的线程数量：" + lock.getHoldCount());
                    if (t1.getState() != Thread.State.TERMINATED) {
                        condition1.signal();
                        condition2.await();
                    }
                }
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "Condition-Thread-02");

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution02-Lock+Condition--------++++++++");
    }
}
