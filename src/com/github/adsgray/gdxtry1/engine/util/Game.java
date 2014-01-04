package com.github.adsgray.gdxtry1.engine.util;


public interface Game {

    public void init();
    public void start();
    public void stop();
    
    public GameCommand getSoundToggle();
    public GameCommand getDifficultySetter();

    public int getFinalScore();

    public GameState getState();
    public void restoreState(GameState state);

    // must be serializable?
    public interface GameState {
    }
}
