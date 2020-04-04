package com.conque_java.knowledge.proxy.dynamic_proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class UserServiceCglibInterceptor implements MethodInterceptor {
    private Object realObject;

    public UserServiceCglibInterceptor(Object realObject) {
        super();
        this.realObject = realObject;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (objects != null && objects.length > 0 && objects[0] instanceof User) {
            User user = (User) objects[0];
            // 插入业务逻辑处理
            if (user.getName().length() < 4 || user.getName().length() > 16) {
                throw new RuntimeException("用户名长度必须大于等于4小于等于16");
            }
            if (user.getPassword().length() < 8 || user.getPassword().length() > 16) {
                throw new RuntimeException("密码长度必须大于等于8小于等于16");
            }
        }

        Object result = method.invoke(realObject, objects);
        System.out.println("经过CGLIB动态代理处理，用户注册成功！");
        return result;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("Alexander 温");
        user.setPassword("87654321");
        // 被代理类
        UserService delegate = new UserServiceCglibImpl();
        MethodInterceptor userServiceCglibInterceptor = new UserServiceCglibInterceptor(delegate);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(delegate.getClass());
        enhancer.setCallback(userServiceCglibInterceptor);
        // 动态代理类
        UserService userServiceCglibProxy = (UserService) enhancer.create();
        System.out.println("CGLIB动态代理类：" + userServiceCglibProxy.getClass().getSuperclass());
        System.out.println("CGLIB动态代理类的父类：" + userServiceCglibProxy.getClass().getSuperclass());
        userServiceCglibProxy.addUser(user);
    }
}
