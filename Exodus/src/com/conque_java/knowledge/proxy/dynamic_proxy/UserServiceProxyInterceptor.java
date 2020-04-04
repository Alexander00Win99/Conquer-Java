package com.conque_java.knowledge.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserServiceProxyInterceptor implements InvocationHandler {
    private Object realObject;

    public UserServiceProxyInterceptor(Object realObject) {
        super();
        this.realObject = realObject;
    }

    public Object getRealObject() {
        return realObject;
    }

    public void setRealObject(Object realObject) {
        this.realObject = realObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前置逻辑
        if (args != null && args.length > 0 && args[0] instanceof User) {
            User user = (User) args[0];
            if (user.getName().length() < 4 || user.getName().length() > 16) {
                throw new RuntimeException("用户名长度必须大于等于4小于等于16");
            }
            if (user.getPassword().length() < 8 || user.getPassword().length() > 16) {
                throw new RuntimeException("密码长度必须大于等于8小于等于16");
            }
        }

        Object result = method.invoke(realObject, args);

        // 后置逻辑
        System.out.println("经过Java Proxy动态代理处理，用户注册成功！");
        return result;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("Alex Wen");
        user.setPassword("12345678");
        // 被代理类
        UserService delegate = new UserServiceProxyImpl();
        InvocationHandler userServiceProxyInterceptor = new UserServiceProxyInterceptor(delegate);
        // 动态代理类
        UserService userServiceJDKProxy = (UserService) Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(), userServiceProxyInterceptor);
        System.out.println("Java Proxy动态代理类：" + userServiceJDKProxy.getClass());
        userServiceJDKProxy.addUser(user);
    }
}
