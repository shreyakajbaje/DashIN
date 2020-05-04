package com.example.dashin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentScreen extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    RadioGroup radioGroup,radioGroup1,radioGroup2,radioGroup3;
    RadioButton rb1,rb2,rb3,rb4,rb5,rb6;
    ImageView backpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

        backpage = findViewById(R.id.back_pg);
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentScreen.this,RestaurantCart.class);
                startActivity(intent);
            }
        });

        final BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setSelectedItemId(R.id.nav_cart);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_cart:
                        break;
                    case R.id.nav_home:
                        Intent a = new Intent(PaymentScreen.this,HomescreenActivity.class);
                        startActivity(a);
                        break;
                    case  R.id.nav_setting:
                        Intent a1 = new Intent(PaymentScreen.this,SettingsActivity.class);
                        startActivity(a1);
                    case  R.id.nav_search:
                        Intent a2 = new Intent(PaymentScreen.this,SearchActivity.class);
                        startActivity(a2);
                }
                return true;
            }
        });

        radioGroup = findViewById(R.id.radio_group);
        radioGroup1 = findViewById(R.id.grp1);
        radioGroup2 = findViewById(R.id.grp2);
        radioGroup3 = findViewById(R.id.grp3);

        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(PaymentScreen.this, "Selected", Toast.LENGTH_SHORT).show();
            }
        });

        rb1 = findViewById(R.id.radio_card);
        rb2 = findViewById(R.id.radio_gpay);
        rb3 = findViewById(R.id.radio_upi);
        rb4 = findViewById(R.id.radio_paytm);
        rb5 = findViewById(R.id.radio_amzpay);
        rb6 = findViewById(R.id.radio_paypal);

        rb1.setOnCheckedChangeListener(this);
        rb2.setOnCheckedChangeListener(this);
        rb3.setOnCheckedChangeListener(this);
        rb4.setOnCheckedChangeListener(this);
        rb5.setOnCheckedChangeListener(this);
        rb6.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        radioGroup.clearCheck();
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup3.clearCheck();
    }
}