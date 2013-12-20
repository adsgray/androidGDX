package com.github.adsgray.gdxtry1.game;

import java.util.Random;

import com.github.adsgray.gdxtry1.engine.*;
import com.github.adsgray.gdxtry1.output.RenderConfig;

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
         BlobVelocity vel = new BlobVelocity();
         vel.setXVelocity(rnd.nextInt(MAX_VEL * 2) - MAX_VEL + MIN_VEL);
         vel.setYVelocity(rnd.nextInt(MAX_VEL * 2) - MAX_VEL + MIN_VEL);
         return vel;
    }
    
    private static AccelIF randomAccel() {
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
        inWorld.addBlobToWorld(b);
        return b;
    }
    
    public static WorldIF populateWorldWithBlobs(WorldIF inWorld, int howMany, RenderConfig r) {

        while (howMany > 0) {
            createDefaultBlob(inWorld, r);
            howMany -= 1;
        }
        
        return inWorld;
    }

}
