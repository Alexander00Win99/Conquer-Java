package com.conquer_java.spring.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler { // 代理实例 <==一一对应==> 调用处理句柄实例：代理实例任一方法调用均被传至代理实例调用处理该句柄对象的invoke方法。
    private IRent rent;

//    public MyInvocationHandler(IRent rent) {
//        this.rent = rent;
//    }

    public void setRent(IRent rent) {
        this.rent = rent;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        showHouse();
        // JDK动态代理的本质：使用反射实现
        Object obj = method.invoke(rent, args);
        signContract();
        charge();
        return obj;
    }

    // 生成代理实例
    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), rent.getClass().getInterfaces(), this);
    }

    // 扩展业务1
    public void showHouse() {
        System.out.println("中介带着客户看房！");
    }

    // 扩展业务2
    public void signContract() {
        System.out.println("中介主导签署居间合同！");
    }

    // 扩展业务3
    public void charge() {
        System.out.println("中介收中介费！");
    }
}
