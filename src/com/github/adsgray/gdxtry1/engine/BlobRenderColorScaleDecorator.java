package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.RenderConfig.RenderConfigIF;

public class BlobRenderColorScaleDecorator extends BlobScaleDecorator {


    public BlobRenderColorScaleDecorator(BlobIF component, int[][] entries) {
        super(component, entries);
    }
    
    @Override protected void doScaling(float factor) {
        RenderConfigIF rc = component.getRenderConfig();
        rc.scaleColor(factor);
    }
}
