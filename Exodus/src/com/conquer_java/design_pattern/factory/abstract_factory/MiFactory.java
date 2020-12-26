package com.conquer_java.design_pattern.factory.abstract_factory;

public class MiFactory implements IProductFactory {
    @Override
    public IMobile produceMobile() {
        return new MiMobile();
    }

    @Override
    public IMP3 produceMP3() {
        return new MiMP3();
    }
}
