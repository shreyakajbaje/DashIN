package com.example.dashin.LoginModule.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.dashin.LoginModule.fragments.EnterNumberFragment;
import com.example.dashin.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.main_frame_1, new EnterNumberFragment(), "");
        ft1.commit();

    }
}
