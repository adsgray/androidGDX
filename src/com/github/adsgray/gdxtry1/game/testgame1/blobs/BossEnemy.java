package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.game.PositionFactory;
import com.github.adsgray.gdxtry1.game.TriggerFactory;
import com.github.adsgray.gdxtry1.game.testgame1.BossTargetMissileSource;
import com.github.adsgray.gdxtry1.game.testgame1.TargetUtils;

public class BossEnemy extends BlobDecorator implements DamagerIF, DamagableIF, EnemyIF {

    protected int hitPoints = 75;
    protected PositionIF aimTarget;
    protected int bonusAfterHitChance = 25;
    protected int goLowerTickCount = 300;
    BlobSource missileSource;

    public BossEnemy(BlobIF component, PositionIF aimTarget) {
        super(component);
        this.aimTarget = aimTarget;
        missileSource = new BossTargetMissileSource(aimTarget);
    }
    
    protected void sendAimedBombs(int howMany) {
        for (int i = 0; i < howMany; i++) {
            // send an aimed bomb
            BlobIF bomb = missileSource.get(this);
            bomb.setWorld(world);
            world.addTargetToWorld(bomb);
        }
    }
    
    protected void sendBonuses(int howMany) {
         for (int i = 0; i < howMany; i++) {
            BlobIF b = EnemyFactory.hitPointBonusSource.get(this);
            b.setTickPause(TargetUtils.rnd.nextInt(20));
        }       
    }
    
    // called when this enemy has died
    protected BlobIF died() {
        // throw out a bunch of bombs
        sendAimedBombs(4);
        sendBonuses(2);
        return TriggerFactory.replaceWithExplosion(this);
    }

    @Override
    public BlobIF reactToMissileHit(BlobIF missile) {
        int hpToDeduct = 5;

        // TODO: make missiles into DamagerIFs??
        // is that possible with the way that
        // collision triggers work?
        if (missile instanceof DamagerIF) {
            hpToDeduct = ((DamagerIF)missile).getHitPoints();
        }

        hitPoints -= hpToDeduct;
        
        if (hitPoints <= 0) {
            return died();
        }
        
        // send some aimed bombs 
        sendAimedBombs(3);
        
        if (TargetUtils.rnd.nextInt(100) < bonusAfterHitChance) {
            sendBonuses(1);
        }
        
        return this;
    }

    // DamagableIF
    @Override public int setHitPoints(int hp) { hitPoints = hp; return hitPoints; }
    @Override public int incHitPoints(int hp) { return setHitPoints(hp + hitPoints); }
    @Override public int decHitPoints(int hp) { return setHitPoints(hitPoints - hp); }
    // this is how much each hit is worth in terms of points...
    @Override public int getHitPoints() { return 15; }
    
    @Override public Boolean tick() {
        ticks++;
        if (ticks % goLowerTickCount == 0) {
            PositionIF p = getPosition();
            p.setY(p.getY() - 5);
        }
        return component.tick();
    }
}
