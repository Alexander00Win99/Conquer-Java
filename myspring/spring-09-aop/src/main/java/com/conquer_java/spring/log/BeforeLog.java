package com.conquer_java.spring.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class BeforeLog implements MethodBeforeAdvice {
    /**
     * 前置日志
     * @param method 目标对象将被执行的方法
     * @param args 方法参数
     * @param target 目标对象
     * @throws Throwable
     */
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(target.getClass().getName() + "类的" + method.getName() + "方法将被执行！");
    }
}
