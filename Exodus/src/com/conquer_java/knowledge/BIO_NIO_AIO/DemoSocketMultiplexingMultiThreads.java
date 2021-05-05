package com.conquer_java.knowledge.BIO_NIO_AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoSocketMultiplexingMultiThreads {
    private ServerSocketChannel server = null;
    private Selector selector1 = null;
    private Selector selector2 = null;
    private Selector selector3 = null;
    private Selector selector4 = null;
    private int port = 9999;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            selector1 = Selector.open();
            selector2 = Selector.open();
            selector3 = Selector.open();
            selector4 = Selector.open();
            server.register(selector1, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DemoSocketMultiplexingMultiThreads service = new DemoSocketMultiplexingMultiThreads();
        service.initServer();
        NioThread t1 = new NioThread(service.selector1, 3);
        NioThread t2 = new NioThread(service.selector2);
        NioThread t3 = new NioThread(service.selector2);
        NioThread t4 = new NioThread(service.selector2);
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        t3.start();
        t4.start();
    }

    private static class NioThread extends Thread {
        Selector selector = null;
        static int selectorCount = 0;
        int id = 0;
        volatile static BlockingQueue<SocketChannel>[] queue;
        static AtomicInteger idx = new AtomicInteger();

        NioThread(Selector sel, int n) {
            this.selector = sel;
            this.selectorCount = n;

            queue = new LinkedBlockingDeque[selectorCount];
            for (int i = 0; i < queue.length; i++) {
                queue[i] = new LinkedBlockingDeque<>();
            }
            System.out.println("Boss线程启动！");
        }

        NioThread(Selector sel) {
            this.selector = sel;
            this.id = idx.getAndIncrement() % selectorCount;
            System.out.println("Worker线程启动！");
        }

        @Override
        public void run() {
            try {
                while (true) {
                    while (true) {
                        if (!(selector.select(100) > 0)) {
                            Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
