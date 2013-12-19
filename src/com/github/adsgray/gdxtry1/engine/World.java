package com.github.adsgray.gdxtry1.engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import android.util.Log;

public class World implements WorldIF {

    private Vector<BlobIF> objs;

    public World() {
        Log.d("trace", "World created");
        objs = new Vector<BlobIF>();
    }
    
    @Override
    public Boolean addBlobToWorld(BlobIF b) {
        return objs.add(b);
    }
    
    @Override
    public Boolean removeBlobFromWorld(BlobIF b) {
        return objs.remove(b);
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

        Iterator<BlobIF> iter = objs.iterator();

        while (iter.hasNext()) {
            BlobIF primary = iter.next();
            // huh? gross.
            Iterator<BlobIF> iter2 = objs.iterator();
            while (iter2.hasNext()) {
                BlobIF secondary = iter2.next();
                // skip ourselves, and skip if secondary is already involved in another collision ??
                // may have to remove already-involved check.
                if (primary != secondary && !col.containsValue(secondary) && primary.intersects(secondary)) {
                    col.put(primary, secondary);
                    // one collision per tick()/check
                    break;
                }
            }
        }

        return col;
    }
    
    private static void handleCollisions(CollisionMap collisions) {
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
    
    @Override
    public void tick() {
        Iterator<BlobIF> iter = objs.iterator();
        
        while (iter.hasNext()) {
            BlobIF b = iter.next();
            // Note that this may add or remove Blobs from objs.
            // They will be tick()ed on the next World tick()
            // BUT they will be rendered to the screen during this World tick()
            b.tick();
        }
        
        // save collisions for the next iteration and use it to optimize collision detection?
        CollisionMap collisions = findCollisions();
        handleCollisions(collisions);
    }

    @Override
    public void render() {
        Iterator<BlobIF> iter = objs.iterator();
        while(iter.hasNext()) {
            iter.next().render();
        }
    }
}
