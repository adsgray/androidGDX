package com.github.adsgray.gdxtry1.testgame1.config;

import com.github.adsgray.gdxtry1.testgame1.TargetUtils;

public class EasyGameConfig extends BaseGameConfig implements GameConfigIF {

    public EasyGameConfig() {
        numEnemies = 3;
        shieldScoreIncrement = 250;
        bossScoreIncrement = 1000000000; // never? James doesn't like Bosses.
        bonusDropperChance = 5;
        bonusDropperBossPointDiff = bossScoreIncrement; // so always?
        damageDefender = false;
        defaultEnemyFireLoop = TargetUtils.defaultEnemyFireLoop;
        bossFireRate = 2;
        bonusDropSpeed = -7; // make them drop slower
        bonusDropperLifeTime = 1000;
        shieldLifeTime = 500;
        shieldsUpOverride = true; // no limits on deploying shields
    }
}
