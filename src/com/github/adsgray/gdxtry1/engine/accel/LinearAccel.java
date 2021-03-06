package com.github.adsgray.gdxtry1.engine.accel;

import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

import android.util.Log;

public class LinearAccel extends BaseAccel {

    protected Integer x;
    protected Integer y;

    public LinearAccel(Integer xin, Integer yin) {
        x = xin;
        y = yin;
    }
    
    // dangerous
    public LinearAccel() {}

    @Override
    public VelocityIF accellerate(VelocityIF vel) {
        //Log.d("velocity", String.format("accelerate x is %d y is %d", x, y));
        //VelocityIF newvel = new BlobVelocity(vel.getXVelocity() + x, vel.getYVelocity() + y);
        //return newvel;

        vel.setXVelocity(vel.getXVelocity() + x);
        vel.setYVelocity(vel.getYVelocity() + y);
        return vel;
    }

}
