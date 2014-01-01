package com.github.adsgray.gdxtry1.engine.velocity;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

public interface VelocityIF {
    public int getXVelocity();
    public int getYVelocity();
    
    public int deltaX(int xin);
    public int deltaY(int xin);
    
    public int setXVelocity(int xin);
    public int setYVelocity(int yin);
    
    public VelocityIF accelerate(AccelIF a);
    
    public PositionIF updatePosition(PositionIF pos);
}
