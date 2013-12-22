package com.github.adsgray.gdxtry1.engine;

import android.util.Log;

public class VelocityComposeDecorator extends VelocityDecorator {

    // primary is a bad name for this, could be "wrapper" ??
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
        Log.d("velocity", "calling SetX in compose");
        return x = xin;
    }
    @Override public Integer setYVelocity(Integer yin) { 
        Log.d("velocity", "calling SetY in compose");
        return y = yin;
    }

    @Override
    public PositionIF updatePosition(PositionIF pos) {
        pos.setX(deltaX(pos.getX()));
        pos.setY(deltaX(pos.getY()));
        return pos;
    }

    @Override
    public VelocityIF accelerate(AccelIF a) {
        component = component.accelerate(a);
        //primary = primary.accelerate(a);
        //component = a.accellerate(component);
        //primary = a.accellerate(primary);
        return this;
    }

}
