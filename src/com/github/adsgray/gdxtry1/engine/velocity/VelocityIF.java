package com.github.adsgray.gdxtry1.engine.velocity;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

public interface VelocityIF {
    public Integer getXVelocity();
    public Integer getYVelocity();
    
    public Integer deltaX(Integer xin);
    public Integer deltaY(Integer xin);
    
    public Integer setXVelocity(Integer xin);
    public Integer setYVelocity(Integer yin);
    
    public VelocityIF accelerate(AccelIF a);
    
    public PositionIF updatePosition(PositionIF pos);
}
