package com.github.adsgray.gdxtry1.engine;

import android.util.Log;

import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.BlobFactory;

public class BlobTrailDecorator extends BlobDecorator {

    protected int step = 3;
    protected int count = 0;
    protected int lifetime = 25;
    protected BlobSource bs;

    // Coleen Jones "Christmas Tree" of constructors.
    public BlobTrailDecorator(BlobIF component, BlobSource bs) {
        super(component);
        this.bs = bs;
    }

    public BlobTrailDecorator(BlobIF component, BlobSource bs, int step) {
        this(component, bs);
        this.step = step;
    }

    public BlobTrailDecorator(BlobIF component, BlobSource bs, int step, int lifetime) {
        this(component, bs, step);
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
        
        BlobIF b = bs.generate(component);
        b.setLifeTime(lifetime);
        
        world.addBlobToWorld(b);
        return ret;
    }

}
