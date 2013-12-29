package com.github.adsgray.gdxtry1.test;


import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.accel.LinearAccel;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
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
   
}
