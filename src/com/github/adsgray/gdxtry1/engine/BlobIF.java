package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.*;

public interface BlobIF {
    public PositionIF getPosition();
    public Integer getMass();
    public void setWorld(WorldIF w);
    public void setSound(SoundIF s);
    public void setExtent(ExtentIF e);
    public void setAccel(AccelIF a);
    public void setVelocity(VelocityIF v);
    
    public BlobIF absorbBlob(BlobIF b); // return self
    public BlobIF absorbBlob(BlobIF b, BlobTransform transform); // return self
   
    public WorldIF getWorld();
    public VelocityIF getVelocity();
    public AccelIF getAccel();
    public RenderConfig getRenderer();
    
    public void setLifeTime(Integer ticks);

    public Boolean tick();
    public void setTickPause(int ticks);
    public void render();

    public boolean intersects(BlobIF with);
    public BlobIF collision(BlobIF with);
     
    public static class BlobPath {
        public VelocityIF vel;
        public AccelIF acc;
        
        public BlobPath(VelocityIF vel, AccelIF acc) {
            this.vel = vel;
            this.acc = acc;
        }
    }
    
    public abstract static class BlobTransform {
        public abstract BlobIF transform(BlobIF b);
    }
}
