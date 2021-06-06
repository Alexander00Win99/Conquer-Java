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

/**
 * 【多路复用器的作用】——JVM高层的Java NIO包封装Selector <====> 操作系统底层封装select|poll|epoll|kqueue)
 * 1) 负责通知数据状态，并不负责数据读写；
 * 2) 一次系统调用（select|poll的）
 * 3) 无论select、poll、epoll都是同步IO模式，需要APP应用程序自行处理数据读写；
 *
 * 【strace跟踪系统调用过程的调试技巧】
 * bind(fd4, 8888)
 * epoll_create ==> fd6
 * epoll_ctl(fd7, add, fd5)
 * 如何查看确定fd4和fd5是否pipe关系？
 * # jps
 * # 1357 ${jvm_pid}
 * #
 * # lsof -p 1357
 * # 4u *:websm (LISTEN)
 * # 5r FIFO pipe
 * # 6w FIFO pipe
 * # 7u REG [eventpoll]
 */
public class DemoSocketMultiplexingSingleThread {
    private ServerSocketChannel server = null;
    private Selector selector = null;
    private static final int PORT = 8888;

    public void init() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(PORT));

            // 底层使用不同多路复用器，对应不同动作：
            // 1) select|poll <====> JVM在用户空间中开辟数组空间，以备将来存放server fd和client fd；
            // 2) epoll <====> epoll_create(int size)在内核空间中开辟空间创建epoll实例；
            selector = Selector.open();
            // 往多路复用器上注册server fd
            // 1) select|poll <====> JVM将listen的server fd置于JVM进程空间；
            // 2) epoll <====> 将listen的server fd绑定到内核空间创建的epoll实例上；
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
                // 调用多路复用器的select()方法（可带超时参数），查询注册的fd有哪些事件发生（新的客户端连接进来或者哪些已有客户端连接数据就绪可以读写）
                // 1) select|poll <====> select()|poll()，将JVM用户空间注册的文件描述符传递给内核进行查询；
                // int select(int nfds, fd_set *readfds, fd_set *writefds, fd_set *exceptfds, struct timeval *timeout);
                // On success, select() and pselect() return the number of file descriptors contained in the three returned descriptor sets
                // (that is, the total number of bits that are set in readfds, writefds, exceptfds) which may be zero if the timeout expires
                // before anything interesting happens. On error, -1 is returned, and errno is set appropriately; the sets and timeout become
                // undefined, so do not rely on their contents after an error.
                //
                // int poll(struct pollfd *fds, nfds_t nfds, int timeout);
                // On success, a positive number is returned; this is the number of structures which have nonzero revents fields
                // (in other words, those descriptors with events or errors reported). A value of 0 indicates that the call timed out
                // and no file descriptors were ready. On error, -1 is returned, and errno is set appropriately.
                //
                // 2) epoll <====> epoll_wait()，
                while (selector.select(1000) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            key.cancel();
                            readHandler(key);
                        } else if (key.isWritable()) {
                            key.cancel();
                            writeHandle(key);
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

    private void writeHandle(SelectionKey key) {
    }
}
