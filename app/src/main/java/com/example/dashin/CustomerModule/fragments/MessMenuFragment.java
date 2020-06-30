package com.example.dashin.CustomerModule.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dashin.CustomerModule.activities.BookingScreen;
import com.example.dashin.CustomerModule.adapters.RecyclerViewAdapter;
import com.example.dashin.CustomerModule.adapters.SliderAdapter;
import com.example.dashin.CustomerModule.adapters.menuItemAdapter;
import com.example.dashin.CustomerModule.models.menuItem;
import com.example.dashin.R;

import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MessMenuFragment extends Fragment {
    TextView messName,rating,description,priceOPeningHours,openClose;
    ImageView btn1,btn2,btn3,btn4,btn5,bookseat,appBarImage;
    RecyclerView thaliView,rotiChapatiView,ricePulavView,sweetsView,beveragesView;
    boolean isUpBtn1=false,isUpBtn2=false,isUpBtn3=false,isUpBtn4=false,isUpBtn5=false;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    int []arr={R.drawable.food4,R.drawable.food5};
    RelativeLayout l1,l2,l3,l4,l5;
    private FirebaseFirestore db;
    private CollectionReference menuRef;
    private menuItemAdapter menuItemAdapter;
    SliderAdapter sliderAdapter;
    SliderView sliderView;
    String phone="";
    public MessMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_mess_menu, container, false);
        appBarImage=view.findViewById(R.id.app_bar_image);
        messName=view.findViewById(R.id.messName);
        description=view.findViewById(R.id.description);
        priceOPeningHours=view.findViewById(R.id.priceOpeningHours);
        openClose=view.findViewById(R.id.openClose);
        rating=view.findViewById(R.id.rating);
        db= FirebaseFirestore.getInstance();
        sliderView = view.findViewById(R.id.imageSlider);
        Intent intent = getActivity().getIntent();
        phone = intent.getStringExtra("phone");

        menuRef=db.collection("vendors/"+phone+"/menu");
        Query query = menuRef.orderBy("name",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<menuItem> options=new FirestoreRecyclerOptions.Builder<menuItem>()
                .setQuery(query,menuItem.class)
                .build();
        menuItemAdapter = new menuItemAdapter(options,getContext(),phone);

        DocumentReference docRef = db.collection("vendors").document(phone);
        docRef.collection("mess_images")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {

                            sliderView.setVisibility(View.VISIBLE);
                            appBarImage.setVisibility(View.GONE);
                            ArrayList<String> ImageList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());

                                ImageList.add((String) document.get("imageuri"));
                            }

                            sliderAdapter = new SliderAdapter(getContext(), ImageList);
                            sliderView.setSliderAdapter(sliderAdapter);
                            sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
                            sliderView.setIndicatorSelectedColor(Color.parseColor("#00000000"));
                            sliderView.setIndicatorUnselectedColor(Color.parseColor("#00000000"));
                            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                            sliderView.setScrollTimeInSec(4);
                            sliderView.startAutoCycle();
                            sliderView.getSliderPager().setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    return true;
                                }
                            });

                        } else {
                            // Log.w(TAG, "Error getting documents.", task.getException());
                            sliderView.setVisibility(View.GONE);
                            appBarImage.setVisibility(View.VISIBLE);
                        }
                    }
                });
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    System.err.println("Listen failed: " + e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    //System.out.println("Current data: " + snapshot.getData());
                    JSONObject mess= new JSONObject(snapshot.getData()) ;

                    try {

                        messName.setText(mess.getString("busi_name"));
                        description.setText(mess.getString("mess_description"));
                        priceOPeningHours.setText(mess.getString("open_from")+"-"+mess.get("open_till"));
                        rating.setText(mess.getString("rating"));
                        String[] opentime=mess.getString("open_from").split(" ");
                        String[] closetime=mess.getString("open_till").split(" ");
                        String delegate = "aa hh:mm";
                        String time=(String) DateFormat.format(delegate, Calendar.getInstance().getTime());
                        if((opentime[1]+" "+opentime[0]).compareTo(time)<0&&0>time.compareTo(closetime[1]+" "+closetime[0]))
                        {
                            openClose.setTextColor(Color.parseColor("#4CAF50"));
                            openClose.setText("Open Now");
                        }
                        else
                        {
                            openClose.setTextColor(Color.parseColor("#A33B3B"));
                            openClose.setText("Close Now");
                        }
                        //Toast.makeText(mess_activity.this,""+time,Toast.LENGTH_SHORT).show();
                    } catch (JSONException p) {
                        p.printStackTrace();
                    }
                } else {
                    System.out.print("Current data: null");
                }
            }
        });
        l1=view.findViewById(R.id.thaliLayout);
        l2=view.findViewById(R.id.ricePulavLayout);
        l3=view.findViewById(R.id.rotiChapatiLayout);
        l4=view.findViewById(R.id.sweetsLayout);
        l5=view.findViewById(R.id.beveragesLayout);

        thaliView=view.findViewById(R.id.thaliView);
        // thaliView.setNestedScrollingEnabled(false);
        rotiChapatiView=view.findViewById(R.id.rotiChapatiView);
        // rotiChapatiView.setNestedScrollingEnabled(false);
        ricePulavView=view.findViewById(R.id.ricePulavView);
        //ricePulavView.setNestedScrollingEnabled(false);
        sweetsView=view.findViewById(R.id.sweetsView);
        // sweetsView.setNestedScrollingEnabled(false);
        beveragesView=view.findViewById(R.id.beveragesView);
        //  beveragesView.setNestedScrollingEnabled(false);


        thaliView.setVisibility(View.GONE);
        rotiChapatiView.setVisibility(View.GONE);
        ricePulavView.setVisibility(View.GONE);
        sweetsView.setVisibility(View.GONE);
        beveragesView.setVisibility(View.GONE);

        btn1=view.findViewById(R.id.btn1);
        btn2=view.findViewById(R.id.btn2);
        btn3=view.findViewById(R.id.btn3);
        btn4=view.findViewById(R.id.btn4);
        btn5=view.findViewById(R.id.btn5);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUpBtn1)
                {
                    // slideUp(thaliView);
                    thaliView.setVisibility(View.GONE);
                    btn1.setImageResource(R.drawable.down_arrow_icon);
                    isUpBtn1=false;

                }
                else
                {
                    // slideDown(thaliView);
                    thaliView.setVisibility(View.VISIBLE);
                    btn1.setImageResource(R.drawable.up_arrow_icon);
                    isUpBtn1=true;
                }
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUpBtn2)
                {
                    // slideUp(thaliView);

                    ricePulavView.setVisibility(View.GONE);
                    btn2.setImageResource(R.drawable.down_arrow_icon);
                    isUpBtn2=false;

                }
                else
                {
                    // slideDown(thaliView);

                    ricePulavView.setVisibility(View.VISIBLE);
                    btn2.setImageResource(R.drawable.up_arrow_icon);
                    isUpBtn2=true;
                }
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUpBtn3)
                {
                    // slideUp(thaliView);
                    rotiChapatiView.setVisibility(View.GONE);
                    btn3.setImageResource(R.drawable.down_arrow_icon);
                    isUpBtn3=false;

                }
                else
                {
                    // slideDown(thaliView);
                    rotiChapatiView.setVisibility(View.VISIBLE);
                    btn3.setImageResource(R.drawable.up_arrow_icon);
                    isUpBtn3=true;
                }
            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUpBtn4)
                {
                    // slideUp(thaliView);
                    sweetsView.setVisibility(View.GONE);
                    btn4.setImageResource(R.drawable.down_arrow_icon);
                    isUpBtn4=false;

                }
                else
                {
                    // slideDown(thaliView);
                    sweetsView.setVisibility(View.VISIBLE);
                    btn4.setImageResource(R.drawable.up_arrow_icon);
                    isUpBtn4=true;
                }
            }
        });
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUpBtn5)
                {
                    // slideUp(thaliView);
                    beveragesView.setVisibility(View.GONE);
                    btn5.setImageResource(R.drawable.down_arrow_icon);
                    isUpBtn5=false;

                }
                else
                {
                    // slideDown(thaliView);
                    beveragesView.setVisibility(View.VISIBLE);
                    btn5.setImageResource(R.drawable.up_arrow_icon);
                    isUpBtn5=true;
                }
            }
        });


        //thaliView.setHasFixedSize(true);
        thaliView.setLayoutManager(new LinearLayoutManager(getContext()));
        thaliView.setAdapter(menuItemAdapter);

        //rotiChapatiView.setHasFixedSize(true);
        rotiChapatiView.setLayoutManager(new LinearLayoutManager(getContext()));
        rotiChapatiView.setAdapter(menuItemAdapter);

        //ricePulavView.setHasFixedSize(true);
        ricePulavView.setLayoutManager(new LinearLayoutManager(getContext()));
        ricePulavView.setAdapter(menuItemAdapter);

        //sweetsView.setHasFixedSize(true);
        sweetsView.setLayoutManager(new LinearLayoutManager(getContext()));
        sweetsView.setAdapter(menuItemAdapter);

        //beveragesView.setHasFixedSize(true);
        beveragesView.setLayoutManager(new LinearLayoutManager(getContext()));
        beveragesView.setAdapter(menuItemAdapter);

        bookseat = view.findViewById(R.id.book_a_seat);
        bookseat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookingScreen.class);
                startActivity(intent);
            }
        });

        return view;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        menuItemAdapter.startListening();
    }
    @Override
    public void onStop()
    {
        super.onStop();
        menuItemAdapter.stopListening();
    }
}
