package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.PathFactory;
import com.github.adsgray.gdxtry1.game.testgame1.GameCommand;
import com.github.adsgray.gdxtry1.game.testgame1.MissileBlobSource;
import com.github.adsgray.gdxtry1.input.Draggable;
import com.github.adsgray.gdxtry1.input.Flingable;
import com.github.adsgray.gdxtry1.input.SimpleDirectionGestureDetector.DirectionListener.FlingInfo;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.CircleConfig;

public class FiringBlobDecorator extends BlobDecorator implements
        Flingable, Draggable, Damagable {

    protected BlobSource missileSource;
    protected ExtentIF flingExtent;
    protected ExtentIF dragExtent;
    protected int hitPoints;
    protected int maxMissiles; // max missiles in the air at one time

    public FiringBlobDecorator(BlobIF component, GameCommand postKillCommand) {
        super(component);
        missileSource = new MissileBlobSource(postKillCommand);
        CircleExtent ce = (CircleExtent)component.getExtent();
        flingExtent = new CircleExtent(ce.getRadius() * 3);
        dragExtent = new CircleExtent(ce.getRadius() * 2);
        // TODO: text display 'widget' in engine, display these hitpoints
        // at the top of the screen.
        // When you go <= 0 you explode and can start again.
        hitPoints = 75;
        maxMissiles = 3; // the defender/triangle counts as a missile, so this means
                         // you can launch up to 2 simultaneous missiles
    }

    @Override
    public void onFlingUp(FlingInfo f) {
        if (world.getNumMissiles() < maxMissiles) {
            BlobIF missile = missileSource.get(this);
        }
        // else make the defender flash/shake?
        // OK now set the missile's velocity based on FlingInfo
    }

    @Override public void onFlingLeft(FlingInfo f) { }
    @Override public void onFlingRight(FlingInfo f) { }
    @Override public void onFlingDown(FlingInfo f) { }

    @Override
    public void panStarted(PositionIF start) {
        // TODO Auto-generated method stub
    }

    @Override
    public void panInProgress(PositionIF cur) {
        // discard Y coord changes
        // what if position is a decorator?
        PositionIF p = component.getPosition();
        component.setPosition(new BlobPosition(cur.getX(), p.getY()));
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
}
