package com.conquer_java.knowledge.BIO_NIO_AIO_Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 【关于Boss和Worker】
 * 1) boss: 1 + boot.group(boss, boss) ==> 在一个线程中，即接收连接又读写数据；
 * 2) boss: 4 + boot.group(boss, boss) ==> 总计四个线程，第一个线程，即接收连接又读写数据，其余三个线程仅仅负责读写数据；
 * 3) boss: 4 worker: 4 + boot.group(boss, worker) ==> 总计四个线程，第一个线程，即接收连接又读写数据，其余三个线程仅仅负责读写数据；
 * 注：
 * 对于boss和worker分工场景3)来说，boss = new NioEventLoopGrouup(x)的参数x没有任何意义，如果只是监听一个端口，实际就是一个线程，
 * boss只做接收连接工作，worker只做读写数据工作——boss线程分配指定的客户端连接
 */
public class DemoNettyIO {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(4);
        NioEventLoopGroup worker = new NioEventLoopGroup(4);
        ServerBootstrap boot = new ServerBootstrap();

        try {
            boot.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, false)
//                    .childHandler((ChannelInitializer) (ch) -> {
//                        ChannelPipeline p = ch.pipeline();
//                        p.addLast();
//                    })
                    .bind(9999)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
