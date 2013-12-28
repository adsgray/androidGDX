package com.github.adsgray.gdxtry1.engine.blob.decorator;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.game.GameFactory;

import android.util.Log;

/**
 * Crazy Ivan
 * Swap in a new randomAccel every few ticks
 * @author andrew
 *
 */
public class BlobCrazyAccelDecorator extends BlobDecorator {

    protected int step = 10;
    protected int count = 0;

    public BlobCrazyAccelDecorator(BlobIF component) {
        super(component);
    }

    public BlobCrazyAccelDecorator(BlobIF component, int step) {
        super(component);
        this.step = step;
    }

    
    @Override
    public Boolean tick() {
        Boolean ret = component.tick();
        
        // should abstract this, I keep messing it up when I copy/re-create it.
        if (count < step) {
            count += 1;
            return ret;
        }
        
        count = 0;
        component.setAccel(GameFactory.randomAccel());
        return ret;
    }
}
