package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.TextBlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.game.TriggerFactory;
import com.github.adsgray.gdxtry1.game.testgame1.GameSound;
import com.github.adsgray.gdxtry1.game.testgame1.TargetUtils;
import com.github.adsgray.gdxtry1.game.testgame1.GameSound.SoundId;

public class HitpointBonusDecorator extends BlobDecorator implements BonusIF, EnemyIF {

    int hitPoints;
    TextBlobIF companionText;

    public HitpointBonusDecorator(BlobIF component, TextBlobIF companionText, int hitPoints) {
        super(component);
        this.hitPoints = hitPoints;
        this.companionText = companionText;
    }

    @Override
    public int getHitPoints() { return hitPoints; }
    
    @Override
    public void destroyCompanionBlobs() {
        companionText.setLifeTime(0);
    }

    @Override
    public BlobIF reactToMissileHit(BlobIF missile) {
        destroyCompanionBlobs();
        EnemyFactory.flashMessage(world, renderer, "Lost Bonus :-(", 30);
        GameSound.get().playSoundId(SoundId.defenderHit); // use this one for now...
        return TargetUtils.replaceWithBonusExplosion(this);
    }

    @Override public int getWeight() { return 1; } 
}
