package com.example.dashin.CustomerModule.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.dashin.utils.DatabaseLogActivity;
import com.example.dashin.CustomerModule.adapters.OrdersAdapter;
import com.example.dashin.R;

public class MyOrdersActivity extends AppCompatActivity {
    public View.OnClickListener listener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(new MyOrdersActivity(), DetailedBillAndRepeat.class));
        }
    };
    OrdersAdapter ordersAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        DatabaseLogActivity.startSession();
        //                                                                  Here must go the customer's number
        ordersAdapter=new OrdersAdapter(DatabaseLogActivity.makeRecyclerView("7218149193"));
        RecyclerView recyclerView= findViewById(R.id.ListMyOrders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ordersAdapter);
        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("\tYOUR ORDERS");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setBackgroundColor(getResources().getColor(R.color.pink));
        Drawable drawable= getResources().getDrawable(R.mipmap.back_button_foreground);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 160, 160, true));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(newdrawable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ordersAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        ordersAdapter.stopListening();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
