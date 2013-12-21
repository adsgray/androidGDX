package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.RenderConfig;
import com.github.adsgray.gdxtry1.output.SoundIF;

/**
 * A group of Blobs
 * Has its own position but component blobs also have their own positions.
 * 
 * @author andrew
 *
 */

public class BlobSet implements BlobIF {

    @Override
    public PositionIF getPosition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getMass() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setWorld(WorldIF w) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setSound(SoundIF s) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setExtent(ExtentIF e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setAccel(AccelIF a) {
        // TODO Auto-generated method stub
    }

    @Override
    public WorldIF getWorld() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VelocityIF getVelocity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RenderConfig getRenderer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLifeTime(Integer ticks) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean tick() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean intersects(BlobIF with) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public BlobIF collision(BlobIF with) {
        // TODO Auto-generated method stub
        return null;
    }

}
