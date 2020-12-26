package com.conquer_java.design_pattern.factory.abstract_factory;

public class HuaweiFactory implements IProductFactory {
    @Override
    public IMobile produceMobile() {
        return new HuaweiMobile();
    }

    @Override
    public IMP3 produceMP3() {
        return new HuaweiMP3();
    }
}
