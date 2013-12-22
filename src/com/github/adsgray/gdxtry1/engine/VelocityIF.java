package com.github.adsgray.gdxtry1.engine;

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
