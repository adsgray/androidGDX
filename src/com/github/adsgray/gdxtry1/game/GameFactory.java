package com.github.adsgray.gdxtry1.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.*;
import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.accel.LinearAccel;
import com.github.adsgray.gdxtry1.engine.accel.WeirdAccel;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.blob.CircleBlob;
import com.github.adsgray.gdxtry1.engine.blob.ExplosionBlob;
import com.github.adsgray.gdxtry1.engine.blob.NullBlob;
import com.github.adsgray.gdxtry1.engine.blob.RectangleBlob;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobSource;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTransform;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.blob.TriangleBlob;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobTrailDecorator;
import com.github.adsgray.gdxtry1.engine.blob.decorator.ShowExtentDecorator;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.extent.RectangleExtent;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.output.RenderConfig;
import com.github.adsgray.gdxtry1.output.RenderConfig.CircleConfig;
import com.github.adsgray.gdxtry1.output.RenderConfig.RectConfig;

public class GameFactory {
    public static WorldIF defaultWorld() {
        return new World();
    }
    
    public static RenderConfig defaultGDXRender() {
        return new RenderConfig();
    }
    
    public static Random rnd = new Random();
   
    static public Color randomColor() {
        return new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
    }
  
    // this should be a property of world?
    private static final int BOUNDS_X = 800;
    private static final int BOUNDS_Y = 1422;

    public static PositionIF randomPosition(int minX, int maxX, int minY, int maxY) {
        return new BlobPosition(rnd.nextInt(maxX - minX) + minX, rnd.nextInt(maxY - minY) + minY);
    }
    public static PositionIF randomPosition() {
        return randomPosition(0, BOUNDS_X, 0, BOUNDS_Y);
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
        BlobIF b = new CircleBlob(0, new BlobPosition(rnd.nextInt(400) + 100, 0), launch.vel, launch.acc, r);
        //inWorld.addBlobToWorld(BlobFactory.throbber(ooze));
        //BlobIF b = BlobFactory.throbber(BlobFactory.createOozeBlob(inWorld, r));
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
                    ExplosionBlob ex = new ExplosionBlob(randomMass(), sourcePos, zeroVelocity(), AccelFactory.zeroAccel(), renderConfig);
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

        //BlobPath p = PathFactory.upperTriangle(4, 2);
        //BlobPath p = PathFactory.stationary();
        //BlobPath p = PathFactory.backAndForth(5, 2);
        BlobPath p = PathFactory.upAndDown(5, 1);
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
        BlobIF b1 = new CircleBlob(0, p1, v1, AccelFactory.zeroAccel(), r, c1);
        //b1.setWorld(inWorld);
        b1.setLifeTime(100000);
        b1.setTickPause(100);
        b1 = new ShowExtentDecorator(b1);

        PositionIF p2 = new BlobPosition(BOUNDS_X,500 + rnd.nextInt(20));
        BlobVelocity v2 = new BlobVelocity(-10 - rnd.nextInt(5),0);
        CircleConfig c2 = new CircleConfig(randomColor(), 30);
        BlobIF b2 = new CircleBlob(0, p2, v2, AccelFactory.zeroAccel(), r, c2);
        //b2 = BlobFactory.flashColorCycler(b2, 10);
        b2.setLifeTime(100000);
        //b2.setWorld(inWorld);
        b2.setTickPause(100);
        b2 = new ShowExtentDecorator(b2);
        
        // if we wanted to have the trigger generate more blobs we'd
        // have to call its constructor with (r)
        // then have access to renderConfig inside the trigger.
        
        // special transformation to
        // 1. stop blob
        // 2. replace it with a "flashin" version of itself
        // 3. make it fall downward
        BlobTransform primaryTransform = new BlobTransform() {
            @Override public BlobIF transform(BlobIF b) {
                WorldIF w = b.getWorld();
                b.setVelocity(new BlobVelocity(-1, 5));
                w.removeBlobFromWorld(b);
                b.setLifeTime(100);
                b.setAccel(new LinearAccel(0, -1));
                b = BlobFactory.flashColorCycler(b, 10);
                w.addBlobToWorld(b);
                return b;
            }
        };
        
        BlobTransform secondaryTransform = TriggerFactory.transformReplaceWithExplosion();

        // b1 is a missile and its trigger will handle destroying the target/secondary.
        // so no need to register a collision trigger on the target (b2)
        // Use TriggerFactory to define what happens to primary and secondary separately.
        // Not sure if this is worth it.
        b1.registerCollisionTrigger(TriggerFactory.primaryTransformTrigger(primaryTransform));
        b1.registerCollisionTrigger(TriggerFactory.secondaryTransformTrigger(secondaryTransform));

        inWorld.addMissileToWorld(b1);
        inWorld.addTargetToWorld(b2);

        return inWorld;
    }
    

