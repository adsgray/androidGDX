package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.PathFactory;
import com.github.adsgray.gdxtry1.game.TriggerFactory;
import com.github.adsgray.gdxtry1.game.testgame1.TargetUtils;

// this default enemy "evolves" into an "angry" version of itself
// the first time it is hit.
public class DefaultEnemy extends BlobDecorator implements DamagerIF, DamagableIF, EnemyIF {

    public enum Type {
        Initial, Angry
    }

    protected int hitPoints;
    protected Type type;
    protected int bonusChance = 25;

    // set up stuff in this decorator constructor?
    public DefaultEnemy(BlobIF component) {
        super(component);
        hitPoints = 10;
        type = Type.Initial;
    }

    // Damagable:
    @Override public int setHitPoints(int hp) { hitPoints = hp; return hitPoints; }
    @Override public int incHitPoints(int hp) { hitPoints += hp; return hitPoints; }
    @Override public int decHitPoints(int hp) { hitPoints -= hp; return hitPoints; }
    @Override public int getHitPoints() { return hitPoints; }

    private void setAngryPath() {
        setPath(TargetUtils.chooseBackAndForthPath(position));
    }
    
    private void angryExplosion() {
        BlobIF explosion = TargetUtils.becomeAngryExplosion(this);
        world.addBlobToWorld(explosion);
    }
    
    private void leaveCluster() {
        if (cluster != null) {
            position = new BlobPosition(cluster.getPosition());
            cluster.leaveCluster(this);
        }
    }

    protected void becomeAngry() {
        if (type == Type.Initial) {
            type = Type.Angry;
            BlobIF angryMe = BlobFactory.rainbowColorCycler(component, 5);
            component = angryMe;
            
            angryExplosion();
            leaveCluster(); // if part of a cluster, leave it as our path is about to become independent
            setAngryPath();
            
            // we're now worth more to kill
            hitPoints = 15;

            // also change missile source so that they are faster
            // by replacing tickDeathTrigger. And have this enemy
            // fire almost immediately (after 10 ticks)
            setLifeTime(10);
            clearTickDeathTriggers();
            registerTickDeathTrigger(TargetUtils.fireAtDefenderLoop(350, TargetUtils.angryTargetMissileSource));
        }
    }

    // EnemyIF
    @Override
    public BlobIF reactToMissileHit(BlobIF missile) {
        BlobIF ret = this;

        if (type == Type.Initial) {
            becomeAngry();
        } else {
                
            // explode
            ret = TriggerFactory.replaceWithExplosion(this);

            // then throw some more bombs down as we die
            for (int i = 0; i < 2; i++) {
                // angryTargetMissileSource adds the target to the world.
                BlobIF bomb = TargetUtils.angryTargetMissileSource.get(this);
            }
            
            // also randomly throw down some extra hit points
            // set the hitPoints on this to negative so that
            // (a) when it collides with the ship it gives hitPoints (subtract a neg. number)
            // (b) if you shoot it, you lose points haha.
            if (TargetUtils.rnd.nextInt(100) < bonusChance) {
                EnemyFactory.hitPointBonusSource.get(this);
            }
        }
        
        return ret;
    }
}
