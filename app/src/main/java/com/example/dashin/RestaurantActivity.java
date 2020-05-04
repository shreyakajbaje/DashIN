package com.example.dashin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.example.dashin.adapters.RecyclerViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantActivity extends AppCompatActivity {
    RecyclerView recyclerView,recyclerView1;
    RecyclerView.LayoutManager layoutManager,layoutManager1;
    RecyclerViewAdapter recyclerViewAdapter,recyclerViewAdapter1;
    ImageView rightBtn,leftBtn,bookseat;
    HorizontalScrollView hsv;
    int []arr={R.drawable.food4,R.drawable.food5,R.drawable.food6,R.drawable.food7,R.drawable.food4,R.drawable.food5,R.drawable.food6,R.drawable.food7};
    int []arr1={R.drawable.food7,R.drawable.food6,R.drawable.food5,R.drawable.food4,R.drawable.food7,R.drawable.food6,R.drawable.food5,R.drawable.food4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_activity);

        final BottomNavigationView navigation = findViewById(R.id.bottom_nav_res);
        navigation.setSelectedItemId(R.id.res_menu);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.res_navigate:
                        Intent a = new Intent(RestaurantActivity.this,RestaurantNavigation.class);
                        startActivity(a);
                        break;
                    case R.id.res_menu:
                        break;
                    case R.id.res_contact:
                        Intent b = new Intent(RestaurantActivity.this,RestaurantContact.class);
                        startActivity(b);
                        break;
                }
                return true;
            }
        });

        bookseat = findViewById(R.id.book_a_seat);
        bookseat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this,BookingScreen.class);
                startActivity(intent);
            }
        });

        hsv = findViewById(R.id.hsv_restaurant);
        rightBtn = findViewById(R.id.right_arrow);
        rightBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hsv.scrollTo(hsv.getScrollX() + 1100, hsv.getScrollY());
            }
        });

        leftBtn = findViewById(R.id.left_arrow);
        leftBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hsv.scrollTo(hsv.getScrollX() - 1100, hsv.getScrollY());
            }
        });


        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this,8);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(arr);

        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setHasFixedSize(true);


        recyclerView1 = findViewById(R.id.recycler_view1);
        layoutManager1 = new GridLayoutManager(this,8);
        recyclerView1.setLayoutManager(layoutManager1);

        recyclerViewAdapter1 = new RecyclerViewAdapter(arr1);

        recyclerView1.setAdapter(recyclerViewAdapter1);

        recyclerView1.setHasFixedSize(true);
    }
}