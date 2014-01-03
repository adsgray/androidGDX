package com.github.adsgray.gdxtry1.engine.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BaseBlob;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.util.AccelFactory;
import com.github.adsgray.gdxtry1.engine.util.BlobFactory;
import com.github.adsgray.gdxtry1.engine.util.GameFactory;
import com.github.adsgray.gdxtry1.engine.util.PositionFactory;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;

public class TestWorld {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testInit() {
        WorldIF w = TestFactory.world();
        assertEquals("no blobs", 0, w.getNumBlobs());
        assertEquals("no missiles", 0, w.getNumMissiles());
        assertEquals("no targets", 0, w.getNumTargets());
    }
    
    @Test
    public void testAddRemoveAllBlobTypes() {
        WorldIF w = TestFactory.world();
        Renderer r = TestFactory.renderer();

        // not testing collisions here so they can all be in the same place
        BlobIF b = new BaseBlob(0, PositionFactory.origin(), GameFactory.zeroVelocity(), AccelFactory.zeroAccel(), r);
        b.setExtent(new CircleExtent(10));
        BlobIF target = new BaseBlob(0, PositionFactory.origin(), GameFactory.zeroVelocity(), AccelFactory.zeroAccel(), r);
        target.setExtent(new CircleExtent(10));
        BlobIF missile = new BaseBlob(0, PositionFactory.origin(), GameFactory.zeroVelocity(), AccelFactory.zeroAccel(), r);
        missile.setExtent(new CircleExtent(10));
        
        w.addBlobToWorld(b);
        w.addTargetToWorld(target);
        w.addMissileToWorld(missile);
        
        // must do this so that scheduled things are added
        w.tick();

        assertEquals("num blobs", 1, w.getNumBlobs());
        assertEquals("num missiles", 1, w.getNumMissiles());
        assertEquals("num targets", 1, w.getNumTargets());
        
        w.removeBlobFromWorld(b);
        w.tick();
        assertEquals("num blobs", 0, w.getNumBlobs());
        assertEquals("num missiles", 1, w.getNumMissiles());
        assertEquals("num targets", 1, w.getNumTargets());
        
        w.removeBlobFromWorld(missile);
        w.tick();
        assertEquals("num blobs", 0, w.getNumBlobs());
        assertEquals("num missiles", 0, w.getNumMissiles());
        assertEquals("num targets", 1, w.getNumTargets());

        w.removeBlobFromWorld(target);
        w.tick();
        assertEquals("num blobs", 0, w.getNumBlobs());
        assertEquals("num missiles", 0, w.getNumMissiles());
        assertEquals("num targets", 0, w.getNumTargets());
    }
    
    @Test
    public void testSimpleTargetMissileCollision() {
        WorldIF w = TestFactory.world();
        Renderer r = TestFactory.renderer();

        // not testing collisions here so they can all be in the same place
        BlobIF target = new BaseBlob(0, PositionFactory.origin(), GameFactory.zeroVelocity(), AccelFactory.zeroAccel(), r);
        target.setExtent(new CircleExtent(10));
        target.setClientType(42);
        BlobIF missile = new BaseBlob(0, new BlobPosition(0,21), new BlobVelocity(0,-1), AccelFactory.zeroAccel(), r);
        missile.setExtent(new CircleExtent(10));
        
        TestFactory.TestBlobTrigger trigger = new TestFactory.TestBlobTrigger() {
            @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
                // this tests that the blob passed to us (secondary) is the
                // one we expected to collide with.
                assertEquals("secondary clientType", 42, secondary.getClientType());
                num += 1;
                return source;
            }
        };
        missile.registerCollisionTrigger(trigger);
        
        w.addTargetToWorld(target);
        w.addMissileToWorld(missile);
        
        // should not collide after 1 tick. They're juuuuust barely touching
        w.tick();
        assertEquals("missile trigger count", 0, trigger.num);

        // should collide now
        w.tick();
        assertEquals("missile trigger count", 1, trigger.num);
    }
    
    // check that missiles will not hit a regular blob that isn't a target
    @Test
    public void testBlobDoesntCollide() {
         WorldIF w = TestFactory.world();
        Renderer r = TestFactory.renderer();

        // not testing collisions here so they can all be in the same place
        BlobIF nontarget = new BaseBlob(0, PositionFactory.origin(), GameFactory.zeroVelocity(), AccelFactory.zeroAccel(), r);
        nontarget.setExtent(new CircleExtent(10));
        BlobIF missile = new BaseBlob(0, new BlobPosition(0,21), new BlobVelocity(0,-1), AccelFactory.zeroAccel(), r);
        missile.setExtent(new CircleExtent(10));
        
        TestFactory.TestBlobTrigger trigger = new TestFactory.TestBlobTrigger();
        missile.registerCollisionTrigger(trigger);
        
        w.addBlobToWorld(nontarget);
        w.addMissileToWorld(missile);
        
        // should not collide after 1 tick. They're juuuuust barely touching
        w.tick();
        assertEquals("missile trigger count", 0, trigger.num);

        // still should not collide
        w.tick();
        assertEquals("missile trigger count", 0, trigger.num);       

        // tick one more time just to be sure
        w.tick();
        assertEquals("missile trigger count", 0, trigger.num);       

    }

    
    // doesn't actually decorate, just needed for BlobIF reference
    private static class TestDecorator extends BlobDecorator {
        public TestDecorator(BlobIF component) { super(component); }
    }

    // Test that blobs are moved properly when they are decorated
    @Test
    public void testBlobManagersWithDecorators() {
        WorldIF w = TestFactory.world();
        Renderer r = TestFactory.renderer();

        //BlobIF nontarget = new BaseBlob(0, PositionFactory.origin(), GameFactory.zeroVelocity(), AccelFactory.zeroAccel(), r);
        //nontarget.setExtent(new CircleExtent(10));
        BlobIF missile = new BaseBlob(0, new BlobPosition(0,21), new BlobVelocity(0,-1), AccelFactory.zeroAccel(), r);
        missile.setExtent(new CircleExtent(10));
        
        BlobIF decoratedMissile = new TestDecorator(missile);
        w.addBlobToWorld(decoratedMissile);
        w.tick();
        assertEquals("one blob", 1, w.getNumBlobs());
        
        // now remove the base blob
        w.removeBlobFromWorld(missile);
        w.tick();
        assertEquals("blob removed", 0, w.getNumBlobs());
        
        // multilevel:
        BlobIF doubleDecorated = new TestDecorator(decoratedMissile);
        w.addBlobToWorld(doubleDecorated);
        w.tick();
        assertEquals("one blob", 1, w.getNumBlobs());

        // now remove the base blob
        w.removeBlobFromWorld(missile);
        w.tick();
        assertEquals("blob removed", 0, w.getNumBlobs());
    }
}
