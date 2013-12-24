package com.github.adsgray.gdxtry1.engine;

// Used in place of a RectangleExtent.
// It is a CircleExtent whose radius is the average of w and h
public class FakeRectangleExtent extends CircleExtent {
 
    private int width;
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; resetRadius(); }
    private int height;
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; resetRadius(); }

    private void resetRadius() {
        radius = (width + height) / 2;
    }

    public FakeRectangleExtent(int radius) {
        super(radius);
    }
    
    public FakeRectangleExtent(int w, int h) {
        super((w + h) / 2);
        width = w;
        height = h;
    }
}
