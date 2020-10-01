package com.conquer_java.knowledge.thread.thread_communication;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * 【题目】
 * 两个线程，一个依次打印输出数字，另外一个依次打印输出字母，如何实现两者交替打印输出“1A2B3C4D5E6F7G”？
 *
 * 线程间通信：当某个线程执行到某个动作时，需要使用某种方式通知另外一个(一些)线程。
 * 最佳解法：LockSupport | Lock + Condition | synchronized + wait()/notify()
 */
public class Demo05_TransferQueue_NOK {
    private static Thread t1 = null;
    private static Thread t2 = null;

    private static char[] arrInt = "1234567".toCharArray();
    private static char[] arrChar = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    //====Solution05-TransferQueue====
    private static TransferQueue<Character> queue = new LinkedTransferQueue<>();
    //====Solution05-TransferQueue====

    /**
     * LinkedTransferQueue是一个由链表结构组成的无界阻塞TransferQueue队列。相比其他阻塞队列，LinkedTransferQueue多提供了tryTransfer和transfer方法。
     *
     * LinkedTransferQueue采用一种预占模式实现“匹配”操作：
     * 1) 当消费者线程取元素时，若队列不为空，则直接取走数据，若队列为空，那就生成一个节点（节点元素置为null）入队，然后消费者线程在这个节点上此阻塞等待；
     * 2) 当生产者线程添元素时，若发现一个节点元素为null，则无须入队元素，直接将元素填充到该null节点，并唤醒在该节点上等待的线程，被唤醒的消费者线程直接取走元素返回。
     *
     * 【场景说明】
     * 古代牛马市场交易，卖家和买家不会口头讨价还价，而是各自伸出一只手进入一个竹管，一方提供
     */
    public static void main(String[] args) {
        System.out.println("++++++++--------Begin: Solution05-TransferQueue--------++++++++");
        t1 = new Thread(() -> {
            try {
                for (char c : arrChar) {
                    if (t2.getState() != Thread.State.TERMINATED) {
                        System.out.println(queue.take());
                        Thread.sleep(10);
                        queue.transfer(c);
                    } else {
                        System.out.println("成功进入T1线程else分支");
                        System.out.println(c);
                        Thread.currentThread().interrupt();
                    }
                }
                if (t2.getState() == Thread.State.WAITING) {
                    queue.transfer('x');
                }
            } catch (InterruptedException e) {
                System.out.println("成功进入T1线程catch异常分支");
                e.printStackTrace();
            }
        });
        t2 = new Thread(() -> {
            try {
                for (char c : arrInt) {
                    if (t1.getState() != Thread.State.TERMINATED) {
                        queue.transfer(c);
                        Thread.sleep(10);
                        System.out.println(queue.take());
                    } else {
                        System.out.println("成功进入T2线程else分支");
                        System.out.println(c);
                        Thread.currentThread().interrupt();
                    }
                }
                if (t1.getState() == Thread.State.WAITING) {
                    queue.transfer('9');
                }
            } catch (InterruptedException e) {
                System.out.println("成功进入T2线程catch异常分支");
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t1.getState());
        System.out.println(t2.getState());

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //t1.interrupt();
        System.out.println(t1.getState());
        System.out.println(t2.getState());
        System.out.println("++++++++--------End: Solution05-TransferQueue--------++++++++");
    }
}
