package com.github.adsgray.gdxtry1.game;

import java.util.Random;
import java.util.Vector;

import android.util.Log;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.*;
import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTransform;
import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.output.RenderConfig;
import com.github.adsgray.gdxtry1.output.RenderConfig.CircleConfig;

public class GameFactory {
    public static WorldIF defaultWorld() {
        return new World();
    }
    
    public static RenderConfig defaultGDXRender() {
        return new RenderConfig();
    }
    
    public static Random rnd = new Random();
   
    static private Color randomColor() {
        return new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
    }
  
    // this should be a property of world?
    private static final int BOUNDS_X = 800;
    private static final int BOUNDS_Y = 1422;

    public static PositionIF randomPosition() {
        return new BlobPosition(rnd.nextInt(BOUNDS_X), rnd.nextInt(BOUNDS_Y));
        //return new BlobPosition(0,0);
    }
    
    private static final int MIN_MASS = 15;
    private static final int MAX_MASS = 100;

    public static int randomMass() {
        return rnd.nextInt(MAX_MASS) + MIN_MASS;
    }
    
    private static final int MIN_VEL = 1;
    private static final int MAX_VEL = 4;

    public static VelocityIF randomVelocity() {
         BlobVelocity vel = new BlobVelocity(rnd.nextInt(MAX_VEL * 2) - MAX_VEL + MIN_VEL,
                     rnd.nextInt(MAX_VEL * 2) - MAX_VEL + MIN_VEL);
         return vel;
    }
    
    public static AccelIF randomAccel() {
        // more Weirds
        if (rnd.nextInt(100) < 25) {
            return randomLinearAccel();
        } else {
            return randomWeirdAccel();
        }
    }
    
    public static AccelIF randomLinearAccel() {
        return new LinearAccel(rnd.nextInt(6) - 3, rnd.nextInt(6) - 3);
        //return new LinearAccel(0,0);
    }
    
    public static AccelIF randomWeirdAccel() {
        return WeirdAccel.randomWeirdAccel();
    }
    
    private static final int MIN_W = 50;
    private static final int MAX_W = 150;
    private static final int MIN_H = MIN_W;
    private static final int MAX_H = MAX_W;

    public static ExtentIF randomExtent() {
        return new RectangleExtent(rnd.nextInt(MAX_W) + MIN_W, rnd.nextInt(MAX_H) + MIN_H);
    }
    
 

    static final int[] a = new int[]{ 100,200 };
 
    
    public static WorldIF populateWorldWithBlobs(WorldIF inWorld, int howMany, RenderConfig r) {

        while (howMany > 0) {
            BlobFactory.createDefaultBlob(inWorld, r);
            howMany -= 1;
        }
        
        return inWorld;
    }

    public static VelocityIF zeroVelocity() {
        return new BlobVelocity(0,0);
    }
    
    public static AccelIF zeroAccel() {
        return new LinearAccel(0, 0);
    }

    public static WorldIF populateWorldNonRandom(WorldIF inWorld, RenderConfig r) {
        AccelIF a = new LinearAccel(0, 0);

        BlobPath p = PathFactory.upperTriangle(5, 2);

        //BlobPath p = jigglePath(10);
        //BlobPath p = PathFactory.squarePath(5, 5);
        //BlobPath p = trianglePath(4, 4);

        //BlobPath p = PathFactory.backAndForth(10, 1);
        //BlobPath p = PathFactory.upAndDown(10, 1);

        //BlobIF b1 = new RectangleBlob(randomMass(), new BlobPosition(400, 400), zeroVelocity(), a, r);
        //BlobIF b1 = new RectangleBlob(randomMass(), new BlobPosition(400, 400), new BlobVelocity(100,0), a, r);
        BlobIF b1 = new RectangleBlob(randomMass(), new BlobPosition(400, 400), p.vel, p.acc, r);
        //BlobIF b1 = new RectangleBlob(randomMass(), new BlobPosition(100, 100), p.vel, p.acc, r);
        //BlobIF b1 = new RectangleBlob(randomMass(), new BlobPosition(rnd.nextInt(600), rnd.nextInt(600)), p.vel, p.acc, r);
        //BlobIF b1 = new ThrobbingCircleBlob(randomMass(), new BlobPosition(rnd.nextInt(1024), rnd.nextInt(1024)), p.vel, p.acc, r);
        //BlobIF b2 = new CircleBlob(randomMass(), new BlobPosition(300,300), zeroVelocity(), new AccelRandomDecorator(a), r, smokeTrail);
        //BlobIF b2 = createSmokeTrailBlob(b1);
        
        //BlobIF ex = new ExplosionBlob(randomMass(), new BlobPosition(rnd.nextInt(500) + 200, rnd.nextInt(500) + 200), zeroVelocity(), a, r);
        ExplosionBlob ex = new ExplosionBlob(randomMass(), new BlobPosition(rnd.nextInt(500) + 200, rnd.nextInt(500) + 200), zeroVelocity(), a, r);
        ex.setBlobSource(BlobFactory.explosionBlobSource);
        ex.setWorld(inWorld);
        inWorld.addBlobToWorld(ex);

        b1.setWorld(inWorld);
        b1.setLifeTime(10000000);
        //inWorld.addBlobToWorld(new BlobTrailDecorator(b1, 2, 18));
        inWorld.addBlobToWorld(b1);
        //b2.setWorld(inWorld);
        //inWorld.addBlobToWorld(b2);

        return inWorld;
    }
    
