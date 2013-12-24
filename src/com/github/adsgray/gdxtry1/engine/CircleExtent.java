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
        PositionIF themPos = them.getPosition();
        PositionIF vector = themPos.subtract(me);
        PositionIF furthestPoint = vector.unitVector().multiply(radius).add(me);
        
        // haha:
        //return them.getExtent().contains(them.getPosition(), them.getPosition().subtract(me).unitVector().multiply(radius).add(me));
        return them.getExtent().contains(them.getPosition(), furthestPoint);
    }

    @Override
    public boolean contains(PositionIF me, PositionIF point) {
        /*
         * if length of vector is less than or equal to radius then return true
         */
        PositionIF vector = point.subtract(me);
        return vector.length() <= radius;
    }

    @Override public void scale(float factor) { radius *= factor; }
    
    @Override
    public ExtentIF clone() {
        return new CircleExtent(radius);
    }
}
