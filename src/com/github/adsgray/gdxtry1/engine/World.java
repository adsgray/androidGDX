package com.github.adsgray.gdxtry1.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.util.StateIF;

import android.util.Log;

public class World implements WorldIF {

    private BlobManager blobs;
    private BlobManager missiles;
    private BlobManager targets;
    private Synchro synchro;
    private List<BlobManager> trackBlobs;

    protected CollisionMap collisions;

    public World() {
        //Log.d("trace", "World created");
        blobs = new BlobManager();
        missiles = new BlobManager();
        targets = new BlobManager();
        collisions = new CollisionMap();
        synchro = new RealSynchro();

        trackBlobs = new ArrayList<BlobManager>();
    }
    
    private static interface Synchro {
        public void lock();
        public void unlock();
    }
    
    private static class RealSynchro implements Synchro {
        private Lock m;

        public RealSynchro() {
            m = new ReentrantLock(true);
        }

        @Override public void lock() { m.lock(); }
        @Override public void unlock() { m.unlock(); }
    }
    
    private static class FakeSynchro implements Synchro {
        @Override public void lock() { }
        @Override public void unlock() { }
    }


    /*
    public World(RenderConfig r) { renderer = r; }
    public RenderConfig getRenderer() { return renderer; }
    public void setRenderer(RenderConfig r) { renderer = r; }
    */
    
    /* synchro is a re-entrant lock so we can do nested lock/unlock 
     * The reason we have to do this here is because there are potentially 2 (or 3?)
     * threads all trying to look at BlobManager.objs at the same time.
     * 
     * 1. World.tick() --> WorldTimer thread --> holds the lock
     * 2. World.render() --> libGDX render thread --> holds the lock
     * 3. Gesture/Input thread? Does not hold the lock if any of that code calls add*ToWorld()
     *    which it potentially can (And does in shape-mergency: tap the triangle and it adds missiles
     *    to the world. I can't believe it doesn't constantly crash. I've only seen it a couple
     *    of times.
     * */
    protected Boolean scheduleAddBlobToBlobManager(BlobIF b, BlobManager manager) {
        synchro.lock();
        boolean ret = manager.scheduleAdd(b);
        synchro.unlock();
        return ret;
    }
    
    protected Boolean scheduleRemoveBlobFromBlobManager(BlobIF b, BlobManager manager) {
        synchro.lock();
        boolean ret = manager.scheduleRemoval(b);
        synchro.unlock();
        return ret;
    }

    @Override
    public Boolean addBlobToWorld(BlobIF b) {
        return scheduleAddBlobToBlobManager(b, blobs);
    }
  
    @Override
    public Boolean removeBlobFromWorld(BlobIF b) {
        synchro.lock();
        removeFromTrackableBlobLists(b);
        missiles.scheduleRemoval(b);
        targets.scheduleRemoval(b);
        boolean ret = blobs.scheduleRemoval(b);
        synchro.unlock();
        return ret;
    }
 
    @Override
    public Boolean addTargetToWorld(BlobIF b) {
        return scheduleAddBlobToBlobManager(b, targets);
    }
  
    @Override
    public Boolean removeTargetFromWorld(BlobIF b) {
        return scheduleRemoveBlobFromBlobManager(b, targets);
    }
 
    @Override
    public Boolean addMissileToWorld(BlobIF b) {
        return scheduleAddBlobToBlobManager(b, missiles);
    }
  
    @Override
    public Boolean removeMissileFromWorld(BlobIF b) {
        return scheduleRemoveBlobFromBlobManager(b, missiles);
    }

    @Override
    public void killAllBlobs() {
        synchro.lock();
        blobs.killAllBlobs();
        targets.killAllBlobs();
        missiles.killAllBlobs();
        synchro.unlock();
    }
    
    // this is the accepted lame way of doing type aliases in Java?
    private class CollisionMap extends HashMap<BlobIF, BlobIF> {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
    }
    
    private CollisionMap findCollisions() {

        CollisionMap col = collisions;
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
        if (collisions.isEmpty()) return;

        Iterator<BlobIF> iter = collisions.keySet().iterator();

        // for Blobs that have collided, call b.collision(with)
        while (iter.hasNext()) {
            BlobIF primary = iter.next();
            BlobIF secondary = collisions.get(primary);
            
            // they collide with each other
            primary.collision(secondary);
            secondary.collision(primary);
        }
        
        collisions.clear();
    }
    
    static int ct = 0;
    @Override
    public void tick() {
        synchro.lock();

        blobs.tick();
        missiles.tick();
        targets.tick();
        handleTrackableBlobListsAddsRemovals();
        
        ct += 1;
        if (ct == 100) {
            ct = 0;
            Log.d("trace", String.format("counts: b=%d m=%d t=%d", blobs.objs.size(), missiles.objs.size(), targets.objs.size()));
            //blobs.debugDump();
            Log.d("trace", String.format("to rem counts: b=%d m=%d t=%d", blobs.toRemove.size(), missiles.toRemove.size(), targets.toRemove.size()));
            //missiles.debugDump();
            Log.d("trace", String.format("to add counts: b=%d m=%d t=%d", blobs.toAdd.size(), missiles.toAdd.size(), targets.toAdd.size()));
            //targets.debugDump();
            dumpTrackableBlobLists();
        }
        
        // save collisions for the next iteration and use it to optimize collision detection?
        // only every other tick
        if (ct % 2 == 0) {
            collisions = findCollisions();
            handleCollisions(); 
        }
        
        synchro.unlock();
    }

