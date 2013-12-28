package com.github.adsgray.gdxtry1.engine.blob;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.CircleConfig;
import com.github.adsgray.gdxtry1.output.Renderer.TriangleConfig;

public class TriangleBlob extends BaseBlob {

    protected TriangleConfig triangleConfig;

    private void createExtent() {
        ExtentIF ce = new CircleExtent((int)triangleConfig.radius);
        setExtent(ce);
    }

    public TriangleBlob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel, Renderer gdx) {
        super(massin, posin, velin, accel, gdx);

        triangleConfig = renderer.randomTriangleConfig();
        renderConfig = triangleConfig;
        createExtent();
    }     

    public TriangleBlob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel, Renderer gdx, TriangleConfig tc) {
        super(massin, posin, velin, accel, gdx);

        triangleConfig = tc;
        renderConfig = triangleConfig;
        createExtent();
    }     
}
