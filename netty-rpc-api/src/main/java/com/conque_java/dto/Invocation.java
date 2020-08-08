package com.conque_java.dto;

import java.io.Serializable;

public class Invocation implements Serializable {
    /**
     * 接口名称<====>服务名称
     */
    private String className;

    /**
     * 远程调用方法名称
     */
    private String methodName;

    /**
     * 远程调用方法参数类型
     */
    private Class<?>[] paramTypes;

    /**
     * 远程调用方法参数取值
     */
    private Object[] paramValues;

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public Object[] getParamValues() {
        return paramValues;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public void setParamValues(Object[] paramValues) {
        this.paramValues = paramValues;
    }
}
