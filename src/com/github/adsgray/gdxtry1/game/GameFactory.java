package com.github.adsgray.gdxtry1.game;

import java.util.Random;

import com.github.adsgray.gdxtry1.engine.*;

public class GameFactory {
    public static WorldIF defaultWorld() {
        return new World();
    }
    
    private static Random rnd = new Random();
    
    private static final int BOUNDS_X = 1024;
    private static final int BOUNDS_Y = 1024;

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
        return new LinearAccel(rnd.nextInt(6) - 3, rnd.nextInt(6) - 3);
        //return new LinearAccel(0,0);
    }
    
    private static final int MIN_W = 50;
    private static final int MAX_W = 150;
    private static final int MIN_H = MIN_W;
    private static final int MAX_H = MAX_W;

    private static ExtentIF randomExtent() {
        return new RectangleExtent(rnd.nextInt(MAX_W) + MIN_W, rnd.nextInt(MAX_H) + MIN_H);
    }

    static BlobIF createDefaultBlob(WorldIF inWorld) {
        BlobIF b = new Blob(randomMass(), randomPosition(), randomVelocity(), randomAccel());
        b.setWorld(inWorld);
        b.setExtent(randomExtent());
        inWorld.addBlobToWorld(b);
        return b;
    }
    
    public static WorldIF populateWorldWithBlobs(WorldIF inWorld, int howMany) {

        while (howMany > 0) {
            createDefaultBlob(inWorld);
            howMany -= 1;
        }
        
        return inWorld;
    }

}
