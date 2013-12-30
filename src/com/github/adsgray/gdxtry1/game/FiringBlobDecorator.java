package com.github.adsgray.gdxtry1.game;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.input.Draggable;
import com.github.adsgray.gdxtry1.input.Flingable;
import com.github.adsgray.gdxtry1.input.SimpleDirectionGestureDetector.DirectionListener.FlingInfo;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.CircleConfig;

public class FiringBlobDecorator extends BlobDecorator implements
        Flingable, Draggable {

    protected BlobSource missileSource;
    protected ExtentIF flingExtent;
    protected ExtentIF dragExtent;

    public FiringBlobDecorator(BlobIF component) {
        super(component);
        missileSource = new BlobSource() {
            @Override protected BlobIF generate(BlobIF parent) {
                WorldIF w = parent.getWorld();
                Renderer r = parent.getRenderer();
                BlobPosition p = new BlobPosition(parent);
                CircleConfig rc = r.new CircleConfig(Color.RED, 25f);

                // create a missile blob whose initial position
                // is based on parent
                BlobIF m = BlobFactory.circleBlob(p, PathFactory.launchUp(), rc, r);
                w.addMissileToWorld(m);
                return m;
            }
        };
        
        CircleExtent ce = (CircleExtent)component.getExtent();
        flingExtent = new CircleExtent(ce.getRadius() * 3);
        dragExtent = new CircleExtent(ce.getRadius() * 2);
    }

    @Override
    public void onFlingUp(FlingInfo f) {
        BlobIF missile = missileSource.get(this);
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

    @Override
    public ExtentIF getFlingExtent() {
        return flingExtent;
    }

    @Override
    public ExtentIF getDragExtent() {
        return dragExtent;
    }
}
