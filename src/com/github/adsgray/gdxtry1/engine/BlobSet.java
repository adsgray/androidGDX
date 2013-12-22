package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.RenderConfig;

public class BlobSet extends BaseBlob {

    public BlobSet(Integer massin, PositionIF posin, VelocityIF velin,
            AccelIF accel, RenderConfig gdx) {
        super(massin, posin, velin, accel, gdx);
    }

    /* called by outside controller to tell this Blob
     * to advance one time unit.
     * Return true if blob should remain in world
     * Return false if it should be removed from world
     */
    @Override
    public Boolean tick() {
        // update each BlobIF in set by composing its velocity with the set's
        position.updateByVelocity(velocity);
        // update velocity with its accelleration
        velocity = acceleration.accellerate(velocity);
        
        ticks += 1;
        if (ticks >= maxTicks) {
            return false;
        }
        
        return true;
    }
   
    @Override
    public void render() {
        // render each BlobIF in the set
    }

    @Override
    public BlobIF absorbBlob(BlobIF b) {
        // TODO Auto-generated method stub
        return this;
    }


}
