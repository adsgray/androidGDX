package com.github.adsgray.gdxtry1.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.badlogic.gdx.utils.Array;
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
    
    // same as above but always registers the chainTrigger after
    // performing the transformation
    static BlobTrigger primaryTransformChainingTrigger(BlobTransform bt) {
          BlobTrigger trig = new BlobTrigger(bt) {
            @Override
            public BlobIF trigger(BlobIF source, BlobIF secondary) {
                BlobIF transformed = blobTransform.transform(source);
                //transformed.clearTickDeathTriggers();
                // make sure that the chainTrigger only appears once in the list of triggers
                // inside the Blob
                //if (transformed != source) {
                    // ugh "scheduleRegisterTick..." necessary
                    transformed.deregisterTickDeathTrigger(chainTrigger);
                    transformed.registerTickDeathTrigger(chainTrigger);
                //}
                return transformed;
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
    
    

    // note: uses tickDeathTriggers and assumes that b has no such triggers already
    static public BlobTrigger createTransformSequence(List<BlobTransform> transforms, Boolean loop) {
        List<BlobTrigger> triggers = new ArrayList<BlobTrigger>();
        
        // create triggers out of the transforms
        Iterator<BlobTransform> iter = transforms.iterator();
        while (iter.hasNext()) {
            triggers.add(primaryTransformChainingTrigger(iter.next()));
        }
        
        // now chain them together
        // 0 chains to 1, 1 chains to 2, ... N chains to 0
        int i = 0;
        for (i = 0; i < triggers.size() - 1; i++) {
            triggers.get(i).setChainTrigger(triggers.get(i + 1));
        }

        // last trigger chains to trigger 0 if looping
        if (loop) {
            BlobTrigger lastTrigger = triggers.get(triggers.size() - 1);
            lastTrigger.setChainTrigger(triggers.get(0));
        }

        // kick things off by registering the first trigger
        return triggers.get(0);
    }
}
