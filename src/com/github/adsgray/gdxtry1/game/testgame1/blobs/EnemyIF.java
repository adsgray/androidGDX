package com.github.adsgray.gdxtry1.game.testgame1.blobs;

public interface EnemyIF {
    
    public enum Type {
        Initial, Angry
    }
    
    public void becomeAngry();
    public Type getType();
}
