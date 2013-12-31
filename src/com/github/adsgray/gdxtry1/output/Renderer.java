package com.github.adsgray.gdxtry1.output;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.TextBlobIF;
import com.github.adsgray.gdxtry1.game.GameFactory;


public class Renderer {
 
    private static Random rnd = new Random();
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    
    public Renderer() { }
    public Renderer(ShapeRenderer sr, SpriteBatch sb) {
        shapeRenderer = sr;
        spriteBatch = sb;
    }
    
    /// singleton crap ///
    private static Renderer realInstance;
    private static Renderer testInstance;
    public static Renderer createRealInstance(ShapeRenderer sr, SpriteBatch sb) {
        if (realInstance == null) {
            realInstance = new Renderer(sr, sb);
        }
        return realInstance;
    }
    public static Renderer getRealInstance() { return realInstance; }
    public static Renderer getTestInstance() {
        if (testInstance == null) {
            testInstance = new Renderer();
        }
        return testInstance;
    }
    /// ///
    
    public interface RenderConfigIF {
        public void scale(float factor);
        public void setColor(Color c);
        public void scaleColor(float factor);
        public void render(BlobIF b);
    }
    
    public RenderConfigIF nullRenderConfig = new RenderConfigIF() {
        @Override public void scale(float factor) { }
        @Override public void setColor(Color c) { }
        @Override public void scaleColor(float factor) { }
        @Override public void render(BlobIF b) { }
    };
    
    public abstract class BaseRenderConfig implements RenderConfigIF {
        public Color color;
        public ShapeType shapeType;
        
        public BaseRenderConfig() { shapeType = ShapeType.Filled; }
        public BaseRenderConfig(Color color) { this(); this.color = color; }

        // scale(float factor) must be implemented by subclasses

        @Override public void setColor(Color c) { color = c; }
        @Override public void scaleColor(float factor) { 
            color = new Color(color.r * factor, color.g * factor, color.b * factor, color.a);
        }
    }
    
     
    public class BlobSetRenderConfig implements RenderConfigIF {
        protected List<BlobIF> objs;
        
        public BlobSetRenderConfig(List<BlobIF> objs) { this.objs = objs; }
        public List<BlobIF> setObjs(List<BlobIF> objs) { this.objs = objs; return objs; }
        public List<BlobIF> getObjs() { return objs; }

        @Override
        public void scale(float factor) {
            Iterator<BlobIF> iter = objs.iterator();
            while (iter.hasNext()) {
                iter.next().getRenderConfig().scale(factor);
            }
        }

        // just clobber all the children's colors
        @Override
        public void setColor(Color c) { 
            Iterator<BlobIF> iter = objs.iterator();
            while (iter.hasNext()) {
                iter.next().getRenderConfig().setColor(c);
            }
        }

        @Override
        public void scaleColor(float factor) {
            Iterator<BlobIF> iter = objs.iterator();
            while (iter.hasNext()) {
                iter.next().getRenderConfig().scaleColor(factor);
            }
        }
        @Override public void render(BlobIF b) {
            Iterator<BlobIF> iter = objs.iterator();
            while (iter.hasNext()) {
                BlobIF child = iter.next();
                child.getRenderConfig().render(child);
            }
        }
    }

    // Unashamedly using this like a C struct.
    public class RectConfig extends BaseRenderConfig {
        public float w;
        public float h;
        
        public RectConfig() {}
        public RectConfig(Color color, float w, float h) {
            super(color);
            this.w = w;
            this.h = h;
        }

        @Override public void scale(float factor) { w *= factor; h *= factor; }
        @Override
        public void render(BlobIF b) {
            shapeRenderer.begin(shapeType);
            shapeRenderer.setColor(color);
            shapeRenderer.rect(b.getPosition().getX() - w / 2, b.getPosition().getY() - h / 2, w, h);
            shapeRenderer.end();
        } 
    }
    
    public RectConfig randomRectConfig() {
        RectConfig rc = new RectConfig();
        rc.color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        rc.w = rnd.nextInt(100);
        rc.h = rc.w + rnd.nextInt(20) - rnd.nextInt(20);
        return rc;
    }
    
    public class CircleConfig extends BaseRenderConfig {
        public CircleConfig(Color color, float radius) {
            super(color);
            this.radius = radius;
        }

        public CircleConfig() {}
        public float radius;

        @Override public void scale(float factor) { radius *= factor; }

        @Override
        public void render(BlobIF b) {
            shapeRenderer.begin(shapeType);
            shapeRenderer.setColor(color);
            shapeRenderer.circle(b.getPosition().getX(), b.getPosition().getY(), radius);
            shapeRenderer.end();
        }
    }
    
    private static double sqrtOf2 = Math.sqrt(2);
    public class TriangleConfig extends CircleConfig {
        public TriangleConfig(Color c, float radius) {
            super(c,radius);
        }
        
        public TriangleConfig() {}

        @Override
        public void render(BlobIF b) {
            shapeRenderer.begin(shapeType);
            shapeRenderer.setColor(color);
            int bx = b.getPosition().getX();
            int by = b.getPosition().getY();
            shapeRenderer.triangle((float)bx, (float)(by + radius), 
                               (float)(bx - radius / sqrtOf2), (float)(by - radius / sqrtOf2),
                               (float)(bx + radius / sqrtOf2), (float)(by - radius / sqrtOf2));
            //Color.RED, Color.GREEN, Color.BLUE);
            shapeRenderer.end(); 
        }
    }
    
    public class TextConfig extends BaseRenderConfig {
        public float scaleamount;

        public TextConfig(Color color, float scaleamount) {
            super(color);
            this.scaleamount = scaleamount;
        }

        @Override
        public void scale(float factor) {
            scaleamount *= factor;
        }

        @Override
        public void render(BlobIF b) {
            if (b instanceof TextBlobIF) {
                // have to do this every time??
                BitmapFont bitmapFont = new BitmapFont();
                TextBlobIF t = (TextBlobIF)b;
                spriteBatch.begin();
                bitmapFont.scale(scaleamount);
                bitmapFont.setColor(color);
                bitmapFont.setUseIntegerPositions(false);
                bitmapFont.draw(spriteBatch, t.getText(), b.getPosition().getX(), b.getPosition().getY());
                spriteBatch.end();
            }
        }
        
    }
    
    /*
    public static class TriangleConfig extends BaseRenderConfig {
        float height;
        float width;
        public TriangleConfig(Color color, float height, float width) {
            super(color);
            this.height = height;
            this.width = width;
        }
        
    }
    
    public TriangleConfig randomTriangleConfig() {
        TriangleConfig tc = new TriangleConfig(GameFactory.randomColor(), rnd.nextFloat() * 50, rnd.nextFloat() * 50);
        return tc;
    }
    */

    public CircleConfig randomCircleConfig() {
        CircleConfig cc = new CircleConfig();
        cc.color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        cc.radius = rnd.nextInt(100);
        return cc;
    }
    
    public TriangleConfig randomTriangleConfig() {
        TriangleConfig tc = new TriangleConfig();
        tc.color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        tc.radius = rnd.nextInt(100);
        return tc;
    }
    

    // http://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/glutils/ShapeRenderer.html
}
