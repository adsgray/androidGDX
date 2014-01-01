package com.github.adsgray.gdxtry1.game.testgame1;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.game.GameCommand;

public class ShieldCollisionTrigger extends MissileCollisionTrigger {

    public ShieldCollisionTrigger(GameCommand gc) {
        super(gc);
    }

    @Override protected BlobIF postCollision(BlobIF source, BlobIF secondary) {
        // do nothing as we want the shield to be able to kill multiple bombs
        GameSound.get().shieldHit();
        return source;
    }
}
