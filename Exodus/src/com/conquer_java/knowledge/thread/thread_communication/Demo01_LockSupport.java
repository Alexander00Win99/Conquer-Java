package com.conquer_java.knowledge.thread.thread_communication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo01_LockSupport {
    private static Thread t1 = null;
    private static Thread t2 = null;

    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    //====Solution01-LockSupport====
    // 本质：利用同一把锁LockSupport针对两个线程的 加锁park() 和 解锁unpark(t) 来回切换操作实现
    // 线程1：执行打印     解锁对方    锁住自己
    // 线程2：锁住自己     执行打印    解锁对方
    //====Solution01-LockSupport====
    /**
     * concurrent包基于AQS(AbstractQueuedSynchronizer)框架，借助以下两类实现：
     * Unsafe（提供CAS操作）
     * LockSupport（提供park/unpark操作）
     *
     * 两个线程相互之间：利用park()阻塞自己——等待许可；利用unpark()唤醒对方——授予许可
     * [LockSupport]——利用Unsafe的park、unpark实现
     * 具有特点：
     * 1) unpark()操作可以位于park()操作之前，考虑生产者(Producer)和消费者(Consumer)模型，一个线程首先生成授予许可，另一线程上来直接消费许可，然后继续运行；
     * 2) 许可是一次性消费品，无法多次叠加，某个线程连续多次unpark()操作授予许可，另一线程一次park()操作即可全部消费，此时如若再次调用park()就会进入阻塞状态；
     * public static void park() {
     *     UNSAFE.park(false, 0L);
     * }
     * public static void unpark(Thread thread) {
     *     if (thread != null)
     *         UNSAFE.unpark(thread);
     * }
     * [Unsafe]——利用操作系统的线程库实现，使用Parker类里的_counter字段记录“许可”，Parker类继承使用Posix的PlatformParker类的mutex，condition字段实现。
     * public native void park(boolean isAbsolute, long time);
     * public native void unpark(Object var1);
     *
     * 在Linux系统中，使用Posix线程库pthread中的mutex（互斥量）+condition（条件变量）实现：mutex和condition保护了一个名为_counter的变量，当park时，这个变量被设置为0，当unpark时，这个变量被设置为1。
     * class Parker : public os::PlatformParker {
     * private:
     * volatile int _counter;
     *         ...
     * public:
     *         void park(bool isAbsolute, jlong time);
     *         void unpark();
     *         ...
     * }
     * class PlatformParker : public CHeapObj<mtInternal> {
     * protected:
     *         pthread_mutex_t _mutex [1];
     *         pthread_cond_t  _cond  [1];
     *         ...
     * }
     * park过程如下：
     * 首先尝试能否直接拿到“许可”，当_counter>0时，如果成功，则把_counter设置为0，并返回：
     * void Parker::park(bool isAbsolute, jlong time){
     *     // Ideally we'd do something useful while spinning, such
     *     // as calling unpackTime().
     *
     *     // Optional fast-path check:
     *     // Return immediately if a permit is available.
     *     // We depend on Atomic::xchg() having full barrier semantics
     *     // since we are doing a lock-free update to _counter.
     *
     *     if (Atomic::xchg(0,&_counter) > 0) return;
     * 如果失败，则构造一个ThreadBlockInVM，然后检查_counter是否>0，如果是，则把_counter设置为0，unlock mutex并返回：
     *     ThreadBlockInVM tbivm(jt);
     *     if (_counter > 0) { // no wait needed
     *         _counter = 0;
     *         status = pthread_mutex_unlock(_mutex);
     * 否则，继续判断等待的时间，然后再调用pthread_cond_wait函数等待，如果等待返回，则把_counter设置为0，unlock mutex并返回：
     *     if (time == 0) {
     *         status = pthread_cond_wait (_cond, _mutex);
     *     }
     *     _counter = 0;
     *     status = pthread_mutex_unlock(_mutex);
     *     assert_status(status == 0, status, "invariant");
     *     OrderAccess::fence();
     *
     * unpark过程如下：
     * 当unpark时，相对简单多了，直接设置_counter为1，unlock mutex并返回。如果_counter之前的值是0，则还要调用pthread_cond_signal唤醒在park中等待的线程：
     * void Parker::unpark() {
     *     int s, status;
     *     status = pthread_mutex_lock(_mutex);
     *     assert (status == 0, "invariant");
     *     s = _counter;
     *     _counter = 1;
     *     if (s < 1) {
     *         if (WorkAroundNPTLTimedWaitHang) {
     *             status = pthread_cond_signal (_cond);
     *             assert (status == 0, "invariant");
     *             status = pthread_mutex_unlock(_mutex);
     *             assert (status == 0, "invariant");
     *         } else {
     *             status = pthread_mutex_unlock(_mutex);
     *             assert (status == 0, "invariant");
     *             status = pthread_cond_signal (_cond);
     *             assert (status == 0, "invariant");
     *         }
     *     } else {
     *         pthread_mutex_unlock(_mutex);
     *         assert (status == 0, "invariant");
     *     }
     * }
     *
     */

    /**
     * LockSupport的park()、unpark()和synchronized锁的wait()、notify()的区别在于：
     * 一个是static静态方法，一个是对象实例方法；
     * 1) 前者没有顺序要求，park()或者unpark()均可置首；后者要求必须首先获取synchronized锁，然后获取之后，同样必须保证notify() 位于>之前 wait()；
     * 2) park()上锁当前线程，unpark(thread)解锁指定线程；notify()|notify()是从当前锁的等待队列中随机唤醒一个线程尝试获取锁或者唤醒所有等待线程竞争锁资源，wait()是当前持锁线程放弃锁并置等待队列。
     */

    public static void main(String[] args) throws IOException {
        System.out.println("++++++++--------Begin: Solution01-LockSupport--------++++++++");
        t1 = new Thread(() -> {
            for (char c : arrInt) {
                System.out.println(c);
                System.out.println("本端线程1当前状态：" + Thread.currentThread().getState());
                System.out.println("对端线程2当前状态：" + t2.getState());
                if (t2.getState() != Thread.State.TERMINATED) {
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
            LockSupport.unpark(t2);
        }, "LockSupport-Thread-01");
        t2 = new Thread(() -> {
            for (char c : arrChar) {
                if (t1.getState() != Thread.State.TERMINATED) LockSupport.park();
                System.out.println(c);
                System.out.println("本端线程2当前状态：" + Thread.currentThread().getState());
                System.out.println("对端线程1当前状态：" + t1.getState());
                if (t1.getState() != Thread.State.TERMINATED) {
                    LockSupport.unpark(t1);
                }
            }
            LockSupport.unpark(t1);
        }, "LockSupport-Thread-02");

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution01-LockSupport--------++++++++");
    }
}
