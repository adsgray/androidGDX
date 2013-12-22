package com.github.adsgray.gdxtry1.engine;

import android.util.Log;

public class HardCodeAccel implements AccelIF {
    
    /*
     * format of entries is:
     * int[][] entries = new int[][] {
     *      { xvel, yvel, interval },
     *      { xvel, yvel, interval },
     *      { xvel, yvel, interval },
     *      { xvel, yvel, interval }
     * };
     */
    protected int[][] entries;
    private int curRow = 0;
    private int curTick = 0;
    private int numRows;
    private int tickInterval;
    
    public HardCodeAccel(int[][] entries) {
        this.entries = entries;
        tickInterval = entries[0][2];
        numRows = entries.length;
        Log.d("Accel", String.format("numRows is %d", numRows));
    }

    @Override
    public VelocityIF accellerate(VelocityIF vel) {

        curTick += 1;

        if (curTick < tickInterval) {
            return vel;
        }
        
        curTick = 0;
        Log.d("Accel", String.format("curRow is %d", curRow));
        
        vel.setXVelocity(entries[curRow][0]);
        vel.setYVelocity(entries[curRow][1]);

        curRow = (curRow + 1) % numRows;
        tickInterval = entries[curRow][2];

        return vel;
    }

}
