package com.github.adsgray.gdxtry1.engine.position;

import java.util.HashMap;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public class PositionComposeDecorator extends PositionDecorator {

    PositionIF primary;
    protected HashMap<Integer, BlobTrigger> xAxisTriggers;
    protected HashMap<Integer, BlobTrigger> yAxisTriggers;

    public PositionComposeDecorator(PositionIF component, PositionIF primary) {
        super(component);
        this.primary = primary;
    }

    @Override public int getX() { return primary.getX() + component.getX(); }
    @Override public int getY() { return primary.getY() + component.getY(); }
    
    @Override public int setX(int x) { return primary.setX(x) + component.getX(); }
    @Override public int setY(int y) { return primary.setY(y) + component.getY(); }
    
    @Override public PositionIF updateByVelocity(VelocityIF vel) { return primary.updateByVelocity(vel); }
    @Override public PositionIF subtract(PositionIF p) { return primary.add(component).subtract(p); }
    @Override public PositionIF add(PositionIF p) { return primary.add(component).add(p); }
    @Override public PositionIF multiply(double factor) { return primary.add(component).multiply(factor); }
    @Override public PositionIF divide(double factor) { return multiply(1 / factor); }

    // OK now conflating positions with vectors... Not used anywhere, should delete
    @Override public double length() { return primary.add(component).length(); }
    @Override public PositionIF unitVector() { return primary.add(component).unitVector(); }
    @Override public PositionIF ofLength(double factor) { return primary.add(component).ofLength(factor); }
   
    // copied wholesale from BlobPosition... clearly this should be somewhere shareable
    // Both BlobPosition and this class "has-a" trigger subsystem.
    @Override
    public void registerAxisTrigger(Axis type, int val, BlobTrigger trigger) {
        switch (type) {
        case X:
            if (xAxisTriggers == null) {
                xAxisTriggers = new HashMap<Integer, BlobTrigger>();
            }
            xAxisTriggers.put(val, trigger);
            break;
        case Y:
            if (yAxisTriggers == null) {
                yAxisTriggers = new HashMap<Integer, BlobTrigger>();
            }
            yAxisTriggers.put(val, trigger);
            break;
        }
    }
    
    protected void handleTrigger(HashMap<Integer, BlobTrigger> set, Integer pos, BlobIF source) {
        if (set.containsKey(pos)) {
            BlobTrigger t = set.get(pos);
            t.trigger(source, null);
        }
    }

    // another ugly optimization to avoid a method call in the usual case (no triggers)
    public void handleTriggers(BlobIF source) {
        if (xAxisTriggers != null) handleTrigger(xAxisTriggers, getX(), source);
        if (yAxisTriggers != null) handleTrigger(yAxisTriggers, getY(), source);
    }
  
}
