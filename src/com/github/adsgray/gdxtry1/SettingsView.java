package com.github.adsgray.gdxtry1;

import com.github.adsgray.gdxtry1.testgame1.config.GamePreferences;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

public class SettingsView extends Activity {

    Typeface unispace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("trace", "SettingsView onCreate!");

        setContentView(R.layout.activity_settings);
        initFont();
        setFontOnText();
        populateCheckBoxesFromPreferences();
    }

    protected void initFont() {
        unispace = Typeface.createFromAsset(getAssets(),"data/unispace.ttf");
    }

    protected void populateCheckBoxesFromPreferences() {
        GamePreferences pref = GamePreferences.get();
        CheckBox cb;
        
        cb = (CheckBox) findViewById(R.id.soundcheckbox);
        cb.setChecked(pref.getSound() == 1);

        cb = (CheckBox) findViewById(R.id.vibratecheckbox);
        cb.setChecked(pref.getVibrate() == 1);
    }

    // TODO: make this into a function that can be used by all views...
    protected void setFontOnText() {
        TextView[] textIds = new TextView[] {
                (CheckBox) findViewById(R.id.soundcheckbox),
                (CheckBox) findViewById(R.id.vibratecheckbox),
                (TextView) findViewById(R.id.settings_title)
        };

        for (int ct = 0; ct < textIds.length; ct++) {
            textIds[ct].setTypeface(unispace);
        }
    }

}
