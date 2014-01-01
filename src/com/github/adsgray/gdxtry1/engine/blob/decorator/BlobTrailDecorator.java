package com.github.adsgray.gdxtry1.engine.blob.decorator;

import android.util.Log;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.util.BlobFactory;
import com.github.adsgray.gdxtry1.engine.util.GameFactory;

public class BlobTrailDecorator extends BlobDecorator {

    protected int step = 3;
    protected int count = 0;
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
        
        bs.get(component);
        
        return ret;
    }

}
