package com.conquer_java.knowledge.JVM.Synchronized;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1) AtomicInteger.incrementAndGet()调用Unsafe.getAndAddInt()
 * public final int incrementAndGet() {
 *     return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
 * }
 *
 * 2) Unsafe.getAndAddInt()调用Unsafe.compareAndSwapInt()
 * public final int getAndAddInt(Object var1, long var2, int var4) {
 *     int var5;
 *     do {
 *         var5 = this.getIntVolatile(var1, var2);
 *     } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
 *
 *     return var5;
 * }
 *
 * 3) Unsafe.compareAndSwapInt()是native方法
 * public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);
 *
 * 4) C++的unsafe.cpp中CAS代码实现
 * UNSAFE_ENTRY(jboolean, Unsafe_CompareAndSwapInt(JNIEnv *env, jobject unsafe, jobject obj, jlong offset, jint e, jint x))
 *   UnsafeWrapper("Unsafe_CompareAndSwapInt");
 *   oop p = JNIHandles::resolve(obj);
 *   jint* addr = (jint *) index_oop_from_field_offset_long(p, offset);
 *   return (jint)(Atomic::cmpxchg(x, addr, e)) == e;
 * UNSAFE_END
 *
 * 5) Linux内核中基于具体CPU平台U的系统实现jdk8u：atomic_linux_x86.inline.hpp——CPU指令：__asm__ volatile (LOCK_IF_MP(%4) "cmpxchgl %1,(%3)"
 * 汇编指令cmpxchg本身并非原子操作
 * inline jint  Atomic::cmpxchg(jint exchange_value, volatile jint* dest, jint  compare_value) {
 *   int mp = os::is_MP();
 *   __asm__ volatile (LOCK_IF_MP(%4) "cmpxchgl %1,(%3)"
 *                     : "=a" (exchange_value)
 *                     : "r" (exchange_value), "a" (compare_value), "r" (dest), "r" (mp)
 *                     : "cc", "memory");
 *   return exchange_value;
 * }
 *
 * 6) 对于Multi Processor(多核CPU)，在执行cmpxchg指令时需要提前添加加锁lock指令，lock指令在执行后面指令时，锁定一个北桥信号(不是锁总线的方式)，以此保证cmpxchg指令的原子性。
 * #define LOCK_IF_MP(mp) "cmp $0, " #mp "; je 1f; lock; 1: "
 */
public class DemoSynchronized {
    private final static int THREAD_NUM = 1000;
    private final static int LOOP = 1000;

    private static int c = 0;
    private static int cnt = 0;
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_NUM];
        CountDownLatch latch = new CountDownLatch(THREAD_NUM);

        Object o = new Object();

        for (int i = 0; i < THREAD_NUM; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < LOOP; j++) {
                    c++;
                    synchronized (o) {
                        cnt++;
                    }
                    count.incrementAndGet(); // CAS操作，无须加锁synchronized
                }
                latch.countDown();
            }, "Thread" + i);
        }

        Arrays.stream(threads).forEach((t) -> t.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(c);
        System.out.println(cnt);
        System.out.println(count);
    }
}
