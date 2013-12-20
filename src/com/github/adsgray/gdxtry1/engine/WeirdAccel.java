package com.github.adsgray.gdxtry1.engine;

public class WeirdAccel implements AccelIF {

    private int maxvel = 10;
    private int minvel = -10;
    private int step = 4;
    
    private enum accelDirection {
        UP, DOWN 
    }
    
    accelDirection xDirection = accelDirection.UP;
    accelDirection yDirection = accelDirection.DOWN;

    public WeirdAccel() {
    }
    
    public WeirdAccel(int max, int min, int s) {
        maxvel = max;
        minvel = min;
        step = s;
    }
    
    @Override
    public VelocityIF accellerate(VelocityIF vel) {
        VelocityIF newvel = new BlobVelocity();
        
        int x = vel.getXVelocity();
        int y = vel.getYVelocity();
        
        switch(xDirection) {
            case UP:
                if (x < maxvel) {
                    x += step;
                } else {
                    x -= step;
                    xDirection = accelDirection.DOWN;
                }
            break;
            case DOWN:
                if (x > minvel) {
                    x -= step;
                } else {
                    x += step;
                    xDirection = accelDirection.UP;
                }
        }
         
        switch(yDirection) {
            case UP:
                if (y < maxvel) {
                    y += step;
                } else {
                    y -= step;
                    yDirection = accelDirection.DOWN;
                }
            break;
            case DOWN:
                if (y > minvel) {
                    y -= step;
                } else {
                    y += step;
                    yDirection = accelDirection.UP;
                }
        }
       

        newvel.setXVelocity(x);
        newvel.setYVelocity(y);
        return newvel;

    }

}
