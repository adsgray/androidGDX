package com.github.adsgray.gdxtry1.game.testgame1;

import android.util.Log;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.TriggerFactory;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.BonusIF;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.DamagableIF;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.DamagerIF;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.EnemyBomb;
import com.github.adsgray.gdxtry1.game.testgame1.blobs.EnemyFactory;

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
        if (secondary instanceof EnemyBomb) {
            TriggerFactory.replaceWithExplosion(secondary);
        }
        
        if (secondary instanceof BonusIF) {
            // TODO: special explosion for bonuses
            // TODO: flash message "+5 HitPoints!"
            TargetUtils.replaceWithBonusExplosion(secondary);
            ((BonusIF)secondary).destroyCompanionBlobs();
            EnemyFactory.flashMessage(source.getWorld(), source.getRenderer(), "HitPoint Bonus!", 50);
        }

        // could either be a bomb or a hitpoint bonus
        if (secondary instanceof DamagerIF) {
            DamagerIF bomb = (DamagerIF)secondary;
            damageCommand.execute(bomb.getHitPoints());
        }

        return source;
    }

}
