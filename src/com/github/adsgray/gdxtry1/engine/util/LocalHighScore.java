package com.github.adsgray.gdxtry1.engine.util;

import java.util.ArrayList;
import java.util.List;

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
    
    public static class ScoreRecord {
        public ScoreRecord(String label, int num) {
            this.label = label;
            this.num = num;
        }
        public String label;
        public int num;
    }
    
    public List<ScoreRecord> getScoreRecords(String[] labels, String[] keys) {
        List<ScoreRecord> ret = new ArrayList<ScoreRecord>();
        
        for (int ct = 0; ct < labels.length; ct++) {
            int num = getScore(keys[ct]);
            if (num != 0) {
                ret.add(new ScoreRecord(labels[ct], num));
            }
        }
        return ret;
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
