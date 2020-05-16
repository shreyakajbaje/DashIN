package com.example.dashin.LoginModule.activities;

import android.os.Bundle;
import android.widget.Button;

import com.example.dashin.LoginModule.fragments.Signup_options;
import com.example.dashin.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class FirstPage extends AppCompatActivity {

    Button signup,signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage_layout);

        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.first_page, new Signup_options(), "");
        ft1.commit();
    }
}
