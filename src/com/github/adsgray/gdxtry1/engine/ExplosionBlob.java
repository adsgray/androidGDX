package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.output.RenderConfig;

public class ExplosionBlob extends BaseBlob {

    private int numPerTick = 7;

    public ExplosionBlob(Integer massin, PositionIF posin, VelocityIF velin,
            AccelIF accel, RenderConfig gdx) {
        super(massin, posin, velin, accel, gdx);
        maxTicks = 25;
    }
    
    @Override public void render() {
        for (int i = 0; i < numPerTick; i++) {
            BlobIF b = GameFactory.createExplosionBlob(this);
            b.setLifeTime(10);
            world.scheduleEphemeralAddToWorld(b);
        }
    }

}
