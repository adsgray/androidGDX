package com.github.adsgray.gdxtry1.engine;

public class CircleExtent implements ExtentIF {

    protected int radius;
    public int getRadius() { return radius; }

    public CircleExtent(int radius) { 
        this.radius = radius;
    }
    
    public CircleExtent(CircleExtent c) {
        this(c.getRadius());
    }
    
    @Override
    public boolean intersects(PositionIF me, BlobIF them) {
        /*
         * plan:
         * get the unit vector between the two positions
         * multiply by "radius" of me to get the furthest point
         * along that vector that is inside me.
         * Now check if "them" contains that point
         */
        return false;
    }

    @Override
    public boolean contains(PositionIF me, PositionIF point) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public void scale(float factor) { radius *= factor; }
    
    @Override
    public ExtentIF clone() {
        return new CircleExtent(radius);
    }
}
