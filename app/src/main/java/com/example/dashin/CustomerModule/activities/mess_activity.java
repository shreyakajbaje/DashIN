package com.example.dashin.CustomerModule.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dashin.CustomerModule.adapters.RecyclerViewAdapter;
import com.example.dashin.CustomerModule.adapters.menuItemAdapter;
import com.example.dashin.CustomerModule.models.menuItem;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class mess_activity extends AppCompatActivity {
    TextView messName,rating,description,priceOPeningHours,openClose;
    ImageView btn1,btn2,btn3,btn4,btn5;
    RecyclerView thaliView,rotiChapatiView,ricePulavView,sweetsView,beveragesView;
    boolean isUpBtn1=false,isUpBtn2=false,isUpBtn3=false,isUpBtn4=false,isUpBtn5=false;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    int []arr={R.drawable.food4,R.drawable.food5};
    RelativeLayout l1,l2,l3,l4,l5;
    private FirebaseFirestore db;
    private CollectionReference menuRef;
    private menuItemAdapter menuItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mess_activity);

        messName=findViewById(R.id.messName);
        description=findViewById(R.id.description);
        priceOPeningHours=findViewById(R.id.priceOpeningHours);
        openClose=findViewById(R.id.openClose);
        rating=findViewById(R.id.rating);
        db= Constants.mFirestore;
        DocumentReference docRef = db.collection("Vendor").document("vendor1");
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
                        messName.setText(mess.getString("BUSI_NAME"));
                        description.setText(mess.getString("BUSI_DESCRIPTION"));
                        priceOPeningHours.setText(mess.getString("OPEN_FROM")+"-"+mess.get("OPEN_TILL"));
                        rating.setText(mess.getString("RATING"));
                        String[] opentime=mess.getString("OPEN_FROM").split(" ");
                        String[] closetime=mess.getString("OPEN_TILL").split(" ");
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
        l1=findViewById(R.id.thaliLayout);
        l2=findViewById(R.id.ricePulavLayout);
        l3=findViewById(R.id.rotiChapatiLayout);
        l4=findViewById(R.id.sweetsLayout);
        l5=findViewById(R.id.beveragesLayout);

        thaliView=findViewById(R.id.thaliView);
       // thaliView.setNestedScrollingEnabled(false);
        rotiChapatiView=findViewById(R.id.rotiChapatiView);
       // rotiChapatiView.setNestedScrollingEnabled(false);
        ricePulavView=findViewById(R.id.ricePulavView);
        //ricePulavView.setNestedScrollingEnabled(false);
        sweetsView=findViewById(R.id.sweetsView);
       // sweetsView.setNestedScrollingEnabled(false);
        beveragesView=findViewById(R.id.beveragesView);
      //  beveragesView.setNestedScrollingEnabled(false);


        thaliView.setVisibility(View.GONE);
        rotiChapatiView.setVisibility(View.GONE);
        ricePulavView.setVisibility(View.GONE);
        sweetsView.setVisibility(View.GONE);
        beveragesView.setVisibility(View.GONE);

        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);

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
        menuRef=db.collection("Vendor/vendor1/Menu");
        Query query = menuRef.orderBy("Name",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<menuItem> options=new FirestoreRecyclerOptions.Builder<menuItem>()
                .setQuery(query,menuItem.class)
                .build();
        menuItemAdapter = new menuItemAdapter(options,this);

        //thaliView.setHasFixedSize(true);
        thaliView.setLayoutManager(new LinearLayoutManager(this));
        thaliView.setAdapter(menuItemAdapter);

        //rotiChapatiView.setHasFixedSize(true);
        rotiChapatiView.setLayoutManager(new LinearLayoutManager(this));
        rotiChapatiView.setAdapter(menuItemAdapter);

        //ricePulavView.setHasFixedSize(true);
        ricePulavView.setLayoutManager(new LinearLayoutManager(this));
        ricePulavView.setAdapter(menuItemAdapter);

        //sweetsView.setHasFixedSize(true);
        sweetsView.setLayoutManager(new LinearLayoutManager(this));
        sweetsView.setAdapter(menuItemAdapter);

        //beveragesView.setHasFixedSize(true);
        beveragesView.setLayoutManager(new LinearLayoutManager(this));
        beveragesView.setAdapter(menuItemAdapter);


//        layoutManager = new GridLayoutManager(this,1);
//        thaliView.setLayoutManager(layoutManager);
//
//        recyclerViewAdapter = new RecyclerViewAdapter(arr);
//
//        thaliView.setAdapter(recyclerViewAdapter);
//
//        thaliView.setHasFixedSize(true);
//
//
//        FirestoreRecyclerAdapter adapter;
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        menuItemAdapter.startListening();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        menuItemAdapter.stopListening();
    }

}
