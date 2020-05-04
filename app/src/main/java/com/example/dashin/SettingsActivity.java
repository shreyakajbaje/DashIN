package com.example.dashin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);


        final BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setSelectedItemId(R.id.nav_profile);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_cart:
                        Intent a = new Intent(SettingsActivity.this,RestaurantCart.class);
                        startActivity(a);
                        break;
                    case R.id.nav_home:
                        Intent a1 = new Intent(SettingsActivity.this,HomescreenActivity.class);
                        startActivity(a1);
                        break;
                    case  R.id.nav_profile:
                        break;
                    case R.id.nav_search:
                        Intent a2 = new Intent(SettingsActivity.this,SearchActivity.class);
                        startActivity(a2);
                }
                return true;
            }
        });
    }
}
