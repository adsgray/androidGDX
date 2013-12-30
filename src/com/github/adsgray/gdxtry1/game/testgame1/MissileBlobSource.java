package com.github.adsgray.gdxtry1.game.testgame1;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobSource;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.PathFactory;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.CircleConfig;

public class MissileBlobSource extends BlobSource {
    GameCommand postKillCommand;
    
    public MissileBlobSource(GameCommand gc) {
        postKillCommand = gc;
    }

    @Override protected BlobIF generate(BlobIF parent) {
        WorldIF w = parent.getWorld();
        Renderer r = parent.getRenderer();
        BlobPosition p = new BlobPosition(parent);
        CircleConfig rc = r.new CircleConfig(Color.RED, 25f);

        // create a missile blob whose initial position
        // is based on parent
        BlobIF m = BlobFactory.circleBlob(p, PathFactory.launchUp(), rc, r);
        m.setLifeTime(75);
        m.registerCollisionTrigger(new MissileCollisionTrigger(postKillCommand));
        w.addMissileToWorld(m);
        return m;
    }
}
