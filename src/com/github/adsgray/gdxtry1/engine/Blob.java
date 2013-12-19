package com.github.adsgray.gdxtry1.engine;

import java.util.Iterator;
import java.util.Vector;

import com.github.adsgray.gdxtry1.output.*;

import android.util.Log;

/** this blob has a mass, size, position, velocity and can be given
 * impulses.
 * @author andrew
 *
 */
public class Blob implements BlobIF {
    
    private Integer mass;
    private PositionIF position;
    private VelocityIF velocity;
    private ExtentIF extent;
    private AccelIF acceleration;
    private WorldIF world = null;
    private SoundIF sound = new NullSound();
    
    private static final Integer EXPLODE_INTENSITY = 5;
    
    public Blob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel) {
        mass = massin;
        position = posin;
        velocity = velin;
        acceleration = accel;
    }

    public PositionIF getPosition() { return position; }
    public Integer getMass() { return mass; }
    public void setWorld(WorldIF w) { world = w; }
    public void setSound(SoundIF s) { sound = s; }
    public void setExtent(ExtentIF e) { extent = e; }
    
    /* called by outside controller to tell this Blob
     * to advance one time unit.
     */
    public void tick() {
        position.updateByVelocity(velocity);
        // update velocity with its accelleration
        acceleration.accellerate(velocity);
    }
    
    private void updateWorldAfterExplode(Vector<BlobIF> b) {
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
    
    /* split this blob up into numPieces new blobs.
     * conserving momentum etc.
     * Caller will have to
     * - remove this blob from the world
     * - add the new blobs to the world
     * 
     */
    public Vector<BlobIF> explode(Integer numPieces) {
        Vector<BlobIF> vec = new Vector<BlobIF>();
        updateWorldAfterExplode(vec);
        sound.crash(EXPLODE_INTENSITY);
        return vec;
    }
        
    /* return true if we overlap with "with" 
     * delegate to our "extent" which knows our shape.
     * */
    public boolean intersects(BlobIF with) {
        return extent.intersects(position, with.getPosition());
    }
    
    public BlobIF collision(BlobIF with) {
        /* do physics to change properties of "this" based on the
         * Blob "with" that we are colliding with. Note that that other
         * Blob will have to call .collision(us) to change its properties.
         */
        if (this.intersects(with)) {
            // this.explode(5);
            // with.explode(5);
        }
        return this;
    }
    

}
