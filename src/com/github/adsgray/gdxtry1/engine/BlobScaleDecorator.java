package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.RenderConfig.RenderConfigIF;

public class BlobScaleDecorator extends BlobDecorator {

    protected int[][] entries;
    private int curRow = 0;
    private int curTick = 0;
    private int numRows;
    private int tickInterval;

    public BlobScaleDecorator(BlobIF component, int[][] entries) {
        super(component);
        this.entries = entries;
        numRows = entries.length;
        tickInterval = entries[0][1];
    }
    
    @Override public Boolean tick() {
        Boolean ret = component.tick();
 
        curTick += 1;

        if (curTick < tickInterval || !ret) {
            return ret;
        }
        
        curTick = 0;
              
        RenderConfigIF rc = component.getRenderConfig();
        // do stuff

        curRow = (curRow + 1) % numRows;
        // hack to get a float out of an int...
        float scalefactor = (float)entries[curRow][0] / 1000f;
        rc.scale(scalefactor);
        tickInterval = entries[curRow][1];
       
        return true;
    }

}
