package com.github.adsgray.gdxtry1.engine;

public class AccelComposeDecorator extends AccelDecorator {

    protected AccelIF primary;

    public AccelComposeDecorator(AccelIF component, AccelIF primary) {
        super(component);
        this.primary = primary;
    }

    @Override
    public VelocityIF accellerate(VelocityIF vel) {

        // accelerate with the component
        //VelocityIF temp = new BlobVelocity(super.accellerate(vel));
        // now accel with primary and them together
        //vel.accelerate(primary);
        super.accellerate(vel);
        primary.accellerate(vel);
        //vel.accelerate(super.accellerate(vel));
        
        //vel.setXVelocity(vel.deltaX(temp.getXVelocity()));
        //vel.setYVelocity(vel.deltaY(temp.getYVelocity()));
        
        //vel.setXVelocity(vel.getXVelocity() + temp.getXVelocity());
        //vel.setYVelocity(vel.getYVelocity() + temp.getYVelocity());

        return vel;
    }


}
