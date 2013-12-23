package com.github.adsgray.gdxtry1.engine;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.output.RenderConfig.RenderConfigIF;

public class BlobRenderColorDecorator extends BlobDecorator {

    public BlobRenderColorDecorator(BlobIF component) {
        super(component);
        // TODO Auto-generated constructor stub
    }

    public static class ColorDecoratorEntry {
        public Color color;
        public int tickInterval;
        public ColorDecoratorEntry(Color color, int tickInterval) {
            this.color = color;
            this.tickInterval = tickInterval;
        }
    }

    protected ColorDecoratorEntry[] entries;
    private int curRow = 0;
    private int curTick = 0;
    private int numRows;
    private int tickInterval;

    public BlobRenderColorDecorator(BlobIF component, ColorDecoratorEntry[] entries) {
        super(component);
        this.entries = entries;
        numRows = entries.length;
        tickInterval = entries[0].tickInterval;
    }
    
    @Override public Boolean tick() {
        Boolean ret = component.tick();
 
        curTick += 1;

        if (curTick < tickInterval || !ret) {
            return ret;
        }
        
        curTick = 0;
              
        RenderConfigIF rc = component.getRenderConfig();

        curRow = (curRow + 1) % numRows;
        rc.setColor(entries[curRow].color);
        tickInterval = entries[curRow].tickInterval;
       
        return ret;
    }


}
