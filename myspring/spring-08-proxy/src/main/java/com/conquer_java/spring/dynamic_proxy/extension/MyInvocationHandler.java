package com.conquer_java.spring.dynamic_proxy.extension;

import com.conquer_java.spring.dynamic_proxy.IRent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler { // 代理实例 <==一一对应==> 调用处理句柄实例：代理实例任一方法调用均被传至代理实例调用处理该句柄对象的invoke方法。
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // JDK动态代理的本质：使用反射实现
        Object obj = method.invoke(target, args);
        additionalOP(method.getName());
        return obj;
    }

    // 生成代理实例
    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    // 扩展业务——例如：日志打印
    public void additionalOP(String msg) {
        System.out.println("执行" + msg + "扩展业务！");
    }
}
