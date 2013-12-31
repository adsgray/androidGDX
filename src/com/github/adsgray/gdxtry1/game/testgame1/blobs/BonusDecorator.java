package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.TextBlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;

public class BonusDecorator extends BlobDecorator implements Bonus {

    int hitPoints;
    TextBlobIF companionText;

    public BonusDecorator(BlobIF component, TextBlobIF companionText, int hitPoints) {
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
}
