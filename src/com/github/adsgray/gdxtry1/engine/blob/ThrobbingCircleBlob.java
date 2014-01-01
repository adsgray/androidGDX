package com.github.adsgray.gdxtry1.engine.blob;

import android.util.Log;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.output.Renderer.CircleConfig;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public class ThrobbingCircleBlob extends CircleBlob {

    private enum Dir {
        UP,DOWN
    }

    private float origRadius;
    private Dir dir = Dir.DOWN;
    protected float shrinkRate = 0.95f;
    protected float growthRate = 1.06f; // 1.0f / shrinkRate;

    public ThrobbingCircleBlob(Integer massin, PositionIF posin,
            VelocityIF velin, AccelIF accel, Renderer gdx) {
        super(massin, posin, velin, accel, gdx);

        origRadius = circleConfig.radius;
    }
 
    public ThrobbingCircleBlob(Integer massin, PositionIF posin,
            VelocityIF velin, AccelIF accel, Renderer gdx, CircleConfig cc) {
        super(massin, posin, velin, accel, gdx, cc);

        origRadius = circleConfig.radius;
    }
   
    @Override
    public Boolean tick() {
        
            Log.d("circle", String.format("radius is %f", circleConfig.radius));
        switch (dir) {
        case DOWN:
            circleConfig.radius = circleConfig.radius * shrinkRate;
            if (circleConfig.radius < 2.0f) {
                dir = Dir.UP;
            }
            break;
        case UP:
            circleConfig.radius = circleConfig.radius * growthRate;
            if (origRadius - circleConfig.radius < 1.0f) {
                dir = Dir.DOWN;
            }
            break;
        }

        return super.tick();
    }
   
}
