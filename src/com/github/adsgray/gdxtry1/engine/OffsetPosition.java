package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTrigger;

public class OffsetPosition extends PositionDecorator {

    int offsetX;
    int offsetY;
    
    PositionIF primary;

    public OffsetPosition(PositionIF component, int x, int y) {
        super(component);
        this.offsetX = x;
        this.offsetY = y;
        
        primary = new BlobPosition(0,0);
    }

    @Override public int getX() { return component.getX() + offsetX + primary.getX(); }
    @Override public int getY() { return component.getY() + offsetY + primary.getY(); }

    // don't set anything in component ???
    // in fact abuse PositionIF to set the offset
    @Override public int setX(int x) { offsetX = x; return offsetX; }
    @Override public int setY(int y) { offsetY = y; return offsetY; }

    @Override public PositionIF updateByVelocity(VelocityIF vel) {
        primary.updateByVelocity(vel);
        return this;
    }

    @Override public void registerAxisTrigger(Axis type, int val, BlobTrigger trigger) {
        
    }
    @Override public void handleTriggers(BlobIF source) {
        
    }

}
