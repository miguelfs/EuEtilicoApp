package com.partiufast.euetilicoapp.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Miguel on 16/12/2016.
 */

public class SplashActivity extends AppCompatActivity {
    final static int INSTRUCTIONS_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", true);
        if (firstRun) {
            // here run your first-time instructions, for example :
            startActivityForResult(
                    new Intent(SplashActivity.this, MainActivity.class),
                    INSTRUCTIONS_CODE);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstRun", false).apply();
            finish();

        } else {
/*
            //creates a new handler object, calling postDelayed to delay the end of the splash screen.
            int SPLASH_DISPLAY_LENGTH = 2600;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, ListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
            */
            //same as if first run == true
            startActivityForResult(
                    new Intent(SplashActivity.this, MainActivity.class),
                    INSTRUCTIONS_CODE);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstRun", false).apply();
            finish();
        }
    }


}
