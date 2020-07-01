package com.example.dashin.LoginModule.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dashin.CustomerModule.activities.MainActivity;
import com.example.dashin.CustomerModule.models.Customer;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class SplashActivity extends AppCompatActivity {
   private ImageView name;
    private static int splashTimeOut=4000;
    Thread objectthread;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        startSplash();
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

                    if(Constants.mAuth.getCurrentUser() != null){

                        Constants.mFirestore.collection("customer")
                                .document(Constants.mAuth.getCurrentUser().getPhoneNumber()).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        Constants.CurrentUser = task.getResult().toObject(Customer.class);
                                    }
                                });

                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }else
                    {
                        startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    }
                }
            };
            objectthread.start();
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