    public static WorldIF populateWorldNonRandomBlobSet(WorldIF inWorld, RenderConfig r) {

        PositionIF p1 = new BlobPosition(400,400);
        PositionIF p2 = new BlobPosition(450,450);
        PositionIF p3 = new BlobPosition(400,400);

        // will have to compose velocities too...
        BlobIF b1 = new RectangleBlob(10, p1, zeroVelocity(), new LinearAccel(0, 0), r);
        b1.setWorld(inWorld);
        BlobIF b2 = new CircleBlob(10, p2, zeroVelocity(), new LinearAccel(0, 0), r);
        b2.setWorld(inWorld);
        
        //inWorld.addBlobToWorld(b1);
        //inWorld.addBlobToWorld(b2);

        //BlobPath bp = jigglePath(7);
        //BlobPath bp = PathFactory.squarePathClockwise(15, 1);
        //BlobPath bp = trianglePath(7,7);
        BlobPath bp = PathFactory.squarePath(rnd.nextInt(5) + 5,rnd.nextInt(3) + 2);
        //BlobPath bp = PathFactory.backAndForth(8, 4);
        //BlobSet bs = new BlobSet(10, p3, new BlobVelocity(1, 1), new LinearAccel(0, 0), r);
        BlobSet bs = new BlobSet(10, p3, bp.vel, bp.acc, r);
        //BlobSet bs = new BlobSet(10, p3, new BlobVelocity(10, 10), WeirdAccel.randomWeirdAccel(), r);
        bs.setWorld(inWorld);
        
        BlobTransform bt = new BlobTransform() {
            @Override
            public BlobIF transform(BlobIF b) {
                // wierd: if this is set to 5 (and bp above is 7) then vel/acc don't compose properly??
                //BlobPath bp = jigglePath(rnd.nextInt(8));
                //BlobPath bp = new BlobPath(zeroVelocity(), new LinearAccel(0, 0));
                BlobPath bp = PathFactory.upperTriangle(rnd.nextInt(4) + 2,rnd.nextInt(2) + 1);
                b.setAccel(bp.acc);
                b.setVelocity(bp.vel);
                b.setLifeTime(100000);
                //b = new BlobTrailDecorator(b);
                return b;
            }

        };
        
        bs.absorbBlob(b1, bt);
        bs.absorbBlob(b2, bt);
        bs.setLifeTime(1000000);
        
        BlobIF bstrail = new BlobTrailDecorator(bs, BlobFactory.smokeTrailBlobSource);
        inWorld.addBlobToWorld(bstrail);
        //inWorld.addBlobToWorld(bs);

        return inWorld;
    }
 
    private static BlobIF createLaunchUpBlob(WorldIF inWorld, RenderConfig r) {
        BlobPath launch = PathFactory.launchUp();
        //BlobIF b = new CircleBlob(0, new BlobPosition(rnd.nextInt(400) + 100, 0), launch.vel, launch.acc, r);
        //inWorld.addBlobToWorld(BlobFactory.throbber(ooze));
        BlobIF b = BlobFactory.throbber(BlobFactory.createOozeBlob(inWorld, r));
        //BlobIF b = BlobFactory.throbber(BlobFactory.createPrizeBlob(inWorld, r));
        b = BlobFactory.rainbowColorCycler(b, 2);
        b.setPosition(new BlobPosition(rnd.nextInt(400) + 100, 0));
        b.setPath(launch);
        
        b.setWorld(inWorld);
        b.setLifeTime(200);
        b = new BlobTrailDecorator(b, BlobFactory.smokeTrailBlobSource);
        b.setTickPause(rnd.nextInt(15));
        inWorld.addBlobToWorld(b);
        
        BlobTrigger bt = new BlobTrigger(r) {
            private int count = 0;
            @Override
            public BlobIF trigger(BlobIF source, BlobIF unused) {
                if (count == 2) {
                    source.getWorld().removeBlobFromWorld(source);
                    PositionIF sourcePos = source.getPosition();
                    sourcePos.setY(sourcePos.getY() + 75);
                    ExplosionBlob ex = new ExplosionBlob(randomMass(), sourcePos, zeroVelocity(), zeroAccel(), renderConfig);
                    ex.setBlobSource(BlobFactory.explosionBlobSource);
                    ex.setWorld(source.getWorld());
                    source.getWorld().addBlobToWorld(ex);
                    return source;
                } else {
                    count += 1;
                    source.setPath(PathFactory.launchUp());
                    return source;
                }
            }
        };
        
        // register so that when y==0 in b.position the trigger will fire
        b.getPosition().registerAxisTrigger(PositionIF.Axis.Y, 0, bt);
        
        return b;
    }
   
    
    public static WorldIF populateWorldLaunchUp(WorldIF inWorld, RenderConfig r) {
        int num = 8;
        while (num > 0) {
            createLaunchUpBlob(inWorld, r);
            num--;
        }

        return inWorld;
    }
    
