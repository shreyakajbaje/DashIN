package com.example.dashin.CustomerModule.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.dashin.CustomerModule.models.ModelMess;
import com.example.dashin.R;
import com.example.dashin.utils.CircleTransform;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;

public class MessContactFragment extends Fragment {
    private FirebaseFirestore db;
    private TextView messName,ownerName,ownerAtMess,priceOpeningHours,vegNonVegText;
    private TextView rating,rating5,rating4,rating3,rating2,rating1,totalReviews,writeReview;
    private ProgressBar progressBar5,progressBar4,progressBar3,progressBar2,progressBar1;
    private ImageView vegNonVegIcon,ownerImage,contactMess,reportMess;
    public MessContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mess_contact, container, false);
        db= FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("VENDORS").document("QPH1dEgzgljulg1kOs6C");

        messName=view.findViewById(R.id.messName);
        ownerName=view.findViewById(R.id.ownerName);
        ownerAtMess=view.findViewById(R.id.ownerAtMess);
        priceOpeningHours=view.findViewById(R.id.priceOpeningHours);
        vegNonVegText=view.findViewById(R.id.vegNonVegText);

        rating=view.findViewById(R.id.rating);
        rating1=view.findViewById(R.id.textView1);
        rating2=view.findViewById(R.id.textView2);
        rating3=view.findViewById(R.id.textView3);
        rating4=view.findViewById(R.id.textView4);
        rating5=view.findViewById(R.id.textView5);
        totalReviews=view.findViewById(R.id.totalReviews);
        writeReview=view.findViewById(R.id.writeReview);


        progressBar1=view.findViewById(R.id.progressBar1);
        progressBar2=view.findViewById(R.id.progressBar2);
        progressBar3=view.findViewById(R.id.progressBar3);
        progressBar4=view.findViewById(R.id.progressBar4);
        progressBar5=view.findViewById(R.id.progressBar5);

        vegNonVegIcon=view.findViewById(R.id.vegNonVegIcon);
        ownerImage=view.findViewById(R.id.ownerImage);
        contactMess=view.findViewById(R.id.contactMess);
        reportMess=view.findViewById(R.id.reportMess);

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
                        Picasso.get().load(mess.getString("OWNER_IMAGE")).transform(new CircleTransform()).into(ownerImage);
                        messName.setText(mess.getString("BUSI_NAME"));
                        priceOpeningHours.setText(mess.getString("OPEN_FROM")+"-"+mess.get("OPEN_TILL"));
                        rating.setText(mess.getString("RATING"));
                        ownerName.setText(mess.getString("OWNER_NAME"));
                        ownerAtMess.setText("Owner at "+mess.getString("BUSI_NAME"));

                        if (mess.getString("VEG").equals("true"))
                        {
                            vegNonVegIcon.setImageResource(R.drawable.vegeterian_logo);
                            vegNonVegText.setText("The Restaurant Serves Vegetarian Food");
                        }
                        else
                        {
                            vegNonVegIcon.setImageResource(R.drawable.non_vegeterian_logo);
                            vegNonVegText.setText("The Restaurant Serves Vegetarian and Non Vegetarian Food");
                        }

                        totalReviews.setText(mess.getString("TOTAL_REVIEWS")+" reviews");
                        rating.setText(mess.getString("RATING"));
                        int rat5=0;
                        rat5 =(int) ((Double.parseDouble(mess.getString("5STAR_REVIEW_COUNT"))/Double.parseDouble(mess.getString("TOTAL_REVIEWS")))*100);
                        rating5.setText(rat5+"%");
                        progressBar5.setProgress(rat5);
                        int rat4=0;
                        rat4 =(int) ((Double.parseDouble(mess.getString("4STAR_REVIEW_COUNT"))/Double.parseDouble(mess.getString("TOTAL_REVIEWS")))*100);
                        rating4.setText(rat4+"%");
                        progressBar4.setProgress(rat4);
                        int rat3=0;
                        rat3 = (int) ((Double.parseDouble(mess.getString("3STAR_REVIEW_COUNT"))/Double.parseDouble(mess.getString("TOTAL_REVIEWS")))*100);
                        rating3.setText(rat3+"%");
                        progressBar3.setProgress(rat3);
                        int rat2=0;
                        rat2 = (int) ((Double.parseDouble(mess.getString("2STAR_REVIEW_COUNT"))/Double.parseDouble(mess.getString("TOTAL_REVIEWS")))*100);
                        rating2.setText(rat2+"%");
                        progressBar2.setProgress(rat2);
                        int rat1=0;
                        rat1 =(int)  ((Double.parseDouble(mess.getString("1STAR_REVIEW_COUNT"))/Double.parseDouble(mess.getString("TOTAL_REVIEWS")))*100);
                        rating1.setText(rat1+"%");
                        progressBar1.setProgress(rat1);
                        final long contactNumber=Long.parseLong(mess.getString("OWNER_CONTACT"));
                        contactMess.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                                }
                                else
                                {
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:"+contactNumber));
                                    startActivity(callIntent);
                                }
                            }
                        });


                    } catch (JSONException p) {
                        p.printStackTrace();
                    }
                } else {
                    System.out.print("Current data: null");
                }
            }
        });

        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Review form",Toast.LENGTH_SHORT).show();
            }
        });

        reportMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Report Mess",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
