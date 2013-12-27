package com.github.adsgray.gdxtry1.game;

import com.github.adsgray.gdxtry1.engine.AccelIF;
import com.github.adsgray.gdxtry1.engine.BlobIF;
import com.github.adsgray.gdxtry1.engine.BumpAccel;
import com.github.adsgray.gdxtry1.engine.LinearAccel;

public class AccelFactory {

    public static AccelIF up(int amt) {
        return new LinearAccel(0, amt);
    }

    public static AccelIF down(int amt) {
        return new LinearAccel(0, -amt);
    }

    public static AccelIF right(int amt) {
        return new LinearAccel(amt, 0);
    }

    public static AccelIF left(int amt) {
        return new LinearAccel(-amt, 0);
    }

    public static BlobIF bump(BlobIF b, AccelIF bump, int duration) {
        b.setAccel(new BumpAccel(b.getAccel(), bump, duration));
        return b;
    }
   
} 
