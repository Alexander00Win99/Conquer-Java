package com.conquer_java.knowledge.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    public static <T> T produceProxy(Class clazz) {
        ClassLoader loader = clazz.getClassLoader();
        Class[] classes = new Class[]{clazz};
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName() == "sellPhone") {

                }
                return null;
            }
        };
        T proxy = (T) Proxy.newProxyInstance(loader, classes, handler);
        return proxy;
    }
}
