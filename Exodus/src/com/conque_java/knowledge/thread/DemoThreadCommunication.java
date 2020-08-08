package com.conque_java.knowledge.thread;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程间通信：线程执行到某个动作时，需要使用某种方式通知另外一个或者一些线程。
 * 最好解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class DemoThreadCommunication {
    private static Thread t1 = null;
    private static Thread t2 = null;

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
     * 使用两个变量相互阻塞等待实现：ArrayBlockingQueue：take()方法(阻塞执行-取不出来) + put()方法(阻塞执行-放不进去)
     * 线程1——打印输出-先放-后取；线程2——先取-打印输出-后放；
     */
    static BlockingQueue<String> q1 = new ArrayBlockingQueue<>(1);
    static BlockingQueue<String> q2 = new ArrayBlockingQueue<>(1);
    //====Solution04-BlockingQueue====

    //====Solution05-PipedStream====
    /**
     * 利用Stream类的read()、write()阻塞操作实现
     * PipedStream——效率太低，仅仅用于学术实验，无法应用生成环境：两个线程的InputStream和OutputStream连接起来以后，可以实现单向信息传递。
     */
    private static PipedInputStream input1 = new PipedInputStream();
    private static PipedInputStream input2 = new PipedInputStream();
    private static PipedOutputStream output1 = new PipedOutputStream();
    private static PipedOutputStream output2 = new PipedOutputStream();
    //====Solution05-PipedStream====

    //====Solution06-Synchronized====
    private static final Object o = new Object();
    /**
     * 线程2初始陷入while (isT1PriorToT2) {o.wait();}死循环进行CAS，线程1运行之后isT1PriorToT2置false，致使线程2跳出死循环往下执行。
     */
    private static volatile boolean isT1PriorToT2 = true;
    private static CountDownLatch latch = new CountDownLatch(1);
    //====Solution06-Synchronized====

    //====Solution07-Condition====
    /**
     * Condition VS synchronized：
     * Condition是一把Lock锁的两个或者多个状态(条件)，synchronized锁只有单个状态；
     * Condition可以精确控制其中处于某个状态(条件)的线程，synchronized无法指定唤醒等待队列中具体哪个线程；
     */
    private static Lock lock = new ReentrantLock();
    private static Condition condition1 = lock.newCondition();
    private static Condition condition2 = lock.newCondition();
    //====Solution07-Condition====

    //====Solution08-Semaphore====
    private static Semaphore semaphore = new Semaphore(1);
    //====Solution08-Semaphore====

    public static void main(String[] args) throws IOException {
        char[] arrInt = "1234567".toCharArray();
        char[] arrChar = "abcdefg".toCharArray();

        System.out.println("++++++++--------Begin: Solution01-LockSupport--------++++++++");
        t1 = new Thread(() -> {
            for (char c : arrInt) {
                System.out.println(c);
                System.out.println("线程1当前状态：" + Thread.currentThread().getState());
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "LockSupport-Thread-01");
        t2 = new Thread(() -> {
            for (char c : arrChar) {
                LockSupport.park();
                System.out.println(c);
                System.out.println("线程2当前状态：" + Thread.currentThread().getState());
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
        System.out.println("++++++++--------End: Solution01-LockSupport--------++++++++");

        System.out.println("++++++++--------Begin: Solution02-SpinLock--------++++++++");
        /**
         * synchronized锁升级：偏向锁 -> 自旋锁 -> 重量级锁
         * 偏向锁：在对象头上记录线程ID；场景——只有一个线程使用共享资源
         * 自旋锁：线程不放弃CPU资源，空转；场景——并发线程数量较少，单个线程的任务代码CPU执行时间较短；
         * 重量级锁：场景——并发线程数量很多，一个执行，其他位于等待队列
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
        new Thread(() -> { // 先放后取
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
        new Thread(() -> { // 先取后放
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
        input1.connect(output2);
        input2.connect(output1);

        String msg = "Your turn:"; // 注意：线程之中的buffer大小必须等于此处字符串长度

        new Thread(() -> { // 先写后读
            byte[] buffer = new byte[10];

            try {
                for (char c : arrInt) {
                    System.out.println(c);
                    output1.write(msg.getBytes());
                    input1.read(buffer);
                    //System.out.println("当前buffer: " + buffer + "，内容：" + new String(buffer));
                    //if (new String(buffer).equals(msg)) // 貌似并非必须
                    //continue; // 貌似并非必须
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "PipedStream-Thread-01").start();
        new Thread(() -> { // 先读后写
            byte[] buffer = new byte[10];

            try {
                for (char c : arrChar) {
                    input2.read(buffer);
                    //System.out.println("当前buffer: " + buffer + "，内容：" + new String(buffer));
                    if (new String(buffer).equals(msg))
                        System.out.println(c);
                    output2.write(msg.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "PipedStream-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(13);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution05-PipedStream--------++++++++");

        System.out.println("++++++++--------Begin: Solution06-Synchronized--------++++++++");
        new Thread(() -> {
            latch.countDown();
            synchronized (o) { // 此处synchronized(this)出错：this指代各个线程本身，显然无法达到同步目的。
                for (char c : arrInt) {
                    System.out.println(c);
//                    isT1PriorToT2 = false;
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("线程" + Thread.currentThread().getName() + "当前状态：" + Thread.currentThread().getState());
                o.notify(); // 必须存在，否则最终两个线程总会存在其中一个(唤醒别人却无人唤醒者)处于等待队列，程序无法结束。
            }
        }, "Synchronized-Thread-01").start();
        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o) { // 此处synchronized(this)出错：this指代各个线程本身，显然无法达到同步目的。
//                while (isT1PriorToT2) {
//                    try {
//                        o.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                for (char c : arrChar) {
                    System.out.println(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("线程" + Thread.currentThread().getName() + "当前状态：" + Thread.currentThread().getState());
                o.notify(); // 必须存在，否则最终两个线程总会存在其中一个(唤醒别人却无人唤醒者)处于等待队列，程序无法结束。
            }
        }, "Synchronized-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution06-Synchronized--------++++++++");

        System.out.println("++++++++--------Begin: Solution07-Condition--------++++++++");
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            latch.countDown();
            lock.lock();
            try {
                for (char c : arrInt) {
                    System.out.println(c);
                    //System.out.println("持有当前可重入锁的线程数量：" + lock.getHoldCount());
                    condition2.signal();
                    condition1.await();
                }
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "Condition-Thread-01").start();
        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                for (char c : arrChar) {
                    System.out.println(c);
                    //System.out.println("持有当前可重入锁的线程数量：" + lock.getHoldCount());
                    condition1.signal();
                    condition2.await();
                }
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "Condition-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution07-Condition--------++++++++");

        System.out.println("++++++++--------Begin: Solution08-Semaphore--------++++++++");
        CountDownLatch latchNew = new CountDownLatch(1);
        new Thread(() -> {
            for (char c : arrInt) {
                try {
                    semaphore.acquire();
                    System.out.println(c);
                    System.out.println("当前线程：" + Thread.currentThread().getName());
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "Semaphore-Thread-01").start();
        new Thread(() -> {
            for (char c : arrChar) {
                try {
                    semaphore.acquire();
                    System.out.println(c);
                    System.out.println("当前线程：" + Thread.currentThread().getName());
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "Semaphore-Thread-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution08-Semaphore--------++++++++");
    }
}
