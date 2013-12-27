package com.github.adsgray.gdxtry1.engine;

import java.util.Iterator;

import com.github.adsgray.gdxtry1.output.RenderConfig;

public class BlobSet2 extends BlobSet {

    public BlobSet2(Integer massin, PositionIF posin, VelocityIF velin,
            AccelIF accel, RenderConfig gdx) {
        super(massin, posin, velin, accel, gdx);
    }

    @Override
    public BlobIF absorbBlob(BlobIF b, BlobTransform bt) {
        // add to objs
        // remove from world (we'll be handling ticks/renders ?
        
        world.removeBlobFromWorld(b);
        //if ( true && bt != null) {
        if (bt != null) {
            // transform could be to zero out velocity and accel so that
            // the absorbed blob stops moving.
            b = bt.transform(b);
        }
        //b.setAccel(new AccelComposeDecorator(b.getAccel(), acceleration));
        //b.setVelocity(new VelocityComposeDecorator(b.getVelocity(), velocity));
        b.setPosition(new PositionComposeDecorator(b.getPosition(), position));
        objs.add(b);
        
        return this;
    }
     
    @Override public void setPosition(PositionIF p) { 
        Iterator<BlobIF> iter = objs.iterator();

        position = p;
        
        while (iter.hasNext()) {
            BlobIF b = iter.next();
            PositionIF bpos = b.getPosition();
            if (bpos instanceof PositionComposeDecorator) {
                // uuuuuggggglllllyyyyy
                PositionComposeDecorator dec = (PositionComposeDecorator)bpos;
                bpos = dec.getComponent();
            }
            b.setPosition(new PositionComposeDecorator(bpos, position));
        }
    }
  
}
