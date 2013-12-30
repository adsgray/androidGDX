package com.github.adsgray.gdxtry1.game.testgame1;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobSource;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.game.AccelFactory;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.CircleConfig;
import com.badlogic.gdx.graphics.Color;
public class AngryTargetMissileSource extends BlobSource {

    // TODO: make these have hitpoints to inflict damage on defender
    @Override
    protected BlobIF generate(BlobIF parent) {
        WorldIF w = parent.getWorld();
        Renderer r = parent.getRenderer();
        BlobPath path = new BlobPath(new BlobVelocity(0,-15), AccelFactory.zeroAccel());
        CircleConfig rc = r.new CircleConfig(Color.CYAN, 14);
        BlobIF b = BlobFactory.circleBlob(new BlobPosition(parent.getPosition()), path, rc, r);
        b = BlobFactory.rainbowColorCycler(b, 1);
        b.setLifeTime(100);
        b = BlobFactory.addTriangleSmokeTrail(b);
        w.addTargetToWorld(b);
        return b;
    }

}
