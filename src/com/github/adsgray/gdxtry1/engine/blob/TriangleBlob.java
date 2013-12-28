package com.github.adsgray.gdxtry1.engine.blob;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.CircleConfig;

public class TriangleBlob extends BaseBlob {

    protected CircleConfig circleConfig;

    private void createExtent() {
        ExtentIF ce = new CircleExtent((int)circleConfig.radius);
        setExtent(ce);
    }

    public TriangleBlob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel, Renderer gdx) {
        super(massin, posin, velin, accel, gdx);

        circleConfig = renderer.randomCircleConfig();
        renderConfig = circleConfig;
        createExtent();
    }     

    public TriangleBlob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel, Renderer gdx, CircleConfig cc) {
        super(massin, posin, velin, accel, gdx);

        circleConfig = cc;
        renderConfig = circleConfig;
        createExtent();
    }     

    @Override
    public void render() {
        renderer.renderTriangle(this, circleConfig);
    }

}
