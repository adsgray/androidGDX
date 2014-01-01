package com.github.adsgray.gdxtry1.testgame1.blobs;

import android.util.Log;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.engine.blob.decorator.ShowExtentDecorator;
import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.input.Draggable;
import com.github.adsgray.gdxtry1.engine.input.Flingable;
import com.github.adsgray.gdxtry1.engine.input.SimpleDirectionGestureDetector.DirectionListener.FlingInfo;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.output.Renderer.CircleConfig;
import com.github.adsgray.gdxtry1.engine.output.Renderer.RectConfig;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionComposeDecorator;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.util.BlobFactory;
import com.github.adsgray.gdxtry1.engine.util.GameCommand;
import com.github.adsgray.gdxtry1.engine.util.PathFactory;
import com.github.adsgray.gdxtry1.testgame1.GameSound;
import com.github.adsgray.gdxtry1.testgame1.MissileBlobSource;
import com.github.adsgray.gdxtry1.testgame1.MissileCollisionTrigger;
import com.github.adsgray.gdxtry1.testgame1.ShieldCollisionTrigger;
import com.github.adsgray.gdxtry1.testgame1.GameSound.SoundId;

public class FiringBlobDecorator extends BlobDecorator implements
        Flingable, Draggable, DamagableIF {

    protected BlobSource missileSource;
    protected ExtentIF flingExtent;
    protected ExtentIF dragExtent;
    protected int hitPoints;
    protected int maxMissiles; // max missiles in the air at one time
    protected int numShields;
    protected BlobTrigger shieldCollisionTrigger;
    GameCommand incShield;

    public FiringBlobDecorator(BlobIF component, GameCommand postKillCommand, GameCommand incShield) {
        super(component);
        missileSource = new MissileBlobSource(postKillCommand);
        // shield acts like a "missile"
        this.shieldCollisionTrigger = new ShieldCollisionTrigger(postKillCommand);
        CircleExtent ce = (CircleExtent)component.getExtent();
        flingExtent = new CircleExtent(ce.getRadius() * 3);
        dragExtent = new CircleExtent(ce.getRadius() * 3);
        // TODO: text display 'widget' in engine, display these hitpoints
        // at the top of the screen.
        // When you go <= 0 you explode and can start again.
        hitPoints = 50;
        maxMissiles = 3; // the defender/triangle counts as a missile, so this means
                         // you can launch up to 2 simultaneous missiles
        this.incShield = incShield;
        numShields = 1;
        incShield.execute(1);
    }

    public int incrementNumShields(int ct) {
        numShields += ct;
        return numShields;
    }

    @Override
    public void onFlingUp(FlingInfo f) {
        if (world.getNumMissiles() < maxMissiles) {
            BlobIF missile = missileSource.get(this);
            GameSound.get().playSoundId(SoundId.shoot);
        }
        // else make the defender flash/shake?
        // OK now set the missile's velocity based on FlingInfo
    }

    @Override public void onFlingLeft(FlingInfo f) { }
    @Override public void onFlingRight(FlingInfo f) { }

    @Override public void onFlingDown(FlingInfo f) { 
        shieldsUp();
    }

    @Override
    public void panStarted(PositionIF start) {
        // TODO Auto-generated method stub
    }

    @Override
    public void panInProgress(PositionIF cur) {
        // discard Y coord changes
        // what if position is a decorator?
        PositionIF p = component.getPosition();
        p.setX(cur.getX());
        // TODO: allow a small range of Y movement
        //p.setY(cur.getY());
    }

    @Override
    public void completePan(PositionIF start, PositionIF stop) {
        // TODO Auto-generated method stub
    }

    @Override public ExtentIF getFlingExtent() { return flingExtent; }
    @Override public ExtentIF getDragExtent() { return dragExtent; }

    // Damagable:
    @Override public int setHitPoints(int hp) { hitPoints = hp; return hitPoints; }
    @Override public int incHitPoints(int hp) { hitPoints += hp; return hitPoints; }
    @Override public int decHitPoints(int hp) { hitPoints -= hp; return hitPoints; }
    @Override public int getHitPoints() { return hitPoints; }
    
    private int ticksWhenShieldsWentUp = 0;
    // this is how long you have to wait until you can put shields up again.
    private int shieldTickInterval = 150;

    public void shieldsUp() {
        if (numShields == 0 || ticks - ticksWhenShieldsWentUp < shieldTickInterval) {
            GameSound.get().playSoundId(SoundId.shieldDenied);
            EnemyFactory.flashMessage(world, renderer, "Shield Denied!", 20);
            return;
        }

        numShields -= 1;
        incShield.execute(-1);
        ticksWhenShieldsWentUp = ticks;

        // shield moves with us
        PositionIF p = new PositionComposeDecorator(component.getPosition(), new BlobPosition(0, 90));
        RectConfig rc = renderer.new RectConfig(Color.RED, 100, 15);
        BlobIF b = BlobFactory.rectangleBlob(p, PathFactory.stationary(), rc, renderer);
        b.setExtent(new CircleExtent(100));
        b.setLifeTime(135);
        b = BlobFactory.flashColorCycler(b, 1);
        b = BlobFactory.throbber(b);
        b.setWorld(world);
        
        // behave like a missile
        b.registerCollisionTrigger(shieldCollisionTrigger);
        world.addMissileToWorld(b);
    }
    
    @Override public Boolean tick() {
        // TODO idea: ammo limit, you get 1 ammo every 100 ticks
        // and also get 2 ammos when you destroy an ememy object
        // would have to add ammo to score display
        ticks++;
        return component.tick();
    }
}
