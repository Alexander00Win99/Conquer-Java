package com.conquer_java.spring.anno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 类：切面
 * 方法：通知
 * 注解execution表达式：切入点（Around环绕方法参数指定连接点）
 */
@Component("myAnnntationAspect")
@Aspect
public class MyAnnotationAspect {
    @Before("execution(* com.conquer_java.spring.service.UserServiceImpl.*(..))")
    public void before() {
        System.out.println("++++++++++++++++方法执行之前++++++++++++++++");
    }

    @After("execution(* com.conquer_java.spring.service.UserServiceImpl.*(..))")
    public void after() {
        System.out.println("----------------方法执行之后----------------");
    }

    // 在环绕增强中，可以给定一个参数，用于代表想要获取的切入点对应的连接点。
    @Around("execution(* com.conquer_java.spring.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) {
        System.out.println("++++++++--------环绕方法之前--------++++++++");
        try {
            Signature signature = jp.getSignature();
            System.out.println(signature);
            Object proceed = jp.proceed();
            System.out.println(proceed); // jp.proceed()结果为null
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("++++++++--------环绕方法之后--------++++++++");
    }
}
