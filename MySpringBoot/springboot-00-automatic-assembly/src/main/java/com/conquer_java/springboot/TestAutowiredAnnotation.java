package com.conquer_java.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestAutowiredAnnotation {
    private BeanB b;
    private BeanC c;

    @Autowired
    public void setBeanB(BeanB beanB) {
        System.out.println("取消@Autowired注解导致抛出空指针异常——java.lang.NullPointerException");
        beanB.setName("setter使用@Autowired注解可以修改name属性");
        this.b = beanB;
    }

    @Autowired
    public void setBeanC(BeanC beanC) {
        System.out.println("无论标准setter方法名称还是诸如init方法名称，添加@Autowired注解就会检测@Bean并且自动注入");
        this.c = beanC;
    }

    public BeanB getBeanB() {
        return b;
    }

    public BeanC getBeanC() {
        return c;
    }
}
