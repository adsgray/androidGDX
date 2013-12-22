package com.github.adsgray.gdxtry1.game;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.*;
import com.github.adsgray.gdxtry1.engine.BlobIF.BlobTransform;
import com.github.adsgray.gdxtry1.output.RenderConfig;
import com.github.adsgray.gdxtry1.output.RenderConfig.CircleConfig;

public class GameFactory {
    public static WorldIF defaultWorld() {
        return new World();
    }
    
    public static RenderConfig defaultGDXRender() {
        return new RenderConfig();
    }
    
    private static Random rnd = new Random();
    
    // this should be a property of world?
    private static final int BOUNDS_X = 480;
    private static final int BOUNDS_Y = 800;

    private static PositionIF randomPosition() {
        return new BlobPosition(rnd.nextInt(BOUNDS_X), rnd.nextInt(BOUNDS_Y));
        //return new BlobPosition(0,0);
    }
    
    private static final int MIN_MASS = 15;
    private static final int MAX_MASS = 100;

    private static int randomMass() {
        return rnd.nextInt(MAX_MASS) + MIN_MASS;
    }
    
    private static final int MIN_VEL = 1;
    private static final int MAX_VEL = 4;

    private static VelocityIF randomVelocity() {
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
    
    private static AccelIF randomLinearAccel() {
        return new LinearAccel(rnd.nextInt(6) - 3, rnd.nextInt(6) - 3);
        //return new LinearAccel(0,0);
    }
    
    private static AccelIF randomWeirdAccel() {
        return WeirdAccel.randomWeirdAccel();
    }
    
    private static final int MIN_W = 50;
    private static final int MAX_W = 150;
    private static final int MIN_H = MIN_W;
    private static final int MAX_H = MAX_W;

    public static ExtentIF randomExtent() {
        return new RectangleExtent(rnd.nextInt(MAX_W) + MIN_W, rnd.nextInt(MAX_H) + MIN_H);
    }
    

    static BlobIF createDefaultBlob(WorldIF inWorld, RenderConfig r) {
        BlobIF b;
        if (rnd.nextInt(100) < 50) {
            //b = new RectangleBlob(randomMass(), randomPosition(), randomVelocity(), randomAccel(), r);
            b = new SplittingRectangleBlob(randomMass(), randomPosition(), randomVelocity(), randomAccel(), r);
        } else {
            b = new ShrinkingCircleBlob(randomMass(), randomPosition(), randomVelocity(), randomAccel(), r);
        }
        
        b.setWorld(inWorld);
        b.setExtent(randomExtent());

        if (rnd.nextInt(100) < 10) {
            b = new BlobTrailDecorator(b);
        }

        // possibly stack some decorators:
        if (rnd.nextInt(100) < 50) {
            b = new BlobCrazyAccelDecorator(b);
        }

        inWorld.addBlobToWorld(b);
        return b;
    }
    

    static final int[] a = new int[]{ 100,200 };

    static Color[] colors = new Color[] {
        Color.RED,Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN,
        Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW,
        Color.WHITE
    };
    static private CircleConfig smokeTrail() {
        //return new CircleConfig(Color.GRAY, 7);
        //static Vector<Color> colors = new Vector<Color>();
        //int choice = rnd.nextInt(colors.length);
        //Color color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        return new CircleConfig(Color.GRAY, 7);
    }
    public static BlobIF createSmokeTrailBlob(BlobIF c) {
        BlobIF b = new ShrinkingCircleBlob(randomMass(), new BlobPosition(c.getPosition()), randomVelocity(),
                WeirdAccel.randomWeirdAccel(), c.getRenderer(), smokeTrail());
        b = new BlobCrazyAccelDecorator(b);
        b.setWorld(c.getWorld());
        return b;
    }
    
    static Color[] explosionColors = new Color[] {
        Color.RED, Color.ORANGE
    };
    static private CircleConfig explosionBlob() {
        Color color = explosionColors[rnd.nextInt(explosionColors.length)];
        return new CircleConfig(color, rnd.nextFloat() * 7 + 5);
    }
    public static BlobIF createExplosionBlob(BlobIF c) {
         BlobIF b = new ShrinkingCircleBlob(randomMass(), new BlobPosition(c.getPosition()), randomVelocity(),
                WeirdAccel.randomWeirdAccel(), c.getRenderer(), explosionBlob());
        b = new BlobCrazyAccelDecorator(b);
        b.setWorld(c.getWorld());
        return b;       
    }
    
    
    public static WorldIF populateWorldWithBlobs(WorldIF inWorld, int howMany, RenderConfig r) {

        while (howMany > 0) {
            createDefaultBlob(inWorld, r);
            howMany -= 1;
        }
        
        return inWorld;
    }

    private static VelocityIF zeroVelocity() {
        VelocityIF v = new BlobVelocity(0,0);
        return v;
    }
    public static WorldIF populateWorldNonRandom(WorldIF inWorld, RenderConfig r) {
        AccelIF a = new LinearAccel(0, 0);

        //BlobPath p = jigglePath(10);
        BlobPath p = squarePath(5, 5);
        //BlobIF b1 = new RectangleBlob(randomMass(), new BlobPosition(400, 400), zeroVelocity(), a, r);
        //BlobIF b1 = new RectangleBlob(randomMass(), new BlobPosition(100, 100), p.vel, p.acc, r);
        //BlobIF b1 = new RectangleBlob(randomMass(), new BlobPosition(rnd.nextInt(600), rnd.nextInt(600)), p.vel, p.acc, r);
        BlobIF b1 = new ThrobbingCircleBlob(randomMass(), new BlobPosition(rnd.nextInt(1024), rnd.nextInt(1024)), p.vel, p.acc, r);
        //BlobIF b2 = new CircleBlob(randomMass(), new BlobPosition(300,300), zeroVelocity(), new AccelRandomDecorator(a), r, smokeTrail);
        //BlobIF b2 = createSmokeTrailBlob(b1);
        
        BlobIF ex = new ExplosionBlob(randomMass(), new BlobPosition(rnd.nextInt(500) + 200, rnd.nextInt(500) + 200), 
                zeroVelocity(), a, r);
        ex.setWorld(inWorld);
        inWorld.addBlobToWorld(ex);

        b1.setWorld(inWorld);
        b1.setLifeTime(10000000);
        inWorld.addBlobToWorld(new BlobTrailDecorator(b1, 2, 18));
        //b2.setWorld(inWorld);
        //inWorld.addBlobToWorld(b2);

        return inWorld;
    }
    
    public static WorldIF populateWorldNonRandomBlobSet(WorldIF inWorld, RenderConfig r) {

        PositionIF p1 = new BlobPosition(400,400);
        PositionIF p2 = new BlobPosition(450,450);
        PositionIF p3 = new BlobPosition(250,250);

        // will have to compose velocities too...
        BlobIF b1 = new RectangleBlob(10, p1, zeroVelocity(), new LinearAccel(0, 0), r);
        b1.setWorld(inWorld);
        BlobIF b2 = new CircleBlob(10, p2, zeroVelocity(), new LinearAccel(0, 0), r);
        b2.setWorld(inWorld);
        
        //inWorld.addBlobToWorld(b1);
        //inWorld.addBlobToWorld(b2);

        //BlobPath bp = jigglePath(7);
        //BlobPath bp = squarePath(7,7);
        BlobPath bp = squarePath(rnd.nextInt(5) + 5,rnd.nextInt(3) + 2);
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
                BlobPath bp = squarePath(rnd.nextInt(5) + 5,rnd.nextInt(3) + 2);
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
        
        inWorld.addBlobToWorld(bs);

        return inWorld;
    }

    public static class BlobPath {
        public VelocityIF vel;
        public AccelIF acc;
        
        public BlobPath(VelocityIF vel, AccelIF acc) {
            this.vel = vel;
            this.acc = acc;
        }
    }
    
    public static BlobPath jigglePath(int speed) {
         
        BlobVelocity vel = new BlobVelocity(0,0);

        WeirdAccel.WeirdAccelConfig wc = new WeirdAccel.WeirdAccelConfig();

        wc.xConfig.dir = WeirdAccel.accelDirection.DOWN;
        wc.xConfig.maxVel = speed;
        wc.xConfig.minVel = -speed;
        wc.xConfig.step = speed/5 + 1;
        wc.yConfig.dir = WeirdAccel.accelDirection.UP;
        wc.yConfig.maxVel = speed;
        wc.yConfig.minVel = -speed;
        wc.yConfig.step = speed/5 + 1;
        WeirdAccel accel = new WeirdAccel(wc);
        

        return new BlobPath(vel, accel);
    }
    
    public static BlobPath squarePath(int speed, int interval) {
        
        VelocityIF vel = new BlobVelocity(0,0);
        
        int[][] arr = {
           { speed, 0, interval },
           { 0, speed, interval },
           { -speed, 0, interval },
           { 0, -speed, interval }
        };

        AccelIF acc = new HardCodeAccel(arr);
        return new BlobPath(vel, acc);
    }
    
}
