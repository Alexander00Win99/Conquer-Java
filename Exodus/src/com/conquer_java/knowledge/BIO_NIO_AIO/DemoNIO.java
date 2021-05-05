package com.conquer_java.knowledge.BIO_NIO_AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * CLI: man 2 select
 *
 * 【谈谈NIO】——两层含义
 * 1) Java的New IO，一个包含ByteBuffer、ServerSockertChannel、SockerChannel、Selector等的IO package包；
 * 2) Linux系统内核的NonBlocking IO，支持非阻塞方式的IO操作accept()、recv()；
 * 注：
 * 
 */
public class DemoNIO {
    public static void main(String[] args) throws IOException, InterruptedException {
        LinkedList<SocketChannel> clients = new LinkedList<>();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress((9999)));
        server.configureBlocking(false); // OS NonBlocking

        while (true) {
            Thread.sleep(1000);
            // 如果没有客户端连接进来，返回null(对应底层调用accept()返回客户端文件描述符fd==-1)；
            // 程序不会阻塞在此，只是后续代码需要根据返回值不同进行不同处理。
            SocketChannel client = server.accept();

            if (client == null) {
                System.out.println("null......");
            } else {
                client.configureBlocking(false); // OS NonBlocking
                int port = client.socket().getPort();
                System.out.println("client......port: " + port + "establish connection!");
                clients.add(client);
            }

            // 分配直接内存，可以位于堆里或者堆外
            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
            // 遍历所有已经连接进来的客户端，逐一检查能否读写数据
            for (SocketChannel c : clients) { // 串行化 多线程
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
