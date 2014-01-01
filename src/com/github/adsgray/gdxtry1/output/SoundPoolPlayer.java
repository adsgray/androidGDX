package com.github.adsgray.gdxtry1.output;

import java.util.Random;

import com.github.adsgray.gdxtry1.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SoundPoolPlayer implements SoundIF {

    private SoundPool sounds;

    // this seems gross but so does using a HashMap (that would be slower??
    private int sShoot;
    private int[] eFire;
    private int[] explosion;
    private int sEnemyCreated;
    private int sNoShield;
    private int sBecomeAngry;
    private int sBonusDrop;
    private int sBonusDropper;
    private int sBonusReceive;
    private int sBonusShield;
    private int sBossDie;
    private int sDefenderHit;
    private int sWelcome;

    Context context;
    Random rnd;

    public SoundPoolPlayer(Context context) {
        this.context = context;
        rnd = new Random();
        sounds = new SoundPool(20, AudioManager.STREAM_MUSIC,0);
        sShoot = sounds.load(context, R.raw.shoot1, 1);
        eFire = new int[] {
                sounds.load(context, R.raw.enemyfire1, 1),
                sounds.load(context, R.raw.enemyfire2, 1),
                sounds.load(context, R.raw.enemyfire3, 1)
        };
        explosion = new int[] {
                sounds.load(context, R.raw.explosion, 1),
                sounds.load(context, R.raw.explosionshort, 1),
                sounds.load(context, R.raw.explosionshort2, 1)
        };
        sEnemyCreated = sounds.load(context, R.raw.enemycreated, 1);
        sNoShield = sounds.load(context, R.raw.noshield, 1);
        sBecomeAngry = sounds.load(context, R.raw.becomeangry, 1); 
        
        sBonusDrop = sounds.load(context, R.raw.bonusdrop, 1); 
        sBonusDropper = sounds.load(context, R.raw.bonusdropper, 1); 
        sBonusReceive = sounds.load(context, R.raw.bonusreceive, 1); 
        sBonusShield = sounds.load(context, R.raw.bonusshield, 1); 

        sBossDie = sounds.load(context, R.raw.bossdie, 1); 
        sDefenderHit = sounds.load(context, R.raw.defenderhit, 1); 
        sWelcome = sounds.load(context, R.raw.welcome, 1); 

    }

    @Override
    public void crash(Integer intensity) {
        // TODO Auto-generated method stub
    }

    @Override public void shoot() {
        Log.d("sound", "playing shoot?");
        sounds.play(sShoot, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    private void playRandom(int[] soundlist) {
        int idx = rnd.nextInt(soundlist.length);
        sounds.play(soundlist[idx], 1.0f, 1.0f, 0, 0, 1.5f);
    }

    /*
    enemyfire1.mp3
    enemyfire2.mp3
    enemyfire3.mp3
    */
    @Override
    public void enemyfire() {
        playRandom(eFire);
    }

    /*
    explosion.mp3
    explosionshort.mp3
    explosionshort2.mp3
    */
    @Override
    public void explosion() {
        playRandom(explosion);
    }

    @Override
    public void enemycreated() {
        sounds.play(sEnemyCreated, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    @Override
    public void shieldDenied() {
        sounds.play(sNoShield, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    @Override
    public void enemyBecomeAngry() {
        sounds.play(sBecomeAngry, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    @Override
    public void bonusDrop() {
        sounds.play(sBonusDrop, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    @Override
    public void bonusDropperAppear() {
        sounds.play(sBonusDropper, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    @Override
    public void bonusReceive() {
        sounds.play(sBonusReceive, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    @Override
    public void bonusShieldReceive() {
        sounds.play(sBonusShield, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    @Override
    public void bossDie() {
        sounds.play(sBossDie, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    @Override
    public void defenderHit() {
        sounds.play(sDefenderHit, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    @Override
    public void welcome() {
        sounds.play(sWelcome, 1.0f, 1.0f, 0, 0, 1.5f);
    }

}
