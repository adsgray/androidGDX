package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.*;
import com.github.adsgray.gdxtry1.output.RenderConfig.RenderConfigIF;

public interface BlobIF {
    public PositionIF getPosition();
    public Integer getMass();
    public void setWorld(WorldIF w);
    public void setSound(SoundIF s);
    public void setExtent(ExtentIF e);
    public void setAccel(AccelIF a);
    public void setVelocity(VelocityIF v);
    public void setPosition(PositionIF p);
    
    public void setPath(BlobPath p);
    
    public BlobIF absorbBlob(BlobIF b); // return self
    public BlobIF absorbBlob(BlobIF b, BlobTransform transform); // return self
   
    public WorldIF getWorld();
    public VelocityIF getVelocity();
    public AccelIF getAccel();
    public RenderConfig getRenderer();
    public RenderConfigIF getRenderConfig();
    
    public void setLifeTime(Integer ticks);

    public Boolean tick();
    public void setTickPause(int ticks);
    public void render();

    public boolean intersects(BlobIF with);
    public BlobIF collision(BlobIF with);
    
    public abstract static class BlobTransform {
        public abstract BlobIF transform(BlobIF b);
    }
    
    public abstract static class BlobSource {
        public abstract BlobIF generate(BlobIF parent);
    }
    
    public abstract static class BlobTrigger {
        public abstract BlobIF trigger(BlobIF source);
    }

}
