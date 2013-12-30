package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.testgame1.TargetUtils;

public class EnemyDecorator extends BlobDecorator implements Damagable {

    protected int hitPoints;

    // set up stuff in this decorator constructor?
    public EnemyDecorator(BlobIF component) {
        super(component);
        position = GameFactory.randomPosition(10,800,700,1000);
        setLifeTime(100);
        registerTickDeathTrigger(TargetUtils.fireAtDefenderLoop());
    }

    // Damagable:
    @Override public int setHitPoints(int hp) { hitPoints = hp; return hitPoints; }
    @Override public int incHitPints(int hp) { hitPoints += hp; return hitPoints; }
    @Override public int decHitPints(int hp) { hitPoints -= hp; return hitPoints; }
    @Override public int getHitPoints() { return hitPoints; }
}
