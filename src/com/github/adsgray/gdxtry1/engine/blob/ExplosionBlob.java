package com.github.adsgray.gdxtry1.engine.blob;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.util.BlobFactory;
import com.github.adsgray.gdxtry1.engine.util.GameFactory;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public class ExplosionBlob extends BaseBlob {

    private int numPerTick = 7;
    private BlobSource bs;

    public ExplosionBlob(Integer massin, PositionIF posin, VelocityIF velin,
            AccelIF accel, Renderer gdx) {
        super(massin, posin, velin, accel, gdx);
        maxTicks = 25;
    }
    
    public void setBlobSource(BlobSource bs) {
        this.bs = bs;
    }
    
    @Override public void render() {
        for (int i = 0; i < numPerTick; i++) {
            BlobIF b = bs.generate(this);
            b.setLifeTime(10);
            //b.setDebugStr("explosionparticle");
            //world.addBlobToWorld(b);
        }
    }

}
