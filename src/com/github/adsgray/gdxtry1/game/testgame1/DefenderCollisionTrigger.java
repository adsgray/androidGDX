package com.github.adsgray.gdxtry1.game.testgame1;

import android.util.Log;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.TriggerFactory;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.Damagable;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.EnemyBomb;

public class DefenderCollisionTrigger extends BlobTrigger {

    protected GameCommand damageCommand;

    public DefenderCollisionTrigger(GameCommand gc) { 
        damageCommand = gc; 
    }

    // the defender is a missile
    // and the enemies rain down "targets"
    // this trigger inflicts damage on "source" (defender)
    // and makes secondary explode.
    @Override
    public BlobIF trigger(BlobIF source, BlobIF secondary) {
        TriggerFactory.replaceWithExplosion(secondary);

        if (secondary instanceof EnemyBomb) {
            EnemyBomb bomb = (EnemyBomb)secondary;
            damageCommand.execute(bomb.getHitPoints());
        }

        return source;
    }

}