package com.conquer_java.spring.dynamic_proxy.extension;

import java.util.concurrent.locks.ReentrantLock;

public class Client {
    public static void main(String[] args) {
        // 一个动态代理类可以代理多个类，只需实现同一接口即可，代码复用成本极低；
        // 例如：UserServiceImplA和UserServiceImplB均实现UserService接口，
        // 下面依次替换实际对象即可，无须额外操作！不像静态代理类需要复写多份代理类！
        // UserServiceImplA userService = new UserServiceImplA(); ==> UserServiceImplB userService = new UserServiceImplB();
        UserServiceImpl userService = new UserServiceImpl();
        MyInvocationHandler mih = new MyInvocationHandler();
        mih.setTarget(userService);
        UserService proxy = (UserService) mih.getProxy();
        proxy.create();
        proxy.retrieve();
        proxy.update();
        proxy.delete();
        ReentrantLock reentrantLock = new ReentrantLock(true);
        System.out.println(reentrantLock.isFair());
    }
}