    private static BlobIF randomSpinnerBlob(WorldIF inWorld, RenderConfig r) {
        if (rnd.nextInt(100) < 50) {
            return BlobFactory.createOozeBlob(inWorld, r);
        } else {
            return BlobFactory.createPrizeBlob(inWorld, r);
        }
    }

    public static WorldIF populateWorldOoze(WorldIF inWorld, RenderConfig r) {
        BlobIF ooze = randomSpinnerBlob(inWorld, r);
        //BlobIF ooze = BlobFactory.createPrizeBlob(inWorld, r);
        //BlobPath p = PathFactory.jigglePath(10);

        //BlobPath p = PathFactory.upperTriangle(5, 3);
        BlobPath p = PathFactory.backAndForth(10, 5);
        ooze.setPath(p);

        //inWorld.addBlobToWorld(new BlobIgnoreTickDecorator(BlobFactory.throbber(ooze), rnd.nextInt(2) + 1));
        //inWorld.addBlobToWorld(BlobFactory.throbber(ooze));
        //ooze = BlobFactory.throbber(ooze);
        //ooze = BlobFactory.rainbowColorCycler(ooze, 1);
        
        ooze = BlobFactory.flashColorCycler(ooze, 1);
        ooze = BlobFactory.rainbowColorCycler(ooze, 1);
        ooze = BlobFactory.throbber(ooze);
        inWorld.addBlobToWorld(ooze);
        return inWorld;
    }
    
    
    public static WorldIF populateWorldCollisionTest(WorldIF inWorld, RenderConfig r) {

        PositionIF p1 = new BlobPosition(0,500 - rnd.nextInt(20));
        BlobVelocity v1 = new BlobVelocity(10 + rnd.nextInt(5),0);
        CircleConfig c1 = new CircleConfig(randomColor(), 30);
        BlobIF b1 = new CircleBlob(0, p1, v1, zeroAccel(), r, c1);
        //b1.setWorld(inWorld);
        b1.setLifeTime(100000);
        b1.setTickPause(100);
        b1 = new ShowExtentDecorator(b1);

        PositionIF p2 = new BlobPosition(BOUNDS_X,500 + rnd.nextInt(20));
        BlobVelocity v2 = new BlobVelocity(-10 - rnd.nextInt(5),0);
        CircleConfig c2 = new CircleConfig(randomColor(), 30);
        BlobIF b2 = new CircleBlob(0, p2, v2, zeroAccel(), r, c2);
        //b2 = BlobFactory.flashColorCycler(b2, 10);
        b2.setLifeTime(100000);
        //b2.setWorld(inWorld);
        b2.setTickPause(100);
        b2 = new ShowExtentDecorator(b2);
        
        // if we wanted to have the trigger generate more blobs we'd
        // have to call its constructor with (r)
        // then have access to renderConfig inside the trigger.
        BlobTrigger missileTrigger = new BlobTrigger() {
            @Override
            public BlobIF trigger(BlobIF source, BlobIF secondary) {

                WorldIF w = source.getWorld();
                RenderConfig r = source.getRenderer();
                //source.setVelocity(GameFactory.zeroVelocity());
                source.setVelocity(new BlobVelocity(-1, 5));

                // removing and adding turns source from a missile into a normal/ephemeral blob
                w.removeBlobFromWorld(source);
                source.setLifeTime(100);
                source.setAccel(new LinearAccel(0, -1));
                source = BlobFactory.flashColorCycler(source, 10);
                w.addBlobToWorld(source);
                
                TriggerFactory.replaceWithExplosion(secondary);

                // haha important:
                // without this the source blob was scheduled for removal
                // every tick... and repeatedly added to world...
                //source.deregisterCollisionTrigger(this);
                
                // always return the transformed version of source so that
                // triggers/transformations can be properly chained!
                return source;
            }
        };

        // b1 is a missile and its trigger will handle destroying the target/secondary.
        // so no need to register a collision trigger on the target (b2)
        b1.registerCollisionTrigger(missileTrigger);

        inWorld.addMissileToWorld(b1);
        inWorld.addTargetToWorld(b2);

        return inWorld;
    }
    
    public static WorldIF populateWorldGameTestOne(WorldIF inWorld, RenderConfig r) {
        return inWorld;
    }
    
}
