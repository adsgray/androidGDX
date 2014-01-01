package com.github.adsgray.gdxtry1.engine.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.util.PositionFactory;

public class TestCircleExtent {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testContains() {
        CircleExtent c = new CircleExtent(10);
        PositionIF centre = PositionFactory.origin();

        PositionIF notIn = new BlobPosition(11,0);
        PositionIF yesIn = new BlobPosition(10,0);
        PositionIF yesInToo = new BlobPosition(5,0);
        
        assertFalse("notIn is not contained", c.contains(centre, notIn));
        assertTrue("yesIn is contained", c.contains(centre, yesIn));
        assertTrue("yesInToo is contained", c.contains(centre, yesInToo));
    }
}
