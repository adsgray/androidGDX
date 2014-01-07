package com.github.adsgray.gdxtry1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class GameActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        Log.d("trace", "GameActivity onCreate!");
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        int difficultyLevel =  extras.getInt("DIFFICULTY_LEVEL");
        // int resumeGame = extras.getInt("RESUME_GAME");
		initialize(new GameScreen(context, difficultyLevel), false);		// initialize a new instance of your Game class
    }

}
