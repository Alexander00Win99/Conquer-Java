package com.conque_java.knowledge.BIO_NIO_AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class DemoSocketMultiplexingSingleThread {
    private ServerSocketChannel ssc = null;
    private Selector selector = null;
    private static final int PORT = 9999;

    public void init() {
        try {
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(PORT));
            selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
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
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel clientChannel = ssc.accept();
            clientChannel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocateDirect(8192);
            clientChannel.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("++++++++++++++++Begin++++++++++++++++");
            System.out.println("客户端：" + clientChannel.getRemoteAddress() + "成功连接");
            System.out.println("----------------End----------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = clientChannel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        clientChannel.write(buffer);
                    }
                    buffer.clear();
                } if (read == 0) {
                    break;
                } else {
                    clientChannel.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
