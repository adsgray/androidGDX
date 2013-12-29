package com.github.adsgray.gdxtry1.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.accel.LinearAccel;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.game.AccelFactory;

public class TestAccel {

    @Before
    public void setUp() throws Exception {
    }
 
    @Test
    public void testLinearAccel() {
        //PositionIF p = TestFactory.position42();
        VelocityIF v = TestFactory.velocity1dash1();
        AccelIF a = TestFactory.linearAccel1dash1();
        
        v.accelerate(a);
        // TODO: change getXVel, getYVel to return ints
        assertEquals("X vel after acc", 2, (int)v.getXVelocity());
        assertEquals("Y vel after acc", 2, (int)v.getYVelocity());
    }
 
    @Test
    public void testBumpAccel() {
        //PositionIF p = TestFactory.position42();
        VelocityIF v = TestFactory.velocity1dash1();
        AccelIF a = TestFactory.linearAccel1dash1();
        AccelIF bump = TestFactory.linearAccel1dash1();
        AccelIF expiringBump = AccelFactory.bump(a, bump, 1);
        
        // combined accel should be (2,2)
        expiringBump.accellerate(v);

        // (3,3)
        assertEquals("X vel after bump acc", 3, (int)v.getXVelocity());
        assertEquals("Y vel after bump acc", 3, (int)v.getXVelocity());

        // accel should now be (1,1) as expiringBump has expired
        expiringBump.accellerate(v);

        // (4,4)
        assertEquals("X vel after bump acc", 4, (int)v.getXVelocity());
        assertEquals("Y vel after bump acc", 4, (int)v.getXVelocity());
    }
    
    @Test
    @Ignore
    public void testAccelCompressDecorators() {
    }

}
