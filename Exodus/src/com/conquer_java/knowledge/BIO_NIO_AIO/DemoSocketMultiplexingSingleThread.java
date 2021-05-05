package com.conquer_java.knowledge.BIO_NIO_AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 【多路复用器】——Java NIO的Selector包(封装select|poll|epoll)
 * 1) 负责通知数据状态，并不负责数据读写；
 * 2)
 * 3) 无论select、poll、epoll都是同步IO模式，需要APP应用程序自行处理数据读写；
 */
public class DemoSocketMultiplexingSingleThread {
    private ServerSocketChannel server = null;
    private Selector selector = null;
    private static final int PORT = 9999;

    public void init() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(PORT));
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        init();
        System.out.println("服务器启动。。。。。。");
        while (true) {
            Set<SelectionKey> keys = selector.keys();
            System.out.println("size: " + keys.size());
            try {
                while (selector.select(1000) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocateDirect(8192);
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("++++++++++++++++Begin++++++++++++++++");
            System.out.println("客户端：" + client.getRemoteAddress() + "成功连接");
            System.out.println("----------------End----------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
