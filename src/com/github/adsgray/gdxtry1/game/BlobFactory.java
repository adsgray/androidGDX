package com.github.adsgray.gdxtry1.game;

import android.util.Log;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.*;
import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.blob.BaseBlob;
import com.github.adsgray.gdxtry1.engine.blob.BaseTextBlob;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.blob.CircleBlob;
import com.github.adsgray.gdxtry1.engine.blob.ExplosionBlob;
import com.github.adsgray.gdxtry1.engine.blob.NullBlob;
import com.github.adsgray.gdxtry1.engine.blob.RectangleBlob;
import com.github.adsgray.gdxtry1.engine.blob.ShrinkingCircleBlob;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobSource;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTransform;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.blob.TextBlobIF;
import com.github.adsgray.gdxtry1.engine.blob.TriangleBlob;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobCrazyAccelDecorator;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobRenderColorDecorator;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobRenderColorScaleDecorator;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobScaleDecorator;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobTrailDecorator;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobRenderColorDecorator.ColorDecoratorEntry;
import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.engine.extent.FakeRectangleExtent;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.output.Renderer.CircleConfig;
import com.github.adsgray.gdxtry1.engine.output.Renderer.RectConfig;
import com.github.adsgray.gdxtry1.engine.output.Renderer.TextConfig;
import com.github.adsgray.gdxtry1.engine.output.Renderer.TriangleConfig;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionComposeDecorator;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public class BlobFactory extends GameFactory {

    static Color[] colors = new Color[] {
        //Color.RED,Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN,
        //Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW,
        //Color.WHITE
        Color.RED,Color.BLUE,Color.CYAN,Color.GREEN,
        Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW,
    };
    
    private static Color randomRainbowColor() {
        return colors[rnd.nextInt(colors.length)];
    }
    
    ///////////////////////
    public static BlobIF createBaseBlob(PositionIF p, VelocityIF v, AccelIF a, Renderer r) {
        BlobIF b = new BaseBlob(0, p, v, a, r);
        return b;
    }
    public static BlobIF createBaseBlob(PositionIF p, BlobPath path, Renderer r) {
        return createBaseBlob(p, path.vel, path.acc, r);
    }

    public static BlobIF rectangleBlob(PositionIF p, BlobPath path, RectConfig renderconfig, Renderer r) {
        BlobIF b = createBaseBlob(p, path, r);
        b.setRenderConfig(renderconfig);
        b.setExtent(new FakeRectangleExtent((int)renderconfig.w, (int)renderconfig.h));
        return b;
    }
    
    public static BlobIF circleBlob(PositionIF p, BlobPath path, CircleConfig rc, Renderer r) {
        BlobIF b = createBaseBlob(p, path, r);
        b.setRenderConfig(rc);
        b.setExtent(new CircleExtent((int)rc.radius));
        return b;
    }
    
    public static BlobIF triangleBlob(PositionIF p, BlobPath path, TriangleConfig rc, Renderer r) {
        BlobIF b = createBaseBlob(p, path, r);
        b.setRenderConfig(rc);
        b.setExtent(new CircleExtent((int)(rc.radius * 0.80f)));
        return b;
    }
    
    public static TextBlobIF textBlob(PositionIF p, BlobPath path, TextConfig rc, Renderer r) {
        TextBlobIF b = new BaseTextBlob(p, path.vel, path.acc, r, rc);
        b.setRenderConfig(rc);
        // no extent so don't make it a missile/target
        return b;
    }

    ///////////////////////
    

    static private CircleConfig smokeTrail() {
        //return new CircleConfig(Color.GRAY, 7);
        //static Vector<Color> colors = new Vector<Color>();
        //int choice = rnd.nextInt(colors.length);
        //Color color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        return Renderer.getRealInstance().new CircleConfig(Color.GRAY, 7);
    }
   
    private static BlobIF createSmokeTrailBlob(BlobIF c) {
        BlobIF b = new ShrinkingCircleBlob(randomMass(), new BlobPosition(c.getPosition()), randomVelocity(),
                AccelFactory.smokeTrailAccel(), c.getRenderer(), smokeTrail());
        b = new BlobCrazyAccelDecorator(b);
        b.setLifeTime(25);
        return b;
    }
    
    // convenience when creating BlobTrailDecorators
    static public BlobSource smokeTrailBlobSource = new BlobSource() {
        @Override
        protected BlobIF generate(BlobIF parent) {
            WorldIF w = parent.getWorld();
            BlobIF st = createSmokeTrailBlob(parent);
            w.addBlobToWorld(st);
            return st;
        }
    };
    
    private static CircleConfig altSmokeTrail() {
        return Renderer.getRealInstance().new CircleConfig(Color.RED, 8);
    }
    private static BlobIF createAltSmokeTrailBlob(BlobIF c) {
        BlobIF b = new CircleBlob(0, new BlobPosition(c.getPosition()), randomVelocity(),
                AccelFactory.smokeTrailAccel(), c.getRenderer(), altSmokeTrail());
        b = rainbowColorCycler(b, 3);
        b = shrinker(b, 1);
        b.setLifeTime(15);
        b.setTickPause(2);
        return b;
    }
    static public BlobSource altSmokeTrailBlobSource = new BlobSource() {
        @Override
        protected BlobIF generate(BlobIF parent) {
            WorldIF w = parent.getWorld();
            BlobIF st = createAltSmokeTrailBlob(parent);
            w.addBlobToWorld(st);
            return st;
        }
    };
    
    static public BlobIF addSmokeTrail(BlobIF b) {
        return new BlobTrailDecorator(b, BlobFactory.smokeTrailBlobSource);
    }

    static public BlobIF addAltSmokeTrail(BlobIF b) {
        return new BlobTrailDecorator(b, altSmokeTrailBlobSource, 1);
    }
    
    static public BlobIF createTriangleSmokeTrailBlob(BlobIF parent) {
        TriangleConfig tc = Renderer.getRealInstance().new TriangleConfig(Color.BLUE, rnd.nextFloat() * 10 + 4);
        BlobIF b = new TriangleBlob(0, new BlobPosition(parent), randomVelocity(), 
                AccelFactory.smokeTrailAccel(), parent.getRenderer(), tc);
        b = shrinker(b, 1);
        b.setLifeTime(10);
        b.setTickPause(2);
        return b;
    }

    static public BlobSource triangleSmokeTrailBlobSource = new BlobSource() {
        @Override protected BlobIF generate(BlobIF parent) {
            WorldIF w = parent.getWorld();
            BlobIF st = createTriangleSmokeTrailBlob(parent);
            w.addBlobToWorld(st);
            return st;
        }
    };
    static public BlobIF addTriangleSmokeTrail(BlobIF b) {
        return new BlobTrailDecorator(b, triangleSmokeTrailBlobSource, 2);
    }

    static Color[] explosionColors = new Color[] {
        Color.RED, Color.ORANGE, Color.MAGENTA
    };

    static private CircleConfig explosionBlob() {
        Color color = explosionColors[rnd.nextInt(explosionColors.length)];
        return Renderer.getRealInstance().new CircleConfig(color, rnd.nextFloat() * 7 + 5);
    }
    public static BlobIF createExplosionBlob(BlobIF c) {
         BlobIF b = new ShrinkingCircleBlob(randomMass(), new BlobPosition(c.getPosition()), randomVelocity(),
                AccelFactory.explosionAccel(), c.getRenderer(), explosionBlob());
        //b = new BlobCrazyAccelDecorator(b);
        b.setWorld(c.getWorld());
        return b;       
    }
    
    // convenience when creating ExplosionBlobs
    static public BlobSource explosionBlobSource = new BlobSource() {
        @Override
        protected BlobIF generate(BlobIF parent) {
            WorldIF w = parent.getWorld();
            BlobIF eb = createExplosionBlob(parent);
            // neither target nor missile ("ephemeral")
            eb.setLifeTime(20);
            w.addBlobToWorld(eb);
            return eb;
        }
    };
    
    // returns a stationary ExplosionBlob at the same position as b
    static public BlobSource explosionSource = new BlobSource() {
        @Override protected BlobIF generate(BlobIF b) {
            Renderer r = b.getRenderer();
            ExplosionBlob ex = new ExplosionBlob(0, b.getPosition(), GameFactory.zeroVelocity(), AccelFactory.zeroAccel(), r);
            ex.setBlobSource(BlobFactory.explosionBlobSource);
            return ex;
        }
    };
  
    public static BlobIF invisibleBlob(WorldIF w, Renderer r) {
        BlobIF b = new NullBlob(PositionFactory.origin(), GameFactory.zeroVelocity(), AccelFactory.zeroAccel(), r);
        return b;
    }
 
    public static BlobIF offsetBlob(BlobIF b, BlobIF source, int x, int y) {
        b.setPosition(new PositionComposeDecorator(source.getPosition(), new BlobPosition(x,y)));
        return b;
    }

    public static BlobIF createTestBlob(WorldIF w, Renderer r, BlobTransform bt) {
        CircleConfig cc = r.new CircleConfig(randomColor(), 60);
        BlobIF b = new CircleBlob(0, randomPosition(100,600,100,800), zeroVelocity(), AccelFactory.zeroAccel(), r, cc);
        b.setLifeTime(1000000);
        b.setWorld(w);
        
        if (bt != null) {
            b = bt.transform(b);
        }

        w.addBlobToWorld(b);
        return b;       
    }
    
    public static BlobIF createTestCluster(WorldIF w, Renderer r) {
        BlobIF c = new BlobCluster(randomPosition(100,600,100,1000), PathFactory.stationary(), r);
        c.setLifeTime(1000000);
        c.setWorld(w);
        w.addBlobToWorld(c);
        return c;
    }

    public static BlobIF createTestBlobSet2(WorldIF w, Renderer r) {
        BlobPath p = PathFactory.stationary();
        BlobIF bs = new BlobSet2(0, randomPosition(100,600,100,1000), p.vel, p.acc, r);
        bs.setLifeTime(1000000);
        bs.setWorld(w);
        w.addBlobToWorld(bs);
        return bs;
    }

    public static BlobIF createDefaultBlob(WorldIF inWorld, Renderer r) {
        BlobIF b;
        if (rnd.nextInt(100) < 50) {
            b = new RectangleBlob(randomMass(), randomPosition(), randomVelocity(), randomAccel(), r);
            //b = new SplittingRectangleBlob(randomMass(), randomPosition(), randomVelocity(), randomAccel(), r);
        } else {
            b = new CircleBlob(randomMass(), randomPosition(), randomVelocity(), randomAccel(), r);
        }
        
            b = throbber(b);
        b.setWorld(inWorld);
        //b.setExtent(randomExtent());

        if (rnd.nextInt(100) < 10) {
            b = new BlobTrailDecorator(b, BlobFactory.smokeTrailBlobSource);
        }

        // possibly stack some decorators:
        if (rnd.nextInt(100) < 50) {
            b = new BlobCrazyAccelDecorator(b);
        }
        
        //b = new ShowExtentDecorator(b);

        inWorld.addBlobToWorld(b);
        return b;
    }
    
    private static Color blackOozeColor = new Color(0.2f, 0.2f, 0.2f, 0.0f);
    private static CircleConfig blackOozeCircle() {
        return Renderer.getRealInstance().new CircleConfig(blackOozeColor, 18.0f);
    };

    private static BlobIF createBlackOozeComponent(BlobIF parent) {
        //BlobPath p = PathFactory.stationary();
        BlobPath p = PathFactory.squarePath(5, 2);
        BlobIF o = new CircleBlob(0, null, p.vel, p.acc, parent.getRenderer(), blackOozeCircle());
        return o;
    }

    static public BlobSource blackOozeBlobSource = new BlobSource() {
        @Override
        protected BlobIF generate(BlobIF parent) {
            return createBlackOozeComponent(parent);
        }
    };
    
    private static RectConfig prizeRectangle() {
        return Renderer.getRealInstance().new RectConfig(randomColor(), 25, 25);
    }

    private static BlobIF createPrizeComponent(BlobIF parent) {
        //BlobPath p = PathFactory.stationary();
        BlobPath p = PathFactory.squarePath(5, 2);
        BlobIF b = new RectangleBlob(0, null, p.vel, p.acc, parent.getRenderer(), prizeRectangle());
        return b;
    }
    
    static public BlobSource prizeBlobSource = new BlobSource() {
        @Override
        public BlobIF generate(BlobIF parent) {
            return createPrizeComponent(parent);
        }
    };

    public static BlobIF createSpinnerBlobset(WorldIF inWorld, Renderer r, BlobSource blobSource, int numComponents, int posStep) {
        //BlobIF bs = new BlobSet(10, randomPosition(), zeroVelocity(), zeroAccel(), r);
        BlobIF bs = new BlobSet2(10, randomPosition(100,600,100,1000), zeroVelocity(), AccelFactory.zeroAccel(), r);
        bs.setWorld(inWorld);
        bs.setLifeTime(100000);
        
        // I feel like BlobSet child positioning can be done
        // with offset position instead of composing accel ?!
        while (numComponents > 0) {
            // put them all in the same place (BlobSet position)
            /*
            PositionIF pos = new BlobPosition(bs.getPosition());
            pos.setX(pos.getX() - numComponents * posStep);
            pos.setY(pos.getY() - numComponents * posStep);
            */

            ///// for BlobSet2 only:
            PositionIF pos = new BlobPosition(-numComponents * posStep, -numComponents * posStep);
            /////

            BlobIF o = blobSource.get(bs);

            o.setPosition(pos);
            // each of them will start moving at a different time:
            // this actually interacts badly with Blobsets...
            //o.setTickPause(numComponents);
            o.setLifeTime(100000);
            o.setWorld(inWorld);
            bs.absorbBlob(o);
            numComponents -= 1;
        }

        return bs;
    }
 
    public static BlobIF createOozeBlob(WorldIF inWorld, Renderer r) {
        return createSpinnerBlobset(inWorld, r, blackOozeBlobSource, 3, 1);
    }
    
    public static BlobIF createPrizeBlob(WorldIF inWorld, Renderer r) {
        return createSpinnerBlobset(inWorld, r, prizeBlobSource, 4, 3);
    }
   
    public static BlobIF throbber(BlobIF in) {
        // floating point doesn't drift us here thankfully
        int[][] entries = new int[][] {
                { 1250, 1 },
                { 1250, 2 },
                { 1250, 1 },
                { 800, 1 },
                { 800, 2 },
                { 800, 1 },
        };
        return new BlobScaleDecorator(in, entries);
    }
    
    // I'm melting... melting
    public static BlobIF shrinker(BlobIF in, int interval) {
        int[][] entries = new int[][] {
                { 950, interval },
        };
        return new BlobScaleDecorator(in, entries);       
    }

    public static BlobIF grower(BlobIF in, int interval) {
        int[][] entries = new int[][] {
                { 1250, interval },
        };
        return new BlobScaleDecorator(in, entries);       
    }
      
    public static BlobIF rainbowColorCycler(BlobIF in, int interval) {
        ColorDecoratorEntry[] entries = new ColorDecoratorEntry[]  {
                new ColorDecoratorEntry(Color.RED, interval),
                new ColorDecoratorEntry(Color.ORANGE, interval),
                new ColorDecoratorEntry(Color.YELLOW, interval),
                new ColorDecoratorEntry(Color.GREEN, interval),
                new ColorDecoratorEntry(Color.CYAN, interval),
                new ColorDecoratorEntry(Color.BLUE, interval),
                new ColorDecoratorEntry(Color.PINK, interval),
        };
        
        return new BlobRenderColorDecorator(in, entries);
    }
    
    public static BlobIF flashColorCycler(BlobIF in, int interval) {
     int[][] entries = new int[][] {
                { 1250, interval },
                { 1250, interval },
                { 1250, interval },
                { 1250, interval },
                { 1250, interval },
                { 800, interval },
                { 800, interval },
                { 800, interval },
                { 800, interval },
                { 800, interval },
        };       
        return new BlobRenderColorScaleDecorator(in, entries);
    }
       
    public static BlobIF createNineCluster(PositionIF pos, BlobPath path, BlobSource source, int distance, WorldIF w, Renderer r) {
        
        int[][] entries = new int[][] {
                // above
                { distance,  distance },
                { 0,   distance },
                { -distance, distance },
                
                // inline (0 "y" offset)
                { distance,  0 },
                { 0,   0 },
                { -distance, 0 },

                // below
                { distance,  -distance },
                { 0,   -distance },
                { -distance, -distance },
        };

        return createCluster(pos, path, source, w, r, entries);
    }
        
    public static BlobIF createFourCluster(PositionIF pos, BlobPath path, BlobSource source, int distance, WorldIF w, Renderer r) {
        
        int[][] entries = new int[][] {
                // above
                { distance,  distance },
                { -distance, distance },

                // below
                { distance,  -distance },
                { -distance, -distance },
        };

        return createCluster(pos, path, source, w, r, entries);

    }
    
    private static double sqrtOf2 = Math.sqrt(2);
    public static BlobIF createThreeCluster(PositionIF pos, BlobPath path, BlobSource source, int distance, WorldIF w, Renderer r) {
        
        int adjustedDistance = (int)((double)distance/sqrtOf2);

        int[][] entries = new int[][] {
                // above
                { 0,  distance },
                // beside left
                { -adjustedDistance, -adjustedDistance },
                // beside right
                { adjustedDistance, -adjustedDistance },
        };

        return createCluster(pos, path, source, w, r, entries);
    }
      
         
    // returns the invisible Blob that is at the middle of the cluster. If you want to add
    // triggers etc to the Blobs that will be in the cluster then it has to be done
    // in the BlobSource. Lack of closures makes that a pain in the ass.
    public static BlobIF createCluster(PositionIF pos, BlobPath path, BlobSource source, WorldIF w, Renderer r, int[][] offsets) {
        ClusterIF key = new BlobCluster(pos, path, r);

        key.setWorld(w);
        
        for (int i = 0; i < offsets.length; i++) {
            BlobIF b = source.get(key);
            b.setPosition(new BlobPosition(offsets[i][0], offsets[i][1]));
            PathFactory.composePositions(b, key);
            b.setCluster(key);
            key.absorbBlob(b);
        }
        
        return key;  
    }
  
    static public BlobSource nullBlobSource(BlobTrigger t, BlobTransform tr) {
        BlobSource n = new BlobSource(t, tr) {
            @Override
            public BlobIF generate(BlobIF parent) {
                Renderer r = parent.getRenderer();
                BlobIF n = new NullBlob(r);
                return n;
            }
        };
        return n;
    }
}
