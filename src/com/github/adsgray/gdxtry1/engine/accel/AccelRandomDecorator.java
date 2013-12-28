package com.github.adsgray.gdxtry1.engine.accel;

import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.game.GameFactory;

/* this class is actually not so much a decorator as a replacer.
 * It clobbers/replaces its initial component.
 * Note: this is an alternative way of doing what BlobCrazyAccelDecorator does.
 */
public class AccelRandomDecorator extends AccelDecorator {

    protected int step = 10;
    protected int count = 0;

    public AccelRandomDecorator(AccelIF component) {
        super(component);
    }
    
    @Override
    public VelocityIF accellerate(VelocityIF vel) {
        // should abstract this, I keep messing it up when I copy/re-create it.
        if (count >= step) {
            count = 0;
            component = GameFactory.randomAccel();
        }
        
        count += 1;
        return component.accellerate(vel);
    }

}
