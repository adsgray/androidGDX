package com.github.adsgray.gdxtry1.engine;

public class AccelComposeDecorator extends AccelDecorator {

    protected AccelIF primary;

    public AccelComposeDecorator(AccelIF component, AccelIF primary) {
        super(component);
        this.primary = primary;
    }

    @Override
    public VelocityIF accellerate(VelocityIF vel) {
        // TODO Auto-generated method stub
        return primary.accellerate(component.accellerate(vel));
    }

}
