package com.github.adsgray.gdxtry1.engine.blob;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.output.Renderer.CircleConfig;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public class ShrinkingCircleBlob extends CircleBlob {

    public ShrinkingCircleBlob(Integer massin, PositionIF posin,
            VelocityIF velin, AccelIF accel, Renderer gdx) {
        super(massin, posin, velin, accel, gdx);
    }
    
    public ShrinkingCircleBlob(Integer massin, PositionIF posin,
            VelocityIF velin, AccelIF accel, Renderer gdx, CircleConfig cc) {
        super(massin, posin, velin, accel, gdx, cc);
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
