package com.github.adsgray.gdxtry1.engine;

public interface PositionIF {
    public Integer getX();
    public Integer getY();
    
    public Integer setX(Integer x);
    public Integer setY(Integer y);
    
    public PositionIF updateByVelocity(VelocityIF vel);
}
