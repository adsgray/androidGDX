package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.RenderConfig;
import com.github.adsgray.gdxtry1.output.RenderConfig.RenderConfigIF;
import com.github.adsgray.gdxtry1.output.SoundIF;

public abstract class BlobDecorator implements BlobIF {

    protected BlobIF component;
    protected WorldIF world;

    public BlobDecorator(BlobIF component) {
        this.component = component;
        world = component.getWorld();
    }

    @Override public WorldIF getWorld() { return component.getWorld(); }
    @Override public PositionIF getPosition() { return component.getPosition(); }
    @Override public VelocityIF getVelocity() { return component.getVelocity(); }
    @Override public AccelIF getAccel() { return component.getAccel(); }
    @Override public RenderConfig getRenderer() { return component.getRenderer(); }
    @Override public RenderConfigIF getRenderConfig() { return component.getRenderConfig(); }
    @Override public Integer getMass() { return component.getMass(); }
    @Override public void setAccel(AccelIF a) { component.setAccel(a); }
    @Override public void setVelocity(VelocityIF v) { component.setVelocity(v); }
    @Override public void setPosition(PositionIF p) { component.setPosition(p); }
    @Override public void setSound(SoundIF s) { component.setSound(s); }
    @Override public void setExtent(ExtentIF e) { component.setExtent(e); }
    @Override public void setLifeTime(Integer ticks) { component.setLifeTime(ticks); }
    @Override public void setTickPause(int ticks) { component.setTickPause(ticks); }
    @Override public void setPath(BlobPath p) { component.setPath(p); }

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
}
