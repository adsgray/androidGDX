package com.github.adsgray.gdxtry1.engine.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.adsgray.gdxtry1.engine.BlobSet2;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionComposeDecorator;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.util.BlobFactory;
import com.github.adsgray.gdxtry1.engine.util.PathFactory;
import com.github.adsgray.gdxtry1.engine.util.PositionFactory;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityComposeDecorator;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

// This will be more like "integration"?
public class TestBlobSet2 {

    @Before
    public void setUp() throws Exception {
    }

    private BlobIF testBlobSet2() {
        Renderer r = TestFactory.renderer();
        BlobPath path = PathFactory.stationary();
        BlobIF bs2 = new BlobSet2(0, new BlobPosition(10,11), path.vel, path.acc, r);
        bs2.setWorld(TestFactory.world());
        return bs2;
    }

    @Test
    public void testInit() {
        BlobIF bs2 = testBlobSet2();
        
        assertEquals("bs2 X init", 10, bs2.getPosition().getX());
        assertEquals("bs2 Y init", 11, bs2.getPosition().getY());
        
    }
    
    @Test
    public void testAbsorb() {
        Renderer r = TestFactory.renderer();
        BlobPath path = PathFactory.stationary();
        BlobIF bs2 = testBlobSet2();
        WorldIF w = TestFactory.world();

        w.addBlobToWorld(bs2);

        assertEquals("one blob in world", 1, w.getNumBlobs());
        
        BlobIF b1 = BlobFactory.createBaseBlob(PositionFactory.origin(), path, r);
        BlobIF b2 = BlobFactory.createBaseBlob(PositionFactory.origin(), path, r);
        
        assertFalse(b1.getVelocity() instanceof VelocityComposeDecorator);
        assertFalse(b2.getVelocity() instanceof VelocityComposeDecorator);
        assertFalse(b1.getPosition() instanceof PositionComposeDecorator);
        assertFalse(b2.getPosition() instanceof PositionComposeDecorator);
        
        bs2.absorbBlob(b1);
        bs2.absorbBlob(b2);

        assertEquals("one blob in world after absorb", 1, w.getNumBlobs());

        // verify that only position is being composed, not velocity
        assertFalse(b1.getVelocity() instanceof VelocityComposeDecorator);
        assertFalse(b2.getVelocity() instanceof VelocityComposeDecorator);
        assertTrue(b1.getPosition() instanceof PositionComposeDecorator);
        assertTrue(b2.getPosition() instanceof PositionComposeDecorator);
        
        assertEquals("b1 X after absorb", 10, b1.getPosition().getX());
        assertEquals("b1 Y after absorb", 11, b1.getPosition().getY());

        assertEquals("b2 X after absorb", 10, b1.getPosition().getX());
        assertEquals("b2 Y after absorb", 11, b1.getPosition().getY());
    }
    
    @Test
    public void testAbsorbAndVel() {
         Renderer r = TestFactory.renderer();
        BlobPath path = PathFactory.stationary();
        BlobIF bs2 = testBlobSet2();
        
        BlobIF b1 = BlobFactory.createBaseBlob(PositionFactory.origin(), path, r);
        BlobIF b2 = BlobFactory.createBaseBlob(PositionFactory.origin(), path, r);
        
        bs2.absorbBlob(b1);
        bs2.absorbBlob(b2);
        
        
        VelocityIF v = TestFactory.velocity1dash1();
        bs2.setVelocity(v);

        assertFalse(b1.getVelocity() instanceof VelocityComposeDecorator);
        assertFalse(b2.getVelocity() instanceof VelocityComposeDecorator);
        assertTrue(b1.getPosition() instanceof PositionComposeDecorator);
        assertTrue(b2.getPosition() instanceof PositionComposeDecorator);

        assertEquals("b1 vel X after absorb", 0, (int)b1.getVelocity().getXVelocity());
        assertEquals("b1 vel Y after absorb", 0, (int)b1.getVelocity().getYVelocity());
        assertEquals("b2 vel X after absorb", 0, (int)b2.getVelocity().getXVelocity());
        assertEquals("b2 vel Y after absorb", 0, (int)b2.getVelocity().getYVelocity());

        //bs2.getPosition().updateByVelocity(v);
        bs2.tick();

        assertEquals("bs2 X after vel", 11, b1.getPosition().getX());
        assertEquals("bs2 Y after vel", 12, b1.getPosition().getY());

        assertEquals("b1 X after bs2 vel", 11, b1.getPosition().getX());
        assertEquals("b1 Y after bs2 vel", 12, b1.getPosition().getY());

        assertEquals("b2 X after bs2 vel", 11, b2.getPosition().getX());
        assertEquals("b2 Y after bs2 vel", 12, b2.getPosition().getY());
    }

