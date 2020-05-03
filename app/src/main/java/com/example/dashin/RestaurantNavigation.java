package com.example.dashin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resnavigate_layout);

        BottomNavigationView navigation = findViewById(R.id.bottom_nav_res);
        navigation.setSelectedItemId(R.id.res_navigate);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.res_navigate:
                        break;
                    case R.id.res_menu:
                        Intent a = new Intent(RestaurantNavigation.this,RestaurantActivity.class);
                        startActivity(a);
                        break;
                    case R.id.res_contact:
                        Intent b = new Intent(RestaurantNavigation.this,RestaurantContact.class);
                        startActivity(b);

                        break;
                }
                return false;
            }
        });
    }
}
