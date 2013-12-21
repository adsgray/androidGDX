package com.github.adsgray.gdxtry1.engine;

public class BlobTrailDecorator extends BlobDecorator {

    public BlobTrailDecorator(BlobIF component) {
        super(component);
    }
    
    @Override public void tick() {
        // now add a short-lived ephemeral blob to the world at
        // this position
        // could inject a blob factory so that this same decorator
        // can be used to create trails of different types of blobs
        BlobIF b = new CircleBlob(0, component.getPosition(), component.getVelocity(), 
                WeirdAccel.randomWeirdAccel(), component.getRenderer());

        b.setLifeTime(5);
        b.setWorld(world);
        
        world.scheduleEphemeralAddToWorld(b);

        component.tick();
    }

}
