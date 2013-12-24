package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTrigger;

public interface PositionIF {
    public int getX();
    public int getY();
    
    public int setX(int x);
    public int setY(int y);
    
    public PositionIF updateByVelocity(VelocityIF vel);
    public PositionIF subtract(PositionIF p);
    public PositionIF multiply(double factor);
    public PositionIF divide(double factor); // convenience wrapper around multiply

    // OK now conflating positions with vectors...
    public double length();
    public PositionIF unitVector();
    
    public enum Axis {
        X,Y
    }
    
    public void registerAxisTrigger(Axis type, int val, BlobTrigger trigger);
    public void handleTriggers(BlobIF source);
}
