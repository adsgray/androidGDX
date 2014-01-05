package com.github.adsgray.gdxtry1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
//import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

    private OnClickListener playButtonListener = new OnClickListener() {
        @Override public void onClick(View arg0) {
            // initialize a new instance of your Game class
            //initialize(new MainPanel(getApplicationContext()), false); 
            Log.d("trace", "play button tapped");
            Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("trace", "onCreate!");
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        setContentView(R.layout.activity_main);
        
        Button playbutton = (Button)findViewById(R.id.play_button);
        playbutton.setOnClickListener(playButtonListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
