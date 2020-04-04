package com.conque_java.knowledge.proxy.dynamic_proxy;

public class UserServiceCglibImpl implements UserService {
    @Override
    public void addUser(User user) {
        System.out.println("CGLIB动态代理正在注册用户，用户注册信息如下：" + user);
    }
}
