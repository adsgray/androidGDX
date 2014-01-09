package com.github.adsgray.gdxtry1;

import com.github.adsgray.gdxtry1.engine.util.LocalHighScore;
import com.github.adsgray.gdxtry1.testgame1.config.GamePreferences;

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
import android.widget.Toast;


public class MainActivity extends Activity {

    protected int difficultyLevel = 1;
    protected final static int START_GAME = 1;

    private OnClickListener playButtonListener = new OnClickListener() {
        @Override public void onClick(View arg0) {
            // initialize a new instance of your Game class
            //initialize(new MainPanel(getApplicationContext()), false); 
            Log.d("trace", "play button tapped");
            Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
            GamePreferences.get().setDifficulty(difficultyLevel);
            GamePreferences.get().save();
            myIntent.putExtra("DIFFICULTY_LEVEL", difficultyLevel);
            MainActivity.this.startActivityForResult(myIntent, START_GAME);
        }
    };
 
    private OnClickListener highScoreButtonListener = new OnClickListener() {
        @Override public void onClick(View arg0) {
            // initialize a new instance of your Game class
            //initialize(new MainPanel(getApplicationContext()), false); 
            Log.d("trace", "high score button tapped");
            Intent myIntent = new Intent(MainActivity.this, HighScoreView.class);
            MainActivity.this.startActivity(myIntent);
        }
    };
  
    private OnClickListener settingsButtonListener = new OnClickListener() {
        @Override public void onClick(View arg0) {
            // initialize a new instance of your Game class
            //initialize(new MainPanel(getApplicationContext()), false); 
            Log.d("trace", "high score button tapped");
            Intent myIntent = new Intent(MainActivity.this, SettingsView.class);
            MainActivity.this.startActivity(myIntent);
        }
    };
     
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case START_GAME:
                if (resultCode == RESULT_OK) {
                    int score = data.getIntExtra("score", 0);
                    // Show the score
                    Toast.makeText(getApplicationContext(), String.format("Score: %d", score), Toast.LENGTH_LONG).show();
                } 
            break;
        }
    }


    protected void setFontOnText() {
        TextView[] textIds = new TextView[] {
                (TextView) findViewById(R.id.instructions),
                (RadioButton) findViewById(R.id.difficulty_easy),
                (RadioButton) findViewById(R.id.difficulty_normal),
                (RadioButton) findViewById(R.id.difficulty_hard),
                (Button) findViewById(R.id.play_button),
                (Button) findViewById(R.id.high_score_button),
                (Button) findViewById(R.id.settings_button)
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
        LocalHighScore.createInstance(context);
        GamePreferences.createInstance(context).load();

        setContentView(R.layout.activity_main);

        // must do these after setContentView
        setDifficultyRadioButton(GamePreferences.get().getDifficulty());
        setFontOnText();
        
        Button playbutton = (Button)findViewById(R.id.play_button);
        playbutton.setOnClickListener(playButtonListener);
        Button highScorebutton = (Button)findViewById(R.id.high_score_button);
        highScorebutton.setOnClickListener(highScoreButtonListener);
        Button settingsbutton = (Button)findViewById(R.id.settings_button);
        settingsbutton.setOnClickListener(settingsButtonListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void setDifficultyRadioButton(int choice) {
        // first turn them all off
        int[] ids = new int[] {
                R.id.difficulty_easy,
                R.id.difficulty_normal,
                R.id.difficulty_hard,
        };
        
        for (int ct = 0; ct < ids.length; ct++) {
            RadioButton b = (RadioButton) findViewById(ids[ct]);
            b.setChecked(false);
        }
        
        // then enable the one we want
        // the ids match up with the choice...
        RadioButton b = (RadioButton) findViewById(ids[choice]);
        b.setChecked(true);
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
