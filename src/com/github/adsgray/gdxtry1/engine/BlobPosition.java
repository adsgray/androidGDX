package com.github.adsgray.gdxtry1.engine;

import java.util.HashMap;

import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTrigger;

/** x,y position
 * 
 * @author andrew
 *
 */

public class BlobPosition implements PositionIF {
    private Integer x = 0;
    private Integer y = 0;
    
    // map from position value to trigger
    protected HashMap<Integer, BlobTrigger> xAxisTriggers;
    protected HashMap<Integer, BlobTrigger> yAxisTriggers;

    public BlobPosition(Integer xin, Integer yin) {
        x = xin;
        y = yin;
    }
    
    public BlobPosition(PositionIF frompos) {
        this(frompos.getX(), frompos.getY());
    }
    
    public Integer getX() { return x; }
    public Integer getY() { return y; }
    
    public Integer setX(Integer xin) { x = xin; return x; }
    public Integer setY(Integer yin) { y = yin; return y; }

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
            t.trigger(source);
        }
    }

    // another ugly optimization to avoid a method call in the usual case (no triggers)
    public void handleTriggers(BlobIF source) {
        if (xAxisTriggers != null) handleTrigger(xAxisTriggers, x, source);
        if (yAxisTriggers != null) handleTrigger(yAxisTriggers, y, source);
    }
}
