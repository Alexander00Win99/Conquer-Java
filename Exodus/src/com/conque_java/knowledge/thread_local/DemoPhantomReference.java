package com.conque_java.knowledge.thread_local;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * 【目标】——掌握强软弱虚引用的应用场景
 * 1) 强引用——对象地址引用；
 * 2) 软引用——缓存；
 * 3) 弱引用——ThreadLocal；
 * 4) 虚引用——管理直接内存：
 *
 * 堆外内存|直接内存
 */
public class DemoPhantomReference {
    private static final List<Object> LIST = new LinkedList<>(); // 堆内内存
    private static final ReferenceQueue<DemoReferenceTypeObject> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {
        // 指向堆外内存的虚引用
        PhantomReference<DemoReferenceTypeObject> phantomReference = new PhantomReference<>(new DemoReferenceTypeObject(), QUEUE);
        System.out.println(phantomReference.get());

        new Thread(() -> {
            while (true) {
                LIST.add(new byte[1024*1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(phantomReference.get());
            }

        }, "THREAD-PHANTOM").start();

        new Thread(() -> {
            while (true) {
                Reference<? extends DemoReferenceTypeObject> polledObject = QUEUE.poll();
                if (phantomReference != null) {
                    System.out.println("虚引用对象已被回收！");
                }
            }
        }, "THREAD-GC").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
