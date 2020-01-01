package com.conque_java.knowledge.thread_pool;

import javafx.concurrent.Task;
import sun.java2d.DisposerTarget;
import sun.security.util.BitArray;

import java.util.concurrent.*;

/**
 * https://www.cnblogs.com/fengff/p/9622026.html 参考
 * https://www.jianshu.com/p/2dc01727be45 参考
 * https://blog.csdn.net/weixin_43258908/article/details/89417917 参考
 * http://www.sohu.com/a/321408502_663371 阮一峰
 * 【进程 VS 线程】
 * 进程是具备一定独立功能的程序(可并发执行的程序)在某个数据集合上的一次计算活动或者执行过程，是操作系统进行资源分配和调度的一个基本单位，是应用程序运行的载体，进程一般由程序，数据集合和进程控制块三部分组成。。
 * 进程是操作系统中除处理器外进行的资源分配和保护的基本单位，它有一个独立的虚拟地址空间，用来容纳进程映像(如与进程关联的程序与数据)，并以进程为单位对各种资源实施保护，如受保护地访问处理器、文件、外部设备以及其他进程(进程间通信)。
 * 线程是操作系统进程中能够并发执行的实体，是处理器调度和分派的基本单位。
 *
 * CTRL+ALT+SHIFT+U查看类的继承体系架构
 *
 * ULT(User Level Thread) VS KLT(Kernel Level Thread)
 * 下面for循环运行以后，在Windows的Task Manager中可以看到Threads数量明显增加 => JVM线程是KLT。
 *
 * 线程池数量设定依据：
 * IO密集型：线程池数量可以多点
 * CPU密集型：线程池数量只能少点
 *
 * 阻塞队列：任一时刻，只能一个线程对其进行或存或取操作(其余线程只能等待)。
 * 1) ArrayBlockingQueue有界队列;
 * 2) LinkedBlockingDeque最大值是Integer.MAX_VALUE(2147483647);
 * 3) PriorityBlockingQueue无界队列;
 */
public class DemoJVMThreadKLT {
    private static final int THREAD_POOL_SIZE = 100;
    final ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(8), Executors.defaultThreadFactory());

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.start();
        }

        final ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(8), Executors.defaultThreadFactory());


    }
}