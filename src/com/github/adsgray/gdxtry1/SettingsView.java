package com.github.adsgray.gdxtry1;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SettingsView extends Activity {

    Typeface unispace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("trace", "SettingsView onCreate!");

        //setContentView(R.layout.activity_settings);
        initFont();
        setFontOnText();
        //populateViewWithScores();
    }

    protected void initFont() {
        unispace = Typeface.createFromAsset(getAssets(),"data/unispace.ttf");
    }

    // TODO: make this into a function that can be used by all views...
    protected void setFontOnText() {
        TextView[] textIds = new TextView[] {
            // TODO: put the "High Scores" title view id in here
                /*
                (RadioButton) findViewById(R.id.difficulty_easy),
                (RadioButton) findViewById(R.id.difficulty_normal),
                (RadioButton) findViewById(R.id.difficulty_hard),
                (Button) findViewById(R.id.play_button)
                */
        };

        //TextView view = (TextView) findViewById(viewId);

        for (int ct = 0; ct < textIds.length; ct++) {
            textIds[ct].setTypeface(unispace);
        }
    }

}
