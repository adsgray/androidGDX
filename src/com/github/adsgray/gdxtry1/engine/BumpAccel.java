package com.github.adsgray.gdxtry1.engine;

public class BumpAccel extends AccelComposeDecorator {

    int tickExpire;
    int ticks;

    public BumpAccel(AccelIF component, AccelIF primary, int tickExpire) {
        super(component, primary);
        this.tickExpire = tickExpire;
        ticks = 0;
        // TODO Auto-generated constructor stub
    }
    
    public VelocityIF accellerate(VelocityIF vel) {
        
        VelocityIF ret;

        ticks += 1;

        if (ticks < tickExpire) {
            // compose velocities
            ret = super.accellerate(vel);
        } else {
            // we've expired so just do the component
            ret = component.accellerate(vel);
        }
        
        return ret;
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
