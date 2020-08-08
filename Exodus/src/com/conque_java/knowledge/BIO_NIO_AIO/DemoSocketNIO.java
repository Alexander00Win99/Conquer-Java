package com.conque_java.knowledge.BIO_NIO_AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * CLI: man 2 select
 *
 */
public class DemoSocketNIO {
    public static void main(String[] args) throws IOException, InterruptedException {
        LinkedList<SocketChannel> clientChannels = new LinkedList<>();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress((9999)));
        ssc.configureBlocking(false); // OS NonBlocking

        while (true) {
            Thread.sleep(1000);
            // 如果没有客户端连接进来，返回null(对应底层调用accept()返回客户端文件描述符fd==-1)，因而程序不会阻塞在此，只是后续代码需要根据返回值不同进行不同处理。
            SocketChannel clientChannel = ssc.accept();

            if (clientChannel == null) {
                System.out.println("null......");
            } else {
                clientChannel.configureBlocking(false); // OS NonBlocking
                int port = clientChannel.socket().getPort();
                System.out.println("client......port: " + port + "establish connection!");
                clientChannels.add(clientChannel);
            }

            // 分配直接内存，可以位于堆里或者堆外
            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
            // 遍历所有已经连接进来的客户端，逐一检查能否读写数据
            for (SocketChannel c : clientChannels) { // 串行化 多线程
                int num = c.read(buffer); // -1 0 n 不会阻塞
                if (num > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);
                    String s = new String(bytes);
                    System.out.println("client......port: " + c.socket().getPort() + "receiving data: " + s);
                    buffer.clear();
                }
            }
        }
    }
}
