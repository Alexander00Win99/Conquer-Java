package com.conque_java.client;

import com.conque_java.dto.*;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RPCProxy {
    public static <T> T create(final Class<?> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //return null;
                        // 本地调用
                        if (Object.class.equals(method.getDeclaringClass())) {
                            System.out.println("本地调用！");
                            return method.invoke(this, args);
                        }
                        // 远程调用
                        System.out.println("远程调用！");
                        return rpcInvoke(clazz, method, args);
                    }
                });
    }

    private static Object rpcInvoke(Class<?> clazz, Method method, Object[] args) throws InterruptedException {
        final RPCClientHandler handler = new RPCClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(group)
                    .channel(NioSocketChannel.class)
                    // 关闭Nagle算法，避免缓存数据导致发送延迟
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(handler);
                        }
                    });

            ChannelFuture future =  bootstrap.connect("localhost", 8888).sync();

            // 客户端将RPC调用信息传递给服务器
            Invocation invocation = new Invocation();
            invocation.setClassName(clazz.getName());
            invocation.setMethodName(method.getName());
            invocation.setParamTypes(method.getParameterTypes());
            invocation.setParamValues(args);

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
        return null;
//        return handler.getResult();
        /**
         * fdaiofdnna dfafdfadf
         */
    }
}
