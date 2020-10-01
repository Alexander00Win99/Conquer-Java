package com.conquer_java.knowledge.thread.thread_communication;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo04_Synchronized_CAS {
    private static Thread t1 = null;
    private static Thread t2 = null;

    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    //====Solution04-Synchronized-CAS====
    /**
     * 线程2初始陷入while (canStart) {o.wait();}死循环进行CAS，线程1运行之后isT1PriorToT2置false，致使线程2跳出死循环往下执行。
     */
    private static volatile boolean canStart = false;
    private static final Object o = new Object();
    //====Solution04-Synchronized-CAS====

    /**
     * 【synchronized和ReentrantLock的区别】
     * 1) 两者都是可重入锁；
     * 2) synchronized依赖JVM实现，ReentrantLock依赖JDK API实现；
     * 3) 前者等待若无唤醒不可中断，后者通过lock.lockInterruptibly()可以实现等待可中断机制；
     * 4) 前者是非公平锁，后者默认非公平锁，可以通过ReentrantLock(boolean fair)构造公平锁（先到先得）；
     * 5) 前者基于对象的Moniter对象实现对象监视器，也即管程锁，每个锁只有一个同步队列+一个等待队列，使用wait+notify|notifyAll实现等待通知机制；后者通过Condition condition = lock.newCondition()，
     * 可以创建多个ConditionObject（对象监视器，AQS内部类，能够访问AQS同步器方法），每个锁拥有一个同步队列+多个等待队列，使用Condition.await()实现不同线程注册不同等待队列，使用Condition.signal()实现唤醒指定位于等待队列中的线程，从而确保对于线程的精确控制、定向调度。
     *
     * 【ReentrantLock的本质：CAS+AQS】
     * 1) ReentrantLock有三个内部类：Sync、FairSync、NonfairSync。其中FairSync和NonfairSync继承了Sync，Sync继承了AbstractQueuedSynchronizer。AbstractQueuedSynchronizer可以看做是给Java工程师提供的一个控制并发的模板类。
     *
     * abstract static class Sync extends AbstractQueuedSynchronizer {
     *     private static final long serialVersionUID = -5179523762034025860L;
     *
     *     // Performs {@link Lock#lock}. The main reason for subclassing is to allow fast path for nonfair version.
     *     // 抽象方法，非公平锁和公平锁实现lock的方式不同
     *     abstract void lock();
     *
     *     // Performs non-fair tryLock.  tryAcquire is implemented in subclasses, but both need nonfair try for trylock method.
     *     final boolean nonfairTryAcquire(int acquires) {
     *         final Thread current = Thread.currentThread();
     *         int c = getState();
     *         if (c == 0) {
     *             if (compareAndSetState(0, acquires)) { //核心方法，原子操作修改state
     *                 setExclusiveOwnerThread(current);
     *                 return true;
     *             }
     *         }
     *         else if (current == getExclusiveOwnerThread()) {
     *             int nextc = c + acquires;
     *             if (nextc < 0) // overflow
     *                 throw new Error("Maximum lock count exceeded");
     *             setState(nextc);
     *             return true;
     *         }
     *         return false;
     *     }
     *
     *     protected final boolean tryRelease(int releases) {
     *         int c = getState() - releases;
     *         if (Thread.currentThread() != getExclusiveOwnerThread())
     *             throw new IllegalMonitorStateException();
     *         boolean free = false;
     *         if (c == 0) {
     *             free = true;
     *             setExclusiveOwnerThread(null);
     *         }
     *         setState(c);
     *         return free;
     *     }
     *
     *     protected final boolean isHeldExclusively() {
     *         // While we must in general read state before owner,
     *         // we don't need to do so to check if current thread is owner
     *         return getExclusiveOwnerThread() == Thread.currentThread();
     *     }
     *
     *     final ConditionObject newCondition() {
     *         return new ConditionObject();
     *     }
     *
     *     // Methods relayed from outer class
     *
     *     final Thread getOwner() {
     *         return getState() == 0 ? null : getExclusiveOwnerThread();
     *     }
     *
     *     final int getHoldCount() {
     *         return isHeldExclusively() ? getState() : 0;
     *     }
     *
     *     final boolean isLocked() {
     *         return getState() != 0;
     *     }
     *
     *     // Reconstitutes the instance from a stream (that is, deserializes it).
     *     private void readObject(java.io.ObjectInputStream s)
     *             throws java.io.IOException, ClassNotFoundException {
     *         s.defaultReadObject();
     *         setState(0); // reset to unlocked state
     *     }
     * }
     *
     * 2) 公平锁相比非公平锁增加判断hasQueuedPredecessors()，公平锁会首先判断当前执行线程是否位于队列头部。
     */
    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Solution04-Synchronized-CAS--------++++++++");
        t1 = new Thread(() -> {
            synchronized (o) { // 此处synchronized(this)出错：this指代各个线程本身，显然无法达到同步目的。
                for (char c : arrInt) {
                    System.out.println(c);
                    if (!canStart) canStart = true;
                    if (t2.getState() != Thread.State.TERMINATED) {
                        try {
                            o.notify();
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                o.notify(); // 必须存在，保证首先执行完毕处于TEMINATED状态的线程能够唤醒尚位于等待队列中另一线程。
            }
        }, "Synchronized-Thread-01");
        t2 = new Thread(() -> {
            synchronized (o) { // 此处synchronized(this)出错：this指代各个线程本身，显然无法达到同步目的。
                while (!canStart) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (char c : arrChar) {
                    System.out.println(c);
                    if (t1.getState() != Thread.State.TERMINATED) {
                        try {
                            o.notify();
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                o.notify(); // 必须存在，保证首先执行完毕处于TEMINATED状态的线程能够唤醒尚位于等待队列中另一线程。
            }
        }, "Synchronized-Thread-02");

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution04-Synchronized-CAS--------++++++++");
    }
}
