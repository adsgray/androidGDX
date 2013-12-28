package com.github.adsgray.gdxtry1.engine.position;

public class MultiplyPosition extends PositionComposeDecorator
 {

    // The idea is to pass in BlobPosition(-1,0) to get
    // something that mirrors component position in the Y plane.
    // I have no idea what it would look like if the primary
    // position actually had a velocity/accel applied to it.
    // Also: only really useful for things that are inside
    // BlobSet2s or BlobClusters where (0,0) is the set/cluster's position.

    public MultiplyPosition(PositionIF component, PositionIF primary) {
        super(component, primary);
    }

    @Override public int getX() { return primary.getX() * component.getX(); }
    @Override public int getY() { return primary.getY() * component.getY(); }
    
    @Override public int setX(int x) { return primary.setX(x) * component.getX(); }
    @Override public int setY(int y) { return primary.setY(y) * component.getY(); }
   
}
