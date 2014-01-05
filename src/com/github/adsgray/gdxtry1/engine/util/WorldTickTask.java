package com.github.adsgray.gdxtry1.engine.util;

import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.github.adsgray.gdxtry1.engine.WorldIF;

public class WorldTickTask {

    // apparently TimerTask is obsolete??
    static TimerTask instance;
    public abstract static class WorldTask extends TimerTask {
        WorldIF w;
        public WorldTask(WorldIF w) { this.w = w; }
    }
    
    public static TimerTask createInstance(WorldIF w) {
        if (instance == null) {
            instance = new WorldTask(w) {
                @Override
                public void run() {
                    w.tick();
                    Gdx.graphics.requestRendering();
                }
            };
        }
        return instance;
    }
    
    public static TimerTask get() { return instance; }
}
