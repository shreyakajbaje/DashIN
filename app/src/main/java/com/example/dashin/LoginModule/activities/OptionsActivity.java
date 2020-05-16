package com.example.dashin.LoginModule.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dashin.R;

public class OptionsActivity extends AppCompatActivity {

    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_activity);

        btnlogin = findViewById(R.id.login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
