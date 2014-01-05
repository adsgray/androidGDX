package com.github.adsgray.gdxtry1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
//import android.app.Activity;
import android.view.Menu;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class MainActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("trace", "onCreate!");
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
		initialize(new GameScreen(context), false);		// initialize a new instance of your Game class
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
