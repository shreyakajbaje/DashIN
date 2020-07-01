package com.example.dashin.CustomerModule.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.dashin.CustomerModule.adapters.SliderAdapter;
import com.example.dashin.CustomerModule.models.ModelMess;
import com.example.dashin.R;
import com.example.dashin.utils.CircleTransform;
import com.example.dashin.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MessContactFragment extends Fragment {
    private FirebaseFirestore db;
    private TextView messName,ownerName,ownerAtMess,priceOpeningHours,vegNonVegText;
    private TextView rating,rating5,rating4,rating3,rating2,rating1,totalReviews,writeReview,notSub,price,type,from,to;
    private ProgressBar progressBar5,progressBar4,progressBar3,progressBar2,progressBar1;
    private ImageView vegNonVegIcon,ownerImage,contactMess,reportMess,appBarImage;
    private Toolbar toolbar;
    SliderAdapter sliderAdapter;
    SliderView sliderView;
    String phone;
    LinearLayout sub;
    public MessContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_mess_contact, container, false);
        toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        Intent intent = getActivity().getIntent();
        phone = intent.getStringExtra("phone");
        sub=view.findViewById(R.id.sub);
        notSub=view.findViewById(R.id.notSub);
        price=view.findViewById(R.id.price);
        type=view.findViewById(R.id.type);
        from=view.findViewById(R.id.from);
        to=view.findViewById(R.id.to);
