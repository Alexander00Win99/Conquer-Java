package com.conque_java.knowledge.JMM;

/**
 * JMM(Java Memory Model，Java内存模型)本身是一种抽象的概念，并不真实存在，它描述的是一组内存数据访问规范，通过这组规范定义了程序中各个变量（包括实例字段，静态字段和构成数组对象的元素）的访问方式。
 * JMM目的是为了保证Java程序在各种平台上对内存的访问都能保证效果一致。
 *
 * 【JIT编译器优化+指令乱序】
 * 通常来说，JIT需要将.class字节码指令逐条翻译生成汇编代码放入CPU core-i中执行，但是，对于某些热点代码，JIT编译器直接将业已存在的汇编代码直接放入方法区中执行，省略频繁编译过程，另外，对于汇编代码中的相互无关语句可能为了提高执行效率目的修改执行顺序。
 *
 * 【volatile可见性原理】
 * 底层汇编指令之中加入lock锁机制，在执行机器指令时触发硬件锁机制：
 * 1) Thread-A加载共享变量x，MESI状态=E；
 * 2) Thread-B加载共享变量x，MESI状态=S；
 * 3) Thread-A通过总线嗅探机制，获悉变量状态发生改变，相应改变MESI状态=E->S；
 * 4) Thread-B修改共享变量x，MESI状态=S->M(假设Thread-X同时希望修改变量x，但是经过总线仲裁，裁决Thread-B优先修改)；
 * 5) Thread-A通过总线嗅探机制，获悉变量状态发生改变，相应失效MESI状态=E->S->I；
 * 6) Thread-B完成修改，更新主存共享变量，MESI状态=S->M->E；
 * 7) Thread-A重新加载共享变量，MESI状态=S；
 * 8) 缓存行锁，通常是64Kbit/128Kbit，如果变量太大无法锁住，那么退化成为总线锁，禁止并行，效率降低。
 *
 * 参考：https://my.oschina.net/u/3728792/blog/3050325
 *
 *
 */
public class DemoJMM {
    public static void main(String[] args) {
    }
}
