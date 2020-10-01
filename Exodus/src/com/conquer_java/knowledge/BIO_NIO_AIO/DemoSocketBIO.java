package com.conquer_java.knowledge.BIO_NIO_AIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * CLI: yum -y install man-pages
 * CLI: strace -ff -o SocketIO.output java DemoSocketIO
 * CLI: nc localhost 9999
 * CLI: man 2 socket
 */
public class DemoSocketBIO {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9999);
        System.out.println("Step-01: create server");

        while (true) {
            Socket client = server.accept(); // 阻塞
            System.out.println("Step-02: accept client");
            new Thread(() -> {
                InputStream input = null;
                try {
                    input = client.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    while (true) {
                        String data = reader.readLine(); // 阻塞
                        if (data != null) {
                            System.out.println(data);
                        } else {
                            client.close();
                            break;
                        }
                    }
                    System.out.println("客户端已断开");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
