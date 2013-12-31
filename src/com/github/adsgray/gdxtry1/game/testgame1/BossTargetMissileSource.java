package com.github.adsgray.gdxtry1.game.testgame1;

import android.util.Log;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTransform;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

// This aims bombs at the target supplied to constructor
public class BossTargetMissileSource extends AngryTargetMissileSource {

    protected PositionIF aimTarget;
    protected float minSpeed;
    protected float maxSpeed;
    protected int maxError;

    public BossTargetMissileSource(PositionIF aimTarget) {
        super(null);
        this.aimTarget = aimTarget;
        minSpeed = 18;
        maxSpeed = 25;
        maxError = 50;
    }
    
    public float setMinSpeed(float in) { minSpeed = in; return minSpeed; }
    public float setMaxSpeed(float in) { maxSpeed = in; return maxSpeed; }
    public int setMaxError(int in) { maxError = in; return maxError; }
     
    protected float bombSpeed() {
        // minimum 8, maximum 16?
        return TargetUtils.rnd.nextFloat() * minSpeed + (maxSpeed - minSpeed);
    }
    
    // return a position that is within the bounds of this enemy
    // so that the bomb will drop from somewhere inside the boss
    protected PositionIF chooseSourcePosition(PositionIF origin) {
        // TODO send from somewhere other than middle of this enemy
        return new BlobPosition(origin.getX() + TargetUtils.rnd.nextInt(200) + 100,
                                origin.getY() + TargetUtils.rnd.nextInt(200) + 100);
    }
    
    // based on p (source of bomb) and the target set in the constructor, create
    // a velocity that will launch the bomb at the target. Some
    // error will be introduced by the aimError transform.
    protected VelocityIF aimAtTargetFrom(PositionIF p) {
        // aim a little to the left or right
        PositionIF target = new BlobPosition(aimTarget);
        target.setX(target.getX() + TargetUtils.rnd.nextInt(2 * maxError) - maxError);

        // this is the velocity in "position form"
        PositionIF vector = target.subtract(p);

        double length = Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
        double xunit = (double)vector.getX() / length;
        double yunit = (double)vector.getY() / length;
        float speed = bombSpeed();
        xunit *= speed;
        yunit *= speed;

        VelocityIF v = new BlobVelocity((int)xunit, (int)yunit);

        return v;
    }
    
    protected BlobTransform aimError = new BlobTransform() {
        @Override public BlobIF transform(BlobIF b) {
            /*
            // introduce error:
            VelocityIF v = b.getVelocity();
            v.setXVelocity(v.getXVelocity() + (TargetUtils.rnd.nextInt(2 * maxError) - maxError));
            v.setYVelocity(v.getYVelocity() + (TargetUtils.rnd.nextInt(2 * maxError) - maxError));
            */
            return b;
        }
    };

    private void logPosition(String prefix, PositionIF p) {
        Log.d("testgame1", String.format("p %s: (%d,%d)", prefix, p.getX(), p.getY()));
    }
   
    private void logVelocity(String prefix, VelocityIF v) {
        Log.d("testgame1", String.format("v %s: (%d,%d)", prefix, v.getXVelocity(), v.getYVelocity()));
    }

    @Override 
    protected BlobIF generate(BlobIF parent) {
        BlobIF angryBomb = super.generate(parent);
        angryBomb.setPosition(chooseSourcePosition(parent.getPosition()));
        angryBomb.setVelocity(aimAtTargetFrom(angryBomb.getPosition()));
        
        logVelocity("boss", angryBomb.getVelocity());
        angryBomb = aimError.transform(angryBomb);
        return angryBomb;
    }

}
