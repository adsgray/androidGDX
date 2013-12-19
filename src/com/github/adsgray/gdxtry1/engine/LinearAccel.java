package com.github.adsgray.gdxtry1.engine;

public class LinearAccel implements AccelIF {

    private Integer x;
    private Integer y;

    public LinearAccel(Integer xin, Integer yin) {
        x = xin;
        y = yin;
    }

    @Override
    public VelocityIF accellerate(VelocityIF vel) {
        vel.setXVelocity(vel.getXVelocity() + x);
        vel.setXVelocity(vel.getYVelocity() + y);
        return vel;
    }

}
