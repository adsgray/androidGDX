package com.github.adsgray.gdxtry1.engine.util;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.accel.BumpAccel;
import com.github.adsgray.gdxtry1.engine.accel.LinearAccel;
import com.github.adsgray.gdxtry1.engine.accel.RandomAccel;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

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
    public static AccelIF up(VelocityIF v) {
        int y = v.getYVelocity();
        return up(-y/2);
    }

    public static AccelIF down(int amt) {
        return new LinearAccel(0, -amt);
    }
    
    public static AccelIF down(VelocityIF v) {
        return down(-v.getYVelocity());
    }

    public static AccelIF right(int amt) {
        return new LinearAccel(amt, 0);
    }

    public static AccelIF right(VelocityIF v) {
        return right(-v.getXVelocity());
    }

    public static AccelIF left(int amt) {
        return new LinearAccel(-amt, 0);
    }

    public static AccelIF left(VelocityIF v) {
        return left(-v.getXVelocity());
    }

    
    public static AccelIF bump(AccelIF source, AccelIF bump, int duration) {
        return new BumpAccel(source, bump, duration);
    }
 
    public static AccelIF explosionAccel() {
        return new RandomAccel(1, 5, 1);
    }

    public static AccelIF smokeTrailAccel() {
        return new RandomAccel(1, 3, 1);
    }

} 
