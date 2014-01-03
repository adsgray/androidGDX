package com.github.adsgray.gdxtry1.engine.blob.decorator;

import java.util.List;

import com.github.adsgray.gdxtry1.engine.ClusterIF;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.blob.BaseBlob;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.output.Renderer.RenderConfigIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

// I don't think base BlobDecorator should extend BaseBlob...
public abstract class BlobDecorator extends BaseBlob {

    protected BlobIF component;
    protected WorldIF world;

    public BlobDecorator(BlobIF component) {
        super(component);
        this.component = component;
        world = component.getWorld();
    }

    @Override public WorldIF getWorld() { return component.getWorld(); }
    @Override public PositionIF getPosition() { return component.getPosition(); }
    @Override public VelocityIF getVelocity() { return component.getVelocity(); }
    @Override public ExtentIF getExtent() { return component.getExtent(); }
    @Override public AccelIF getAccel() { return component.getAccel(); }
    @Override public Renderer getRenderer() { return component.getRenderer(); }
    @Override public RenderConfigIF getRenderConfig() { return component.getRenderConfig(); }
    @Override public void setRenderConfig(RenderConfigIF r) { component.setRenderConfig(r); }
    @Override public Integer getMass() { return component.getMass(); }
    @Override public void setAccel(AccelIF a) { component.setAccel(a); }
    @Override public void setVelocity(VelocityIF v) { component.setVelocity(v); }
    @Override public void setPosition(PositionIF p) { component.setPosition(p); }
    @Override public void setExtent(ExtentIF e) { component.setExtent(e); }
    @Override public void setLifeTime(Integer ticks) { component.setLifeTime(ticks); }
    @Override public void setTickPause(int ticks) { component.setTickPause(ticks); }
    @Override public void setPath(BlobPath p) { component.setPath(p); }
    @Override public int getClientType() { return component.getClientType(); }
    @Override public void setClientType(int clientType) { component.setClientType(clientType); }

    @Override public BlobIF absorbBlob(BlobIF b) { component = component.absorbBlob(b); return this; }
    @Override public BlobIF absorbBlob(BlobIF b, BlobTransform bt) { component = component.absorbBlob(b, bt); return this; }

    @Override public void setWorld(WorldIF w) { 
        component.setWorld(w); 
        this.world = w; 
    }

    @Override public boolean intersects(BlobIF with) { return component.intersects(with); }
    @Override public BlobIF collision(BlobIF with) { return component.collision(with); }

    // these are the most interesting places to hook into:
    @Override public Boolean tick() { return component.tick(); }
    @Override public void render() { component.render(); }
    @Override public void registerCollisionTrigger(BlobTrigger trigger) { component.registerCollisionTrigger(trigger);}
    @Override public void deregisterCollisionTrigger(BlobTrigger trigger) { component.deregisterCollisionTrigger(trigger);}
    @Override public void clearCollisionTriggers() { component.clearCollisionTriggers(); }
    @Override public void registerTickDeathTrigger(BlobTrigger trigger) { component.registerTickDeathTrigger(trigger);}
    @Override public void deregisterTickDeathTrigger(BlobTrigger trigger) { component.deregisterTickDeathTrigger(trigger);}
    @Override public void clearTickDeathTriggers() { component.clearTickDeathTriggers(); }
    @Override public List<BlobTrigger> getCollisionTriggers() { return component.getCollisionTriggers(); }
    
    @Override public ClusterIF setCluster(ClusterIF c) { return component.setCluster(c); }
    @Override public ClusterIF getCluster() { return component.getCluster(); }

    @Override public BlobIF baseBlob() { return component.baseBlob(); }
    
    public BlobIF getComponent() { return component; }

    @Override public String setDebugStr(String str) { return component.setDebugStr(str); }
    @Override public String getDebugStr() { return component.getDebugStr(); }
}
