package com.github.adsgray.gdxtry1.engine;

public class RectangleExtent implements ExtentIF {
    
    private Integer width;
    private Integer height;

    public RectangleExtent(Integer w, Integer h) {
        width = w;
        height = h;
    }

    @Override
    // not great as only checks points
    public boolean intersects(PositionIF me, PositionIF them) {
        return them.getX() <= (me.getX() + width) && them.getY() <= (me.getY() + height);
    }

}
