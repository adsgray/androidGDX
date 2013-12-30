package com.github.adsgray.gdxtry1.game.testgame1;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.TriggerFactory;

public class DefenderCollisionTrigger extends BlobTrigger {

    // the defender is a missile
    // and the enemies rain down "targets"
    // this trigger inflicts damage on "source" (defender)
    // and makes secondary explode.
    @Override
    public BlobIF trigger(BlobIF source, BlobIF secondary) {
        TriggerFactory.replaceWithExplosion(secondary);
        return source;
    }

}
