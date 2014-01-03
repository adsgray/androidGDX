package com.github.adsgray.gdxtry1.testgame1.config;

import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.testgame1.TargetUtils;

public class InsaneGameConfig extends BaseGameConfig implements GameConfigIF {

   public InsaneGameConfig() {
        numEnemies = 8;
        shieldScoreIncrement = 800;
        bossScoreIncrement = 1200; 
        bonusDropperChance = 5;
        bonusDropperBossPointDiff = 250; 
        bonusDeathChance = 5;
        damageDefender = true; 
        bossFireRate = 3;
        bonusDropSpeed = -11; 
        bonusDropperLifeTime = 300;
        shieldLifeTime = 125;
        shieldsUpOverride = false; 
        shieldTickInterval = 175;
        defaultEnemyFireLoop = TargetUtils.fireAtDefenderLoop(800, TargetUtils.targetMissileSource, 1);
        angryEnemyFireLoop = TargetUtils.fireAtDefenderLoop(250, TargetUtils.angryTargetMissileSource, 2);
        defaultEnemyBombVel = new BlobVelocity(0, -13);
    }

    @Override
    public VelocityIF angryEnemyBombVel() {
        return new BlobVelocity(0,-18);
    }
}

