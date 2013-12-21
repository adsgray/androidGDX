package com.github.adsgray.gdxtry1.engine;

import java.util.Vector;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.output.RenderConfig;

public class SplittingRectangleBlob extends RectangleBlob {

    private int explodeTime = 100;

    public SplittingRectangleBlob(Integer massin, PositionIF posin,
            VelocityIF velin, AccelIF accel, RenderConfig gdx) {
        super(massin, posin, velin, accel, gdx);
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
    public Boolean tick() {
        // explode into 2 pieces at a predetermined time
        if (ticks == explodeTime) {
            explode(2);
        }

        return super.tick();
    }

    private VelocityIF mangleVelocity(VelocityIF vel) {
        //vel.setXVelocity(-1 * vel.getXVelocity());
        //return vel;
        VelocityIF newvel = new BlobVelocity();
        newvel.setXVelocity(-vel.getXVelocity());
        newvel.setYVelocity(vel.getYVelocity());
        return newvel;
    }
    
    private AccelIF mangleAccel(AccelIF accel) {
        return accel;
    }

    @Override
    public Vector<BlobIF> explode(Integer numPieces) {
        Vector<BlobIF> vec = new Vector<BlobIF>();
        
        while (numPieces > 0) {
            BlobIF b = new SplittingRectangleBlob(mass, position, 
                    mangleVelocity(velocity),
                    mangleAccel(acceleration), renderer);
            b.setWorld(world);
            b.setExtent(GameFactory.randomExtent());
            b.setLifeTime(50);
            vec.add(b);
            numPieces -= 1;
        }

        updateWorldAfterExplode(vec);
        sound.crash(EXPLODE_INTENSITY);
        return vec;
    }

}
