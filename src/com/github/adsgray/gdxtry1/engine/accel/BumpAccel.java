package com.github.adsgray.gdxtry1.engine.accel;

import com.github.adsgray.gdxtry1.engine.DecoratorIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public class BumpAccel extends AccelComposeDecorator {

    int tickExpire;
    int ticks;

    public BumpAccel(AccelIF component, AccelIF primary, int tickExpire) {
        super(component, primary);
        this.tickExpire = tickExpire;
        ticks = 0;
    }
    
    public VelocityIF accellerate(VelocityIF vel) {
        
        if (ticks < tickExpire) {
            // compose velocities
           super.accellerate(vel);
        } else {
            // we've expired so just do the component
           component.accellerate(vel);
           //vel.accelerate(component);
        }
        
        ticks += 1;
        return vel;
    }
    
    // if we're not expired return ourselves
    // otherwise return our compnent...
    // in this way we'll be removed from the "chain" of
    // decorators once we expire
    public DecoratorIF compressDecorators() {
        if (ticks < tickExpire)
            return this;
        else
            return component.compressDecorators();
    }

}
