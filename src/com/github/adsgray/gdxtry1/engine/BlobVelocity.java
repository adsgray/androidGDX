package com.github.adsgray.gdxtry1.engine;

import android.util.Log;

public class BlobVelocity implements VelocityIF {
    
    private Integer x;
    private Integer y;
    
    public BlobVelocity(VelocityIF fromv) {
        x = fromv.getXVelocity();
        y = fromv.getYVelocity();
    }
    
    public BlobVelocity(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public Integer getXVelocity() {
        return x;
    }

    @Override
    public Integer getYVelocity() {
        return x;
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
    public Integer deltaX(Integer xin) {
        Log.d("velocity", String.format("xin is %d x is %d", xin, x));
        xin = xin + x;
        return xin;
    }

    @Override
    public Integer deltaY(Integer yin) {
        yin = yin + y;
        return yin;
    }

    @Override
    public Integer setXVelocity(Integer xin) {
        x = xin;
        return x;
    }

    @Override
    public Integer setYVelocity(Integer yin) {
        y = yin;
        return y;
    }

}
