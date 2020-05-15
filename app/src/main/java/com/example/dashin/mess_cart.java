package com.example.dashin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dashin.adapters.cartItemAdapter;
import com.example.dashin.utils.cartItem;
import com.example.dashin.utils.menuItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class mess_cart extends AppCompatActivity implements cartItemAdapter.setBill {
    RecyclerView cartitems;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();

    private CollectionReference cartRef=db.collection("CUSTOMER/8682259087/Cart");
    cartItemAdapter cartItemAdapter;

    TextView itemTotalPrice,tax,deliveryFees,totalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_cart);


        cartitems=findViewById(R.id.cartListView);

        Query query = cartRef.orderBy("Name",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<cartItem> options=new FirestoreRecyclerOptions.Builder<cartItem>()
                .setQuery(query, cartItem.class)
                .build();
        cartItemAdapter = new cartItemAdapter(options,this);

        cartitems.setNestedScrollingEnabled(false);
        cartitems.setLayoutManager(new LinearLayoutManager(this));
        cartitems.setAdapter(cartItemAdapter);
        //cartitems.setHasFixedSize(true);
        cartItemAdapter.notifyDataSetChanged();
        itemTotalPrice=findViewById(R.id.itemTotalPrice);
        tax=findViewById(R.id.taxAmount);
        deliveryFees=findViewById(R.id.deliveryFees);
        totalPrice=findViewById(R.id.totalPrice);

        //cartItemAdapter.startListening();
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        cartItemAdapter.startListening();

    }
    @Override
    protected void onStop()
    {
        super.onStop();
        cartItemAdapter.stopListening();
    }

    @Override
    public void setBillAmounts(int itemTotalPriceAmt) {
        int taxAmt=25,deliverCharge=50;
        tax.setText("₹"+taxAmt);
        deliveryFees.setText("₹"+deliverCharge);
        itemTotalPrice.setText("₹"+itemTotalPriceAmt);
        totalPrice.setText("₹"+(taxAmt+deliverCharge+itemTotalPriceAmt));
    }
}
