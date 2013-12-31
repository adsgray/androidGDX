package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.game.PositionFactory;
import com.github.adsgray.gdxtry1.game.testgame1.BossTargetMissileSource;
import com.github.adsgray.gdxtry1.game.testgame1.TargetUtils;

public class BossEnemy extends BlobDecorator implements DamagerIF, DamagableIF, EnemyIF {

    protected int hitPoints = 45;
    protected PositionIF aimTarget;
    BlobSource missileSource;

    public BossEnemy(BlobIF component, PositionIF aimTarget) {
        super(component);
        this.aimTarget = aimTarget;
        //missileSource = new BossTargetMissileSource(TargetUtils.displaceBomb);
        missileSource = new BossTargetMissileSource();
    }
    
    protected float bombSpeed() {
        // minimum 8, maximum 16?
        return TargetUtils.rnd.nextFloat() * 8 + 8;
    }
    
    // return a position that is within the bounds of this enemy
    // so that the bomb will drop from somewhere inside the boss
    protected PositionIF chooseSourcePosition() {
        // TODO send from somewhere other than middle of this enemy
        return new BlobPosition(getPosition());
    }
    
    // based on p (source of bomb) and the target set in the constructor, create
    // a velocity that will launch the bomb at the target. Some
    // error will be introduced by the aimError transform.
    protected VelocityIF aimAtTargetFrom(PositionIF p) {

        // this is the velocity in "position form"
        // velocity along vector is 10?
        PositionIF vector = aimTarget.subtract(p).unitVector().ofLength(bombSpeed());
        return new BlobVelocity(vector.getX(), vector.getY());
    }
    
    protected static BlobTransform aimError = new BlobTransform() {
        // TODO: introduce error in velocity
        @Override public BlobIF transform(BlobIF b) {
            return b;
        }
    };
    
    protected void sendAimedBombs(int howMany) {
        for (int i = 0; i < howMany; i++) {
            // send an aimed bomb
            PositionIF p = chooseSourcePosition();
            VelocityIF v = aimAtTargetFrom(p);
            
            BlobIF bomb = missileSource.get(this);
            bomb.setPosition(p);
            bomb.setVelocity(v);
            bomb = aimError.transform(bomb);
            bomb.setWorld(world);
            world.addTargetToWorld(bomb);
        }
    }
    
    // called when this enemy has died
    protected BlobIF died() {
        // throw out a bunch of bombs
        sendAimedBombs(4);
        return this;
    }

    @Override
    public BlobIF reactToMissileHit(BlobIF missile) {
        int hpToDeduct = 5;
        if (missile instanceof DamagerIF) {
            hpToDeduct = ((DamagerIF)missile).getHitPoints();
        }

        hitPoints -= hpToDeduct;
        
        if (hitPoints <= 0) {
            return died();
        }
        
        // send two aimed bombs 
        sendAimedBombs(2);
        
        return this;
    }

    // DamagableIF
    @Override public int setHitPoints(int hp) { hitPoints = hp; return hitPoints; }
    @Override public int incHitPoints(int hp) { return setHitPoints(hp + hitPoints); }
    @Override public int decHitPoints(int hp) { return setHitPoints(hitPoints - hp); }
    @Override public int getHitPoints() { return hitPoints; }

}
