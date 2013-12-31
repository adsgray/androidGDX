package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;

public class BonusDecorator extends BlobDecorator implements Bonus {

    int hitPoints;

    public BonusDecorator(BlobIF component, int hitPoints) {
        super(component);
        this.hitPoints = hitPoints;
    }

    @Override
    public int getHitPoints() { return hitPoints; }
}
