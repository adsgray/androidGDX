package com.github.adsgray.gdxtry1.game.testgame1;

import android.util.Log;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.TriggerFactory;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.Damager;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.Enemy;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.EnemyFactory;

public class MissileCollisionTrigger extends BlobTrigger {

    GameCommand postKillCommand;

    public MissileCollisionTrigger(GameCommand gc) {
        postKillCommand = gc;
    }

    private int getPointsFromEnemy(BlobIF enemy) {
        if (enemy instanceof Damager) {
            Damager bomb = (Damager)enemy;
            return bomb.getHitPoints();
        }
        Log.d("testgame1", "hit something that's not a Damager??");
        return 0;
    }

    // make source (missile) go away and make target (secondary) explode
    @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
        WorldIF w = source.getWorld();

        // do this before possible "becomeAngry" so that 
        // the correct number of points is awarded.
        postKillCommand.execute(getPointsFromEnemy(secondary));

        // Check to see if we're hitting an enemy ship
        if (secondary instanceof Enemy) {
            Enemy target = (Enemy)secondary;
            if (target.getType() == Enemy.Type.Initial) {
                target.becomeAngry();
            } else {
                // throw some more bombs down as we die
                TriggerFactory.replaceWithExplosion(secondary);
                for (int i = 0; i < 2; i++) {
                    // angryTargetMissileSource adds the target to the world.
                    BlobIF bomb = TargetUtils.angryTargetMissileSource.get(secondary);
                }
                
                // also randomly throw down some extra hit points
                // set the hitPoints on this to negative so that
                // (a) when it collides with the ship it gives hitPoints (subtract a neg. number)
                // (b) if you shoot it, you lose points haha.
                if (TargetUtils.rnd.nextInt(100) < 25) {
                    EnemyFactory.hitPointBonusSource.get(secondary);
                }
            }
        } else {
            // if it's a regular 'target' (like a bomb) just explode it
            
            TriggerFactory.replaceWithExplosion(secondary);
        }


        // change missile into a regular blob
        w.removeBlobFromWorld(source);
        source = TargetUtils.disarmMissile.transform(source);
        w.addBlobToWorld(source);
        
        return source;
    }

}
