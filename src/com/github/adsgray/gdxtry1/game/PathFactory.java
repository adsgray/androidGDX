package com.github.adsgray.gdxtry1.game;

import com.github.adsgray.gdxtry1.engine.*;
import com.github.adsgray.gdxtry1.engine.accel.AccelComposeDecorator;
import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.accel.HardCodeAccel;
import com.github.adsgray.gdxtry1.engine.accel.LinearAccel;
import com.github.adsgray.gdxtry1.engine.accel.RandomAccel;
import com.github.adsgray.gdxtry1.engine.accel.WeirdAccel;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.position.PositionComposeDecorator;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

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
        
        VelocityIF vel = new BlobVelocity(0,-speed);
        
        /*
        int[][] arr = {
            // must start at 0,0
           { speed, 0, interval }, // right,0
           { -speed, 0, 1 }, // 0,0

           { 0, speed, interval }, //0, up
           { 0, -speed, 1 }, // 0,0

           { -speed, 0, interval }, // left, 0
           { speed, 0, 1 }, // 0,0

           { 0, -speed, interval }, // 0,down
           { 0, speed, 1 } // 0,0
        };
        */

        int[][] arr = {
            // must start at 0,-speed
           { speed, speed, interval }, // right,0
           { -speed, speed, interval }, //0, up
           { -speed, -speed, interval }, // left, 0
           { speed, -speed, interval }, // 0,down
        };

        AccelIF acc = new HardCodeAccel(arr);
        return new BlobPath(vel, acc);
    }
    
    public static BlobPath squarePathClockwise(int speed, int interval) {
        return squarePath(-speed, interval);
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
          
        VelocityIF vel = new BlobVelocity(speed,0);
        int interval = 5 * intervalFactor;
        
        // starts at speed,0
        int[][] arr = {
                { -speed * 2, 0, interval }, // -speed,0
                { speed * 2, 0, interval }, // speed, 0
        };

        AccelIF acc = new HardCodeAccel(arr);
        return new BlobPath(vel, acc);              
    }

    public static BlobPath backAndForthLeft(int speed, int intervalFactor) {
        return backAndForth(-speed, intervalFactor);
    }

    public static BlobPath upAndDown(int speed, int intervalFactor) {
        VelocityIF vel = new BlobVelocity(0,speed);
        int interval = 5 * intervalFactor;
        
        // starts at 0,speed
        int[][] arr = {
                { 0, -speed * 2, interval }, // -speed,0
                { 0, speed * 2, interval }, // speed, 0
        };

        AccelIF acc = new HardCodeAccel(arr);
        return new BlobPath(vel, acc);              
    }
    
    // ???
    public static BlobPath upperTriangle(int speed, int intervalFactor) {
        BlobPath p1 = backAndForth(speed, intervalFactor * 2);
        BlobPath p2 = upAndDown(speed, intervalFactor);
        
        // necessary to seed the Velocity with (speed,speed) because of the nature
        // of backAndForth and upAndDown
        return new BlobPath(new BlobVelocity(speed,speed), new AccelComposeDecorator(p1.acc, p2.acc));
    }
    
    // need a way to delay the initiation of an acceleration

    public static BlobPath straightUpDown(int speed) {
        return new BlobPath(new BlobVelocity(0, speed), AccelFactory.zeroAccel());
    }

    public static BlobPath straightLeftRight(int speed) {
        return new BlobPath(new BlobVelocity(speed, 0), AccelFactory.zeroAccel());
    }
    
    public static BlobPath stationary() {
        return new BlobPath(zeroVelocity(), AccelFactory.zeroAccel());
    }
    
    public static BlobPath launchUp(int initialSpeed, int decel) {
        return new BlobPath(new BlobVelocity(0, initialSpeed), new LinearAccel(0, decel));
    }
    
    public static BlobPath launchUp() {
        return launchUp(60, -2);
    }
    
    public static BlobIF composePositions(BlobIF primary, BlobIF component) {
        primary.setPosition(new PositionComposeDecorator(component.getPosition(), primary.getPosition()));
        return primary;
    }
}
