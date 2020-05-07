package com.example.dashin;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dashin.PaymentModule.PaymentScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantCart extends AppCompatActivity {

   ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_cart);

        ImageView dinebtn = findViewById(R.id.dine_in);
        dinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantCart.this, PaymentScreen.class);
                startActivity(intent);
            }
        });

        ImageView backpg = findViewById(R.id.back_page);
        backpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantCart.this,HomescreenActivity.class);
                startActivity(intent);
            }
        });

        final int[] count = {1};
        final TextView txtCount = findViewById(R.id.item_no1);
        ImageView buttonInc = findViewById(R.id.add_item1);
        ImageView buttonDec = findViewById(R.id.remove_item1);

        buttonInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]++;
                txtCount.setText(String.valueOf(count[0]));
            }
        });

        buttonDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]--;
                if (count[0] >= 0){
                    txtCount.setText(String.valueOf(count[0]));
                }
                else {
                    count[0] = 0;
                }
            }
        });

        final int[] count1 = {1};
        final TextView txtCount1 = findViewById(R.id.item_no2);
        ImageView buttonInc1 = findViewById(R.id.add_item2);
        ImageView buttonDec1 = findViewById(R.id.remove_item2);

        buttonInc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1[0]++;
                txtCount1.setText(String.valueOf(count1[0]));
            }
        });

        buttonDec1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1[0]--;
                if (count1[0] >= 0){
                    txtCount1.setText(String.valueOf(count1[0]));
                }
                else {
                    count1[0] = 0;
                }

            }
        });

        final int[] count2 = {1};
        final TextView txtCount2 = findViewById(R.id.item_no3);
        ImageView buttonInc2 = findViewById(R.id.add_item3);
        ImageView buttonDec2 = findViewById(R.id.remove_item3);

        buttonInc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2[0]++;
                txtCount2.setText(String.valueOf(count2[0]));
            }
        });

        buttonDec2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2[0]--;
                if (count2[0] >= 0){
                    txtCount2.setText(String.valueOf(count2[0]));
                }
                else {
                    count2[0] = 0;
                }

            }
        });

        imageView = findViewById(R.id.alertbox);
        imageView.setImageResource(R.drawable.cupon_popup_txt);

        CountDownTimer timer = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                imageView.setVisibility(View.INVISIBLE); //(or GONE)
            }
        }.start();

        final BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setSelectedItemId(R.id.nav_cart);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_cart:
                        break;
                    case R.id.nav_home:
                        Intent a = new Intent(RestaurantCart.this,HomescreenActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_profile:
                        Intent a1 = new Intent(RestaurantCart.this,SettingsActivity.class);
                        startActivity(a1);
                        break;
                    case R.id.nav_search:
                        Intent a2 = new Intent(RestaurantCart.this,SearchActivity.class);
                        startActivity(a2);
                        break;
                }
                return true;
            }
        });
    }
}