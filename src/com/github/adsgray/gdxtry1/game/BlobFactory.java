package com.github.adsgray.gdxtry1.game;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.*;
import com.github.adsgray.gdxtry1.output.RenderConfig;
import com.github.adsgray.gdxtry1.output.RenderConfig.CircleConfig;

public class BlobFactory extends GameFactory {

    static Color[] colors = new Color[] {
        Color.RED,Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN,
        Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW,
        Color.WHITE
    };
    static private CircleConfig smokeTrail() {
        //return new CircleConfig(Color.GRAY, 7);
        //static Vector<Color> colors = new Vector<Color>();
        //int choice = rnd.nextInt(colors.length);
        //Color color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        return new CircleConfig(Color.GRAY, 7);
    }
   
    public static BlobIF createSmokeTrailBlob(BlobIF c) {
        BlobIF b = new ShrinkingCircleBlob(randomMass(), new BlobPosition(c.getPosition()), randomVelocity(),
                WeirdAccel.randomWeirdAccel(), c.getRenderer(), smokeTrail());
        b = new BlobCrazyAccelDecorator(b);
        b.setWorld(c.getWorld());
        return b;
    }
    
    static Color[] explosionColors = new Color[] {
        Color.RED, Color.ORANGE, Color.MAGENTA
    };
    static private CircleConfig explosionBlob() {
        Color color = explosionColors[rnd.nextInt(explosionColors.length)];
        return new CircleConfig(color, rnd.nextFloat() * 7 + 5);
    }
    public static BlobIF createExplosionBlob(BlobIF c) {
         BlobIF b = new ShrinkingCircleBlob(randomMass(), new BlobPosition(c.getPosition()), randomVelocity(),
                WeirdAccel.randomWeirdAccel(), c.getRenderer(), explosionBlob());
        b = new BlobCrazyAccelDecorator(b);
        b.setWorld(c.getWorld());
        return b;       
    }
 
    public static BlobIF createDefaultBlob(WorldIF inWorld, RenderConfig r) {
        BlobIF b;
        if (rnd.nextInt(100) < 50) {
            //b = new RectangleBlob(randomMass(), randomPosition(), randomVelocity(), randomAccel(), r);
            b = new SplittingRectangleBlob(randomMass(), randomPosition(), randomVelocity(), randomAccel(), r);
        } else {
            b = new ShrinkingCircleBlob(randomMass(), randomPosition(), randomVelocity(), randomAccel(), r);
        }
        
        b.setWorld(inWorld);
        b.setExtent(randomExtent());

        if (rnd.nextInt(100) < 10) {
            b = new BlobTrailDecorator(b);
        }

        // possibly stack some decorators:
        if (rnd.nextInt(100) < 50) {
            b = new BlobCrazyAccelDecorator(b);
        }

        inWorld.addBlobToWorld(b);
        return b;
    }
    
    public static BlobIF createOozeBlob(WorldIF inWorld, RenderConfig r) {
        BlobIF bs = new BlobSet(10, randomPosition(), zeroVelocity(), zeroAccel(), r);
        bs.setWorld(inWorld);
        bs.setLifeTime(100000);
        return bs;
    }
    
}
