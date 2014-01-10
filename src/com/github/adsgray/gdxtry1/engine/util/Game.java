package com.github.adsgray.gdxtry1.engine.util;


public interface Game {

    public void init();
    public void start();
    public void save(); // save state to SavedGame
    public void stop();
    
    public GameCommand getSoundToggle();
    public GameCommand getVibrateToggle();
    public GameCommand getDifficultySetter();

    public int getFinalScore();
    
}
