package com.github.adsgray.gdxtry1.engine;

public class BaseAccel implements AccelIF {

    @Override public DecoratorIF compressDecorators() {
        return this;
    }

    @Override public VelocityIF accellerate(VelocityIF vel) {
        return vel;
    }

}
