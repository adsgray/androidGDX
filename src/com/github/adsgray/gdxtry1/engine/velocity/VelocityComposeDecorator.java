package com.github.adsgray.gdxtry1.engine.velocity;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

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
    
    @Override public int getXVelocity() { return component.getXVelocity() + primary.getXVelocity(); }
    @Override public int getYVelocity() { return component.getYVelocity() + primary.getYVelocity(); }
    @Override public int deltaX(int xin) { return primary.deltaX(component.deltaX(xin)); }
    @Override public int deltaY(int yin) { return primary.deltaY(component.deltaY(yin)); }
    
    // not sure what to do with these...
    @Override public int setXVelocity(int xin) { 
        //Log.d("velocity", "calling SetX in compose");
        return x = xin;
    }
    @Override public int setYVelocity(int yin) { 
        //Log.d("velocity", "calling SetY in compose");
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
