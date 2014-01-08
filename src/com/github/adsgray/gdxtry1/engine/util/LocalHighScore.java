package com.github.adsgray.gdxtry1.engine.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalHighScore implements HighScoreSaveIF {

    protected SharedPreferences store;

    public LocalHighScore(Context context) {
        store = PreferenceManager.getDefaultSharedPreferences(context);
        // not sure if can do this here
    }

    // only used from LocalHighScore Activity, which knows that it is dealing
    // with a LocalHighScore instance
    public int getScore(String key) {
        int defaultValue = 0;
        return store.getInt(key, defaultValue);
    }

    @Override 
    public boolean submitScore(String key, int score) {
        // only save if it's better than previous score
        int previousScore = getScore(key);
        if (score > previousScore) {
            SharedPreferences.Editor editor = store.edit();
            editor.putInt(key, score);
            editor.commit();
            return true;
        }
        return false;
    }


    protected static LocalHighScore instance;
    public static LocalHighScore createInstance(Context context) {
        instance = new LocalHighScore(context);
        return instance;
    }
    public static LocalHighScore get() { return instance; }

}
