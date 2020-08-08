package com.conque_java.knowledge.JVM.Volatile;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Solution01:
 * AtomicStampedReference内部维护对象值和版本号，在创建时，需要传入初始对象值和初始版本号，在修改时，对象值以及状态戳均须满足期望值，才会成功写入。
 *
 * Solution02:
 * AtomicMarkableReference无需关心也就不再记录变量修改次数，只需使用一个boolean变量标记变量是否曾被修改。
 */
public class DemoABA {
    private static final int INIT = 1;
    private static final int TARGET = 999;
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(INIT);
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(INIT, 1);
    private static AtomicMarkableReference<Integer> atomicMarkableReference = new AtomicMarkableReference<Integer>(INIT,false);

    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Solution01-AtomicReference=NOK--------++++++++");
        new Thread(() -> {
            if (atomicReference.compareAndSet(INIT, 0))
                System.out.println("线程一成功修改共享内存变量：1 -> 0");
            if (atomicReference.compareAndSet(0, INIT))
                System.out.println("线程一成功修改共享内存变量：0 -> 1");
        }, "Thread-AtomicReference-01").start();
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicReference.compareAndSet(INIT, TARGET);
            if (atomicReference.get() == TARGET)
                System.out.println("虽然共享内存变量在线程一中存在ABA问题，线程二无法感知到，还是能够成功修改！");
        }, "Thread-AtomicReference-02").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution01-AtomicReference=NOK--------++++++++");

        System.out.println("++++++++--------Begin: Solution02-AtomicStampedReference=OK:版本号--------++++++++");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("线程一拿到的初始版本号：" + stamp);

            try { // 休眠半秒，使得线程二能够获取相同版本号
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (atomicStampedReference.compareAndSet(INIT, 0, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1))
                System.out.println("线程一成功修改共享内存变量：1 -> 0");
            if (atomicStampedReference.compareAndSet(0, INIT, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1))
                System.out.println("线程一成功修改共享内存变量：0 -> 1");
        }, "Thread-AtomicStampedReference-01").start();
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("线程二拿到的初始版本号：" + stamp);

            try { // 休眠一秒，使得线程一完成ABA操作
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("线程二拿到的最新版本号：" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(1, TARGET, stamp, atomicStampedReference.getStamp() + 1);
            //atomicStampedReference.compareAndSet(1, TARGET, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            if (atomicStampedReference.getReference() == INIT)
                System.out.println("操作之后，当前值为：" + atomicStampedReference.getReference());
            if (atomicStampedReference.getReference() != TARGET)
                System.out.println("AtomicStampedReferenc能够感知共享内存变量发生ABA问题，此处无法完成赋值！");
            else
                System.out.println("无法感知共享内存变量发生ABA问题，此处仍然完成赋值！");
        }, "Thread-AtomicStampedReference-02").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution02-AtomicStampedReference=OK:版本号--------++++++++");

        System.out.println("++++++++--------Begin: Solution03-AtomicMarkableReference=OK:布尔值--------++++++++");
        new Thread(() -> {
            if (!atomicMarkableReference.isMarked()) {
                System.out.println(Thread.currentThread().getName() + "：当前变量未被修改");
            } else {
                Thread.currentThread().destroy();
            }

            try { // 休眠半秒，使得线程二能够拿到合法变量
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (atomicMarkableReference.compareAndSet(INIT, 0,atomicMarkableReference.isMarked(),true))
                System.out.println("线程一成功修改共享内存变量：1 -> 0");
            if (atomicMarkableReference.compareAndSet(0, INIT, atomicMarkableReference.isMarked(),true))
                System.out.println("线程一成功修改共享内存变量：0 -> 1");
        }, "Thread-AtomicMarkableReference-01").start();
        new Thread(() -> {
            if (!atomicMarkableReference.isMarked()) {
                System.out.println(Thread.currentThread().getName() + "：当前变量未被修改");
            } else {
                Thread.currentThread().destroy();
            }

            try { // 休眠一秒，使得线程一完成ABA操作
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (atomicMarkableReference.compareAndSet(INIT, TARGET, false, atomicMarkableReference.isMarked()))
                System.out.println(Thread.currentThread().getName() + "：尽管发生ABA问题，依然能够成功修改！");
            else
                System.out.println(Thread.currentThread().getName() + "：发生ABA问题，无法成功修改！");
        }, "Thread-AtomicMarkableReference-02").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution03-AtomicMarkableReference=OK:布尔值--------++++++++");
    }
}
