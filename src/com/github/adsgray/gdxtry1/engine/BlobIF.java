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
    
    public ClusterIF setCluster(ClusterIF c);
    public ClusterIF getCluster();
   
    public WorldIF getWorld();
    public VelocityIF getVelocity();
    public AccelIF getAccel();
    public ExtentIF getExtent();
    public RenderConfig getRenderer();
    public RenderConfigIF getRenderConfig();
    
    public void setLifeTime(Integer ticks);

    // like "clientData", just for application specific use
    public void setClientType(int clientType);
    public int getClientType();

    public Boolean tick();
    public void setTickPause(int ticks);
    public void render();

    public boolean intersects(BlobIF with);
    public BlobIF collision(BlobIF with);
    
    public abstract static class BlobTransform {
        public abstract BlobIF transform(BlobIF b);
        
        // after transforming you often swap the new blob into the original
        // blob's cluster:
        protected void clusterSwap(BlobIF in, BlobIF out) {
            ClusterIF cluster = out.getCluster();
            if (cluster != null) cluster.swap(in, out);
        }
    }
    
    public abstract static class BlobSource {
        // NOTE: generate is responsible for adding
        // the generated Blob to parent.World.
        // This is so that it can decide whether the 
        // generated Blob is a missile/target/neither

        // allow each blob generated to have a tickDeathTrigger attached it to at
        // creation time.
        public BlobTrigger tickDeathTrigger;
        public BlobSource(BlobTrigger t) { tickDeathTrigger = t; }
        public BlobSource() {}
        public abstract BlobIF generate(BlobIF parent);
    }
    
    public abstract static class BlobTrigger {
        public BlobTrigger() {}
        public BlobTrigger(RenderConfig r) { renderConfig = r; }
        public BlobTrigger(BlobTransform bt) { blobTransform = bt; }
        public BlobTrigger(BlobTrigger ct) { chainTrigger = ct; }
        public void setChainTrigger(BlobTrigger ct) { chainTrigger = ct; }
        public RenderConfig renderConfig;
        public BlobTransform blobTransform;
        public BlobTrigger chainTrigger;
        public abstract BlobIF trigger(BlobIF source, BlobIF secondary/*optional, used only for collisions*/);
    }

    public void registerCollisionTrigger(BlobTrigger trigger);
    public void deregisterCollisionTrigger(BlobTrigger trigger);
    public void clearCollisionTriggers();
    
    public void registerTickDeathTrigger(BlobTrigger trigger);
    public void deregisterTickDeathTrigger(BlobTrigger trigger);
    public void clearTickDeathTriggers();
    
    // if this BlobIF is the top of a chain of decorators this
    // will return the concrete BlobIF that is at the bottom.
    public BlobIF baseBlob();
}
