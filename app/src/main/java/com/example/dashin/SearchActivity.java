package com.example.dashin;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;

    private static final String[] restaurant = new String[]{"Kulkarni mess","Relax Veg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        relativeLayout = findViewById(R.id.restaurant_after_search);

        AutoCompleteTextView searchView = findViewById(R.id.search);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.search_autocompletelayout,R.id.res1,restaurant);
        searchView.setAdapter(adapter);

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,RestaurantActivity.class);
                startActivity(intent);
            }
        });
        final BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setSelectedItemId(R.id.nav_search);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_cart:
                        Intent a = new Intent(SearchActivity.this,RestaurantCart.class);
                        startActivity(a);
                        break;
                    case R.id.nav_home:
                        Intent a1 = new Intent(SearchActivity.this,HomescreenActivity.class);
                        startActivity(a1);
                        break;
                    case  R.id.nav_profile:
                        //Intent a2 = new Intent(SearchActivity.this,SettingsActivity.class);
                        //startActivity(a2);
                        break;
                    case R.id.nav_search:
                        break;
                }
                return true;
            }
        });
    }
}