    @Override
    public void render() {
        synchro.lock();
        blobs.render();
        missiles.render();
        targets.render();
        synchro.unlock();
    }

    // another C struct
    private class BlobManager {
        public List<BlobIF> objs;
        public List<BlobIF> toAdd;
        public List<BlobIF> toRemove;
        // maps from a BlobIF that is in the World to its baseBlob. Necessary
        // because collision triggers will be operating on the baseBlob and will
        // maybe try to remove themselves from the world.
        // Key is baseBlob, Value is BlobIF in World
        private HashMap<BlobIF, BlobIF> baseBlobMap; 
        
        public BlobManager() {
            objs = new ArrayList<BlobIF>();
            toAdd = new ArrayList<BlobIF>();
            toRemove = new ArrayList<BlobIF>();
            baseBlobMap = new HashMap<BlobIF, BlobIF>();
        }
        
        // Key is baseBlob, Value is BlobIF in World
        protected void addToBaseBlobMap(BlobIF b) {
            BlobIF baseBlob = b.baseBlob();
            if (baseBlob != b) {
                baseBlobMap.put(baseBlob, b);
            }
            
        }
        
        // Key is baseBlob, Value is BlobIF in World
        protected void removeFromBaseBlobMap(BlobIF b) {
            baseBlobMap.remove(b);
        }
        
        public Boolean scheduleAdd(BlobIF b) { return toAdd.add(b); }

        public Boolean scheduleRemoval(BlobIF b) { return toRemove.add(b); }

        public void handleScheduledAddsAndRemovals() {
            Iterator<BlobIF> iter = toRemove.iterator();
        
            while (iter.hasNext()) {
                //Log.d("trace", "trying to remove blob from world");
                BlobIF possibleBaseBlob = iter.next();
                BlobIF worldBlob = baseBlobMap.get(possibleBaseBlob);

                objs.remove(possibleBaseBlob);
                objs.remove(worldBlob);
                removeFromBaseBlobMap(possibleBaseBlob);
            }
        
            iter = toAdd.iterator();
            while (iter.hasNext()) {
                BlobIF b = iter.next();
                b.setWorld(World.this);
                objs.add(b);
                addToBaseBlobMap(b);
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
                    removeFromTrackableBlobLists(b);
                }
            }

            handleScheduledAddsAndRemovals();
        }
        
        public void debugDump() {
            Iterator<BlobIF> iter = objs.iterator();
            while (iter.hasNext()) {
                BlobIF b = iter.next();
                Log.d("debugdump", b.getDebugStr());
            }
        }
    }

    @Override public int getNumTargets() { return targets.objs.size(); }
    @Override public int getNumMissiles() { return missiles.objs.size(); }
    @Override public int getNumBlobs() { return blobs.objs.size(); }
    
    private class WorldState implements StateIF {
        
        public WorldState() {
            // save everything about this world in protected
            // member variables
        }

        @Override
        public void restore() {
            // put those saved things back into
            // this World
        }
    }

    @Override public StateIF getState() { return new WorldState(); }


    // game created BlobIF tracking lists
    @Override
    public int createTrackableBlobList() {
        int num = trackBlobs.size();
        BlobManager tracklist = new BlobManager();
        trackBlobs.add(tracklist);
        return num;
    }

    @Override
    public Boolean addBlobToTrackableBlobList(int listid, BlobIF b) {
        BlobManager tracklist = trackBlobs.get(listid);
        return tracklist.scheduleAdd(b);
    }

    @Override
    public int trackableBlobListCount(int listid) {
        BlobManager tracklist = trackBlobs.get(listid);
        return tracklist.objs.size();
    }


    // remove b from all tracking lists
    private void removeFromTrackableBlobLists(BlobIF b) {
        Iterator<BlobManager> listiter = trackBlobs.iterator();

        while (listiter.hasNext()) {
            BlobManager tracklist = listiter.next();
            tracklist.scheduleRemoval(b);
        }
    }

    private void handleTrackableBlobListsAddsRemovals() {
        Iterator<BlobManager> listiter = trackBlobs.iterator();

        while (listiter.hasNext()) {
            BlobManager tracklist = listiter.next();
            tracklist.handleScheduledAddsAndRemovals();
        }
    }
    
    private void dumpTrackableBlobLists() {
        Iterator<BlobManager> listiter = trackBlobs.iterator();
        
        int ct = 0;
        while (listiter.hasNext()) {
            BlobManager tracklist = listiter.next();
            Log.d("trace", String.format("blob track %d size: %d", ct, tracklist.objs.size()));
            ct++;
        }
    }
}
