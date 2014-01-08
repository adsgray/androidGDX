package com.github.adsgray.gdxtry1.engine.util;

// wrapper around Local or Google Play scores
public interface HighScoreSaveIF {
    public boolean submitScore(String key, int score);
}
