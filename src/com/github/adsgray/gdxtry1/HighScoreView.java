package com.github.adsgray.gdxtry1;

import java.util.Iterator;
import java.util.List;

import com.github.adsgray.gdxtry1.engine.util.LocalHighScore;
import com.github.adsgray.gdxtry1.engine.util.LocalHighScore.ScoreRecord;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HighScoreView extends Activity {

    Typeface unispace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("trace", "HighScoreView onCreate!");

        setContentView(R.layout.activity_highscore);
        initFont();
        setFontOnText();
        populateViewWithScores();
    }

    protected void initFont() {
        unispace = Typeface.createFromAsset(getAssets(),"data/unispace.ttf");
    }

    protected void addRowForScoreRecord(ScoreRecord score, TableLayout table, int rowNum) {
        // construct a TableRow out of {title, num}
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        TextView label = new TextView(this);
        label.setText(score.label);
        label.setTypeface(unispace);
        TextView scorenum = new TextView(this);
        scorenum.setText(String.format("%d", score.num));
        scorenum.setTypeface(unispace);

        row.addView(label);
        row.addView(scorenum);

        table.addView(row, rowNum);
    }

    protected void populateViewWithScores() {
        // score has String title, int num
        String[] labels = new String[] {
                "Easy",
                "Normal",
                "Hard"
        };
        
        String[] keys = new String[] {
                "high_score_0",
                "high_score_1",
                "high_score_2"
        };

        LocalHighScore hs = (LocalHighScore)LocalHighScore.get();
        List<ScoreRecord> scores = hs.getScoreRecords(labels, keys);
        Log.d("trace", String.format("scores length is %d", scores.size()));
        TableLayout table = (TableLayout) findViewById(R.id.highScoreTable);

        Iterator<ScoreRecord> iter = scores.iterator();
        int ct = 1; // 0th row is the title in the xml file
        while (iter.hasNext()) {
            addRowForScoreRecord(iter.next(), table, ct++);
        }
    }

    protected void setFontOnText() {
        TextView[] textIds = new TextView[] {
            // TODO: put the "High Scores" title view id in here
                (TextView) findViewById(R.id.high_score_title),
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
