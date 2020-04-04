package com.conque_java.knowledge.proxy.dynamic_proxy;

/**
 * 【引入】——Spring AOP技术：
 * 1) Java Proxy --> JVM字节码(Proxy.newInstance()==JVM自带字节码编辑技术)；
 * 2) CGLib --> [ASM]工具 --> JVM字节码；
 * 注：
 * JDK原生动态代理、无需任何外部依赖、但是只能基于接口进行代理(因为它已经继承了proxy了，Java不支持多继承)
 * 2、CGLIB通过继承的方式进行代理、无论目标对象没有没实现接口都可以代理，但是无法处理final的情况（final修饰的方法不能被覆写）
 *
 * 【动态代理】
 * 在原有代码保持不变的情况下，嵌入所需功能逻辑。
 *
 * 【使用场景】
 * a) AOP；b) 声明式事务；c) 日志集中打印；
 *
 * 【JVM指令码修改方法】
 * 1) 直接修改现有类(Aspect)
 * 2) 新增动态代理类(Java Proxy | CGLib)
 */
public class DemoJVMBytecode {
    public static void main(String[] args) {
        int i = 0;
        System.out.println(i);
    }
}
