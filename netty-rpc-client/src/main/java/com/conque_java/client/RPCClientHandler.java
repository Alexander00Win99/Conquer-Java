package com.conque_java.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RPCClientHandler extends SimpleChannelInboundHandler {
    private Object result;

    public Object getResult() {
        return this.result;
    }

    // o是服务器端远程调用的返回结果
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        this.result = o;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