    public static WorldIF populateWorldTestTriggers(WorldIF w, RenderConfig r) {
        // turn the blob into an explosion
        BlobTrigger explosion = new BlobTrigger() {
            @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
                BlobIF b = TriggerFactory.replaceWithExplosion(source);
                b.registerTickDeathTrigger(chainTrigger);
                return b;
            }
        };
       
        // regenerate a blob at the same position...
        BlobTrigger rebirth = new BlobTrigger() {
            @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
                WorldIF w = source.getWorld();
                RenderConfig r = source.getRenderer();
                BlobIF b1 = BlobFactory.createOozeBlob(w, r);
                b1.setPosition(source.getPosition());
                b1.registerTickDeathTrigger(chainTrigger);
                b1.setWorld(w);
                b1.setLifeTime(200);
                w.addBlobToWorld(b1);
                return b1;
            }
        };
        
        rebirth.setChainTrigger(explosion);
        explosion.setChainTrigger(rebirth);


        BlobIF b1 = BlobFactory.createOozeBlob(w, r);
        b1.setPosition(new BlobPosition(400,400));
        b1.setLifeTime(200);
        b1.registerTickDeathTrigger(explosion);
        b1.setWorld(w);
        w.addBlobToWorld(b1);

        return w;
    }

    public static WorldIF populateWorldTestTriggersAgain(WorldIF w, RenderConfig r) {

        // replace the incoming blob with an OozeBlob
        BlobTransform rebirth = new BlobTransform() {
            @Override
            public BlobIF transform(BlobIF b) {
                WorldIF w = b.getWorld();
                RenderConfig r = b.getRenderer();
                BlobIF b1 = BlobFactory.createOozeBlob(w, r);
                b1.setPosition(b.getPosition());
                b1.setWorld(w);
                b1.setLifeTime(200);
                w.addBlobToWorld(b1);
                return b1;
            }
        };
       

        BlobIF seed = new NullBlob(r);
        seed.setPosition(new BlobPosition(400,400));
        seed.setWorld(w);
        w.addBlobToWorld(seed);

        List<BlobTransform> sequence = new ArrayList<BlobTransform>();
        sequence.add(rebirth);
        sequence.add(TriggerFactory.transformReplaceWithExplosion());
        BlobTrigger trSequence = TriggerFactory.createTransformSequence(sequence, true);
        seed.registerTickDeathTrigger(trSequence);

        return w;   
    }
    
    private static BlobIF createTargetBlob(WorldIF w, RenderConfig r) {
        //BlobIF b = BlobFactory.createDefaultBlob(w, r);
        PositionIF p = new BlobPosition(50 + rnd.nextInt(GameFactory.BOUNDS_X - 100), GameFactory.BOUNDS_Y - 20 - rnd.nextInt(300));
        BlobIF b = new RectangleBlob(0, p, null, null, r);
        b.setPath(PathFactory.backAndForth(5, 4));
        b = BlobFactory.throbber(b);
        b.setWorld(w);
        b.setLifeTime(10000000);
        b.setTickPause(rnd.nextInt(10));
        w.addTargetToWorld(b);
        return b;
    }
    
    private static BlobIF createMissileBlob(WorldIF w, RenderConfig r) {
        // can't use BlobSets in collisions yet because they don't have extents...
        //BlobIF b1 = BlobFactory.createOozeBlob(w, r);
        PositionIF p = new BlobPosition(10 + rnd.nextInt(GameFactory.BOUNDS_X) - 5, 10);
        CircleConfig cc = new CircleConfig(Color.RED, 30);
        BlobIF b1 = new CircleBlob(0, p, GameFactory.zeroVelocity(), AccelFactory.zeroAccel(), r, cc);
        b1.setPath(PathFactory.launchUp(75, -2));
        // TODO: set position trigger for Y=0 which will kill this blob...
        b1.setLifeTime(1000);
        b1.setWorld(w);
        b1 = BlobFactory.rainbowColorCycler(b1, 3);
        // need cooler trail blob sources
        //b1 = new BlobTrailDecorator(b1, BlobFactory.smokeTrailBlobSource);
        b1 = BlobFactory.addAltSmokeTrail(b1);
        w.addMissileToWorld(b1);
        return b1;
    }

    public static WorldIF populateWorldGameTestOne(WorldIF w, RenderConfig r) {
        int numTargets = 15;
        while (w.getNumTargets() < 15 && numTargets > 0) {
            createTargetBlob(w, r);
            numTargets -= 1;
        }
    
        // regenerate a blob at the same position...
        BlobTrigger newmissile = new BlobTrigger() {
            @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
                WorldIF w = source.getWorld();
                RenderConfig r = source.getRenderer();

                BlobIF b1 = BlobFactory.createOozeBlob(w, r);
                b1.registerTickDeathTrigger(chainTrigger);
                b1.setWorld(w);
                b1.setLifeTime(200);
                w.addBlobToWorld(b1);
                return b1;
            }
        };
           
        BlobIF missile = createMissileBlob(w, r);

        BlobTrigger deactivateMissile = new BlobTrigger() {
            @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
                WorldIF w = source.getWorld();
                w.removeBlobFromWorld(source);
                source = BlobFactory.shrinker(source, 5);
                w.addBlobToWorld(source);
                // make it go up a bit then fall. reverse any lateral velocity (bounce back)
                source.setVelocity(new BlobVelocity(-source.getVelocity().getXVelocity(), 4));
                source.setAccel(new LinearAccel(0, -2));
                return source;
            }
        };
        
        BlobTrigger targetHit = new BlobTrigger() {
            @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
                secondary = TriggerFactory.replaceWithExplosion(secondary);
                secondary.setPath(PathFactory.squarePath(3, 3));
                return source;
            }
        };
        
       
        //BlobTransform secondaryTransform = TriggerFactory.transformReplaceWithExplosion();
        //missile.registerCollisionTrigger(TriggerFactory.secondaryTransformTrigger(secondaryTransform));
        missile.registerCollisionTrigger(deactivateMissile);
        missile.registerCollisionTrigger(targetHit);
 
        return w;
    }
    
   
    public static WorldIF populateWorldTestOffsetPosition(WorldIF w, RenderConfig r) {
        
        BlobTransform setupPath = new BlobTransform() {
            @Override
            public BlobIF transform(BlobIF b) {
                WorldIF w = b.getWorld();
                // transformations in these trigger chains must ALWAYS return a new blob
                BlobIF withPath = new NullBlob(b);
                withPath.setPath(PathFactory.backAndForth(10, 2));
                withPath.setLifeTime(0);
                w.addBlobToWorld(withPath);
                clusterSwap(withPath, b);
                return withPath;
            }
        };

        BlobTransform rectangleTransform = new BlobTransform() {
            @Override public BlobIF transform(BlobIF parent) {
                WorldIF w = parent.getWorld();
                RenderConfig r = parent.getRenderer();
                RectConfig rc = new RectConfig(GameFactory.randomColor(), 30,30);

                BlobPath p = PathFactory.stationary();
                //BlobPath p = new BlobPath(parent.getVelocity(), parent.getAccel());
                BlobIF b2 = new RectangleBlob(0, null, null, null, r, rc);
                b2.setPath(p);
                b2.setWorld(w);
                b2.setLifeTime(rnd.nextInt(100) + 200);
                b2.setPosition(parent.getPosition());
                if (rnd.nextInt(100) < 50) {
                    //b2 = BlobFactory.throbber(b2);
                    b2 = BlobFactory.addAltSmokeTrail(b2);
                }

                w.addBlobToWorld(b2); 
                clusterSwap(b2, parent);
                return b2;
            }
        };
        
        BlobTransform oozeTransform = new BlobTransform() {
             @Override public BlobIF transform(BlobIF parent) {
                WorldIF w = parent.getWorld();
                RenderConfig r = parent.getRenderer();
                
                BlobIF ooze = BlobFactory.createOozeBlob(w, r);
                ooze.setPosition(parent.getPosition());
                BlobPath p = PathFactory.stationary();
                //ooze.setPath(new BlobPath(parent.getVelocity(), parent.getAccel()));
                ooze.setPath(p);

                // test if these are necessary
                // not in this case as createOozeBlob inits them as this:
                //ooze.setVelocity(GameFactory.zeroVelocity());
                //ooze.setAccel(GameFactory.zeroAccel());

                ooze.setWorld(w);
                ooze.setLifeTime(rnd.nextInt(100) + 200);
                w.addBlobToWorld(ooze); 
                
                clusterSwap(ooze, parent);
                return ooze;
            }           
        };
        
        // The idea here is to create a cluster of blobs that cycle between
        // the "states" defined by the BlobTransforms defined above.
        // 1. define transforms that maintain the source position (and set a relatively short lifetime)
        // 2. create a trigger loop using TriggerFactory.createTransformSequence
        // 3. create a nullBlobSource that assigns that trigger loop to each created blob's
        //    tickDeathTrigger

        // must find a clean way to have setupPath done only once when blob created
        // likely have to create a blobsource...
        List<BlobTransform> trlist = new ArrayList<BlobTransform>();
        trlist.add(oozeTransform);
        trlist.add(rectangleTransform);
        BlobTrigger transformCycle = TriggerFactory.createTransformSequence(trlist, true);
        

        //PositionIF pos = new BlobPosition(400,400);
        PositionIF pos = GameFactory.randomPosition();
        BlobPath path = PathFactory.squarePath(rnd.nextInt(5) + 10, rnd.nextInt(3) + 7);
        BlobSource bs = BlobFactory.nullBlobSource(transformCycle, setupPath);
        
        int dist = rnd.nextInt(50) + 25;
        if (rnd.nextInt(100) < 50) dist = -dist;
        //BlobIF cluster = BlobFactory.createFourCluster(pos, path, bs, dist, w, r);
        BlobIF cluster = BlobFactory.createNineCluster(pos, path, bs, dist, w, r);
        //BlobIF cluster = BlobFactory.createThreeCluster(pos, path, bs, dist, w, r);
        cluster.setLifeTime(1000000);
        cluster = BlobFactory.throbber(cluster);
        cluster = BlobFactory.rainbowColorCycler(cluster, 10);
        w.addBlobToWorld(cluster);
        cluster.setLifeTime(100000);
 
        return w;
    }
    
    private static BlobIF createFallingBall(RenderConfig r) {
        PositionIF p = randomPosition(10,400,500,800);
        CircleConfig cc = new CircleConfig(randomColor(), 25);
        BlobIF b = new CircleBlob(0, randomPosition(10,400,500,800), new BlobVelocity(5,0), new LinearAccel(0,-1), r, cc);
        //b = BlobFactory.addAltSmokeTrail(b);
        b = BlobFactory.addTriangleSmokeTrail(b);
        return b;
    }
    
    public static WorldIF populateWorldTestBumpAccel(WorldIF w, RenderConfig r) {
        BlobTrigger bottom = new BlobTrigger() {
            // this will be used as an Axis trigger so secondary is empty
            @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
                AccelFactory.bump(source, AccelFactory.up(source), 8);
                source.setLifeTime(100000);
                return source;
            }
        };
        
        BlobIF b = createFallingBall(r);
        // Axis Trigger doesn't work because the Blob's Y position is never exacly
        // 0, it just flys from +N to -M in one velocity step...
        // how to fix position triggers?
        //b.getPosition().registerAxisTrigger(PositionIF.Axis.Y, 0, bottom);
        b.registerTickDeathTrigger(bottom);
        b.setLifeTime(15);
        b.setWorld(w);
        w.addBlobToWorld(b);

        return w;
    }

    public static WorldIF populateWorldTestTriangle(WorldIF w, RenderConfig r) {
        CircleConfig cc = new CircleConfig(randomColor(), 100);
        BlobIF b = new TriangleBlob(0, randomPosition(100,600,100,800), zeroVelocity(), AccelFactory.zeroAccel(), r, cc);
        b = new ShowExtentDecorator(b);
        b.setLifeTime(1000000);
        b.setWorld(w);
        w.addBlobToWorld(b);
        return w;
    }

    public static WorldIF populateWorldTestMultiplyPosition(WorldIF w, RenderConfig r) {
        BlobIF set = BlobFactory.createTestBlobSet2(w,r);

        BlobIF b1 = BlobFactory.createTestBlob(w, r, null);
        b1.setPosition(PositionFactory.origin());
        b1.setPath(PathFactory.backAndForth(5, 5));
        /*
        b1.setPath(PathFactory.stationary());
        b1.getPosition().setX(100);
        b1.getPosition().setY(100);
        b1.setVelocity(new BlobVelocity(1,1));
        */
        
        BlobIF b2 = BlobFactory.createTestBlob(w,r,null);
        b2.setPosition(new BlobPosition(-10,-10)); // doesn't matter what this is as it will be clobbered
        b2.setPath(PathFactory.stationary());
        //b2.setPath(PathFactory.backAndForth(5, 5));

        b2.setPosition(PositionFactory.mirrorX(b1.getPosition()));
        set.absorbBlob(b1);
        set.absorbBlob(b2);

        //PositionFactory.multiplyPosition(b2, b1.getPosition(), new BlobPosition(2,1));


        //set.setPath(PathFactory.backAndForth(1, 5));
        

        return w;
    }
    
}
