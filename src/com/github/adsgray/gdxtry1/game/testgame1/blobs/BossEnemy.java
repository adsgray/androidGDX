package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;

public class BossEnemy extends BlobDecorator implements DamagerIF, DamagableIF, EnemyIF {

    public BossEnemy(BlobIF component) {
        super(component);
        // TODO Auto-generated constructor stub
    }

    @Override
    public BlobIF reactToMissileHit(BlobIF missile) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int setHitPoints(int hp) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int incHitPoints(int hp) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int decHitPoints(int hp) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getHitPoints() {
        // TODO Auto-generated method stub
        return 0;
    }

}
