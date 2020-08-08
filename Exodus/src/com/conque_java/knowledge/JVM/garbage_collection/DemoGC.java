package com.conque_java.knowledge.JVM.garbage_collection;

import java.util.LinkedList;

/**
 * 程序需要内存>xmx设置，就会抛出OutOfMemory异常。
 *
 * 【Java.lang.Runtime函数——totalMemory(), maxMemory(), freeMemory()】
 * totalMemory()：JVM当前已经从操作系统申请到的内存大小，如果设置xms参数，初始totalMemory()==xms，如果没有设置xms参数，JVM总是按需申请从零开始逐渐增多；
 * maxMemory()：JVM能够从操作系统申请到的最大内存大小，如果设置xmx参数，maxMemory()==xmx，如果没有设置xmx参数，默认64M；
 * freeMemory()：JVM当前从操作系统申请到的内存大小与实际已经使用的内存大小的差值，如果设置xms参数，初始freeMemory()=约等=xms，如果没有设置xms参数，因为JVM总是按需申请，初始freeMemory()=约等=0；
 *
 * 【常见参数】
 * -Xms —— initial size of the memory allocation pool(in bytes, default value is 2MB)
 * -Xmx —— maximum size of the memory allocation pool(in bytes, default value is 64MB)——操作系统32bit/64bit|可用物理内存大小|可用虚拟内存大小=决定=>xmx参数大小。
 * -Xmn —— young generation size
 * -Xss —— JVM stack size
 * -XX:NewSize=n
 * -XX:NewRatio=n —— 年轻代和老年代分别总体占比：1/(n+1), n/(n+1)
 * -XX:SurvivorRatio=n —— the ratio between each survivor space and eden space，如果是8，每个幸存者区占年轻代的 1/(1*2+8)
 * -XX:MaxPermSize=n
 * -XX:MaxTenuringThreshold=n —— 年轻代晋升老年代的年龄阈值，4bit取值范围是[0,15]
 * 【收集器设置】
 * -XX:+UseSerialGC:设置串行收集器
 * -XX:+UseParallelGC:设置并行收集器
 * -XX:+UseParalledlOldGC:设置并行年老代收集器
 * -XX:+UseConcMarkSweepGC:设置并发收集器
 * 【并行收集器设置】——吞吐量优先
 * -XX:ParallelGCThreads=n:设置并行收集器收集时使用的CPU数，并行收集线程数
 * -XX:MaxGCPauseMillis=n:设置并行收集最大暂停时间
 * -XX:GCTimeRatio=n:设置垃圾回收时间占程序运行时间的百分比，公式为1/(1+n)
 * 【并发收集器设置】——响应时间优先
 * -XX:+CMSIncrementalMode:设置为增量模式，适用于单CPU情况
 * -XX:ParallelGCThreads=n:设置并发收集器年轻代收集方式为并行收集时，使用的CPU数，并行收集线程数
 * 【垃圾回收统计信息】
 * -XX:+PrintGC
 * -XX:+PrintGCDetails
 * -XX:+PrintGCTimeStamps
 * -XX:+PrintGCApplicationConcurrentTime 打印垃圾回收之前程序未中断的执行时间
 * -XX:+PrintGCApplicationStoppedTime 打印垃圾回收期间程序暂停的时间
 * -XX:PrintHeapAtGC 打印GC前后的详细堆栈信息
 * -Xloggc:filename
 *
 * 【参数配置】
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 * ==>
 * 年轻代=10M，伊甸园区=8M，S0=1M，S1=1M。
 *
 * 【JDK1.4+JDK1.5标准配置】——一般 xms == xmx，以免JVM每次GC以后需要重新分配内存
 * Total    Memory  -Xms    -Xmx    -Xss    Spare Memory    JDK	Thread Count
 * 1024M    256M    256M    256K    768M    1.4             3072
 * 1024M    256M    1M      256K    768M    1.5             768
 * 注：xss越大，可创建线程数越少。当然，操作系统对于进程内部线程数也有参数限制。
 */
public class DemoGC {
    private static final int _1MB = 1024 * 1024;

    private static void method() {
        method();
    }

    public static void main(String[] args) {
        // 报错：Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
        /*
        LinkedList<DemoGC> list = new LinkedList<>();
        while (true) {
            list.add(new DemoGC());
        }
         */

        // 报错：Exception in thread "main" java.lang.StackOverflowError
        /*
        method();
         */

        /**
         * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
         * 年轻代=10M，伊甸园区=8M，S0=1M，S1=1M。
         * 当allocation4时，Eden已经占有6M，需要4M，总计8M，只剩2M，被迫发生MinorGC
         * 当MinorGC时，Survivor只有1M，无法放下任何一个2M对象，只能通过分配担保机制提前转移进入老年代
         *
         * 【打印输出】
         * Connected to the target VM, address: '127.0.0.1:0', transport: 'socket'
         * [GC (Allocation Failure) [PSYoungGen: 6289K->847K(9216K)] 6289K->4951K(19456K), 0.0678385 secs] [Times: user=0.00 sys=0.00, real=0.07 secs]
         * Heap
         *  PSYoungGen      total 9216K, used 7396K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
         *   eden space 8192K, 79% used [0x00000000ff600000,0x00000000ffc656c0,0x00000000ffe00000)
         *   from space 1024K, 82% used [0x00000000ffe00000,0x00000000ffed3ca0,0x00000000fff00000)
         *   to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
         *  ParOldGen       total 10240K, used 4104K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
         *   object space 10240K, 40% used [0x00000000fec00000,0x00000000ff002020,0x00000000ff600000)
         *  Metaspace       used 3047K, capacity 4556K, committed 4864K, reserved 1056768K
         *   class space    used 323K, capacity 392K, committed 512K, reserved 1048576K
         * Disconnected from the target VM, address: '127.0.0.1:0', transport: 'socket'
         *
         * Process finished with exit code 0
         */
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }
}
