package com.example.dashin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dashin.fragments.CartFragment;
import com.example.dashin.fragments.HomeFragment;
import com.example.dashin.fragments.ProfileFragment;
import com.example.dashin.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView botnavview;
    HomeFragment home_fragment;
    SearchFragment search_fragment;
    ProfileFragment profile_fragment;
    CartFragment cart_fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("fragments", "oncreate");
        ActionBar ab = getSupportActionBar();


        botnavview=findViewById(R.id.bottom_nav);
        home_fragment=new HomeFragment();
        search_fragment=new SearchFragment();
        cart_fragment=new CartFragment();
        profile_fragment=new ProfileFragment();
        setFragment(home_fragment);
        Log.e("fragments", "oncreate");
        botnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_search:
                        setFragment(search_fragment);
                        return true;

                    case R.id.nav_cart:
                        setFragment(cart_fragment);
                        return true;

                    case R.id.nav_profile:
                        setFragment(profile_fragment);
                        return true;

                    default:
                        Log.e("Fragment", "home");
                        setFragment(home_fragment);
                        return true;
                }
            }
        });


    }



    private void setFragment(Fragment f)
    {
        Log.e("Fragment", "home");
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.main_frame, f, "");
        ft1.commit();
    }

}
