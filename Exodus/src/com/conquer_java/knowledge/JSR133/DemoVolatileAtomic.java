package com.conquer_java.knowledge.JSR133;

/**
 * 【任务】：掌握volatile可见性原理(基于MESI缓存一致性协议)，以及理解volatile非原子性操作缺陷(简单操作可以保证，复杂操作无法保证)
 *
 * 【结果】：完成
 *
 * 【总线锁 VS 缓存一致性协议锁】
 * 1) 总线锁：效率太低(某个CPU在某个线程中锁住总线以后，其他CPU无法使用总线读写内存任何变量，哪怕并非竞争资源)；
 * 2) 缓存一致性协议簇，包括MSI，MESI，MOSI，Synapse，Firefly以及DragonProtocol等等。下面主要介绍MESI：
 *      a) 状态缩写：M - Modify; E - Exclusive; S - Shared; I - Invalid;
 *      b) 工作场景：多核并行，而非并发；
 *      c) MESI局限性：缓存最小存储单元64Byte或者128Byte，如果某个变量空间大小超过64Bytes或者128Bytes，那么MESI就无法锁住它。
 *      d)
 *      e)
 *      f)
 *
 * 【++|--操作无法保证原子性解读】
 * 
 */
public class DemoVolatileAtomic {
//    static {
//        PropertyConfigurator.configure("log4j.properties");
//    }

    private static volatile int counter = 0;
    private static final int THREAD_COUNT = 10;
    private static final int LOOP_COUNT = 1000;

    public static void main(String[] args) {
//        Logger logger = com.conque_java.util.log4j_util.Log4JUtil.getLogger();

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < LOOP_COUNT; j++) {
                    counter ++;
//                    logger.info(Thread.currentThread().getName() + ": counter -> " + counter);
                }
            }, "Thread" + i);
            thread.start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(counter);
    }
}
