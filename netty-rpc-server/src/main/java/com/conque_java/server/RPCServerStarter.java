package com.conque_java.server;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RPCServerStarter {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException {
        RPCServer server = new RPCServer();
        server.publish("com.conque_java.service");
        server.start();
    }
}
