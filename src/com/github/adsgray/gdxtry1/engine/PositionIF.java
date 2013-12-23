package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTrigger;

public interface PositionIF {
    public Integer getX();
    public Integer getY();
    
    public Integer setX(Integer x);
    public Integer setY(Integer y);
    
    public PositionIF updateByVelocity(VelocityIF vel);
    
    public enum Axis {
        X,Y
    }
    
    public void registerAxisTrigger(Axis type, int val, BlobTrigger trigger);
    public void handleTriggers(BlobIF source);
}
