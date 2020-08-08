package com.conque_java.knowledge.thread_local;

import java.io.IOException;

/**
 * 【目标】——掌握强软弱虚引用的应用场景
 * 1) 强引用——对象地址引用；
 * 2) 软引用——缓存；
 * 3) 弱引用——ThreadLocal；
 * 4) 虚引用——管理直接内存：
 *
 * JVM启动以后，应用程序进程的main线程和GC线程属于不同线程，因此，main线程打印输出demo对象和GC线程调用finalize()方法产生打印输出顺序不定。
 * 因此为了避免应用程序进程首先终结，可以使用System.in.read()阻塞main线程执行，以便观察GC打印。
 */
public class DemoStrongReference {
    public static void main(String[] args) throws IOException, InterruptedException {
        DemoReferenceTypeObject demo = new DemoReferenceTypeObject();
        System.out.println(demo);

        demo = null;
        System.out.println(demo);

        System.gc(); // DisableExplicitGC
        System.out.println(demo);

        //System.in.read();
        Thread.sleep(1000);
        System.out.println("睡眠一秒");
    }
}
