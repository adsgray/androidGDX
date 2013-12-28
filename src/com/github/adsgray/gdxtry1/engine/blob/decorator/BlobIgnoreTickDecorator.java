package com.github.adsgray.gdxtry1.engine.blob.decorator;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;

/* ignores every Nth tick */
public class BlobIgnoreTickDecorator extends BlobDecorator {

    private int nth;
    private int curTick = 0;

    public BlobIgnoreTickDecorator(BlobIF component, int nth) {
        super(component);
        this.nth = nth;
    }

    @Override public Boolean tick() {
        if (curTick == nth) {
            curTick = 0;
            return true;
        }
        
        curTick += 1;
        return component.tick();
    }
}
