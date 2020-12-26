package com.conquer_java.spring.static_proxy.extension;

public class UserServiceImpl implements UserService {
    @Override
    public void create() {
        System.out.println("增加操作！");
    }

    @Override
    public void retrieve() {
        System.out.println("查询操作！");
    }

    @Override
    public void update() {
        System.out.println("更新操作！");
    }

    @Override
    public void delete() {
        System.out.println("删除操作！");
    }
}
