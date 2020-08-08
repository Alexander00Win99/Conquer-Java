package com.conque_java.knowledge.thread_local;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.lang.ref.SoftReference;

/**
 * 【目标】——掌握强软弱虚引用的应用场景
 * 1) 强引用——对象地址引用；
 * 2) 软引用——缓存；
 * 3) 弱引用——ThreadLocal；
 * 4) 虚引用——管理直接内存：
 *
 * 软引用通常用作缓存：内存充足用作缓存，内存不足回收利用。
 * IntelliJ IDEA配置运行时内存：[Run]->[Edit Configurations]->[Configuration]->[VM Options]使用如下配置参数：
 * -Xms20m -Xmx20m -Xmn18m -Xss1m -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=20m
 *
 * 注：配置参数"-Xms20m -Xmx20m -Xmn20m -Xss1m -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=20m"，导致如下告警：
 * "Java HotSpot(TM) 64-Bit Server VM warning: MaxNewSize (20480k) is equal to or greater than the entire heap (20480k).  A new max generation size of 19968k will be used."
 */
public class DemoSoftReference {
    public static void main(String[] args) {
        SoftReference<byte[]> demo = new SoftReference<>(new byte[1024*1024*10]);
        System.out.println(demo.get());
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println(Runtime.getRuntime().freeMemory());

        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(demo.get());
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println(Runtime.getRuntime().freeMemory());

        //byte[] bytes = new byte[1024*1024*13]; // 假设：配置参数"-Xms20m -Xmx20m"，此时如果new 13M对象可以通过，如果new 15M对象，直接抛出异常："Exception in thread "main" java.lang.OutOfMemoryError: Java heap space"

        /**
         * 总计20m内存，软引用占用10m：
         * 1) 如果追加分配1m内存，heap空间可以放下，不会触发垃圾回收，软引用得以继续存活；
         * 2) 如果追加分配13m内存，heap空间放不下，触发垃圾回收，先回收一次，不够继续回收软引用，此时打印null；
         * 3) 如果追加分配14m内存，heap空间放不下，直接抛出异常："Exception in thread "main" java.lang.OutOfMemoryError: Java heap space"；
         */
        //byte[] bytes = new byte[1024*1024*1];
        byte[] bytes = new byte[1024*1024*13];
        //byte[] bytes = new byte[1024*1024*14];
        System.out.println(demo.get());
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println(Runtime.getRuntime().freeMemory());
    }
}
