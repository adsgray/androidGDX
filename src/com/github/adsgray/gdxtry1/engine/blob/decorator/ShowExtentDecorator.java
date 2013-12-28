package com.github.adsgray.gdxtry1.engine.blob.decorator;

import android.util.Log;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.CircleBlob;
import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.output.Renderer.CircleConfig;

public class ShowExtentDecorator extends BlobDecorator {

    CircleConfig cc;  // only circles now, would have to make
                      // ExtentIF return a RenderConfig if
                      // other Extent types are actually used.
    BlobIF extentManifested;

    public ShowExtentDecorator(BlobIF component, Color color) {
        super(component);
        CircleExtent ce = (CircleExtent)component.getExtent();
        Log.d("trace", String.format("ShowExtentDecorator r=%d", ce.getRadius()));
        cc = new CircleConfig(color, ce.getRadius());
        cc.shapeType = ShapeType.Line;
        // I think all properties can be null because we only require the
        // position for rendering and that's set in render() below.
        extentManifested = new CircleBlob(0, null /*pos*/, null /*vel*/, null /*accel*/, component.getRenderer(), cc);
        extentManifested = BlobFactory.flashColorCycler(extentManifested, 5);
    }
    
    public ShowExtentDecorator(BlobIF component) {
        this(component, Color.RED);
    }
    
    @Override public void render() {
        component.render();

        // now also render extent
        extentManifested.setPosition(component.getPosition());
        extentManifested.render();
    }

}
