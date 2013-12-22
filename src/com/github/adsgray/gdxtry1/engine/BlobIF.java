package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.*;

public interface BlobIF {
    public PositionIF getPosition();
    public Integer getMass();
    public void setWorld(WorldIF w);
    public void setSound(SoundIF s);
    public void setExtent(ExtentIF e);
    public void setAccel(AccelIF a);
    
    public BlobIF absorbBlob(BlobIF b); // return self
    
    public WorldIF getWorld();
    public VelocityIF getVelocity();
    public RenderConfig getRenderer();
    
    public void setLifeTime(Integer ticks);

    public Boolean tick();
    public void render();

    public boolean intersects(BlobIF with);
    public BlobIF collision(BlobIF with);
}
