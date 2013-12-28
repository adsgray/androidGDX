package com.github.adsgray.gdxtry1.engine.extent;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

import android.util.Log;

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
        
        // haha:
        //return them.getExtent().contains(them.getPosition(), them.getPosition().subtract(me).unitVector().multiply(radius).add(me));
        
        // http://www.gamasutra.com/view/feature/131424/pool_hall_lessons_fast_accurate_.php
        double deltaXSquared = me.getX() - themPos.getX();
        deltaXSquared *= deltaXSquared;
        double deltaYSquared = me.getY() - themPos.getY();
        deltaYSquared *= deltaYSquared;

        CircleExtent themCe = (CircleExtent)them.getExtent();
        double sumRadiiSquared = radius + themCe.getRadius();
        double sumrad = sumRadiiSquared;
        sumRadiiSquared *= sumRadiiSquared;
        
        Boolean ret =  (deltaXSquared + deltaYSquared) <= sumRadiiSquared;
        double dist = Math.sqrt(deltaXSquared + deltaYSquared);
        return ret;
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
