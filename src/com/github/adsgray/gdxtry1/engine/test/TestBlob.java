package com.github.adsgray.gdxtry1.engine.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.accel.LinearAccel;
import com.github.adsgray.gdxtry1.engine.blob.BaseBlob;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.game.AccelFactory;

public class TestBlob {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testInit() {
        Renderer r = TestFactory.renderer();

        PositionIF posin = TestFactory.position42();
        VelocityIF velin = TestFactory.velocity1dash1();
        AccelIF accel = AccelFactory.zeroAccel();

        BlobIF b = new BaseBlob(0, posin, velin, accel, r);
        
        assertEquals("init pos", posin, b.getPosition());
        assertEquals("init vel", velin, b.getVelocity());
        assertEquals("init accel", accel, b.getAccel());
    }
    
    @Test
    public void testVel() {
        Renderer r = TestFactory.renderer();

        PositionIF posin = TestFactory.position42();
        VelocityIF velin = TestFactory.velocity1dash1();
        AccelIF accel = AccelFactory.zeroAccel();

        BlobIF b = new BaseBlob(0, posin, velin, accel, r);
        b.tick();
        
        PositionIF blobPos = b.getPosition();
        assertEquals("X pos after tick", 43, blobPos.getX());
        assertEquals("Y pos after tick", 43, blobPos.getY());
    }
    
    @Test
    public void testAcc() {
        Renderer r = TestFactory.renderer();

        PositionIF posin = TestFactory.position42();
        VelocityIF velin = TestFactory.velocity1dash1();
        AccelIF accel = new LinearAccel(1,1);

        BlobIF b = new BaseBlob(0, posin, velin, accel, r);
        b.tick();
        
        VelocityIF blobVel = b.getVelocity();
        assertEquals("X pos after tick", 2, (int)blobVel.getXVelocity());
        assertEquals("Y pos after tick", 2, (int)blobVel.getYVelocity());
    }
    
    @Test
    public void testTickDeathTrigger() {
        Renderer r = TestFactory.renderer();

        PositionIF posin = TestFactory.position42();
        VelocityIF velin = TestFactory.velocity1dash1();
        AccelIF accel = new LinearAccel(1,1);

        BlobIF b = new BaseBlob(0, posin, velin, accel, r);
        b.setLifeTime(0);
        TestFactory.TestBlobTrigger trigger = new TestFactory.TestBlobTrigger();
        b.registerTickDeathTrigger(trigger);
        b.tick();
        
        assertEquals("num in trigger", 1, trigger.num);
    }

}
