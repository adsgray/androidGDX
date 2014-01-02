package com.github.adsgray.gdxtry1.testgame1.config;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public interface GameConfigIF {
    
    public int numEnemies();
    public int bonusDropperChance();
    public int bossScoreIncrement();
    public int shieldScoreIncrement();
    public Boolean damageDefender();

    public Boolean dropBonusOnDeath(); // for enemies
    public BlobTrigger defaultEnemyFireLoop();
    public BlobTrigger angryEnemyFireLoop();
    public VelocityIF defaultEnemyBombVel();
    public VelocityIF angryEnemyBombVel(); // must not be shared (must be constructed)
    public BlobTrigger bossEnemyFireLoop(PositionIF target);
    
    public int bonusDropSpeed();
    public int bonusDropperLifeTime();
    public int bonusDropperBossPointDiff();
    public int shieldLifeTime();
    
    public Boolean shieldsUpOverride();
    public int shieldTickInterval();
    
    public enum Difficulty {
        easy,
        normal,
        insane
    }
}
