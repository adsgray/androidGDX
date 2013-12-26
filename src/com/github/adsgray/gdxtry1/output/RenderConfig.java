package com.github.adsgray.gdxtry1.output;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.adsgray.gdxtry1.engine.BlobIF;

public class RenderConfig {
 
    private static Random rnd = new Random();
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    
    public RenderConfig() { }

    public RenderConfig(ShapeRenderer sr, SpriteBatch sb) {
        shapeRenderer = sr;
        spriteBatch = sb;
    }
    
    public static interface RenderConfigIF {
        public void scale(float factor);
        public void setColor(Color c);
        public void scaleColor(float factor);
    }
    
    public static abstract class BaseRenderConfig implements RenderConfigIF {
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
    
     
    public static class BlobSetRenderConfig implements RenderConfigIF {
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
    }

    // Unashamedly using this like a C struct.
    public static class RectConfig extends BaseRenderConfig {
        public float w;
        public float h;
        
        public RectConfig() {}
        public RectConfig(Color color, float w, float h) {
            super(color);
            this.w = w;
            this.h = h;
        }

        @Override public void scale(float factor) { w *= factor; h *= factor; } 
    }
    
    public RectConfig randomRectConfig() {
        RectConfig rc = new RectConfig();
        rc.color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        rc.w = rnd.nextInt(100);
        rc.h = rc.w + rnd.nextInt(20) - rnd.nextInt(20);
        return rc;
    }
    
    public static class CircleConfig extends BaseRenderConfig {
        public CircleConfig(Color color, float radius) {
            super(color);
            this.radius = radius;
        }

        public CircleConfig() {}
        public float radius;

        @Override public void scale(float factor) { radius *= factor; }
    }

    public CircleConfig randomCircleConfig() {
        CircleConfig cc = new CircleConfig();
        cc.color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        cc.radius = rnd.nextInt(100);
        return cc;
    }

    // http://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/glutils/ShapeRenderer.html
    // TODO: change rectangle rendering so that position is centre of rectangle
    public void renderRect(BlobIF b, RectConfig rc) {
        shapeRenderer.begin(rc.shapeType);
        shapeRenderer.setColor(rc.color);
        
        
        shapeRenderer.rect(b.getPosition().getX() - rc.w / 2, b.getPosition().getY() - rc.h / 2, rc.w, rc.h);
        shapeRenderer.end();
    }
    
    public void renderCircle(BlobIF b, CircleConfig cc) {
	    shapeRenderer.begin(cc.shapeType);
        shapeRenderer.setColor(cc.color);
        shapeRenderer.circle(b.getPosition().getX(), b.getPosition().getY(), cc.radius);
        shapeRenderer.end();
    }
}
