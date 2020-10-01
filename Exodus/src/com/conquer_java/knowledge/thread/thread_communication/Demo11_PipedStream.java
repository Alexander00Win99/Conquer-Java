package com.conquer_java.knowledge.thread.thread_communication;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo11_PipedStream {
    private static Thread t1 = null;
    private static Thread t2 = null;

    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * 利用Stream类的read()、write()阻塞操作实现
     * PipedStream——效率太低，仅仅用于学术实验，无法应用生成环境：两个线程的InputStream和OutputStream连接起来以后，可以实现单向信息传递。
     */
    //====Solution11-PipedStream====
    private static PipedInputStream input1 = new PipedInputStream();
    private static PipedInputStream input2 = new PipedInputStream();
    private static PipedOutputStream output1 = new PipedOutputStream();
    private static PipedOutputStream output2 = new PipedOutputStream();
    //====Solution11-PipedStream====
    public static void main(String[] args) throws IOException {
        System.out.println("++++++++--------Begin: Solution11-PipedStream--------++++++++");
        input1.connect(output2);
        input2.connect(output1);

        String msg1 = "Come on!"; // 注意：线程之中的buffer大小必须等于此处字符串长度
        String msg2 = "Move up!"; // 注意：线程之中的buffer大小必须等于此处字符串长度

        t1 = new Thread(() -> { // 先写后读
            byte[] buffer = new byte[8];
            try {
                for (char c : arrInt) {
                    System.out.println(c);
                    output1.write(msg1.getBytes());
                    input1.read(buffer);
                    System.out.println(Thread.currentThread().getName() + "当前buffer: " + buffer + "，内容：" + new String(buffer));
                    if (!new String(buffer).equals(msg2)) break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "PipedStream-Thread-01");
        t2 = new Thread(() -> { // 先读后写
            byte[] buffer = new byte[8];
            try {
                for (char c : arrChar) {
                    input2.read(buffer);
                    System.out.println(Thread.currentThread().getName() + "当前buffer: " + buffer + "，内容：" + new String(buffer));
                    if (!new String(buffer).equals(msg1)) break;
                    System.out.println(c);
                    output2.write(msg2.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "PipedStream-Thread-02");

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++--------End: Solution11-PipedStream--------++++++++");
    }
}
