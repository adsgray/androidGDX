package com.github.adsgray.gdxtry1.game;

import com.github.adsgray.gdxtry1.engine.BlobIF;
import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTransform;
import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.ExplosionBlob;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.output.RenderConfig;

// Transforms and Triggers
public class TriggerFactory {
    
    // performs bt on the primary/source of a (collision) trigger
    static BlobTrigger primaryTransformTrigger(BlobTransform bt) {
        BlobTrigger trig = new BlobTrigger(bt) {
            @Override
            public BlobIF trigger(BlobIF source, BlobIF secondary) {
                return blobTransform.transform(source);
            }
        };
        return trig;
    }
    
    // performs bt on the secondary of a (collision) trigger
    static BlobTrigger secondaryTransformTrigger(BlobTransform bt) {
        BlobTrigger trig = new BlobTrigger(bt) {
            @Override
            public BlobIF trigger(BlobIF source, BlobIF secondary) {
                // triggers must always return source or a transformed source
                // for chaining. If youw want to do multiple things to the
                // secondary then create a single BlobTransform that 
                // chains transformations/decorations
                blobTransform.transform(secondary);
                return source;
            }
        };
        return trig;
    }

    // like a decorator
    static public BlobIF replaceWithExplosion(BlobIF b) {
        WorldIF w = b.getWorld();
        RenderConfig r = b.getRenderer();
        w.removeBlobFromWorld(b);
        ExplosionBlob ex = new ExplosionBlob(0, b.getPosition(), GameFactory.zeroVelocity(), GameFactory.zeroAccel(), r);
        ex.setBlobSource(BlobFactory.explosionBlobSource);
        ex.setWorld(w);
        w.addBlobToWorld(ex);
        return ex;
    }
    
    static public BlobTransform transformReplaceWithExplosion() {
        BlobTransform bt = new BlobTransform() {
            @Override public BlobIF transform(BlobIF b) { return replaceWithExplosion(b); };
        };
        return bt;
    }
    

}