    @Test
    public void testAbsorbComposedPositions() {
        Renderer r = TestFactory.renderer();
        BlobPath path = PathFactory.stationary();
        BlobIF bs2 = testBlobSet2();
        
        BlobIF b1 = BlobFactory.createBaseBlob(new BlobPosition(1,0), path, r);
        BlobIF b2 = BlobFactory.createBaseBlob(PositionFactory.origin(), path, r);
        
        PositionIF mirrorPos = PositionFactory.mirrorX(b1.getPosition());
        b2.setPosition(mirrorPos);

        assertEquals("bs2 X init", 10, bs2.getPosition().getX());
        assertEquals("bs2 Y init", 11, bs2.getPosition().getY());
        
        bs2.absorbBlob(b1);

        assertEquals("bs2 X after vel", 10, bs2.getPosition().getX());
        assertEquals("bs2 Y after vel", 11, bs2.getPosition().getY());

        bs2.absorbBlob(b2);

        assertEquals("bs2 X after vel", 10, bs2.getPosition().getX());
        assertEquals("bs2 Y after vel", 11, bs2.getPosition().getY());
        
        VelocityIF v = TestFactory.velocity1dash1();
        bs2.setVelocity(v);
        //bs2.getPosition().updateByVelocity(v);
        bs2.tick();
        // bs2 pos should now be 11,12
        assertEquals("bs2 X after vel", 11, bs2.getPosition().getX());
        assertEquals("bs2 Y after vel", 12, bs2.getPosition().getY());

        assertEquals("b1 X after bs2 vel", 12, b1.getPosition().getX());
        assertEquals("b1 Y after bs2 vel", 12, b1.getPosition().getY());

        assertEquals("b2 X after bs2 vel", 10, b2.getPosition().getX());
        assertEquals("b2 Y after bs2 vel", 12, b2.getPosition().getY());
    }

    @Test
    public void testAbsorbComposedPositionsWithVel() {
        Renderer r = TestFactory.renderer();
        BlobPath path = PathFactory.stationary();
        BlobIF bs2 = testBlobSet2();
        
        BlobIF b1 = BlobFactory.createBaseBlob(new BlobPosition(1,0), path, r);
        BlobIF b2 = BlobFactory.createBaseBlob(PositionFactory.origin(), path, r);
        
        PositionIF mirrorPos = PositionFactory.mirrorX(b1.getPosition());
        b2.setPosition(mirrorPos);

        assertEquals("b2 X after mirror", -1, b2.getPosition().getX());
        assertEquals("b2 Y after mirror", 0, b2.getPosition().getY());

        bs2.absorbBlob(b1);
        bs2.absorbBlob(b2);

        assertEquals("bs2 X after absorb", 10, bs2.getPosition().getX());
        assertEquals("bs2 Y after absorb", 11, bs2.getPosition().getY());
        
        assertEquals("b1 X after absorb", 11, b1.getPosition().getX());
        assertEquals("b1 Y after absorb", 11, b1.getPosition().getY());

        assertEquals("b2 X after absorb", 9, b2.getPosition().getX());
        assertEquals("b2 Y after absorb", 11, b2.getPosition().getY());

        
        VelocityIF v = TestFactory.velocity1dash1();
        b1.setVelocity(v);
        //bs2.getPosition().updateByVelocity(v);
        b1.tick();

        // bs2 pos should be the same
        assertEquals("bs2 X after vel", 10, bs2.getPosition().getX());
        assertEquals("bs2 Y after vel", 11, bs2.getPosition().getY());

        // b1 pos should have changed
        assertEquals("b1 X after bs2 vel", 12, b1.getPosition().getX());
        assertEquals("b1 Y after bs2 vel", 12, b1.getPosition().getY());

        // b2 should be changed as well
        assertEquals("b2 X after bs2 vel", 8, b2.getPosition().getX());
        assertEquals("b2 Y after bs2 vel", 12, b2.getPosition().getY());
    }
    
    @Test
    public void testSetPosition() {
        BlobIF b = testBlobSet2();
        b.setPosition(TestFactory.position42());

        assertEquals("b X after setpos", 42, b.getPosition().getX());
        assertEquals("b Y after setpos", 42, b.getPosition().getY());
    }

    @Test
    public void testSetPositionAbsorb() {
        Renderer r = TestFactory.renderer();
        BlobPath path = PathFactory.stationary();
        BlobIF bs2 = testBlobSet2();
        
        BlobIF b1 = BlobFactory.createBaseBlob(new BlobPosition(1,0), path, r);
        BlobIF b2 = BlobFactory.createBaseBlob(PositionFactory.origin(), path, r);

        bs2.absorbBlob(b1);
        bs2.absorbBlob(b2);
        
        PositionIF bpos = b1.getPosition();
        assertTrue(bpos instanceof PositionComposeDecorator);
        bpos = b2.getPosition();
        assertTrue(bpos instanceof PositionComposeDecorator);

        assertEquals("b1 X after absorb", 11, b1.getPosition().getX());
        assertEquals("b1 Y after absorb", 11, b1.getPosition().getY());
        assertEquals("b2 X after absorb", 10, b2.getPosition().getX());
        assertEquals("b2 Y after absorb", 11, b2.getPosition().getY());
        
        bs2.setPosition(TestFactory.position42());

        assertEquals("bs2 X after setpos", 42, bs2.getPosition().getX());
        assertEquals("bs2 Y after setpos", 42, bs2.getPosition().getY());
        
        bpos = ((PositionComposeDecorator)b1.getPosition()).getPrimary();
        assertEquals("b1 primary X after bs2 setpos", 1, bpos.getX());
        assertEquals("b1 primary Y after bs2 setpos", 0, bpos.getY());

        bpos = ((PositionComposeDecorator)b2.getPosition()).getPrimary();
        assertEquals("b2 primary X after bs2 setpos", 0, bpos.getX());
        assertEquals("b2 primary Y after bs2 setpos", 0, bpos.getY());

        assertEquals("b1 X after bs2 setpos", 43, b1.getPosition().getX());
        assertEquals("b1 Y after bs2 setpos", 42, b1.getPosition().getY());
        assertEquals("b2 X after bs2 setpos", 42, b2.getPosition().getX());
        assertEquals("b2 Y after bs2 setpos", 42, b2.getPosition().getY());
    }
}
