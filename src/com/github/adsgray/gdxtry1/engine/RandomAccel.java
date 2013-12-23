package com.github.adsgray.gdxtry1.engine;

import java.util.Random;

public class RandomAccel extends LinearAccel {
    
    protected int ticks;
    protected int curTick = 0;
    protected int min, max;
    protected Random rnd = new Random();
    
    protected int getRandomInBounds() {
        int num = rnd.nextInt(2 * max) - max;
        if (num > 0) num += min; else num -= min;
        return num;
    }
    
    protected void randomizeParameters() {
        x = getRandomInBounds();
        y = getRandomInBounds();
    }

    public RandomAccel(int min, int max, int ticksBetweenRandom) {
        this.min = min;
        this.max = max;
        
        randomizeParameters();

        ticks = ticksBetweenRandom;
    }

    @Override
    public VelocityIF accellerate(VelocityIF vel) {
        if (curTick == ticks) {
            curTick = 0;
            randomizeParameters();
        }
        curTick += 1;
        return super.accellerate(vel);
    }

}
