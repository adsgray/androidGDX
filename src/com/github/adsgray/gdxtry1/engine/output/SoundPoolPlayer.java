package com.github.adsgray.gdxtry1.engine.output;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPoolPlayer implements SoundIF {

    private SoundPool sounds;
    Context context;
    private float rate;

    public SoundPoolPlayer(Context context, float rate) {
        this.context = context;
        sounds = new SoundPool(20, AudioManager.STREAM_MUSIC,0);
        this.rate = rate;
    }

    public SoundPoolPlayer(Context context) {
      this(context, 1.5f);
    }
    @Override public int load(int resid) {
        return sounds.load(context, resid, 1);
    }
    
    // sound must have been loaded and returned by load()
    @Override public void play(int soundid) {
        sounds.play(soundid, 1.0f, 1.0f, 0, 0, rate);
    }

    @Override public void play(Integer soundid) { play((int)soundid); }
}
