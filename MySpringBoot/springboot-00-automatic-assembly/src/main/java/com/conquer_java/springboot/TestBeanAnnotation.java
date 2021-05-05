package com.conquer_java.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TestBeanAnnotation {
    @Bean
    public BeanA genBeanA() {
        BeanA beanA = new BeanA();
        beanA.setId("0001");
        beanA.setName("Bean A");
        return beanA;
    }

    @Bean
    public BeanB genBeanB() {
        BeanB beanB = new BeanB();
        beanB.setId("0002");
        beanB.setName("Bean B");
        return beanB;
    }

    @Bean
    public BeanC genBeanC(BeanB beanB) {
        BeanC beanC = new BeanC();
        //beanC.setId("0003");
        //beanC.setName("Bean C");
        beanC.setId(String.valueOf(Integer.parseInt(beanB.getId()) + 1));
        beanC.setName(beanB.getName());
        return beanC;
    }
}
