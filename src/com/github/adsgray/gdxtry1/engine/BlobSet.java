package com.github.adsgray.gdxtry1.engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.github.adsgray.gdxtry1.output.RenderConfig;

// BlobSet is like a mini-world: it has responsibility/control of Blobs.
public class BlobSet extends BaseBlob {

    protected Set<BlobIF> objs;
    protected Set<BlobIF> toRemove;
    
    public BlobSet(Integer massin, PositionIF posin, VelocityIF velin,
            AccelIF accel, RenderConfig gdx) {
        super(massin, posin, velin, accel, gdx);
        objs = new HashSet<BlobIF>();
        toRemove = new HashSet<BlobIF>();
    }

    /* called by outside controller to tell this Blob
     * to advance one time unit.
     * Return true if blob should remain in world
     * Return false if it should be removed from world
     */
    @Override
    public Boolean tick() {
        position.updateByVelocity(velocity);
        // update velocity with its accelleration
        velocity = acceleration.accellerate(velocity);

        // update each BlobIF in set by composing its velocity with the set's
        
        Iterator<BlobIF> iter = objs.iterator();
        while (iter.hasNext()) {
            BlobIF b = iter.next();
            if (!b.tick()) {
                //world.scheduleRemovalFromWorld(b);
                scheduleForRemoval(b);
            }
        }
        
        handleScheduledRemovals();
        
        ticks += 1;
        if (ticks >= maxTicks) {
            return false;
        }
        
        return true;
    }
    
    private void scheduleForRemoval(BlobIF b) {
        toRemove.add(b);
    }
    
    private void handleScheduledRemovals() {
        Iterator<BlobIF> iter = toRemove.iterator();
        while (iter.hasNext()) {
            objs.remove(iter.next());
        }
        
        toRemove.clear();
    }
   
    @Override
    public void render() {
        // render each BlobIF in the set
        Iterator<BlobIF> iter = objs.iterator();
        
        while (iter.hasNext()) {
            iter.next().render();
        }
    }

    @Override
    public BlobIF absorbBlob(BlobIF b) {
        // add to objs
        // remove from world (we'll be handling ticks/renders ?
        return absorbBlob(b, null);
    }

    @Override
    public BlobIF absorbBlob(BlobIF b, BlobTransform bt) {
        // add to objs
        // remove from world (we'll be handling ticks/renders ?
        
        world.scheduleRemovalFromWorld(b);
        if (bt != null) {
            // transform could be to zero out velocity and accel so that
            // the absorbed blob stops moving.
            b = bt.transform(b);
        }
        b.setAccel(new AccelComposeDecorator(b.getAccel(), acceleration));
        objs.add(b);
        
        return this;
    }

}
