package com.github.adsgray.gdxtry1.engine;

public abstract class VelocityDecorator implements VelocityIF {

    protected VelocityIF component;
    
    public VelocityDecorator(VelocityIF component) {
        this.component = component;
    }

    @Override public Integer getXVelocity() { return component.getXVelocity(); }
    @Override public Integer getYVelocity() { return component.getYVelocity(); }
    @Override public Integer deltaX(Integer xin) { return component.deltaX(xin); }
    @Override public Integer deltaY(Integer xin) { return component.deltaY(xin); }
    @Override public Integer setXVelocity(Integer xin) { return component.setXVelocity(xin); }
    @Override public Integer setYVelocity(Integer yin) { return component.setYVelocity(yin); }

}
