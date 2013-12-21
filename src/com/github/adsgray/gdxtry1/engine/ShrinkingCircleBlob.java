package com.github.adsgray.gdxtry1.engine;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.output.RenderConfig;

public class ShrinkingCircleBlob extends CircleBlob {

    public ShrinkingCircleBlob(Integer massin, PositionIF posin,
            VelocityIF velin, AccelIF accel, RenderConfig gdx) {
        super(massin, posin, velin, accel, gdx);
    }
    
    @Override
    public Boolean tick() {
        // the only difference in this class is that we shrink the radius and mess with the color
        circleConfig.radius = circleConfig.radius * 0.98f;

        Color c = circleConfig.color;
        float factor = 1.025f;
        circleConfig.color.set(c.r * factor, c.g * factor, c.b * factor, c.a);
        
        return super.tick();
    }
    

}
