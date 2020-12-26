package com.conquer_java.design_pattern.adapter;

/**
 * 【目标】：掌握适配器模式
 *
 * 【结果】：完成
 *
 * 【适配器模式】-Adapter
 *
 * 【场景】
 * 客户端期望一个Target目标接口，已有Adaptee适配者接口，但不匹配，如果两者之间具备行为(逻辑)相同或者相似，可以使用Adapter适配器接口进行适配转换。
 *
 * 【角色】
 * Adaptee-已有接口；Adapter-适配接口(Adaptee->Target)；Target-期待接口(具体类，抽象类，或者接口)；
 *
 * 【分类】
 * 1) Class Adapter Pattern;
 * 2) Object Adapter Pattern;
 * 3) Default(Interface) Adapter Pattern;
 *
 * 【使用场景】
 * 软件版本更新，支持向后兼容。
 *
 * 【类适配器 VS 对象适配器】
 * 类适配器：优点：适配器Adapter类是适配者Adaptee类的子类，因此可以Override（重写）父类方法，使得适配器更加灵活；缺点：只能继承一个适配者Adaptee类；
 * 对象适配器：优点：一个适配器Adapter类可以包含多个不同的适配者Adaptee类，也即同一适配器可以处理所有适配者，使得适配器更加全面；缺点：如果需要重写适配者类的方法，只能首先创建适配者类子类，实现所需方法，然后用其替换适配器类的属性；
 * 缺省适配器：用户类中聚合目标角色接口作为属性，自身方法调用属性方法，用户在使用时，采用缺省适配器类替换属性类生成具体类对象。
 */
public class DemoAdapter {
    public static void main(String[] args) {
        /**
         * 类适配器模式
         */
        System.out.println("++++++++++++++++Begin Class Adapter++++++++++++++++");
        PowerTarget target1 = new PowerAdapter1();
        System.out.println(target1.output1V());
        System.out.println(target1.output2V());
        System.out.println(target1.output5V());

        PowerTarget target2 = new PowerAdapter2();
        System.out.println(target2.output1V());
        System.out.println(target2.output2V());
        System.out.println(target2.output5V());
        System.out.println("----------------End Class Adapter----------------");

        /**
         * 对象适配器模式
         */
        System.out.println("++++++++++++++++Begin Object Adapter++++++++++++++++");
        DirectPowerTarget target = new PowerAdapter(new AlternatePower220Adaptee(), new AlternatePower380Adaptee());
        System.out.println(target.in220VOutput1V());
        System.out.println(target.in220VOutput2V());
        System.out.println(target.in220VOutput5V());
        System.out.println(target.in380VOutput1V());
        System.out.println(target.in380VOutput2V());
        System.out.println(target.in380VOutput5V());
        System.out.println("----------------End Object Adapter----------------");

        /**
         * 缺省适配器模式
         */
        System.out.println("++++++++++++++++Begin Default Adapter++++++++++++++++");
        // ++++++++++++++++原始方式++++++++++++++++
        Operator originOperator = new Operator();
        originOperator.addOperation(new SampleOperation() {
            @Override
            public void operation1() {

            }

            @Override
            public void operation2() {
                System.out.println("具体操作2-原始方式需要实现所有方法");
            }

            @Override
            public void operation3() {

            }
        });
        originOperator.operation2();
        // ----------------原始方式----------------
        // ++++++++++++++++缺省适配器模式++++++++++++++++
        Operator defaultAdapterOperator = new Operator();
        defaultAdapterOperator.addOperation(new DefaultAdapter() {
            @Override
            public void operation2() {
                super.operation2();
                System.out.println("具体操作2-缺省适配器模式只需实现所需方法");
            }
        });
        defaultAdapterOperator.operation2();
        // ----------------缺省适配器模式----------------
        System.out.println("----------------End Default Adapter----------------");
    }
}

abstract class AlternatePowerAdaptee {
    abstract int alternateOutput();
}

// 适配者角色(AlternatePower220Adaptee.java)
class AlternatePower220Adaptee extends AlternatePowerAdaptee {
    private static final int output = 220;
    private int output220V() {
        System.out.println("交流电源输出电压：" + output);
        return output;
    }

    @Override
    int alternateOutput() {
        return output220V();
    }
}

