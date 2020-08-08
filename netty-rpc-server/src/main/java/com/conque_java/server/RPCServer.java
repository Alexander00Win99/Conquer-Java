package com.conque_java.server;

import java.io.File;
import java.net.URL;
import java.util.*;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class RPCServer {
    // 业务注册表（非sprint-boot的单例模式，服务实例生成之后不作改动，无须线程安全）
    private Map<String, Object> registerMap = new HashMap<String, Object>(); // 线程安全：private Map<String, Object> registerMap = new ConcurrentHashMap<String, Object>();
    // 存放指定包中实现类的类名
    private List<String> classCache = new ArrayList<String>(); // 线程安全：private List<String> classCache = Collections.synchronizedList(new ArrayList<String>());

    /**
     * 接口名(服务名称) <==一一对应==> 指定包中的实现类的实例，采用Map数据结构——服务实例存入registerMap之中
     * @param basePackage
     */
    public void publish(String basePackage) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 扫描指定包中所有服务实现类的实例，写入classCache之中
        cacheClass(basePackage);
        // 注册服务（写入Map）
        doRegister();
    }

    private void cacheClass(String basePackage) {
        URL resource = this.getClass().getClassLoader()
                .getResource(basePackage.replaceAll("\\.", "/"));

        if (resource == null) return;

        // URL -> 文件
        File dir = new File(resource.getFile());
        // 递归扫描目录下所有文件，目录继续递归，.class文件直接录入
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                cacheClass(basePackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                classCache.add(basePackage + "." + file.getName().replace(".class", "").trim());
            }
        }
        System.out.println("服务缓存-classCache：" + classCache);
    }

    private void doRegister() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (classCache.size() == 0)
            return;

        for (String s : classCache) {
            Class<?> clazz = Class.forName(s);
            Class<?>[] interfaces = clazz.getInterfaces();
            System.out.println("interfaces: " + interfaces);
            // 获取接口名<====>服务（类似Dubblo，只会实现一个接口）
//            String interfaceName = clazz.getInterfaces()[0].getName();
//            System.out.println(interfaceName);
//            registerMap.put(interfaceName, clazz.newInstance());
        }
    }

    public void start() throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    // 配置连接处理线程组+数据处理线程组
                    .group(parentGroup, childGroup)
                    // 创建连接处理channel
                    .channel(NioServerSocketChannel.class)
                    // 超出连接线程组处理能力的客户端连接，如果已经完成三次握手，那么临时存储于长度为1024的请求队列
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 使用心跳机制维护客户端长连接，保证连接不被清除
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 设置数据处理channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 添加编码器
                            pipeline.addLast(new ObjectEncoder());
                            // 添加解码器
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            // 添加自定义处理器
                            pipeline.addLast(null);
                            //pipeline.addLast(new RPCServerHandler(registerMap));
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(8888).sync();
            System.out.println("微服务注册完成");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
