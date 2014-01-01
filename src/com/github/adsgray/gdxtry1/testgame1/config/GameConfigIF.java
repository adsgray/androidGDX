package com.github.adsgray.gdxtry1.testgame1.config;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

public interface GameConfigIF {
    
    public int numEnemies();
    public int bonusDropperChance();
    public int bossScoreIncrement();
    public int shieldScoreIncrement();
    public Boolean damageDefender();

    public Boolean dropBonusOnDeath(); // for enemies
    public BlobTrigger defaultEnemyFireLoop();
    public BlobTrigger bossEnemyFireLoop(PositionIF target);
    
    public int bonusDropSpeed();
    public int bonusDropperLifeTime();
    public int shieldLifeTime();
    
    public Boolean shieldsUpOverride();
    public int shieldTickInterval();
    
    public enum Difficulty {
        easy,
        normal,
        insane
    }
}
