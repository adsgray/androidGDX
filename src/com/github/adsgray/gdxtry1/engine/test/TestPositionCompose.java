package com.github.adsgray.gdxtry1.engine.test;


import org.junit.*;

import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionComposeDecorator;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

import static org.junit.Assert.*;

    
public class TestPositionCompose {

    @Before
    public void setUp() throws Exception {
    }

    
    @Test
    public void testSimpleComposition() {
        PositionIF comp = new BlobPosition(1,1);
        PositionIF prim = new BlobPosition(2,2);
        PositionIF p = new PositionComposeDecorator(comp, prim);
        
        assertEquals("composed X pos", 3, p.getX());
        assertEquals("composed Y pos", 3, p.getY());
    }
    
    @Test 
    public void testComposedVel() {
        PositionIF comp = new BlobPosition(1,1);
        PositionIF prim = new BlobPosition(2,2);
        PositionIF p = new PositionComposeDecorator(comp, prim);
        
        VelocityIF v = TestFactory.velocity1dash1();
        // updates "primary" position which is p2:
        p.updateByVelocity(v);
        // should be equivalent to:
        //p2.updateByVelocity(v);

        // should remain unchanged
        assertEquals("component X after compose vel", 1, comp.getX());
        assertEquals("component Y after compose vel", 1, comp.getY());

        // should be updated
        assertEquals("primary X after compose vel", 3, prim.getX());
        assertEquals("primary Y after compose vel", 3, prim.getY());

        // should reflect update of primary
        assertEquals("composed X after compose vel", 4, p.getX());
        assertEquals("composed Y after compose vel", 4, p.getY());
    }
    
    @Test 
    public void testComponentVel() {
        PositionIF comp = new BlobPosition(1,1);
        PositionIF prim = new BlobPosition(2,2);
        PositionIF p = new PositionComposeDecorator(comp, prim);
        
        VelocityIF v = TestFactory.velocity1dash1();
        comp.updateByVelocity(v);

        assertEquals("component X after compose vel", 2, comp.getX());
        assertEquals("component Y after compose vel", 2, comp.getY());

        // should be unchanged
        assertEquals("primary X after compose vel", 2, prim.getX());
        assertEquals("primary Y after compose vel", 2, prim.getY());

        assertEquals("composed X after compose vel", 4, p.getX());
        assertEquals("composed Y after compose vel", 4, p.getY());       
    }
     
    @Test 
    public void testPrimaryVel() {
        PositionIF comp = new BlobPosition(1,1);
        PositionIF prim = new BlobPosition(2,2);
        PositionIF p = new PositionComposeDecorator(comp, prim);
        
        VelocityIF v = TestFactory.velocity1dash1();
        // should be same effect as p3.update...
        prim.updateByVelocity(v);

        assertEquals("component X after compose vel", 1, comp.getX());
        assertEquals("component Y after compose vel", 1, comp.getY());

        assertEquals("primary X after compose vel", 3, prim.getX());
        assertEquals("primary Y after compose vel", 3, prim.getY());

        assertEquals("composed X after compose vel", 4, p.getX());
        assertEquals("composed Y after compose vel", 4, p.getY());       
    }
    
    @Test
    public void testNestedCompose() {
        // getting complicated now.
        // trying to simulate the situation of comp being in a cluster
        // and p's position is composed from it
        PositionIF comp = new BlobPosition(1,1);
        PositionIF prim = new BlobPosition(-2,-2);
        PositionIF cluster = new BlobPosition(10,10);

        PositionIF comp_in_cluster = new PositionComposeDecorator(cluster, comp);
        PositionIF p = new PositionComposeDecorator(comp_in_cluster,prim);
        
        
        // now where is p?
        // comp_in_cluster should be at (11,11)
        // and p should be at (9,9)

        assertEquals("comp_in_cluster X", 11, comp_in_cluster.getX());
        assertEquals("comp_in_cluster Y", 11, comp_in_cluster.getY());

        assertEquals("p X", 9, p.getX());
        assertEquals("p Y", 9, p.getY());
    }
 
    @Test
    public void testNestedComposeClusterVel() {
        // getting complicated now.
        // trying to simulate the situation of comp being in a cluster
        // and p's position is composed from it
        PositionIF comp = new BlobPosition(1,1);
        PositionIF prim = new BlobPosition(-2,-2);
        PositionIF cluster = new BlobPosition(10,10);

        PositionIF comp_in_cluster = new PositionComposeDecorator(cluster, comp);
        PositionIF p = new PositionComposeDecorator(comp_in_cluster,prim);
        
        
        // now where is p?
        // comp_in_cluster should be at (11,11)
        // and p should be at (9,9)

        assertEquals("comp_in_cluster X", 11, comp_in_cluster.getX());
        assertEquals("comp_in_cluster Y", 11, comp_in_cluster.getY());

        assertEquals("p X", 9, p.getX());
        assertEquals("p Y", 9, p.getY());
        
        VelocityIF v = TestFactory.velocity1dash1();
        cluster.updateByVelocity(v);

        // cluster moved:
        assertEquals("cluster X afer vel", 11, cluster.getX());
        assertEquals("cluster Y afer vel", 11, cluster.getY());
        
        // comp_in_cluster moved too
        assertEquals("comp_in_cluster X after cluster vel", 12, comp_in_cluster.getX());
        assertEquals("comp_in_cluster Y after cluster vel", 12, comp_in_cluster.getY());
        
        // so did p
        assertEquals("p X after cluster vel", 10, p.getX());
        assertEquals("p Y after cluster vel", 10, p.getY());
    }
 
    @Test
    public void testNestedComposeCompVel() {
        // getting complicated now.
        // trying to simulate the situation of comp being in a cluster
        // and p's position is composed from it
        PositionIF comp = new BlobPosition(1,1);
        PositionIF prim = new BlobPosition(-2,-2);
        PositionIF cluster = new BlobPosition(10,10);

        PositionIF comp_in_cluster = new PositionComposeDecorator(cluster, comp);
        PositionIF p = new PositionComposeDecorator(comp_in_cluster,prim);
        
        
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

}
