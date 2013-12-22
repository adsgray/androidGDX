package com.github.adsgray.gdxtry1.game;

import com.github.adsgray.gdxtry1.engine.*;

public class PathFactory extends GameFactory {

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

    public static BlobPath squarePathClockwise(int speed, int interval) {
        
        VelocityIF vel = new BlobVelocity(0,0);
        
        int[][] arr = {
           { 0, speed, interval },
           { speed, 0, interval },
           { 0, -speed, interval },
           { -speed, 0, interval }
        };

        AccelIF acc = new HardCodeAccel(arr);
        return new BlobPath(vel, acc);
    }
 
    // this one is not useful because it doesn't loop properly...
    public static BlobPath trianglePath(int speed, int intervalFactor) {
         
        VelocityIF vel = new BlobVelocity(0,0);
        int interval = 5 * intervalFactor;
        
        int[][] arr = {
                { speed, 0, interval * 2},
                { -speed, speed, interval },
                { -speed, -speed, interval }
        };

        AccelIF acc = new HardCodeAccel(arr);
        return new BlobPath(vel, acc);       
    }
    
    public static BlobPath backAndForth(int speed, int intervalFactor) {
          
        VelocityIF vel = new BlobVelocity(0,0);
        int interval = 5 * intervalFactor;
        
        int[][] arr = {
                //{ 0, -speed, interval },
                //{ 0, speed, interval }
                { -speed, 0, interval },
                { speed, 0, interval }
        };

        AccelIF acc = new HardCodeAccel(arr);
        return new BlobPath(vel, acc);              
    }

}
