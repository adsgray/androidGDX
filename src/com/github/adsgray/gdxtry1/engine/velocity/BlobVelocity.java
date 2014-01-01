package com.github.adsgray.gdxtry1.engine.velocity;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

import android.util.Log;

public class BlobVelocity implements VelocityIF {
    
    private int x;
    private int y;
    
    public BlobVelocity(VelocityIF fromv) {
        x = fromv.getXVelocity();
        y = fromv.getYVelocity();
    }
    
    public BlobVelocity(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int getXVelocity() {
        return x;
    }

    @Override
    public int getYVelocity() {
        // argh copy-paste typo, this was "x"
        return y;
    }

    /*
    @Override
    public PositionIF updatePosition(PositionIF pos) {
        Integer posX = pos.getX();
        Integer posY = pos.getY();
        
        pos.setX(posX + x);
        pos.setY(posY + y);

        return pos;
    }
    */

    @Override
    public int deltaX(int xin) {
        //Log.d("velocity", String.format("xin is %d x is %d", xin, x));
        xin = xin + x;
        return xin;
    }

    @Override
    public int deltaY(int yin) {
        //Log.d("velocity", String.format("yin is %d y is %d", yin, y));
        // THESE TWO LINES ARE MOVING WHEN Y is 0??
        yin = yin + y;
        return yin;
    }

    @Override
    public int setXVelocity(int xin) {
        x = xin;
        return x;
    }

    @Override
    public int setYVelocity(int yin) {
        y = yin;
        return y;
    }

    @Override
    public PositionIF updatePosition(PositionIF pos) {
        pos.setX(deltaX(pos.getX()));
        pos.setY(deltaX(pos.getY()));
        return pos;
    }

    @Override
    public VelocityIF accelerate(AccelIF a) {
        VelocityIF vel = a.accellerate(this);
        //x = vel.getXVelocity();
        //y = vel.getYVelocity();
        //return vel;
        return this;
    }

}
