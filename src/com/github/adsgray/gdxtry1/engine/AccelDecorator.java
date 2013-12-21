package com.github.adsgray.gdxtry1.engine;

// eg. use to dampen/intensify acceleration. or compose multiple accelerations.
public abstract class AccelDecorator implements AccelIF {
    
    protected AccelIF component;
    
    public AccelDecorator(AccelIF component) {
        this.component = component;
    }

    @Override
    public VelocityIF accellerate(VelocityIF vel) {
        // TODO Auto-generated method stub
        return component.accellerate(vel);
    }

}
