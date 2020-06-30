package com.example.dashin.CustomerModule.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dashin.utils.Constants;
import com.example.dashin.utils.DatabaseLogActivity;
import com.example.dashin.CustomerModule.adapters.DetailsAdapter;
import com.example.dashin.CustomerModule.adapters.OrdersAdapter;
import com.example.dashin.R;
import com.paytm.pgsdk.easypay.OnSwipeTouchListener;

public class DetailedBillAndRepeat extends AppCompatActivity {
DetailsAdapter detailsAdapter;
RecyclerView recyclerView;
TextView MessName, Address,DelOrEat,Amount,reload;
Button ReSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_bill_and_repeat);
        //                                                                  Here must go the customer's number and unique order number
        detailsAdapter=new DetailsAdapter(DatabaseLogActivity.makeRecyclerView(Constants.mAuth.getCurrentUser().getPhoneNumber(),OrdersAdapter.pos));
        RecyclerView recyclerView= findViewById(R.id.ItemList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(detailsAdapter);
        MessName=(TextView)findViewById(R.id.TitleHolder);
        Address=(TextView)findViewById(R.id.AddressHolder);
        DelOrEat=(TextView)findViewById(R.id.DelOrTakeHolder);
        Amount=(TextView)findViewById(R.id.SubTotalInDetail);
        DatabaseLogActivity.setDetails(MessName,Address,DelOrEat,Amount);
        ReSubmit=(Button)findViewById(R.id.ReSubmit);
        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("\tORDER DETAILS");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setBackgroundColor(getResources().getColor(R.color.pink));
        Drawable drawable= getResources().getDrawable(R.mipmap.back_button_foreground);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 120, 120, true));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        ReSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        detailsAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        detailsAdapter.stopListening();
    }
}
