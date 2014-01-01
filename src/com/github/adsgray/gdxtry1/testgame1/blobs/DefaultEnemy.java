package com.github.adsgray.gdxtry1.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.util.BlobFactory;
import com.github.adsgray.gdxtry1.engine.util.GameFactory;
import com.github.adsgray.gdxtry1.engine.util.PathFactory;
import com.github.adsgray.gdxtry1.engine.util.TriggerFactory;
import com.github.adsgray.gdxtry1.testgame1.GameSound;
import com.github.adsgray.gdxtry1.testgame1.TargetUtils;
import com.github.adsgray.gdxtry1.testgame1.GameSound.SoundId;
import com.github.adsgray.gdxtry1.testgame1.TargetUtils.Difficulty;

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
            registerTickDeathTrigger(TargetUtils.angryEnemyFireLoop);
 
            /*
            // Also a N% chance that parent will move down closer to defender
            // should this be in here??
            if (TargetUtils.rnd.nextInt(100) < 25) {
                PositionIF ppos = parent.getPosition();
                ppos.setY(ppos.getY() - 5);
            }
            */
        }
    }

    // EnemyIF
    @Override
    public BlobIF reactToMissileHit(BlobIF missile) {
        BlobIF ret = this;

        // show a quick little flash message
        // at the location of the enemy to show the
        // number of points awarded? or is that too busy?
        if (type == Type.Initial) {
            becomeAngry();
            GameSound.get().playSoundId(SoundId.enemyBecomeAngry);
        } else {
                
            // explode
            GameSound.get().explosionAll();
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
            if (dropBonusOnDeath()) {
                EnemyFactory.hitPointBonusSource.get(this);
            }
        }
        
        return ret;
    }

    protected Boolean dropBonusOnDeath() {
        return TargetUtils.difficulty == Difficulty.easy || (TargetUtils.rnd.nextInt(100) < bonusChance);
    }

    @Override public int getWeight() { return 1; } 
}
