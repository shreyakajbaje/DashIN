package com.example.dashin.CustomerModule.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.dashin.R;
import com.example.dashin.CustomerModule.fragments.MessContactFragment;
import com.example.dashin.CustomerModule.fragments.MessMenuFragment;
import com.example.dashin.CustomerModule.fragments.MessNavigateFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MessActivity extends AppCompatActivity {

    MessMenuFragment messMenuFragment;
    MessNavigateFragment messNavigateFragment;
    MessContactFragment messContactFragment;
    String gTag="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);

        messMenuFragment = new MessMenuFragment();
        messNavigateFragment = new MessNavigateFragment();
        messContactFragment = new MessContactFragment();

        setFragment(messMenuFragment,MessMenuFragment.class.getSimpleName());

        final BottomNavigationView navigation = findViewById(R.id.bottom_nav_res);
        navigation.setSelectedItemId(R.id.res_menu);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.res_navigate:
                        setFragment(messNavigateFragment,MessNavigateFragment.class.getSimpleName());
                        break;
                    case R.id.res_menu:
                        setFragment(messMenuFragment,MessMenuFragment.class.getSimpleName());
                        break;
                    case R.id.res_contact:
                        setFragment(messContactFragment,MessContactFragment.class.getSimpleName());
                        break;
                }
                return true;
            }
        });
    }
    private void setFragment(Fragment f, String tag)
    {
        Log.e("Fragment", "mess");
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.main_frame, f, tag);
        ft1.addToBackStack(tag);
        ft1.commit();
//
//        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
//            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
//            t.add(R.id.main_frame, f, tag);
//            t.commit();
//        } else {
//            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
//            t.detach(gTag);
//            t.commit();
//            FragmentTransaction t1 = getSupportFragmentManager().beginTransaction();
//            t1.attach(getSupportFragmentManager().findFragmentByTag(tag));
//            t1.commit();
//        }
//
//        Log.e("ee",gTag+""+tag);
//        gTag=tag;
    }
    @Override
    public void onBackPressed()
    {
        finish();
        //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        //startActivity(intent);
    }
}
