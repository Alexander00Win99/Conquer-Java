package com.conquer_java.knowledge.thread;

import com.conquer_java.knowledge.JSR133.UnsafeInstance;
import sun.misc.Unsafe;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * AbstractQueuedSynchronizer通常作为静态内部类使用，参见：ReentrantLock
 */
public class MyAbstractQueuedSynchronizer {
    /**
     * 当前加锁状态（记录加锁次数，volatile保证多线程之间的可见性）
     */
    private volatile int state = 0;
    /**
     * 当前持有锁的线程
     */
    private Thread lockHolder;
    /**
     * 当前锁的等待队列
     */
    private ConcurrentLinkedQueue<Thread> queue = new ConcurrentLinkedQueue<>();
    private static final Unsafe unsafe = UnsafeInstance.getUnsafeByReflect(); // 反射获取Unsafe实例
    private static final long stateOffset; // 报错：Variable 'stateOffset' might not have been initialized

    static {
//        stateOffset = 0L; // 静态常量重复赋值，报错：Variable 'stateOffset' might already have been assigned to
        try {
            stateOffset = unsafe.objectFieldOffset(MyAbstractQueuedSynchronizer.class.getDeclaredField("state")); // 获取state属性相对对象内存空间起始位置的地址偏置
//        } catch (NoSuchFieldException e) { // NoSuchFieldException/Exception异常的处理方式仅仅"e.printStackTrace();"不能保证stateOffset一定赋值
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Thread getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(Thread lockHolder) {
        this.lockHolder = lockHolder;
    }

    /**
     * 加锁
     */
    public void lock() { // lock处理锁的公平逻辑
        Thread current = Thread.currentThread();
        // 1) CAS原子操作获取锁
        if ((queue.size() == 0 || current == queue.peek()) && tryAquire()) return; // 队列为空或者队首线程才有机会获取锁

        // 2) 在当前方法中停留
        queue.add(current); // 新来线程，未能获取锁，放入等待队列
        for ( ; ; ) {
            if (current == queue.peek() && tryAquire()) { // 保证等待队列队首线程，LockSupport.unpark(head)唤醒以后尝试获取锁
                queue.poll();
                return;
            }
            // 阻塞线程(减轻CPU资源消耗——单纯for/while循环idle空转占有大量CPU资源)，无人唤醒，1s以后自动唤醒
            LockSupport.parkNanos(current, 1000*1000*1000);
        }

        // 3) 锁被释放以后，能被再次获取

    }

    /**
     * 解锁
     */
    public void unlock() { // lock处理锁的公平逻辑：唤醒队首线程
        Thread current = Thread.currentThread();
        if (current != lockHolder)
            throw new RuntimeException("非锁持有者，无权释放锁！");
        if (tryRelease()) {
            Thread head = queue.peek();
            if (head != null)
                LockSupport.unpark(head); // 解锁成功，唤醒队首线程
            return;
        }
    }

    private boolean tryAquire() {
        Thread t = Thread.currentThread();
        int state = getState(); // 锁未占用，当前状态是0
        if (state == 0) { // 修改锁状态——CAS原子操作: 0 -> 1
            if (compareAndSwapState(0, 1)) {
                setLockHolder(t); // 一旦CAS成功，锁的持有者置为当前线程
                System.out.println("线程" + t.getName() + "成功获取锁！");
                return true;
            }
        }
        return false;
    }

    private boolean tryRelease() {
        Thread t = Thread.currentThread();
        int state = getState(); // 锁被占用，当前状态是1
        if (state == 1) { // 修改锁状态——CAS原子操作: 1 -> 0
            if (compareAndSwapState(1, 0)) {
                setLockHolder(null); // 一旦CAS成功，锁的持有者置为null
                System.out.println("线程" + t.getName() + "成功释放锁！");
                return true;
            }
        }
        return false;
    }

    /**
     * CAS原子操作
     * @param oldValue 线程栈工作内存的旧值
     * @param newValue 即将替换成为的新值
     * @return
     */
    private boolean compareAndSwapState(int oldValue, int newValue) {
        return unsafe.compareAndSwapInt(this, stateOffset, oldValue, newValue);
    }

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        System.out.println(lock.isFair());
        lock.unlock();
    }
}
