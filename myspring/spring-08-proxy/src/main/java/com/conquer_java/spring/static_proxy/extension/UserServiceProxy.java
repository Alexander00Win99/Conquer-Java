package com.conquer_java.spring.static_proxy.extension;

public class UserServiceProxy implements UserService {
    private UserServiceImpl userService;

//    public UserServiceProxy(UserServiceImpl userService) {
//        this.userService = userService;
//    }

    // Spring建议使用set方法而非构造函数注入对象
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void create() {
        log("create");
        userService.create();
    }

    @Override
    public void retrieve() {
        log("retrieve");
        userService.retrieve();
    }

    @Override
    public void update() {
        log("update");
        userService.update();
    }

    @Override
    public void delete() {
        log("delete");
        userService.delete();
    }

    public void log(String method) {
        System.out.println("[日志前缀]：使用" + method + "方法");
    }
}
