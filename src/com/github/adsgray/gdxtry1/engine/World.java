package com.github.adsgray.gdxtry1.engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.adsgray.gdxtry1.output.RenderConfig;

import android.util.Log;

public class World implements WorldIF {

    private BlobManager blobs;
    private BlobManager missiles;
    private BlobManager targets;

    protected CollisionMap collisions;

    public World() {
        Log.d("trace", "World created");
        blobs = new BlobManager();
        missiles = new BlobManager();
        targets = new BlobManager();
    }

    /*
    public World(RenderConfig r) { renderer = r; }
    public RenderConfig getRenderer() { return renderer; }
    public void setRenderer(RenderConfig r) { renderer = r; }
    */
    
    @Override
    public Boolean addBlobToWorld(BlobIF b) {
        return blobs.scheduleAdd(b);
    }
  
    @Override
    public Boolean removeBlobFromWorld(BlobIF b) {
        missiles.scheduleRemoval(b);
        targets.scheduleRemoval(b);
        return blobs.scheduleRemoval(b);
    }
 
    @Override
    public Boolean addTargetToWorld(BlobIF b) {
        return targets.scheduleAdd(b);
    }
  
    @Override
    public Boolean removeTargetFromWorld(BlobIF b) {
        return targets.scheduleRemoval(b);
    }
 
    @Override
    public Boolean addMissileToWorld(BlobIF b) {
        return missiles.scheduleAdd(b);
    }
  
    @Override
    public Boolean removeMissileFromWorld(BlobIF b) {
        return missiles.scheduleRemoval(b);
    }

    @Override
    public void killAllBlobs() {
        blobs.killAllBlobs();
        targets.killAllBlobs();
        missiles.killAllBlobs();
    }
    
    // this is the accepted lame way of doing type aliases in Java?
    private class CollisionMap extends HashMap<BlobIF, BlobIF> {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
    }
    
    private CollisionMap findCollisions() {

        CollisionMap col = new CollisionMap();
        // iterate over objs and return a HashMap of colliding Blobs
        // there will be one entry per collision participant
        //col.put(blobA, blobB);
        // when we get to blobB, skip it if col.containsValue(blobB)
        // only one collision per tick?
        
        // this is N^2 and gross:
        // Note: objs is not modified by any of the following code:

        Iterator<BlobIF> missiter = missiles.objs.iterator();

        while (missiter.hasNext()) {
            BlobIF missile = missiter.next();
            // huh? gross.
            Iterator<BlobIF> targiter = targets.objs.iterator();
            while (targiter.hasNext()) {
                BlobIF target = targiter.next();
                // skip ourselves, and skip if secondary is already involved in another collision ??
                // may have to remove already-involved check.
                //if (primary != secondary && !col.containsValue(secondary) && primary.intersects(secondary)) {
                if (missile.intersects(target)) {
                    col.put(missile, target);
                    // one collision per tick()/check. That way, if the collision
                    // kills the missile (decided by triggered callback) then you
                    // can only kill one target with a missile.
                    //break;
                }
            }
        }

        return col;
    }
    
    public void handleCollisions() {

        if (collisions == null) return;

        Iterator<BlobIF> iter = collisions.keySet().iterator();

        // for Blobs that have collided, call b.collision(with)
        while (iter.hasNext()) {
            BlobIF primary = iter.next();
            BlobIF secondary = collisions.get(primary);
            
            // they collide with each other
            primary.collision(secondary);
            secondary.collision(primary);
        }
    }
    
    static int ct = 0;
    @Override
    public void tick() {
        blobs.tick();
        missiles.tick();
        targets.tick();
        
        ct += 1;
        if (ct == 100) {
            ct = 0;
            Log.d("trace", String.format("counts: b=%d m=%d t=%d", blobs.objs.size(), missiles.objs.size(), targets.objs.size()));
        }
        
        // save collisions for the next iteration and use it to optimize collision detection?
        collisions = findCollisions();
        handleCollisions();
    }

    @Override
    public void render() {
        blobs.render();
        missiles.render();
        targets.render();
    }

    // another C struct
    private class BlobManager {
        public Vector<BlobIF> objs;
        public Vector<BlobIF> toAdd;
        public Vector<BlobIF> toRemove;
        
        public BlobManager() {
            objs = new Vector<BlobIF>();
            toAdd = new Vector<BlobIF>();
            toRemove = new Vector<BlobIF>();
        }
        
        public Boolean scheduleAdd(BlobIF b) { return toAdd.add(b); }

        public Boolean scheduleRemoval(BlobIF b) { return toRemove.add(b); }

        public void handleScheduledAddsAndRemovals() {
            Iterator<BlobIF> iter = toRemove.iterator();
        
            while (iter.hasNext()) {
                //Log.d("trace", "trying to remove blob from world");
                BlobIF b = iter.next();
                objs.remove(b);
            }
        
            iter = toAdd.iterator();
            while (iter.hasNext()) {
                BlobIF b = iter.next();
                b.setWorld(World.this);
                objs.add(b);
            }
        
            toAdd.clear();
            toRemove.clear();           
        }
        
        public void render() {
            Iterator<BlobIF> iter = objs.iterator();
            while(iter.hasNext()) {
                iter.next().render();
            }
        }
        
        public void killAllBlobs() {
            toAdd.clear();
            toRemove.clear();
            objs.clear();
        }
        
        public void tick() {
            Iterator<BlobIF> iter = objs.iterator();
            while (iter.hasNext()) {
                BlobIF b = iter.next();
                // Note that this may add or remove Blobs from objs.
                // They will be tick()ed on the next World tick()
                // BUT they will be rendered to the screen during this World tick()
                if (!b.tick()) {
                    scheduleRemoval(b);
                }
            }
            handleScheduledAddsAndRemovals();
        }
    }

}
