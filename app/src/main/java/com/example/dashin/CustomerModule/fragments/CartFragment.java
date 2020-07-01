package com.example.dashin.CustomerModule.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.dashin.CustomerModule.activities.MainActivity;
import com.example.dashin.CustomerModule.activities.MessActivity;
import com.example.dashin.CustomerModule.activities.selectAddress;
import com.example.dashin.CustomerModule.adapters.cartItemAdapter;
import com.example.dashin.CustomerModule.models.Customer;
import com.example.dashin.CustomerModule.models.cartItem;
import com.example.dashin.Discount;

import com.example.dashin.PaymentModule.PaymentScreen;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class CartFragment extends Fragment  implements cartItemAdapter.setBill  {

    ImageView imageView,dineIn,homeDel;
    RecyclerView cartitems;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    private LinearLayout linearLayout;
    private NestedScrollView nestedScrollView;
    private CollectionReference cartRef;
    private int taxAmt=25,deliverCharge=50,discount=0,itemTotalPriceAmount=0,sizehere=0;
    String discountName,dicsountCode;
    private EditText couponCode,instructions;
    cartItemAdapter cartItemAdapter;
    TextView itemTotalPrice,tax,deliveryFees,totalPrice;
    TextView mess;
    ImageView addMore;
    String messName,messAddress,messLocation;
    public CartFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_mess_cart, container, false);
        cartRef=db.collection("customer/"+Constants.CurrentUser.getContact()+"/cart");
        cartitems=view.findViewById(R.id.cartListView);
        nestedScrollView=view.findViewById(R.id.mainCartPage);
        linearLayout=view.findViewById(R.id.mainCartLayout);
        mess=view.findViewById(R.id.messName);
        if(Constants.mAuth.getCurrentUser() != null && Constants.CurrentUser.getContact()==null){

            Constants.mFirestore.collection("customer")
                    .document(Constants.mAuth.getCurrentUser().getPhoneNumber()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            Constants.CurrentUser = task.getResult().toObject(Customer.class);
                            db.collection("vendors").document(Constants.CurrentUser.getCart_mess_name()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        System.err.println("Listen failed: " + e);
                                        return;
                                    }

                                    if (snapshot != null && snapshot.exists()) {
                                        messName=snapshot.getString("busi_name");

                                        mess.setText(messName);
                                        messAddress=snapshot.getString("mess_description");
                                        Constants.order.setBUSI_NAME(messName);
                                    }
                                }
                            });
                        }
                    });
            Query query = cartRef.orderBy("name",Query.Direction.ASCENDING);

            FirestoreRecyclerOptions<cartItem> options=new FirestoreRecyclerOptions.Builder<cartItem>()
                    .setQuery(query, cartItem.class)
                    .build();
            cartItemAdapter = new cartItemAdapter(options,getContext());

            cartitems.setNestedScrollingEnabled(false);
            cartitems.setLayoutManager(new LinearLayoutManager(getContext()));
            cartitems.setAdapter(cartItemAdapter);
            //cartitems.setHasFixedSize(true);
            cartItemAdapter.notifyDataSetChanged();
        }
        else
        {
            db.collection("vendors").document(Constants.CurrentUser.getCart_mess_name()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        System.err.println("Listen failed: " + e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        messName=snapshot.getString("busi_name");

                        mess.setText(messName);
                        messAddress=snapshot.getString("mess_description");
                        Constants.order.setBUSI_NAME(messName);
                    }
                }
            });
            Query query = cartRef.orderBy("name",Query.Direction.ASCENDING);

            FirestoreRecyclerOptions<cartItem> options=new FirestoreRecyclerOptions.Builder<cartItem>()
                    .setQuery(query, cartItem.class)
                    .build();
            cartItemAdapter = new cartItemAdapter(options,getContext());

            cartitems.setNestedScrollingEnabled(false);
            cartitems.setLayoutManager(new LinearLayoutManager(getContext()));
            cartitems.setAdapter(cartItemAdapter);
            //cartitems.setHasFixedSize(true);
            cartItemAdapter.notifyDataSetChanged();
        }




        addMore=view.findViewById(R.id.addMore);
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MessActivity.class);
                intent.putExtra("phone",Constants.CurrentUser.getCart_mess_name());
                startActivity(intent);
            }
        });


        itemTotalPrice=view.findViewById(R.id.itemTotalPrice);
        tax=view.findViewById(R.id.taxAmount);
        deliveryFees=view.findViewById(R.id.deliveryFees);
        totalPrice=view.findViewById(R.id.totalPrice);
        instructions=view.findViewById(R.id.instructions);
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
                Constants.order.setAMOUNT(Long.parseLong(totalPrice.getText().toString().replace("₹","")));
                Constants.order.setFROM(Constants.CurrentUser.getCart_mess_name());
                Constants.order.setTO(Constants.CurrentUser.getContact());
                if(discount!=0)
                {
                    Constants.order.setOFFER(true);
                    Constants.order.setOFFER_CODE(dicsountCode);
                }
                if (!instructions.getText().toString().equals(""))
                    Constants.order.setInstructions(instructions.getText().toString());
                Constants.order.setBUSI_ADD(messAddress);
                Constants.order.setParcel(false);
                Intent intent = new Intent(getContext(), selectAddress.class);
                getActivity().startActivity(intent);
            }
        });
        homeDel=view.findViewById(R.id.homeDel);
        homeDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.order.setAMOUNT(Long.parseLong(totalPrice.getText().toString().replace("₹","")));
                Constants.order.setFROM(Constants.CurrentUser.getCart_mess_name());
                Constants.order.setTO(Constants.CurrentUser.getContact());
                if(discount!=0)
                {
                    Constants.order.setOFFER(true);
                    Constants.order.setOFFER_CODE(dicsountCode);
                }
                if (!instructions.getText().toString().equals(""))
                    Constants.order.setInstructions(instructions.getText().toString());
                Constants.order.setBUSI_ADD(messAddress);
                Constants.order.setParcel(true);
                Intent intent = new Intent(getContext(), selectAddress.class);
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
                Query query=db.collection("vendors/"+Constants.CurrentUser.getCart_mess_name()+"/discounts").whereEqualTo("Code",String.valueOf(charSequence) );
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
                                        discountName=disc.getType();
                                        dicsountCode=disc.getCode();
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
        ImageView back = view.findViewById(R.id.back_page);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
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
