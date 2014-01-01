package com.github.adsgray.gdxtry1.engine.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.blob.RectangleBlob;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.MultiplyPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionComposeDecorator;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.game.BlobFactory;
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
    @Test 
    public void testWithNestedComposeAndSomeVelocities() {
        PositionIF comp = new BlobPosition(1,1);
        PositionIF cluster = new BlobPosition(10,10);

        // aha, must compose on the original position... not the composed one...
        // that is the bug!
        // That is, must compose on the position before it is inserted into
        // the cluster! Otherwise it is off the charts?
        PositionIF mirror_comp = PositionFactory.mirrorX(comp);
        PositionIF comp_in_cluster = new PositionComposeDecorator(cluster, comp);
        PositionIF mirror_comp_in_cluster = new PositionComposeDecorator(cluster, mirror_comp);
        
        assertEquals("comp_in_cluster X", 11, comp_in_cluster.getX());
        assertEquals("comp_in_cluster Y", 11, comp_in_cluster.getY());

        assertEquals("mirror_comp X", -1, mirror_comp.getX());
        assertEquals("mirror_comp Y", 1, mirror_comp.getY());
        
        assertEquals("mirror_comp_in_cluster X", 9, mirror_comp_in_cluster.getX());
        assertEquals("mirror_comp_in_cluster Y", 11, mirror_comp_in_cluster.getY());

        
        VelocityIF v = TestFactory.velocity1dash1();
        comp_in_cluster.updateByVelocity(v);

        // cluster did not move
        assertEquals("cluster X afer comp_in_cluster vel", 10, cluster.getX());
        assertEquals("cluster Y afer comp_in_cluster vel", 10, cluster.getY());
        
        // comp_in_cluster moved 
        assertEquals("comp_in_cluster X after vel", 12, comp_in_cluster.getX());
        assertEquals("comp_in_cluster Y after vel", 12, comp_in_cluster.getY());
        
        // so did mirror 
        assertEquals("mirror_comp_in_cluster X after comp_in_cluster vel", 8, mirror_comp_in_cluster.getX());
        assertEquals("mirror_comp_in_cluster Y after comp_in_cluster vel", 12, mirror_comp_in_cluster.getY());
        
        // OK now move the cluster
        cluster.updateByVelocity(v);

        assertEquals("cluster X afer comp_in_cluster vel", 11, cluster.getX());
        assertEquals("cluster Y afer comp_in_cluster vel", 11, cluster.getY());

        assertEquals("comp_in_cluster X after vel", 13, comp_in_cluster.getX());
        assertEquals("comp_in_cluster Y after vel", 13, comp_in_cluster.getY());

        assertEquals("mirror_comp_in_cluster X after comp_in_cluster vel", 9, mirror_comp_in_cluster.getX());
        assertEquals("mirror_comp_in_cluster Y after comp_in_cluster vel", 13, mirror_comp_in_cluster.getY());
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
    
    @Test
    public void testMirrorXWithBlobTick() {
        Renderer r = TestFactory.renderer();
        BlobPath path = PathFactory.stationary();

        BlobIF b1 = BlobFactory.createBaseBlob(PositionFactory.origin(), path, r);
        BlobIF b2 = BlobFactory.createBaseBlob(PositionFactory.origin(), path, r);
        
        b1.setVelocity(new BlobVelocity(1,0));
        b2.setPosition(PositionFactory.mirrorX(b1.getPosition()));
        
        b1.tick();
        assertEquals("b1 pos X after tick", 1, b1.getPosition().getX());
        assertEquals("b1 pos Y after tick", 0, b1.getPosition().getY());
        assertEquals("b2 pos X after tick", -1, b2.getPosition().getX());
        assertEquals("b2 pos Y after tick", 0, b2.getPosition().getY());
        
        b2.tick();
        assertEquals("b2 pos X after tick", -1, b2.getPosition().getX());
        assertEquals("b2 pos Y after tick", 0, b2.getPosition().getY());

        b1.tick();
        assertEquals("b1 pos X after tick", 2, b1.getPosition().getX());
        assertEquals("b1 pos Y after tick", 0, b1.getPosition().getY());
        assertEquals("b2 pos X after tick", -2, b2.getPosition().getX());
        assertEquals("b2 pos Y after tick", 0, b2.getPosition().getY());
    }
    
    @Test
    public void testMirrorWithBackAndForthPath() {
        Renderer r = TestFactory.renderer();
        BlobPath path = PathFactory.stationary();

        BlobIF b1 = BlobFactory.createBaseBlob(PositionFactory.origin(), PathFactory.backAndForth(1, 1), r);
        BlobIF b2 = BlobFactory.createBaseBlob(PositionFactory.origin(), path, r);
        
        b2.setPosition(PositionFactory.mirrorX(b1.getPosition()));
        
        b1.tick();
        assertEquals("b1 pos X after tick", 1, b1.getPosition().getX());
        assertEquals("b1 pos Y after tick", 0, b1.getPosition().getY());
        assertEquals("b2 pos X after tick", -1, b2.getPosition().getX());
        assertEquals("b2 pos Y after tick", 0, b2.getPosition().getY());

        b1.tick();
        assertEquals("b1 pos X after tick", 2, b1.getPosition().getX());
        assertEquals("b1 pos Y after tick", 0, b1.getPosition().getY());
        assertEquals("b2 pos X after tick", -2, b2.getPosition().getX());
        assertEquals("b2 pos Y after tick", 0, b2.getPosition().getY());

        b1.tick();
        assertEquals("b1 pos X after tick", 3, b1.getPosition().getX());
        assertEquals("b1 pos Y after tick", 0, b1.getPosition().getY());
        assertEquals("b2 pos X after tick", -3, b2.getPosition().getX());
        assertEquals("b2 pos Y after tick", 0, b2.getPosition().getY());

        b1.tick();
        assertEquals("b1 pos X after tick", 4, b1.getPosition().getX());
        assertEquals("b1 pos Y after tick", 0, b1.getPosition().getY());
        assertEquals("b2 pos X after tick", -4, b2.getPosition().getX());
        assertEquals("b2 pos Y after tick", 0, b2.getPosition().getY());

        b1.tick();
        b1.tick();
        assertEquals("b1 pos X after tick", 4, b1.getPosition().getX());
        assertEquals("b1 pos Y after tick", 0, b1.getPosition().getY());
        assertEquals("b2 pos X after tick", -4, b2.getPosition().getX());
        assertEquals("b2 pos Y after tick", 0, b2.getPosition().getY());

        b1.tick();
        assertEquals("b1 pos X after tick", 3, b1.getPosition().getX());
        assertEquals("b1 pos Y after tick", 0, b1.getPosition().getY());
        assertEquals("b2 pos X after tick", -3, b2.getPosition().getX());
        assertEquals("b2 pos Y after tick", 0, b2.getPosition().getY());

        b1.tick();
        assertEquals("b1 pos X after tick", 2, b1.getPosition().getX());
        assertEquals("b1 pos Y after tick", 0, b1.getPosition().getY());
        assertEquals("b2 pos X after tick", -2, b2.getPosition().getX());
        assertEquals("b2 pos Y after tick", 0, b2.getPosition().getY());
       
    }
}
