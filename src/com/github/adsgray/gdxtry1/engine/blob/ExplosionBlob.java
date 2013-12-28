package com.github.adsgray.gdxtry1.engine.blob;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.output.RenderConfig;

public class ExplosionBlob extends BaseBlob {

    private int numPerTick = 7;
    private BlobSource bs;

    public ExplosionBlob(Integer massin, PositionIF posin, VelocityIF velin,
            AccelIF accel, RenderConfig gdx) {
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
            //world.addBlobToWorld(b);
        }
    }

}
