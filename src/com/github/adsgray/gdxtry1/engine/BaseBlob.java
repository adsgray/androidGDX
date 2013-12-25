package com.github.adsgray.gdxtry1.engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import android.util.Log;

import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.output.NullSound;
import com.github.adsgray.gdxtry1.output.RenderConfig;
import com.github.adsgray.gdxtry1.output.RenderConfig.RenderConfigIF;
import com.github.adsgray.gdxtry1.output.SoundIF;

public class BaseBlob implements BlobIF {
 
    // TODO change all of these to ints
    protected static final Integer EXPLODE_INTENSITY = 5;
    protected static final Integer BUMP_INTENSITY = 2;
    protected static final Integer MAX_TICKS = 250; // die after this number of ticks

    protected Integer ticks; // how many ticks this Blob has been alive for 
    protected Integer tickPause = 0; // freeze for this many ticks
    protected Integer maxTicks = MAX_TICKS; // when Blob reaches this number of ticks it'll remove itself from World
    protected int clientType;

    protected Integer mass;
    protected PositionIF position;
    protected VelocityIF velocity;
    protected ExtentIF extent;
    protected AccelIF acceleration;
    protected WorldIF world = null;
    protected SoundIF sound = new NullSound();
   
    @Override public WorldIF getWorld() { return world; }
    @Override public VelocityIF getVelocity() { return velocity; }
    @Override public ExtentIF getExtent() { return extent; }
    @Override public AccelIF getAccel() { return acceleration; }
    @Override public PositionIF getPosition() { return position; }
    @Override public RenderConfig getRenderer() { return renderer; }
    @Override public Integer getMass() { return mass; }
    @Override public void setWorld(WorldIF w) { world = w; }
    @Override public void setAccel(AccelIF a) { acceleration = a; }
    @Override public void setVelocity(VelocityIF vel) { velocity = vel; }
    @Override public void setPosition(PositionIF pos) { position = pos; }
    @Override public void setSound(SoundIF s) { sound = s; }
    @Override public void setExtent(ExtentIF e) { extent = e; }
    @Override public void setLifeTime(Integer ticks) { this.ticks = 0; maxTicks = ticks; }
    @Override public void setPath(BlobPath p) { setVelocity(p.vel); setAccel(p.acc); }
    
    @Override public void setClientType(int clientType) { this.clientType = clientType; }
    @Override public int getClientType() { return clientType; }

    protected int minTriggerTick = 25; // don't fire triggers until after this number of ticks

    protected RenderConfig renderer;
    protected RenderConfigIF renderConfig;

    protected Vector<BlobTrigger> collisionTriggers;
    protected Vector<BlobTrigger> tickDeathTriggers;

    @Override public RenderConfigIF getRenderConfig() { return renderConfig; }

    public BaseBlob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel, RenderConfig gdx) {
        mass = massin;
        position = posin;
        velocity = velin;
        acceleration = accel;
        renderer = gdx;
        //extent = new CircleExtent(30);
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
            // note: this may set maxTicks to something higher
            doTriggers(tickDeathTriggers, null);
        }

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
        return extent.intersects(position, with);
    }
   
    protected BlobIF doTriggers(Vector<BlobTrigger> set, BlobIF secondary) {

        if (set == null) return this;

        Iterator<BlobTrigger> iter = set.iterator();
        BlobIF b = this;
        while (iter.hasNext()) {
            // hmmm would be nice to be able to chain transformations of
            // 'with' as well. It'll all have to be done in one trigger call.
            // can chain transformations/decorators anyway.
            b = iter.next().trigger(b, secondary);
        }
        
        return b;
    }

    @Override
    public BlobIF collision(BlobIF with) {
        /* do physics to change properties of "this" based on the
         * Blob "with" that we are colliding with. Note that that other
         * Blob will have to call .collision(us) to change its properties.
         */
        //sound.crash(BUMP_INTENSITY);

        // TODO: take this Blob out of things considered for collisions?
        // or set it inelligible for collision detection for a time period
        // so that it is not repeatedly collided on every tick while it's
        // touching something else?
            
        return doTriggers(collisionTriggers, with);

        // this.explode(5);
        // with.explode(5);
    }

    protected void updateWorldAfterExplode(Vector<BlobIF> b) {
        Iterator<BlobIF> it;
        
        if (world == null) {
            Log.e("trace", "updateWorldAfterExplode: no world set.");
            return;
        }
        
        it = b.iterator();
        while (it.hasNext()) {
            world.addBlobToWorld(it.next());
        }

        world.removeBlobFromWorld(this);
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

    private Vector<BlobTrigger> addTrigger(Vector<BlobTrigger> set, BlobTrigger trigger) {
        if (set == null) {
            set = new Vector<BlobTrigger>();
        }
        set.add(trigger);       
        return set;
    }
    
    private void removeTrigger(Vector<BlobTrigger> set, BlobTrigger trigger) {
        if (set != null) {
            set.remove(trigger);
        }
    }

    @Override public void registerCollisionTrigger(BlobTrigger trigger) { collisionTriggers = addTrigger(collisionTriggers, trigger); }
    @Override public void deregisterCollisionTrigger(BlobTrigger trigger) { removeTrigger(collisionTriggers, trigger); }
    @Override public void clearCollisionTriggers() { collisionTriggers.clear(); }
    @Override public void registerTickDeathTrigger(BlobTrigger trigger) { tickDeathTriggers = addTrigger(tickDeathTriggers, trigger); }
    @Override public void deregisterTickDeathTrigger(BlobTrigger trigger) { removeTrigger(tickDeathTriggers, trigger); }
    @Override public void clearTickDeathTriggers() { tickDeathTriggers.clear(); }
    
    @Override
    public BlobIF baseBlob() { return this; }
}
