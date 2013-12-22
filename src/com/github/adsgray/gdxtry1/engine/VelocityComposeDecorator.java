package com.github.adsgray.gdxtry1.engine;

public class VelocityComposeDecorator extends VelocityDecorator {

    protected VelocityIF primary;

    public VelocityComposeDecorator(VelocityIF component, VelocityIF primary) {
        super(component);
        this.primary = primary;
    }

    @Override public Integer getXVelocity() { return component.getXVelocity() + primary.getXVelocity(); }
    @Override public Integer getYVelocity() { return component.getYVelocity() + primary.getYVelocity(); }
    @Override public Integer deltaX(Integer xin) { return primary.deltaX(component.deltaX(xin)); }
    @Override public Integer deltaY(Integer yin) { return primary.deltaY(component.deltaY(yin)); }
    
    // not sure what to do with these...
    @Override public Integer setXVelocity(Integer xin) { return component.setXVelocity(xin); }
    @Override public Integer setYVelocity(Integer yin) { return component.setYVelocity(yin); }

}
