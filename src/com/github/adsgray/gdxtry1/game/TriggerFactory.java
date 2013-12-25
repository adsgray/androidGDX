package com.github.adsgray.gdxtry1.game;

import com.github.adsgray.gdxtry1.engine.BlobIF;
import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTransform;
import com.github.adsgray.gdxtry1.engine.ExplosionBlob;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.output.RenderConfig;

// Transforms and Triggers
public class TriggerFactory {
    
    // like a decorator
    static public BlobIF replaceWithExplosion(BlobIF b) {
        WorldIF w = b.getWorld();
        RenderConfig r = b.getRenderer();
        w.removeBlobFromWorld(b);
        ExplosionBlob ex = new ExplosionBlob(0, b.getPosition(), GameFactory.zeroVelocity(), GameFactory.zeroAccel(), r);
        ex.setBlobSource(BlobFactory.explosionBlobSource);
        w.addBlobToWorld(ex);
        return ex;
    }

    static public BlobTransform transformReplaceWithExplosion(BlobIF b) {
        BlobTransform bt = new BlobTransform() {
            @Override public BlobIF transform(BlobIF b) { return replaceWithExplosion(b); };
        };
        return bt;
    }

}
