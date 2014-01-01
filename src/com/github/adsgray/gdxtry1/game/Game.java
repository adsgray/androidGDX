package com.github.adsgray.gdxtry1.game;


public interface Game {

    public void init();
    public void start();
    public void stop();
    
    public GameCommand getSoundToggle();

    public int getFinalScore();
}
