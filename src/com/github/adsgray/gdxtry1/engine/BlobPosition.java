package com.github.adsgray.gdxtry1.engine;

import java.util.HashMap;

import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTrigger;

/** x,y position
 * 
 * @author andrew
 *
 */

public class BlobPosition implements PositionIF {
    private int x = 0;
    private int y = 0;
    
    // map from position value to trigger
    protected HashMap<Integer, BlobTrigger> xAxisTriggers;
    protected HashMap<Integer, BlobTrigger> yAxisTriggers;

    public BlobPosition(int xin, int yin) {
        x = xin;
        y = yin;
    }
    
    public BlobPosition(double x, double y) {
        this((int)x, (int)y);
    }
    
    public BlobPosition(PositionIF frompos) {
        this(frompos.getX(), frompos.getY());
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    
    public int setX(int xin) { x = xin; return x; }
    public int setY(int yin) { y = yin; return y; }

    public PositionIF updateByVelocity(VelocityIF vel) {
        x = vel.deltaX(x);
        y = vel.deltaY(y);
        return this;
    }
 
    // kind of an ugly optimization to delay creating the HashMaps
    // until a trigger is registered but seems a big waste to do
    // it in the constructor when very few will use it.
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
        if (xAxisTriggers != null) handleTrigger(xAxisTriggers, x, source);
        if (yAxisTriggers != null) handleTrigger(yAxisTriggers, y, source);
    }

    @Override
    public PositionIF subtract(PositionIF p) {
        return new BlobPosition(x - p.getX(), y - p.getY());
    }

    @Override
    public PositionIF add(PositionIF p) {
        return new BlobPosition(x + p.getX(), y + p.getY());
    }

    @Override
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public PositionIF multiply(double factor) {
        return new BlobPosition(x * factor, y * factor);
    }

    @Override
    public PositionIF divide(double factor) {
        return multiply(1 / factor);
    }

    @Override
    public PositionIF unitVector() {
        double len = length();
        return new BlobPosition(x / len, y / len);
    }
    
    @Override public PositionIF ofLength(double factor) {
        double len = length();
        return new BlobPosition((x * factor) / len, (y * factor) / len);
    }
}
