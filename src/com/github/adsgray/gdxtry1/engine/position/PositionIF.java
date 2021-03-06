package com.github.adsgray.gdxtry1.engine.position;

import com.github.adsgray.gdxtry1.engine.DecoratorIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public interface PositionIF extends DecoratorIF {
    public int getX();
    public int getY();
    
    public int setX(int x);
    public int setY(int y);
    
    public PositionIF updateByVelocity(VelocityIF vel);
    public PositionIF compressDecorators(); // 'recursively' remove decorators that have expired
    
    
    public PositionIF subtract(PositionIF p);
    public PositionIF add(PositionIF p);
    public PositionIF multiply(double factor);
    public PositionIF divide(double factor); // convenience wrapper around multiply

    // OK now conflating positions with vectors...
    public double length();
    public PositionIF unitVector();
    public PositionIF ofLength(double factor); // vector in same direction of length "factor"
    
    
    public enum Axis {
        X,Y
    }
    
    public void registerAxisTrigger(Axis type, int val, BlobTrigger trigger);
    public void handleTriggers(BlobIF source);
}
