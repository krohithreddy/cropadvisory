package com.hhcl.cropadvisory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Karthik Kumar K on 25-11-2017.
 */

public class Splashscreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SharedPreferences pref = getApplicationContext().getSharedPreferences("PROJECTNAME", android.content.Context.MODE_PRIVATE); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                boolean login = pref.getBoolean("ISLOGGEDIN", false);
                if (login) {
                    Intent i = new Intent(Splashscreen.this, Mainactivity.class);
                    startActivity(i);
                    //Log.d("logtrue","logintrue");

                } else {
                    Intent i = new Intent(Splashscreen.this, LoginScreen.class);
                    startActivity(i);
                    // Log.d("logfalse","loginfalse");

                }


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
