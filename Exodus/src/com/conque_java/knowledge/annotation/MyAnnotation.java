package com.conque_java.knowledge.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 作用域
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
// 生命周期
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    public static final String DESCRIPTION = "REDUNDENCY";
    public String[] value() default {""};
}
