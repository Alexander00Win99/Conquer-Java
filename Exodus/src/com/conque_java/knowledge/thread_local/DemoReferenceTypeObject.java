package com.conque_java.knowledge.thread_local;

/**
 * 【目标】——掌握强软弱虚引用的应用场景
 * 1) 强引用——对象地址引用；——宁愿OOM，也不回收；
 * 2) 软引用——高速缓存；——空间不够，即被回收；空间够用，无须回收；可以但不必须和引用队列ReferenceQueue联合使用；
 * 3) 弱引用——ThreadLocal；——GC扫描，一旦发现，无论空间，即刻回收；可以但不必须和引用队列ReferenceQueue联合使用；
 * 4) 虚引用——管理直接内存：——上述三种引用类型可以控制对象生命周期，此种等同于没有任何引用，不会影响对象生命周期。
 * 虚引用用于跟踪对象垃圾回收过程，虚引用指向的对象可能任意时刻被回收，当GC回收某个对象时，如果发现其存在虚引用，
 * 就把对象关联的虚引用加入引用队列ReferenceQueue——程序通过检查引用队列是否存在虚引用判断对象是否即将回收——定制回收前行为。
 */
public class DemoReferenceTypeObject {
    static {
        System.out.println("This is an object for reference type!");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("注意：finalize不是C++的析构函数！此处不宜手动重写内存回收代码，否则容易导致GC拥塞进而OOM！");
    }
}
