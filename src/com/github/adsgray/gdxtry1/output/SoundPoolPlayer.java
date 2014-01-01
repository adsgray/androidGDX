package com.github.adsgray.gdxtry1.output;

import com.github.adsgray.gdxtry1.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SoundPoolPlayer implements SoundIF {

    private SoundPool sounds;
    private int sShoot;
    Context context;

    public SoundPoolPlayer(Context context) {
        this.context = context;
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        sShoot = sounds.load(context, R.raw.shoot1, 1);
    }

    @Override
    public void crash(Integer intensity) {
        // TODO Auto-generated method stub
    }

    @Override public void shoot() {
        Log.d("sound", "playing shoot?");
        sounds.play(sShoot, 1.0f, 1.0f, 0, 0, 1.5f);
    }

}
