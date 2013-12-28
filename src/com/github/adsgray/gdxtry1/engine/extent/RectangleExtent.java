package com.github.adsgray.gdxtry1.engine.extent;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

public class RectangleExtent implements ExtentIF {
    
    private int width;
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    private int height;
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public RectangleExtent(int w, int h) {
        width = w;
        height = h;
    }
    
    public RectangleExtent(RectangleExtent r) {
        this(r.getWidth(), r.getHeight());
    }

    @Override
    // not great as only checks points
    public boolean intersects(PositionIF me, BlobIF them) {
        PositionIF thempos = them.getPosition();
        return thempos.getX() <= (me.getX() + width) && thempos.getY() <= (me.getY() + height);
    }

    @Override
    public boolean contains(PositionIF me, PositionIF point) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void scale(float factor) {
        // not so sure about this:
        width *= factor;
        height *= factor;
    }
    
    @Override
    public ExtentIF clone() {
        return new RectangleExtent(width, height);
    }
}
