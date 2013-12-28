package com.github.adsgray.gdxtry1.engine.accel;

import com.github.adsgray.gdxtry1.engine.DecoratorIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public class BaseAccel implements AccelIF {

    @Override public DecoratorIF compressDecorators() {
        return this;
    }

    @Override public VelocityIF accellerate(VelocityIF vel) {
        return vel;
    }
}
