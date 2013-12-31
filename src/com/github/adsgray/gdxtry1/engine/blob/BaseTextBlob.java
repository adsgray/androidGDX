package com.github.adsgray.gdxtry1.engine.blob;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.RenderConfigIF;

public class BaseTextBlob extends BaseBlob implements TextBlobIF {

    public BaseTextBlob(PositionIF posin, VelocityIF velin,
            AccelIF accel, Renderer gdx, RenderConfigIF rc) {
        super(0, posin, velin, accel, gdx);
        renderConfig = rc;
    }
    
    protected String txt;

    @Override public String getText() { return txt; }
    @Override public void setText(String str) { txt = str; }
}
