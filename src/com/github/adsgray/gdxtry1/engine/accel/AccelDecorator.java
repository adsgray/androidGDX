package com.github.adsgray.gdxtry1.engine.accel;

import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

// eg. use to dampen/intensify acceleration. or compose multiple accelerations.
public class AccelDecorator extends BaseAccel {
    
    protected AccelIF component;
    
    public AccelDecorator(AccelIF component) {
        this.component = component;
    }


    @Override
    public VelocityIF accellerate(VelocityIF vel) {
        vel.accelerate(component);
        return vel;
    }
}
