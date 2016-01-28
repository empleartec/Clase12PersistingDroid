package com.mobaires.persistingdroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class SharedPrefsActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView scoreText;

    private static String[] NAMES = new String[]{ "CRISTO","SEBA","KATA", "SATOCHI", "GOKU", "SENNA", "PROST",
                                                  "AQUILES", "SONIC", "MARIO", "YANO", "KOBA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);
        nameText = (TextView) findViewById(R.id.nameText);
        scoreText = (TextView) findViewById(R.id.scoreText);
    }


    public void doGenerate(View v) {
        Random rnd = new Random(System.currentTimeMillis());
        nameText.setText(NAMES[rnd.nextInt(NAMES.length)]);
        scoreText.setText("" + rnd.nextInt(1200000));
    }

    public void doSave(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("SAVE_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", nameText.getText().toString());
        editor.putInt("score", Integer.valueOf(scoreText.getText().toString()));
        editor.apply();
    }

    public void doLoad(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("SAVE_INFO", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("name")) {
            nameText.setText(sharedPreferences.getString("name","-"));
        }
        if (sharedPreferences.contains("score")) {
            scoreText.setText(""+sharedPreferences.getInt("score",0 ));
        }

    }
}
