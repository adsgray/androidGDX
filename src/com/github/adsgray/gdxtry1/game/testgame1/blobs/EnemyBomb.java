package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;

public class EnemyBomb extends BlobDecorator implements Damager {

    int hitPoints;

    public EnemyBomb(BlobIF component, int hitPoints) {
        super(component);
        this.hitPoints = hitPoints;
        // TODO Auto-generated constructor stub
    }

    @Override public int getHitPoints() { return hitPoints; }
}
