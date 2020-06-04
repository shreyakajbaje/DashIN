package com.example.dashin.CustomerModule.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dashin.CustomerModule.adapters.cartItemAdapter;
import com.example.dashin.CustomerModule.models.cartItem;
import com.example.dashin.Discount;

import com.example.dashin.PaymentModule.PaymentScreen;
import com.example.dashin.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class CartFragment extends Fragment  implements cartItemAdapter.setBill  {

    ImageView imageView,dineIn;
    RecyclerView cartitems;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    private LinearLayout linearLayout;
    private NestedScrollView nestedScrollView;
    private CollectionReference cartRef=db.collection("CUSTOMER/8682259087/Cart");
    private int taxAmt=25,deliverCharge=50,discount=0,itemTotalPriceAmount=0,sizehere=0;
    private EditText couponCode;
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
        nestedScrollView=view.findViewById(R.id.mainCartPage);
        linearLayout=view.findViewById(R.id.mainCartLayout);

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
        dineIn=view.findViewById(R.id.dineIn);
        dineIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),PaymentScreen.class);
                getActivity().startActivity(intent);
            }
        });
        nestedScrollView.setVisibility(View.INVISIBLE);
        linearLayout.setBackgroundResource(R.drawable.empty_cart);

        couponCode=view.findViewById(R.id.couponCode);
        couponCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Query query=db.collection("VENDORS/QPH1dEgzgljulg1kOs6C/Discounts").whereEqualTo("Code",String.valueOf(charSequence) );
                        query.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    QuerySnapshot documentSnapshot = task.getResult();
                                    if(documentSnapshot != null && !documentSnapshot.isEmpty()) {

                                        Discount disc = documentSnapshot.getDocuments().get(0).toObject(Discount.class);
                                       // Toast.makeText(getContext(),"Yes "+disc.getOFFP(),Toast.LENGTH_SHORT).show();
                                        discount=disc.getOFFP();
                                        setBillAmounts(itemTotalPriceAmount,sizehere);
                                    }
                                    else{
                                       // Toast.makeText(getContext(),"no",Toast.LENGTH_SHORT).show();
                                        discount=0;
                                        setBillAmounts(itemTotalPriceAmount,sizehere);
                                    }

                                }
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
    public void setBillAmounts(int itemTotalPriceAmt,int size) {
        if (size==0)
        {
            nestedScrollView.setVisibility(View.INVISIBLE);
            linearLayout.setBackgroundResource(R.drawable.empty_cart);
        }
        else
        {
            nestedScrollView.setVisibility(View.VISIBLE);
            linearLayout.setBackgroundResource(0);
        }
        itemTotalPriceAmount=itemTotalPriceAmt;
        sizehere=size;
        tax.setText("₹"+taxAmt);
        deliveryFees.setText("₹"+deliverCharge);
        itemTotalPrice.setText("₹"+itemTotalPriceAmount);
        int finalPrice=0;
        finalPrice=(itemTotalPriceAmt-((itemTotalPriceAmt*discount)/100));
        totalPrice.setText("₹"+(taxAmt+deliverCharge+finalPrice));
    }
}
