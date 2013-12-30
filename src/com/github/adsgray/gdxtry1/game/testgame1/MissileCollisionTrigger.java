package com.github.adsgray.gdxtry1.game.testgame1;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.TriggerFactory;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.Enemy;

public class MissileCollisionTrigger extends BlobTrigger {

    GameCommand postKillCommand;

    public MissileCollisionTrigger(GameCommand gc) {
        postKillCommand = gc;
    }

    // make source (missile) go away and make target (secondary) explode
    @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
        WorldIF w = source.getWorld();

        // Check to see if we're hitting an enemy ship
        if (secondary instanceof Enemy) {
            Enemy target = (Enemy)secondary;
            if (target.getType() == Enemy.Type.Initial) {
                target.becomeAngry();
            } else {
                TriggerFactory.replaceWithExplosion(secondary);
                for (int i = 0; i < 2; i++) {
                    // angryTargetMissileSource adds the target to the world.
                    BlobIF bomb = TargetUtils.angryTargetMissileSource.get(secondary);
                }
            }
        } else {
            // if it's a regular 'target' (like a bomb) just explode it
            TriggerFactory.replaceWithExplosion(secondary);
        }

        postKillCommand.execute();

        // change missile into a regular blob
        w.removeBlobFromWorld(source);
        source = TargetUtils.disarmMissile.transform(source);
        w.addBlobToWorld(source);
        
        return source;
    }

}
