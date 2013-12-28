package com.github.adsgray.gdxtry1.engine.accel;

import com.github.adsgray.gdxtry1.engine.DecoratorIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public interface AccelIF extends DecoratorIF {
    public VelocityIF accellerate(VelocityIF vel);
}
