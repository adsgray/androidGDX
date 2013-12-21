package com.github.adsgray.gdxtry1.engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.adsgray.gdxtry1.output.RenderConfig;

import android.util.Log;

public class World implements WorldIF {

    private Vector<BlobIF> objs;
    private Vector<BlobIF> toRemove;
    private Vector<BlobIF> toAdd;
    private Vector<BlobIF> toAddEphemeral;
    private Vector<BlobIF> ephemerals; // these are ignored wrt collisions
    //private RenderConfig renderer;

    public World() {
        Log.d("trace", "World created");
        objs = new Vector<BlobIF>();
        toRemove = new Vector<BlobIF>();
        toAdd = new Vector<BlobIF>();
        toAddEphemeral = new Vector<BlobIF>();
        ephemerals = new Vector<BlobIF>();
    }

    /*
    public World(RenderConfig r) { renderer = r; }
    public RenderConfig getRenderer() { return renderer; }
    public void setRenderer(RenderConfig r) { renderer = r; }
    */
    
    @Override
    public Boolean addBlobToWorld(BlobIF b) {
        return objs.add(b);
    }
  
    @Override
    public Boolean addEphemeralBlobToWorld(BlobIF b) {
        return ephemerals.add(b);
    }
  
    @Override
    public Boolean removeBlobFromWorld(BlobIF b) {
        if (objs.contains(b)) {
            return objs.remove(b);
        } else if (ephemerals.contains(b)) {
            return ephemerals.remove(b);
        } 
        return false;
    }

    @Override
    public void scheduleRemovalFromWorld(BlobIF b) {
        toRemove.add(b);
    }
    
    @Override
    public void scheduleAddToWorld(BlobIF b) {
        toAdd.add(b);
    }
     
    @Override
    public void scheduleEphemeralAddToWorld(BlobIF b) {
        toAddEphemeral.add(b);
    }
   
    private void handleScheduledRemovalsAndAdds() {
        Iterator<BlobIF> iter = toRemove.iterator();
        
        while (iter.hasNext()) {
            removeBlobFromWorld(iter.next());
        }
        
        iter = toAdd.iterator();
        while (iter.hasNext()) {
            addBlobToWorld(iter.next());
        }
        
        iter = toAddEphemeral.iterator();
        while (iter.hasNext()) {
            addEphemeralBlobToWorld(iter.next());
        }
        
        toAdd.clear();
        toAddEphemeral.clear();
        toRemove.clear();
    }
    
    @Override
    public void killAllBlobs() {
        Iterator<BlobIF> iter = objs.iterator(); 
        while (iter.hasNext()) { scheduleRemovalFromWorld(iter.next()); }
        iter = ephemerals.iterator();
        while (iter.hasNext()) { scheduleRemovalFromWorld(iter.next()); }
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
        
        iter = ephemerals.iterator();
        while (iter.hasNext()) {
            iter.next().tick();
        }
        
        handleScheduledRemovalsAndAdds();
        
        // save collisions for the next iteration and use it to optimize collision detection?
        //CollisionMap collisions = findCollisions();
        //handleCollisions(collisions);
    }

    private void renderBlobVector(Vector<BlobIF> these) {
        Iterator<BlobIF> iter = these.iterator();
        while(iter.hasNext()) {
            iter.next().render();
        }
    }
    
    @Override
    public void render() {
        renderBlobVector(ephemerals);
        renderBlobVector(objs);
    }

}
