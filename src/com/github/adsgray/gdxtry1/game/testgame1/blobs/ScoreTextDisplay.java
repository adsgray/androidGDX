package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import java.util.Locale;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.blob.BaseTextBlob;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.RenderConfigIF;

public class ScoreTextDisplay extends BaseTextBlob {

    protected int numShields = 0;
    protected int score = 0;
    protected int hitPoints = 0;

    public ScoreTextDisplay(PositionIF posin, VelocityIF velin, AccelIF accel,
            Renderer gdx, RenderConfigIF rc) {
        super(posin, velin, accel, gdx, rc);
    }
    
    private void updateTxt() {
        txt = String.format("Score: %5d   HitPoints: %2d   Shields: %2d", score, hitPoints, numShields);
    }

    public int setScore(int s) { score = s; updateTxt(); return score; }
    public int incScore(int s) { return setScore(score + s); }

    public int setNumShields(int ns) { numShields = ns; updateTxt(); return numShields; }
    public int incNumShields(int ns) { return setNumShields(ns + numShields); }

    public int setHitPoints(int s) { hitPoints = s; updateTxt(); return hitPoints; }
    public int incHitPoints(int s) { return setHitPoints(score + s); }
    
    /*
    @Override public String getText() {
        return String.format("Score: %5d Shields: %2d", score, numShields);
    }
    */
    //@Override public void render() {}

}
