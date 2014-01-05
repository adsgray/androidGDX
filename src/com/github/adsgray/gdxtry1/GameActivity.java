package com.github.adsgray.gdxtry1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class GameActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("trace", "onCreate!");
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
		initialize(new GameScreen(context), false);		// initialize a new instance of your Game class
    }

}
