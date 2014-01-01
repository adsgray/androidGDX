package com.github.adsgray.gdxtry1.testgame1.config;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.testgame1.BossTargetMissileSource;
import com.github.adsgray.gdxtry1.testgame1.TargetUtils;

// this is the "normal" config
public class BaseGameConfig implements GameConfigIF {

    protected int numEnemies;
    protected int bonusDropperChance;
    protected int bossScoreIncrement;
    protected int shieldScoreIncrement;
    protected Boolean damageDefender;
    protected int bonusDeathChance = 25;
    protected BlobTrigger defaultEnemyFireLoop;
    protected int bossFireRate;
    protected int bonusDropSpeed;
    protected int bonusDropperLifeTime;
    protected int bonusDropperBossPointDiff; // when you're within this many points of a boss, you might get a bonusDropper
    protected int shieldLifeTime;
    // this is how long you have to wait until you can put shields up again.
    protected int shieldTickInterval;
    protected Boolean shieldsUpOverride;


    public BaseGameConfig() {
        numEnemies = 6;
        shieldScoreIncrement = 500;
        bossScoreIncrement = 1500; // you'll meet a boss every 1500 points
        bonusDropperChance = 5;
        damageDefender = true;
        defaultEnemyFireLoop = TargetUtils.defaultEnemyFireLoop;
        bossFireRate = 2;
        bonusDropSpeed = -9;
        bonusDropperLifeTime = 400;
        bonusDropperBossPointDiff = 500;
        shieldLifeTime = 150;
        shieldTickInterval = 150;
        shieldsUpOverride = false;
    }

    @Override public int numEnemies() { return numEnemies; }
    @Override public int bonusDropperChance() { return bonusDropperChance; }
    @Override public int bossScoreIncrement() { return bossScoreIncrement; }
    @Override public int shieldScoreIncrement() { return shieldScoreIncrement; }
    @Override public Boolean damageDefender() { return damageDefender; }

    @Override
    public Boolean dropBonusOnDeath() {
        return (TargetUtils.rnd.nextInt(100) < bonusDeathChance);
    }

    @Override
    public BlobTrigger defaultEnemyFireLoop() {
        return defaultEnemyFireLoop;
    }

    @Override
    public BlobTrigger bossEnemyFireLoop(PositionIF target) {
        return TargetUtils.fireAtDefenderLoop(250, new BossTargetMissileSource(target), bossFireRate);
    }

    @Override public int bonusDropSpeed() { return bonusDropSpeed; }
    @Override public int bonusDropperLifeTime() { return bonusDropperLifeTime; }
    @Override public int bonusDropperBossPointDiff() { return bonusDropperBossPointDiff; }
    @Override public int shieldLifeTime() { return shieldLifeTime; }
    @Override public int shieldTickInterval() { return shieldTickInterval; }
    @Override public Boolean shieldsUpOverride() { return shieldsUpOverride; }
}
