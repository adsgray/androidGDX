package com.github.adsgray.gdxtry1.engine;

import android.util.Log;

import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.BlobFactory;

public class BlobTrailDecorator extends BlobDecorator {

    protected int step = 3;
    protected int count = 0;
    protected int lifetime = 25;

    public BlobTrailDecorator(BlobIF component) {
        super(component);
    }

    public BlobTrailDecorator(BlobIF component, int step) {
        super(component);
        this.step = step;
    }

    public BlobTrailDecorator(BlobIF component, int step, int lifetime) {
        super(component);
        this.step = step;
        this.lifetime = lifetime;
    }
   
    
    @Override public Boolean tick() {
        // now add a short-lived ephemeral blob to the world at
        // this position
        // could inject a blob factory so that this same decorator
        // can be used to create trails of different types of blobs
        
        Boolean ret = component.tick();
        count += 1;

        if (count < step) {
            return ret;
        }
        
        count = 0;
        
        /*
        BlobIF b = new ShrinkingCircleBlob(0, component.getPosition(), component.getVelocity(), 
                WeirdAccel.randomWeirdAccel(), component.getRenderer());
                */

        BlobIF b = BlobFactory.createSmokeTrailBlob(component);
        b.setLifeTime(lifetime);
        
        world.scheduleEphemeralAddToWorld(b);
        return ret;
    }

}
