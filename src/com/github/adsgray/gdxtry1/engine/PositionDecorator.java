package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTrigger;

public class PositionDecorator implements PositionIF {

    PositionIF component;

    public PositionDecorator(PositionIF component) {
        this.component = component;
    }

    @Override public int getX() { return component.getX(); }
    @Override public int getY() { return component.getY(); }
    @Override public int setX(int x) { return component.setX(x); }
    @Override public int setY(int y) { return component.setY(y); }
    @Override public PositionIF updateByVelocity(VelocityIF vel) { return component.updateByVelocity(vel); }

    // subclassed decorators can override this to 'expire' themselves after a certain number of ticks.
    // when they expire they will return component.compressDecorators();
    @Override public PositionIF compressDecorators() { return this; }

    @Override public PositionIF subtract(PositionIF p) { return component.subtract(p); }
    @Override public PositionIF add(PositionIF p) { return component.add(p); }
    @Override public PositionIF multiply(double factor) { return component.multiply(factor); }
    @Override public PositionIF divide(double factor) { return component.divide(factor); }
    @Override public double length() { return component.length(); }
    @Override public PositionIF unitVector() { return component.unitVector(); }
    @Override public PositionIF ofLength(double factor) { return component.ofLength(factor); }

    // ignore Axis triggers for now. Would probably have to have this Decorator base class
    // extend BlobPosition and store its own triggers...
    @Override public void registerAxisTrigger(Axis type, int val, BlobTrigger trigger) {
        component.registerAxisTrigger(type, val, trigger);
    }

    @Override public void handleTriggers(BlobIF source) {
        component.handleTriggers(source);
    }

}
