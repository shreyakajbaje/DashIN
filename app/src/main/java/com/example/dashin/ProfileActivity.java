package com.example.dashin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        ImageView back = findViewById(R.id.profile_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,HomescreenActivity.class);
                startActivity(intent);
            }
        });

       final BottomNavigationView navigation = findViewById(R.id.bottom_nav);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_cart:
                        Intent a = new Intent(ProfileActivity.this,RestaurantCart.class);
                        startActivity(a);
                        break;
                    case R.id.nav_home:
                        Intent a1 = new Intent(ProfileActivity.this,HomescreenActivity.class);
                        startActivity(a1);
                        break;
                    case R.id.My_orders:
                        Intent a2 = new Intent(ProfileActivity.this,MyOrdersActivity.class);
                        startActivity(a2);
                        break;
                }
                return true;
            }
        });
    }
}
