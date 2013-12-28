package com.github.adsgray.gdxtry1.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.RectangleBlob;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.MultiplyPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionComposeDecorator;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.game.PathFactory;
import com.github.adsgray.gdxtry1.game.PositionFactory;

public class TestMultiplyPosition {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSimple() {
        PositionIF p1 = new BlobPosition(5,5);
        PositionIF multFactors = new BlobPosition(-2,-1);
        PositionIF multipliedPos = new MultiplyPosition(p1, multFactors);
        

        assertEquals("p1 X", 5, p1.getX());
        assertEquals("p1 Y", 5, p1.getY());

        assertEquals("multipliedPos X", -10, multipliedPos.getX());
        assertEquals("multipliedPos Y", -5, multipliedPos.getY());
    }

    @Test
    public void testComponentVel() {
        PositionIF p1 = new BlobPosition(5,5);
        PositionIF multFactors = new BlobPosition(-2,-1);
        PositionIF multipliedPos = new MultiplyPosition(p1, multFactors);
        VelocityIF v = TestFactory.velocity1dash1();
        
        assertEquals("multipliedPos X", -10, multipliedPos.getX());
        assertEquals("multipliedPos Y", -5, multipliedPos.getY());
        
        p1.updateByVelocity(v);

        assertEquals("multipliedPos X", -12, multipliedPos.getX());
        assertEquals("multipliedPos Y", -6, multipliedPos.getY());
    }
 
    // TODO
    @Test @Ignore
    public void testWithNestedCompose() {
        PositionIF comp = new BlobPosition(1,1);
        PositionIF prim = new BlobPosition(-1,-1);
        PositionIF cluster = new BlobPosition(10,10);

        // aha, must compose on the original position... not the composed one...
        // that is the bug!
        // That is, must compose on the position before it is inserted into
        // the cluster! Otherwise it is off the charts?
        PositionIF comp_in_cluster = new PositionComposeDecorator(cluster, comp);
        PositionIF p = new MultiplyPosition(comp_in_cluster,prim);
        
        // now where is p?
        // comp_in_cluster should be at (11,11)
        // and p should be at (9,9)

        assertEquals("comp_in_cluster X", 11, comp_in_cluster.getX());
        assertEquals("comp_in_cluster Y", 11, comp_in_cluster.getY());

        assertEquals("p X", 9, p.getX());
        assertEquals("p Y", 9, p.getY());
        
        VelocityIF v = TestFactory.velocity1dash1();
        comp_in_cluster.updateByVelocity(v);

        // cluster did not move
        assertEquals("cluster X afer vel", 10, cluster.getX());
        assertEquals("cluster Y afer vel", 10, cluster.getY());
        
        // comp_in_cluster moved 
        assertEquals("comp_in_cluster X after cluster vel", 12, comp_in_cluster.getX());
        assertEquals("comp_in_cluster Y after cluster vel", 12, comp_in_cluster.getY());
        
        // so did p
        assertEquals("p X after cluster vel", 10, p.getX());
        assertEquals("p Y after cluster vel", 10, p.getY());
    }

    // tests game.PositionFactory method
    // maybe separate unit test files for those Factories...
    @Test
    public void testMirrorXAlsoWithVel() {
        PositionIF comp = new BlobPosition(1,1);
        PositionIF p = PositionFactory.mirrorX(comp);

        assertEquals("comp X", 1, comp.getX());
        assertEquals("comp Y", 1, comp.getY()); 

        assertEquals("mirror X", -1, p.getX());
        assertEquals("mirror Y", 1, p.getY());
        
        VelocityIF v = TestFactory.velocity1dash1();
        comp.updateByVelocity(v);

        assertEquals("comp X after vel", 2, comp.getX());
        assertEquals("comp Y after vel", 2, comp.getY()); 

        assertEquals("mirror X after comp vel", -2, p.getX());
        assertEquals("mirror Y after comp vel", 2, p.getY());
    }
}
