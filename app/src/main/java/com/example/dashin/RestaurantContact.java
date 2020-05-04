package com.example.dashin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rescontact_layout);

        BottomNavigationView navigation = findViewById(R.id.bottom_nav_res);
        navigation.setSelectedItemId(R.id.res_contact);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.res_navigate:
                        Intent a = new Intent(RestaurantContact.this,RestaurantNavigation.class);
                        startActivity(a);
                        break;
                    case R.id.res_menu:
                        Intent b = new Intent(RestaurantContact.this,RestaurantActivity.class);
                        startActivity(b);
                        break;
                    case R.id.res_contact:
                        break;
                }
                return false;
            }
        });
    }
}
