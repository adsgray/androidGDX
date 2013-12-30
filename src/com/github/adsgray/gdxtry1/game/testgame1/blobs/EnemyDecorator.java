package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.PathFactory;
import com.github.adsgray.gdxtry1.game.testgame1.TargetUtils;

public class EnemyDecorator extends BlobDecorator implements Damager, Damagable, Enemy {

    protected int hitPoints;
    protected Enemy.Type type;

    // set up stuff in this decorator constructor?
    public EnemyDecorator(BlobIF component) {
        super(component);
        hitPoints = 10;
        type = Enemy.Type.Initial;
    }

    // Damagable:
    @Override public int setHitPoints(int hp) { hitPoints = hp; return hitPoints; }
    @Override public int incHitPoints(int hp) { hitPoints += hp; return hitPoints; }
    @Override public int decHitPoints(int hp) { hitPoints -= hp; return hitPoints; }
    @Override public int getHitPoints() { return hitPoints; }

    private void setAngryPath() {
        // if on the left, go the the right
        // if on the right, go to the left
        if (position.getX() < GameFactory.BOUNDS_X / 2) {
            setPath(PathFactory.backAndForth(15,5));
        } else {
            setPath(PathFactory.backAndForthLeft(15,5));
        }
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

    @Override
    public void becomeAngry() {
        if (type == Enemy.Type.Initial) {
            type = Enemy.Type.Angry;
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

    @Override public Type getType() { return type; }
}