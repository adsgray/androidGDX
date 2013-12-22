package com.github.adsgray.gdxtry1.engine;

import android.util.Log;

public class VelocityComposeDecorator extends VelocityDecorator {

    protected VelocityIF primary;
    protected int x;
    protected int y;

    public VelocityComposeDecorator(VelocityIF component, VelocityIF primary) {
        super(component);
        this.primary = primary;
    }

    @Override public Integer getXVelocity() { return component.getXVelocity() + primary.getXVelocity(); }
    @Override public Integer getYVelocity() { return component.getYVelocity() + primary.getYVelocity(); }
    @Override public Integer deltaX(Integer xin) { return primary.deltaX(component.deltaX(xin)); }
    @Override public Integer deltaY(Integer yin) { return primary.deltaY(component.deltaY(yin)); }
    
    // not sure what to do with these...
    @Override public Integer setXVelocity(Integer xin) { 
        //return primary.setXVelocity(xin); 
        return x = xin;
    }
    @Override public Integer setYVelocity(Integer yin) { 
        //return primary.setYVelocity(yin); 
        return y = yin;
    }

    @Override
    public PositionIF updatePosition(PositionIF pos) {
        pos.setX(deltaX(pos.getX()));
        pos.setY(deltaX(pos.getY()));
        return pos;
    }

    @Override
    public void accelerate(AccelIF a) {
        component = a.accellerate(component);
        primary = a.accellerate(primary);
    }

}
