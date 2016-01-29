package com.mobaires.persistingdroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SharedPrefsActivity extends AppCompatActivity {

    private static final String PREF_KEY_NAME = "name";
    private static final String PREF_KEY_SCORE = "score";
    private TextView nameText;
    private TextView scoreText;
    private TextView rivalsText;
    private Set<String> rivals;


    private static String[] NAMES = new String[]{ "CRISTO","SEBA","KATA", "SATOCHI", "GOKU", "SENNA", "PROST",
                                                  "AQUILES", "SONIC", "MARIO", "YANO", "KOBA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);
        nameText = (TextView) findViewById(R.id.nameText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        rivalsText = (TextView) findViewById(R.id.rivalsText);

        doLoad();
    }

    public String generateName(Random rnd) {
        return NAMES[rnd.nextInt(NAMES.length)];
    }

    public void doGenerate(View v) {
        Random rnd = new Random(System.currentTimeMillis());
        nameText.setText(generateName(rnd));
        scoreText.setText("Score: " + rnd.nextInt(1200000));

        rivals = genRivals();
        showRivals();
    }

    protected void showRivals() {
        if (rivals!=null) {
            rivalsText.setText(rivals.toString());
        }
    }

    public Set<String> genRivals() {
        Random rnd = new Random(System.currentTimeMillis());
        Set<String> rivals = new HashSet<>();

        String name = null;
        for (;rivals.size()<4;) {
            name = generateName(rnd);
            rivals.add(name);
        }

        return rivals;
    }

    public void doSave(View v) {
        String name = nameText.getText().toString();
        int score = Integer.valueOf(
                scoreText.getText().toString()
                        .substring("Score: ".length())
        );

        SharedPreferences prefs = getSharedPreferences("SAVED", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_KEY_NAME, name);
        editor.putInt(PREF_KEY_SCORE, score);
        editor.putStringSet("rivals", rivals);
        editor.apply();
    }

    public void doLoad(View v) {
        doLoad();
    }

    public void doLoad() {

        SharedPreferences prefs = getSharedPreferences("SAVED", Context.MODE_PRIVATE);
        if (prefs.contains(PREF_KEY_NAME)) {
            String name = prefs.getString(PREF_KEY_NAME,"-");
            nameText.setText(name);
        }
        if (prefs.contains(PREF_KEY_SCORE)) {
            int score = prefs.getInt(PREF_KEY_SCORE, 0);
            scoreText.setText(""+score);
        }
        if (prefs.contains("rivals")) {
            rivalsText.setText(
                    prefs.getStringSet("rivals", new HashSet<String>(0)).toString()
            );
        }
    }
}
