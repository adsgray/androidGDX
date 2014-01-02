package com.github.adsgray.gdxtry1.testgame1.config;

import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.testgame1.TargetUtils;

public class EasyGameConfig extends BaseGameConfig implements GameConfigIF {

    public EasyGameConfig() {
        numEnemies = 4;
        shieldScoreIncrement = 250;
        bossScoreIncrement = 1000000000; // never? James doesn't like Bosses.
        bonusDropperChance = 5;
        bonusDeathChance = 100;
        bonusDropperBossPointDiff = bossScoreIncrement; // so always?
        damageDefender = true; // can't make it *too* easy
        bossFireRate = 2;
        bonusDropSpeed = -7; // make them drop slower
        bonusDropperLifeTime = 1000;
        shieldLifeTime = 500;
        shieldsUpOverride = true; // no limits on deploying shields
        defaultEnemyFireLoop = TargetUtils.fireAtDefenderLoop(2000, TargetUtils.targetMissileSource, 1);
        angryEnemyFireLoop = TargetUtils.fireAtDefenderLoop(500, TargetUtils.angryTargetMissileSource, 1);
        defaultEnemyBombVel = new BlobVelocity(0, -7);
    }

    @Override
    public VelocityIF angryEnemyBombVel() {
        return new BlobVelocity(0,-10);
    }
}
