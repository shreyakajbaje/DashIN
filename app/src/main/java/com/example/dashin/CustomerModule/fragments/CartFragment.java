package com.example.dashin.CustomerModule.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dashin.CustomerModule.activities.MainActivity;
import com.example.dashin.CustomerModule.adapters.cartItemAdapter;
import com.example.dashin.CustomerModule.models.cartItem;
import com.example.dashin.PaymentModule.PaymentScreen;
import com.example.dashin.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class CartFragment extends Fragment  implements cartItemAdapter.setBill  {

    ImageView imageView;
    RecyclerView cartitems;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();

    private CollectionReference cartRef=db.collection("CUSTOMER/8682259087/Cart");

    cartItemAdapter cartItemAdapter;
    TextView itemTotalPrice,tax,deliveryFees,totalPrice;

    public CartFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_mess_cart, container, false);

        cartitems=view.findViewById(R.id.cartListView);

        Query query = cartRef.orderBy("Name",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<cartItem> options=new FirestoreRecyclerOptions.Builder<cartItem>()
                .setQuery(query, cartItem.class)
                .build();
        cartItemAdapter = new cartItemAdapter(options,getContext());

        cartitems.setNestedScrollingEnabled(false);
        cartitems.setLayoutManager(new LinearLayoutManager(getContext()));
        cartitems.setAdapter(cartItemAdapter);
        //cartitems.setHasFixedSize(true);
        cartItemAdapter.notifyDataSetChanged();
        itemTotalPrice=view.findViewById(R.id.itemTotalPrice);
        tax=view.findViewById(R.id.taxAmount);
        deliveryFees=view.findViewById(R.id.deliveryFees);
        totalPrice=view.findViewById(R.id.totalPrice);

        imageView = view.findViewById(R.id.alertbox);
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
        cartItemAdapter.setBillListner(CartFragment.this);


        return view;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        cartItemAdapter.startListening();

    }
    @Override
    public void onStop()
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
