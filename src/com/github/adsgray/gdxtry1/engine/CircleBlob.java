package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.RenderConfig;
import com.github.adsgray.gdxtry1.output.RenderConfig.CircleConfig;

public class CircleBlob extends BaseBlob {

    protected CircleConfig circleConfig;

    public CircleBlob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel, RenderConfig gdx) {
        super(massin, posin, velin, accel, gdx);

        circleConfig = renderer.randomCircleConfig();
    }     

    @Override
    public void render() {
        renderer.renderCircle(this, circleConfig);
    }
}
