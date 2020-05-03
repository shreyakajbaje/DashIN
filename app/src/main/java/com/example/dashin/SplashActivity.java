package com.example.dashin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
   private ImageView name;
    private static int splashTimeOut=4000;

    Thread objectthread;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        startSplash();


        /*name = findViewById(R.id.logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,IntroActivity.class);
                startActivity(i);
                finish();
            }
        },splashTimeOut);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.splashanimation);
        name.startAnimation(myanim);*/
    }

    private void startSplash(){
        try {

            Animation objectAnimation = AnimationUtils.loadAnimation(this,R.anim.translate);
            objectAnimation.reset();
            ImageView objectImageView = findViewById(R.id.logo);
            objectImageView.clearAnimation();
            objectImageView.startAnimation(objectAnimation);
            objectthread = new Thread()
            {
                @Override
                public void run() {
                    int pasueIt = 0;
                    while (pasueIt<6000){
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        pasueIt+=100;
                    }

                    startActivity(new Intent(SplashActivity.this,IntroActivity.class));
                    SplashActivity.this.finish();
                }
            };
            objectthread.start();
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}

/*public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
        finish();
    }
}*/