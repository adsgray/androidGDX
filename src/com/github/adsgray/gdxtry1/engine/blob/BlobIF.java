package com.github.adsgray.gdxtry1.engine.blob;

import java.util.List;

import com.github.adsgray.gdxtry1.engine.ClusterIF;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.output.*;
import com.github.adsgray.gdxtry1.engine.output.Renderer.RenderConfigIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public interface BlobIF {
    public PositionIF getPosition();
    public Integer getMass();
    public void setWorld(WorldIF w);
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
    public Renderer getRenderer();
    public RenderConfigIF getRenderConfig();
    public void setRenderConfig(RenderConfigIF r); 
    
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
        public BlobSource blobSource;
        public abstract BlobIF transform(BlobIF b);
        public BlobTransform() {}
        public BlobTransform(BlobSource bs) { blobSource = bs; }
        
        // after transforming you often swap the new blob into the original
        // blob's cluster:
        protected void clusterSwap(BlobIF in, BlobIF out) {
            ClusterIF cluster = out.getCluster();
            // add "in" to the same cluster that "out" was a member of
            if (cluster != null) {
                in.setCluster(cluster);
                cluster.swap(in, out);
            }
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
        public BlobTransform transform; // a transform to be applied by generate before returning blob
        public BlobSource(BlobTrigger t) { tickDeathTrigger = t; }
        public BlobSource(BlobTrigger trig, BlobTransform tran) { tickDeathTrigger = trig; transform = tran; }
        public BlobSource() {}
        protected BlobIF maybeTransform(BlobIF b) {
            if (transform != null) {
                b = transform.transform(b);
            }
            
            if (tickDeathTrigger != null) {
                b.registerTickDeathTrigger(tickDeathTrigger);
            }
            return b;
        }

        // subclass defines this:
        protected abstract BlobIF generate(BlobIF parent);

        // users call this:
        public BlobIF get(BlobIF parent) {
            BlobIF b = generate(parent);
            b.setWorld(parent.getWorld());
            return maybeTransform(b);
        }
    }
    
    public abstract static class BlobTrigger {
        public BlobTrigger() {}
        public BlobTrigger(Renderer r) { renderConfig = r; }
        public BlobTrigger(BlobTransform bt) { blobTransform = bt; }
        public BlobTrigger(BlobTrigger ct) { chainTrigger = ct; }
        public void setChainTrigger(BlobTrigger ct) { chainTrigger = ct; }
        public Renderer renderConfig;
        public BlobTransform blobTransform;
        public BlobTrigger chainTrigger;
        public abstract BlobIF trigger(BlobIF source, BlobIF secondary/*optional, used only for collisions*/);
    }

    public void registerCollisionTrigger(BlobTrigger trigger);
    public List<BlobTrigger> getCollisionTriggers();
    public void deregisterCollisionTrigger(BlobTrigger trigger);
    public void clearCollisionTriggers();
    
    public void registerTickDeathTrigger(BlobTrigger trigger);
    public void deregisterTickDeathTrigger(BlobTrigger trigger);
    public void clearTickDeathTriggers();
    
    // if this BlobIF is the top of a chain of decorators this
    // will return the concrete BlobIF that is at the bottom.
    public BlobIF baseBlob();
}