//        DatabaseReference dbi=FirebaseDatabase.getInstance().getReference().child("HomeImages");
//        dbi.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                imageModelArrayList.clear();
//                currentPage=0;
//                NUM_PAGES=0;
//                for (DataSnapshot ds:dataSnapshot.getChildren())
//                {
//                    ImageModel imageModel=ds.getValue(ImageModel.class);
//                    imageModelArrayList.add(imageModel);
//                }
//                init();
//                slidingImage_adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        db= FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("vendors").document(phone);

        messName=view.findViewById(R.id.messName);
        ownerName=view.findViewById(R.id.ownerName);
        ownerAtMess=view.findViewById(R.id.ownerAtMess);
        priceOpeningHours=view.findViewById(R.id.priceOpeningHours);
        vegNonVegText=view.findViewById(R.id.vegNonVegText);

        appBarImage=view.findViewById(R.id.app_bar_image);

        rating=view.findViewById(R.id.rating);
        rating1=view.findViewById(R.id.textView1);
        rating2=view.findViewById(R.id.textView2);
        rating3=view.findViewById(R.id.textView3);
        rating4=view.findViewById(R.id.textView4);
        rating5=view.findViewById(R.id.textView5);
        totalReviews=view.findViewById(R.id.totalReviews);
        writeReview=view.findViewById(R.id.writeReview);

        sliderView = view.findViewById(R.id.imageSlider);

        progressBar1=view.findViewById(R.id.progressBar1);
        progressBar2=view.findViewById(R.id.progressBar2);
        progressBar3=view.findViewById(R.id.progressBar3);
        progressBar4=view.findViewById(R.id.progressBar4);
        progressBar5=view.findViewById(R.id.progressBar5);

        vegNonVegIcon=view.findViewById(R.id.vegNonVegIcon);
        ownerImage=view.findViewById(R.id.ownerImage);
        contactMess=view.findViewById(R.id.contactMess);
        reportMess=view.findViewById(R.id.reportMess);
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
                    ModelMess vendor = snapshot.toObject(ModelMess.class);
                    messName.setText(vendor.getBUSI_NAME());

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        //Log.e("abcd",vendor.getOWNER_CONTACT()+"/"+vendor.getOWNER_IMAGE());
                            storageRef.child(vendor.getOWNER_CONTACT()+"/"+vendor.getOWNER_IMAGE()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(final Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    //Picasso.get().load(uri).transform(new CircleTransform()).into(profilepic);
                                    Picasso.get().load(uri).transform(new CircleTransform()).into(ownerImage);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                    Log.e("error",exception.toString());
                                }
                            });


                        messName.setText(vendor.getBUSI_NAME());
                        priceOpeningHours.setText(vendor.getOPEN_FROM()+"-"+vendor.getOPEN_TILL());
                        rating.setText(""+vendor.getRATING());
                        ownerName.setText(vendor.getOWNER_NAME());
                        ownerAtMess.setText("Owner at "+vendor.getBUSI_NAME());

                        if (vendor.getVEG().equals(true))
                        {
                            vegNonVegIcon.setImageResource(R.drawable.vegeterian_logo);
                            vegNonVegText.setText("The Restaurant Serves Vegetarian Food");
                        }
                        else
                        {
                            vegNonVegIcon.setImageResource(R.drawable.non_vegeterian_logo);
                            vegNonVegText.setText("The Restaurant Serves Vegetarian and Non Vegetarian Food");
                        }

                        totalReviews.setText(vendor.getTOTAL_REVIEWS()+" reviews");
                        rating.setText(vendor.getRATING()+"");
                        int rat5=0;
                        if(vendor.getTOTAL_REVIEWS()!=0)
                            rat5 =(int) ((vendor.getSTAR_REVIEW_COUNT5()/vendor.getTOTAL_REVIEWS())*100);
                        rating5.setText(rat5+"%");
                        progressBar5.setProgress(rat5);
                        int rat4=0;
                        if (vendor.getTOTAL_REVIEWS()!=0)
                            rat4 =(int) ((vendor.getSTAR_REVIEW_COUNT4()/vendor.getTOTAL_REVIEWS())*100);
                        rating4.setText(rat4+"%");
                        progressBar4.setProgress(rat4);
                        int rat3=0;
                    if (vendor.getTOTAL_REVIEWS()!=0)
                        rat3 = (int) ((vendor.getSTAR_REVIEW_COUNT3()/vendor.getTOTAL_REVIEWS())*100);
                        rating3.setText(rat3+"%");
                        progressBar3.setProgress(rat3);
                        int rat2=0;
                    if (vendor.getTOTAL_REVIEWS()!=0)
                        rat2 = (int) ((vendor.getSTAR_REVIEW_COUNT2()/vendor.getTOTAL_REVIEWS())*100);
                        rating2.setText(rat2+"%");
                        progressBar2.setProgress(rat2);
                        int rat1=0;
                    if (vendor.getTOTAL_REVIEWS()!=0)
                        rat1 =(int) ((vendor.getSTAR_REVIEW_COUNT1()/vendor.getTOTAL_REVIEWS())*100);
                        rating1.setText(rat1+"%");
                        progressBar1.setProgress(rat1);
                        final long contactNumber=Long.parseLong(vendor.getOWNER_CONTACT());
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

                } else {
                    System.out.print("Current data: null");
                }
            }
        });

        db.collection("vendors/"+phone+"/reviews").document(Constants.CurrentUser.getContact())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot != null && documentSnapshot.exists()) {
                            writeReview.setVisibility(View.INVISIBLE);
                        }else{
                            writeReview.setVisibility(View.VISIBLE);
                        }
                    }
                });

        db.collection("vendors/"+phone+"/subscriptions").whereEqualTo("customerid",Constants.CurrentUser.getContact())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    sub.setVisibility(View.GONE);
                    notSub.setVisibility(View.VISIBLE);
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        sub.setVisibility(View.VISIBLE);
                        notSub.setVisibility(View.GONE);
                        price.setText("Price :₹"+document.getLong("price").toString());
                        type.setText("Type :"+document.getString("type"));
                        from.setText(document.getString("fromdate"));
                        to.setText(document.getString("todate"));
                    }
                } else {
                    sub.setVisibility(View.GONE);
                    notSub.setVisibility(View.VISIBLE);
                }
            }
        });

        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Toast.makeText(getContext(), "Review form", Toast.LENGTH_SHORT).show();
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                ViewGroup viewGroup = getView().findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.review_form, viewGroup, false);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                ImageView close = dialogView.findViewById(R.id.close);
                final EditText review = dialogView.findViewById(R.id.review);
                final RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
                ImageView submit = dialogView.findViewById(R.id.submitReview);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    DocumentSnapshot snapshot = task.getResult();
                                    if (snapshot != null && snapshot.exists()) {
                                        JSONObject mess = new JSONObject(snapshot.getData());

                                        try {
                                            int rating = 0, reviews = 0, finalRating = 0,myrating=0;

                                            try {
                                                rating =Integer.parseInt(mess.getString("rating"));
                                                reviews = Integer.parseInt(mess.getString("total_reviews"));
                                            }
                                            catch (Exception e)
                                            {

                                            }

                                            myrating=Math.round(ratingBar.getRating());
                                            if (myrating==0)
                                            {
                                                myrating=1;
                                            }
                                            finalRating = (int) (((rating * reviews) + myrating) / (reviews + 1));
                                            Toast.makeText(getContext(), "" + finalRating, Toast.LENGTH_LONG).show();
                                            docRef.update("rating",finalRating);
                                            HashMap <String,Object> tmp = new HashMap<>();
                                            tmp.put("rating",myrating);
                                            tmp.put("review",review.getText().toString());
                                            docRef.update(myrating+"star_review_count",((int)Double.parseDouble(mess.getString(myrating+"star_review_count")))+1);
                                            docRef.collection("reviews").document(Constants.CurrentUser.getContact()).set(tmp);
                                            docRef.update("total_reviews",reviews+1);
                                            alertDialog.dismiss();
                                        } catch (JSONException ex) {
                                            ex.printStackTrace();
                                            alertDialog.dismiss();
                                        }


                                    }
                                }
                            }
                        });
//                                (new EventListener<DocumentSnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
//
//                                if (e != null) {
//                                    System.err.println("Listen failed: " + e);
//                                    return;
//                                }
//
//
//
//                            }
//                        });
                    }
                });
            }
            });
                reportMess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Report Mess", Toast.LENGTH_SHORT).show();
                    }
                });
                return view;
            }

        }
