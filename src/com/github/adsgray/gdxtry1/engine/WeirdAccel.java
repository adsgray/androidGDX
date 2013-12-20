package com.github.adsgray.gdxtry1.engine;

import android.util.Log;

public class WeirdAccel implements AccelIF {

    public enum accelDirection {
        UP, DOWN 
    }
    
    // defaults
    public static class AxisConfig {
        public int maxVel = 10;
        public int minVel = -10;
        public int step = 4;
        public accelDirection dir = accelDirection.UP;
    }

    public static class WeirdAccelConfig {

        public AxisConfig xConfig;
        public AxisConfig yConfig;

        public WeirdAccelConfig() {
            xConfig = new AxisConfig();
            yConfig = new AxisConfig();
        }
    }
    
    private WeirdAccelConfig accelConfig;

    public WeirdAccel() {
        accelConfig = new WeirdAccelConfig();
    }
    
    public WeirdAccel(WeirdAccelConfig conf) {
        accelConfig = conf;
    }
    
    private static class VelAndConfig {
        public int vel;
        public AxisConfig axisConf;

        public VelAndConfig(int v, AxisConfig ax) {
            vel = v;
            axisConf = ax;
        }
        
        public VelAndConfig() {}
    }
    
    private VelAndConfig mangle(VelAndConfig in) {
        VelAndConfig ret = in; // new VelAndConfig();
        int vel = in.vel;

        ret.axisConf = in.axisConf;
        switch(in.axisConf.dir) {
            case UP:
                if (vel < in.axisConf.maxVel) {
                    vel += in.axisConf.step;
                } else {
                    vel -= in.axisConf.step;
                    ret.axisConf.dir = accelDirection.DOWN;
                }
            break;

            case DOWN:
                if (vel > in.axisConf.minVel) {
                    vel -= in.axisConf.step;
                } else {
                    vel += in.axisConf.step;
                    ret.axisConf.dir = accelDirection.UP;
                }
        }
        
        //Log.d("accel", String.format("vel is now %d", vel));
        ret.vel = vel;
        return ret;
    }
    
    @Override
    public VelocityIF accellerate(VelocityIF vel) {
        
        VelAndConfig xconf = new VelAndConfig(vel.getXVelocity(), accelConfig.xConfig);
        VelAndConfig yconf = new VelAndConfig(vel.getYVelocity(), accelConfig.yConfig);
        xconf = mangle(xconf);
        yconf = mangle(yconf);
        
        accelConfig.xConfig = xconf.axisConf;
        accelConfig.yConfig = yconf.axisConf;
        
        VelocityIF newvel = new BlobVelocity();
        newvel.setXVelocity(xconf.vel);
        newvel.setYVelocity(yconf.vel);
        return newvel;
    }

}
