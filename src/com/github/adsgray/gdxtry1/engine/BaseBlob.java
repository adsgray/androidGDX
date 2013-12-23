package com.github.adsgray.gdxtry1.engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import android.util.Log;

import com.github.adsgray.gdxtry1.output.NullSound;
import com.github.adsgray.gdxtry1.output.RenderConfig;
import com.github.adsgray.gdxtry1.output.SoundIF;

public class BaseBlob implements BlobIF {
 
    protected static final Integer EXPLODE_INTENSITY = 5;
    protected static final Integer BUMP_INTENSITY = 2;
    protected static final Integer MAX_TICKS = 250; // die after this number of ticks

    protected Integer ticks; // how many ticks this Blob has been alive for 
    protected Integer tickPause = 0; // freeze for this many ticks
    protected Integer maxTicks = MAX_TICKS; // when Blob reaches this number of ticks it'll remove itself from World

    protected Integer mass;
    protected PositionIF position;
    protected VelocityIF velocity;
    protected ExtentIF extent;
    protected AccelIF acceleration;
    protected WorldIF world = null;
    protected SoundIF sound = new NullSound();
   
    @Override public WorldIF getWorld() { return world; }
    @Override public VelocityIF getVelocity() { return velocity; }
    @Override public AccelIF getAccel() { return acceleration; }
    @Override public PositionIF getPosition() { return position; }
    @Override public RenderConfig getRenderer() { return renderer; }
    @Override public Integer getMass() { return mass; }
    @Override public void setWorld(WorldIF w) { world = w; }
    @Override public void setAccel(AccelIF a) { acceleration = a; }
    @Override public void setVelocity(VelocityIF vel) { velocity = vel; }
    @Override public void setSound(SoundIF s) { sound = s; }
    @Override public void setExtent(ExtentIF e) { extent = e; }
    @Override public void setLifeTime(Integer ticks) { maxTicks = ticks; }
    @Override public void setPath(BlobPath p) { setVelocity(p.vel); setAccel(p.acc); }

    private int minTriggerTick = 25; // don't fire triggers until after this number of ticks

    protected RenderConfig renderer;

    public BaseBlob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel, RenderConfig gdx) {
        mass = massin;
        position = posin;
        velocity = velin;
        acceleration = accel;
        renderer = gdx;
        ticks = 0;
    }

    /* called by outside controller to tell this Blob
     * to advance one time unit.
     * Return true if blob should remain in world
     * Return false if it should be removed from world
     */
    @Override
    public Boolean tick() {
        
        if (tickPause > 0) {
            tickPause -= 1;
            return true;
        }

        //Log.d("tick", "tick");
        position.updateByVelocity(velocity);
        // update velocity with its accelleration
        //velocity = acceleration.accellerate(velocity);
        velocity.accelerate(acceleration);
        
        if (ticks > minTriggerTick) {
            position.handleTriggers(this);
        }
        
        ticks += 1;
        if (ticks >= maxTicks) {
            return false;
        }
        
        return true;
    }
   
    @Override
    public void render() {
        // TODO Auto-generated method stub
    }

    /* return true if we overlap with "with" 
     * delegate to our "extent" which knows our shape.
     * */
    @Override
    public boolean intersects(BlobIF with) {
        return extent.intersects(position, with.getPosition());
    }
   
    @Override
    public BlobIF collision(BlobIF with) {
        /* do physics to change properties of "this" based on the
         * Blob "with" that we are colliding with. Note that that other
         * Blob will have to call .collision(us) to change its properties.
         */
        if (this.intersects(with)) {
            sound.crash(BUMP_INTENSITY);
            // this.explode(5);
            // with.explode(5);
        }
        return this;
    }

    protected void updateWorldAfterExplode(Vector<BlobIF> b) {
        Iterator<BlobIF> it;
        
        if (world == null) {
            Log.e("trace", "updateWorldAfterExplode: no world set.");
            return;
        }
        
        it = b.iterator();
        while (it.hasNext()) {
            world.scheduleAddToWorld(it.next());
        }

        world.scheduleRemovalFromWorld(this);
    }

    protected Vector<BlobIF> explode(Integer numPieces) {
        Vector<BlobIF> vec = new Vector<BlobIF>();
        // create some more Blobs and return them
        // also make some sound?
        return vec;
    }

    @Override
    public BlobIF absorbBlob(BlobIF b) {
        return absorbBlob(b, null);
    }
   
    @Override
    public BlobIF absorbBlob(BlobIF b, BlobTransform bt) {

        // create an empty BlobSet
        // make the BlobSet inherit our velocity and have zero acceleration?
        BlobSet bs = new BlobSet(mass, new BlobPosition(position), 
                new BlobVelocity(velocity), new LinearAccel(0,0), renderer);

        // put "this" into it. Note that this action will schedule "this" to
        // be removed from World so we don't have to do that here.
        bs.absorbBlob(this, bt);

        // put b into it
        bs.absorbBlob(b, bt);

        return bs;
    }
    @Override
    public void setTickPause(int ticks) {
        tickPause = ticks;
    }
    
   
}
