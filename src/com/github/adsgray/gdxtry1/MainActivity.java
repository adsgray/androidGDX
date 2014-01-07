package com.github.adsgray.gdxtry1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
//import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


public class MainActivity extends Activity {

    protected int difficultyLevel = 1;

    private OnClickListener playButtonListener = new OnClickListener() {
        @Override public void onClick(View arg0) {
            // initialize a new instance of your Game class
            //initialize(new MainPanel(getApplicationContext()), false); 
            Log.d("trace", "play button tapped");
            Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
            myIntent.putExtra("DIFFICULTY_LEVEL", difficultyLevel);
            MainActivity.this.startActivity(myIntent);
        }
    };


    protected void setFontOnText() {
        TextView[] textIds = new TextView[] {
                (TextView) findViewById(R.id.instructions),
                (RadioButton) findViewById(R.id.difficulty_easy),
                (RadioButton) findViewById(R.id.difficulty_normal),
                (RadioButton) findViewById(R.id.difficulty_hard),
                (Button) findViewById(R.id.play_button)
        };

        Typeface unispace = Typeface.createFromAsset(getAssets(),"data/unispace.ttf");
        
        for (int ct = 0; ct < textIds.length; ct++) {
            textIds[ct].setTypeface(unispace);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("trace", "onCreate!");
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();

        setContentView(R.layout.activity_main);
        setFontOnText();
        
        Button playbutton = (Button)findViewById(R.id.play_button);
        playbutton.setOnClickListener(playButtonListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        
        switch (view.getId()) {
            case R.id.difficulty_easy:
                if (checked) {
                    difficultyLevel = 0;
                }
                break;
            case R.id.difficulty_normal:
                if (checked) {
                    difficultyLevel = 1;
                }
                break;
            case R.id.difficulty_hard:
                if (checked) {
                    difficultyLevel = 2;
                }
                break;
        }
    }
    
}
