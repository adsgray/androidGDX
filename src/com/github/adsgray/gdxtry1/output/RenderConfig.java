package com.github.adsgray.gdxtry1.output;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    

    // Unashamedly using this like a C struct.
    // 
    public static class RectConfig {
        public Color color;
        public float w;
        public float h;
    }
    
    public RectConfig randomRectConfig() {
        RectConfig rc = new RectConfig();
        rc.color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        rc.w = rnd.nextInt(100);
        rc.h = rnd.nextInt(100);
        return rc;
    }
    
    public static class CircleConfig {
        public CircleConfig(Color color, float radius) {
            this.color = color;
            this.radius = radius;
        }
        public CircleConfig() {}

        public Color color;
        public float radius;
    }

    public CircleConfig randomCircleConfig() {
        CircleConfig cc = new CircleConfig();
        cc.color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        cc.radius = rnd.nextInt(100);
        return cc;
    }

    public void renderRect(BlobIF b, RectConfig rc) {
        shapeRenderer.setColor(rc.color);
        shapeRenderer.rect(b.getPosition().getX(), b.getPosition().getY(), rc.w, rc.h);
    }
    
    public void renderCircle(BlobIF b, CircleConfig cc) {
        shapeRenderer.setColor(cc.color);
        shapeRenderer.circle(b.getPosition().getX(), b.getPosition().getY(), cc.radius);
    }
}
