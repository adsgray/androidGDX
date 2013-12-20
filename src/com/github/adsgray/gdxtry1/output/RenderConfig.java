package com.github.adsgray.gdxtry1.output;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class RenderConfig {
 
    private static Random rnd = new Random();

    // Unashamedly using this like a C struct.
    // 
    public static class RectConfig {
        public Color color;
        public float w;
        public float h;
    }
    
    public static RectConfig randomRectConfig() {
        RectConfig rc = new RectConfig();
        rc.color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        rc.w = rnd.nextInt(100);
        rc.h = rnd.nextInt(100);
        return rc;
    }

}
