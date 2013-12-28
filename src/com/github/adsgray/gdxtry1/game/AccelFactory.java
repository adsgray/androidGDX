package com.github.adsgray.gdxtry1.game;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.accel.BumpAccel;
import com.github.adsgray.gdxtry1.engine.accel.LinearAccel;
import com.github.adsgray.gdxtry1.engine.accel.RandomAccel;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;

public class AccelFactory {

    public static AccelIF zeroAccel() {
        return new LinearAccel(0, 0);
    }

    public static AccelIF up(int amt) {
        return new LinearAccel(0, amt);
    }
    
    // calculate an upward acceleration based
    // on downward velocity. Assumes blob is moving
    // down...
    public static AccelIF up(BlobIF b) {
        int y = b.getVelocity().getYVelocity();
        return up(-y/2);
    }

    public static AccelIF down(int amt) {
        return new LinearAccel(0, -amt);
    }
    
    public static AccelIF down(BlobIF b) {
        return down(-b.getVelocity().getYVelocity());
    }

    public static AccelIF right(int amt) {
        return new LinearAccel(amt, 0);
    }

    public static AccelIF right(BlobIF b) {
        return right(-b.getVelocity().getXVelocity());
    }

    public static AccelIF left(int amt) {
        return new LinearAccel(-amt, 0);
    }

    public static AccelIF left(BlobIF b) {
        return left(-b.getVelocity().getXVelocity());
    }

    
    public static BlobIF bump(BlobIF b, AccelIF bump, int duration) {
        b.setAccel(new BumpAccel(b.getAccel(), bump, duration));
        return b;
    }
 
    public static AccelIF explosionAccel() {
        return new RandomAccel(1, 5, 1);
    }

    public static AccelIF smokeTrailAccel() {
        return new RandomAccel(1, 3, 1);
    }

} 