// 适配者角色(AlternatePower380Adaptee.java)
class AlternatePower380Adaptee extends AlternatePowerAdaptee {
    private static final int output = 380;
    private int output380V() {
        System.out.println("交流电源输出电压：" + output);
        return output;
    }

    @Override
    int alternateOutput() {
        return output380V();
    }
}

// ++++++++++++++++Begin Class Adapter++++++++++++++++
// 目标角色(PowerTarget.java)-类适配器
interface PowerTarget {
    public int output1V();
    public int output2V();
    public int output5V();
}

// 适配器角色(PowerAdapter1.java)-类适配器-处理220v
class PowerAdapter1 extends AlternatePower220Adaptee implements PowerTarget { // 作为子类，可以重写适配者类方法。
    @Override
    public int output1V() {
        int output = alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 220;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }

    @Override
    public int output2V() {
        int output = alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 110;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }

    @Override
    public int output5V() {
        int output = alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 44;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }
}

// 适配器角色(PowerAdapter2.java)-类适配器-处理380v
class PowerAdapter2 extends AlternatePower380Adaptee implements PowerTarget { // 作为子类，可以重写适配者类方法。
    @Override
    public int output1V() {
        int output = alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 380;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }

    @Override
    public int output2V() {
        int output = alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 190;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }

    @Override
    public int output5V() {
        int output = alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 76;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }
}
// ----------------End Class Adapter----------------

// ++++++++++++++++Begin Object Adapter++++++++++++++++
// 目标角色(DirectPowerTarget.java)-对象适配器
interface DirectPowerTarget {
    public int in220VOutput1V();
    public int in220VOutput2V();
    public int in220VOutput5V();
    public int in380VOutput1V();
    public int in380VOutput2V();
    public int in380VOutput5V();
}


// 适配器角色(Power220Adapter.java)-对象适配器
class PowerAdapter implements DirectPowerTarget { // 如果需要重写适配者类的方法，只能首先创建适配者类子类，实现所需方法，然后用其替换适配器类的属性。
    private AlternatePower220Adaptee power220Adaptee;
    private AlternatePower380Adaptee power380Adaptee;

    public PowerAdapter(AlternatePower220Adaptee power220Adaptee, AlternatePower380Adaptee power380Adaptee) {
        super();
        this.power220Adaptee = power220Adaptee;
        this.power380Adaptee = power380Adaptee;
    }

    @Override
    public int in220VOutput1V() {
        int output = power220Adaptee.alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 220;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }

    @Override
    public int in220VOutput2V() {
        int output = power220Adaptee.alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 110;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }

    @Override
    public int in220VOutput5V() {
        int output = power220Adaptee.alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 44;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }

    @Override
    public int in380VOutput1V() {
        int output = power380Adaptee.alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 380;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }

    @Override
    public int in380VOutput2V() {
        int output = power380Adaptee.alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 190;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }

    @Override
    public int in380VOutput5V() {
        int output = power380Adaptee.alternateOutput();
        System.out.println("电源适配器开始工作，此时输入交流电压是：" + output);
        output = output / 76;
        System.out.println("电源适配器工作完成，此时输出直流电压是：" + output);
        return output;
    }
}
// ----------------End Object Adapter----------------

// ++++++++++++++++Begin Default Adapter++++++++++++++++
// 目标角色(SampleOperation.java) – 包含所有操作：
interface SampleOperation {
    public abstract void operation1();
    public abstract void operation2();
    public abstract void operation3();
}

// 适配器角色(DefaultAdapter.java) - 默认实现所有操作：
abstract class DefaultAdapter implements SampleOperation { // 使用抽象类实现接口
    @Override
    public void operation1() {}

    @Override
    public void operation2() {}

    @Override
    public void operation3() {}
}

// 测试类(Operator.java)
class Operator { // 聚合目标角色接口作为属性，自身方法调用属性方法，用户在使用时，采用缺省适配器类替换属性类生成具体类对象。
    private SampleOperation sampleOperation;

    public void addOperation(SampleOperation sampleOperation) {
        this.sampleOperation = sampleOperation;
    }

    public void operation1() {
        sampleOperation.operation1();
    }

    public void operation2() {
        sampleOperation.operation2();
    }

    public void operation3() {
        sampleOperation.operation3();
    }
}
// ----------------End Default Adapter----------------
