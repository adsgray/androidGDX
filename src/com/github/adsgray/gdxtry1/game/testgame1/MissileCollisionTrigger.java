package com.github.adsgray.gdxtry1.game.testgame1;

import android.util.Log;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.GameCommand;
import com.github.adsgray.gdxtry1.game.TriggerFactory;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.BonusIF;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.DamagerIF;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.EnemyIF;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.EnemyFactory;

public class MissileCollisionTrigger extends BlobTrigger {

    GameCommand postKillCommand;

    public MissileCollisionTrigger(GameCommand gc) {
        postKillCommand = gc;
    }

    protected int getPointsFromEnemy(BlobIF enemy) {
        if (enemy instanceof DamagerIF) {
            DamagerIF bomb = (DamagerIF)enemy;
            return bomb.getHitPoints();
        }
        Log.d("testgame1", "hit something that's not a Damager??");
        return 0;
    }
    
    protected BlobIF postCollision(BlobIF source, BlobIF secondary) {
        WorldIF w = source.getWorld();
        w.removeBlobFromWorld(source);
        source = TargetUtils.disarmMissile.transform(source);
        w.addBlobToWorld(source);
        return source;
    }

    // make source (missile) go away and make target (secondary) explode
    @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
        // do this before possible "becomeAngry" so that 
        // the correct number of points is awarded.
        // we'll lose points here (that is on purpose) if we've hit a Bonus
        postKillCommand.execute(getPointsFromEnemy(secondary));

        // For some reason this has to be done before the
        // reactToMisisleHit below...
        source = postCollision(source, secondary);

        // Check to see if we're hitting an enemy ship
        if (secondary instanceof EnemyIF) {
            ((EnemyIF)secondary).reactToMissileHit(source);
        } else {
            // if it's a regular 'target' (like a bomb) just explode it
            GameSound.get().explosionAll();
            TriggerFactory.replaceWithExplosion(secondary);
        }

        return source;
    }

}
