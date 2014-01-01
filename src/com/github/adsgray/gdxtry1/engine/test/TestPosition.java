package com.github.adsgray.gdxtry1.engine.test;


import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.accel.LinearAccel;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.test.TestFactory.TestBlobTrigger;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

import org.junit.*;

import static org.junit.Assert.*;

public class TestPosition {
   
    @Test
    public void testInit() {
        PositionIF p = TestFactory.position42();
        assertEquals("X position init", 42, p.getX());
        assertEquals("Y position init", 42, p.getY());
    }
    
    @Test
    public void testVelocityStep() {
        PositionIF p = TestFactory.position42();
        VelocityIF v = TestFactory.velocity1dash1();
        
        p.updateByVelocity(v);
        assertEquals("X position after vel", 43, p.getX());
        assertEquals("Y position after vel", 43, p.getY());
    }
   
    @Test
    public void testPositionTrigger() {
        PositionIF p = new BlobPosition(1,1);
        TestFactory.TestBlobTrigger xTrigger = new TestFactory.TestBlobTrigger();
        TestFactory.TestBlobTrigger yTrigger = new TestFactory.TestBlobTrigger();
        
        VelocityIF v = new BlobVelocity(-1,-1);
        
        p.registerAxisTrigger(PositionIF.Axis.X, 0, xTrigger);
        p.registerAxisTrigger(PositionIF.Axis.Y, 0, yTrigger);
        
        assertEquals("X trigger num before vel", 0, xTrigger.num);
        assertEquals("Y trigger num before vel", 0, yTrigger.num);
        
        p.updateByVelocity(v);
        p.handleTriggers(null);

        assertEquals("X trigger num after vel", 1, xTrigger.num);
        assertEquals("Y trigger num after vel", 1, yTrigger.num);
    }
    
    // TODO: tests for triggers on composed positions
}
