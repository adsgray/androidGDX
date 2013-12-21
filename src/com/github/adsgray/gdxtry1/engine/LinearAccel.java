package com.github.adsgray.gdxtry1.engine;

import android.util.Log;

public class LinearAccel implements AccelIF {

    private Integer x;
    private Integer y;

    public LinearAccel(Integer xin, Integer yin) {
        x = xin;
        y = yin;
    }

    @Override
    public VelocityIF accellerate(VelocityIF vel) {
        Log.d("velocity", String.format("accelerate x is %d y is %d", x, y));
        vel.setXVelocity(vel.getXVelocity() + x);
        vel.setXVelocity(vel.getYVelocity() + y);
        return vel;
    }

}
