package com.github.adsgray.gdxtry1.engine.velocity;

public abstract class VelocityDecorator implements VelocityIF {

    protected VelocityIF component;
    
    public VelocityDecorator(VelocityIF component) {
        this.component = component;
    }
    
    public VelocityIF getComponent() { return component; }

    @Override public int getXVelocity() { return component.getXVelocity(); }
    @Override public int getYVelocity() { return component.getYVelocity(); }
    @Override public int deltaX(int xin) { return component.deltaX(xin); }
    @Override public int deltaY(int xin) { return component.deltaY(xin); }
    @Override public int setXVelocity(int xin) { return component.setXVelocity(xin); }
    @Override public int setYVelocity(int yin) { return component.setYVelocity(yin); }

}
