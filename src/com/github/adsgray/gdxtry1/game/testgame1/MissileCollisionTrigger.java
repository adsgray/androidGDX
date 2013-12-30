package com.github.adsgray.gdxtry1.game.testgame1;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.TriggerFactory;

public class MissileCollisionTrigger extends BlobTrigger {

    // make source (missile) go away and make target (secondary) explode
    @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
        WorldIF w = source.getWorld();
        TriggerFactory.replaceWithExplosion(secondary);

        // change missile into a regular blob
        w.removeBlobFromWorld(source);
        w.addBlobToWorld(source);
        return source;
    }

}
