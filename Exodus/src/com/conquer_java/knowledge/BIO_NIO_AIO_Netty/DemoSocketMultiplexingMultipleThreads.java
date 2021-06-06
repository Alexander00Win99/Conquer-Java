package com.conquer_java.knowledge.BIO_NIO_AIO_Netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 */
public class DemoSocketMultiplexingMultipleThreads {
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
        DemoSocketMultiplexingMultipleThreads service = new DemoSocketMultiplexingMultipleThreads();
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
        volatile static BlockingQueue<SocketChannel>[] queues;
        static AtomicInteger idx = new AtomicInteger();
        int id = 0;

        NioThread(Selector sel, int n) {
            this.selector = sel;
            this.selectorCount = n;

            queues = new LinkedBlockingQueue[selectorCount];
            for (int i = 0; i < queues.length; i++) {
                queues[i] = new LinkedBlockingQueue<>();
            }
            System.out.println("Boss线程启动！");
        }

        NioThread(Selector sel) {
            this.selector = sel;
            this.id = idx.getAndIncrement() % selectorCount;
            System.out.println("Worker线程：" + id + "启动！");
        }

        @Override
        public void run() {
            try {
                while (true) {
                    while (selector.select(100) > 0) {
                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey next = iterator.next();
                            iterator.remove();
                            if (next.isAcceptable()) {
                                acceptHandler(next);
                            } else if (next.isReadable()) {
                                readHandler(next);
                            }
                        }
                    }

                    if (!queues[id].isEmpty()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                        SocketChannel client = queues[id].take();
                        client.register(selector, SelectionKey.OP_READ, byteBuffer);
                        System.out.println("----------------------------------------------------------------");
                        System.out.println("客户端：" + client.getRemoteAddress() + ":" + client.socket().getLocalAddress() + client.socket().getLocalPort() + client.socket().getPort() + "分配到队列：" + id);
                        System.out.println("----------------------------------------------------------------");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void acceptHandler(SelectionKey key) {
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel client = ssc.accept();
                client.configureBlocking(false);
                int index = idx.getAndIncrement() % selectorCount;
                queues[index].add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void readHandler(SelectionKey key) {
        }
    }
}
