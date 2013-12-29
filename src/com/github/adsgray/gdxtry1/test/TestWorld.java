package com.github.adsgray.gdxtry1.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BaseBlob;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.game.AccelFactory;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.PositionFactory;
import com.github.adsgray.gdxtry1.output.Renderer;

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

}
