package com.example.dashin.CustomerModule.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dashin.R;
import com.example.dashin.CustomerModule.adapters.MessAdapter;
import com.example.dashin.CustomerModule.adapters.OffersAdapter;
import com.example.dashin.CustomerModule.adapters.TagsAdapter;
import com.example.dashin.utils.LocationAddress;
import com.example.dashin.CustomerModule.models.ModelMess;
import com.example.dashin.CustomerModule.models.ModelTag;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;


import java.text.DecimalFormat;


public class HomeFragment extends Fragment {

    MessAdapter adapter;
    TagsAdapter tagsAdapter;
    OffersAdapter offersAdapter;
    RecyclerView recyclerView, recyclerView2, recyclerView3;
    Location currentLocation;
    TextView locality;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.home_screen, container, false);


        setUpLocation();

        final Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        locality = view.findViewById(R.id.locality_tv);
        locality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locality.getMaxLines()==1) {
                    locality.setMaxLines(4);
                    locality.setBottom(10);
                    toolbar.setMinimumHeight(90);

                }
                else {
                    locality.setMaxLines(1);
                    locality.setBottom(3);
                    toolbar.setMinimumHeight(55);
                }
            }
        });



        setUpMessRecyclerView(view);
        setUpOffersRecyclerView(view);
        setUpFoodRecyclerView(view);

        return view;
    }


    private void setUpFoodRecyclerView(View view) {

        Log.e("Tags", "rv setup");
        recyclerView2 = view.findViewById(R.id.rv_tags);
        Query query =
                Constants.mFirestore.collection("Tags");

        FirestoreRecyclerOptions<ModelTag> options = new FirestoreRecyclerOptions.Builder<ModelTag>()
                .setQuery(query, ModelTag.class)
                .build();

        tagsAdapter = new TagsAdapter(options, new TagsAdapter.ClickListener() {
            @Override public void onPositionClicked(int position) {
                // callback performed on click
            }
        });
//        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
//        recyclerView2.setAdapter(tagsAdapter);

        recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(tagsAdapter);



    }

    private void setUpOffersRecyclerView(View view) {
        recyclerView3 = view.findViewById(R.id.rv_offers);
        Log.e("ModelBooks", "setting up recycler view");
        Query query =
                Constants.mFirestore.collection("VENDORS").whereGreaterThanOrEqualTo("DISCOUNT", 5);

        FirestoreRecyclerOptions<ModelMess> options = new FirestoreRecyclerOptions.Builder<ModelMess>()
                .setQuery(query, ModelMess.class)
                .build();


        offersAdapter = new OffersAdapter(options, new OffersAdapter.ClickListener() {
            @Override public void onPositionClicked(int position) {
                // callback performed on click
            }
        });
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setAdapter(offersAdapter);
    }

    private void setUpMessRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rv);
        Log.e("ModelBooks", "setting up recycler view");
        Query query =
                Constants.mFirestore.collection("VENDORS");

        FirestoreRecyclerOptions<ModelMess> options = new FirestoreRecyclerOptions.Builder<ModelMess>()
                .setQuery(query, ModelMess.class)
                .build();

        adapter = new MessAdapter(options, new MessAdapter.ClickListener() {
            @Override public void onPositionClicked(int position) {
                // callback performed on click
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        Log.e("ModelBooks", "on start");
        super.onStart();
        adapter.startListening();
        tagsAdapter.startListening();
        offersAdapter.startListening();
    }

    @Override
    public void onStop() {
        Log.e("ModelBooks", "on stop");
        super.onStop();
        adapter.stopListening();
        tagsAdapter.stopListening();
        offersAdapter.stopListening();
    }

    Bitmap getRoundedCornerImage(int icon1, int icon2){
        // get the back image
        Bitmap backImage= BitmapFactory.decodeResource( getResources(), icon2);

        // Get the front image

        Bitmap originalImg=BitmapFactory.decodeResource( getResources(),icon1);

        // Convert the image to mutable bitmap for later editing


        Bitmap mutableBitmap = backImage.copy( Bitmap.Config.ARGB_8888, true);

        // Create Canvas object for the mutable image

        Canvas canvas = new Canvas(mutableBitmap);

        // Create paint object

        final Paint paint = new Paint();

        paint.setColor(Color.parseColor("#44aa77")) ;

        //  Draw the front image on the back image

        canvas.drawBitmap(originalImg, 25, 18, paint);

        return mutableBitmap;
    }

    private void setUpLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                // App can access location both in the foreground and in the background.
                // Start your service that doesn't have a foreground service type
                // defined.
            } else {
                // App can only access location in the foreground. Display a dialog
                // warning the user that your app must have all-the-time access to
                // location in order to function properly. Then, request background
                // location.
                ActivityCompat.requestPermissions(getActivity(), new String[] {
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        100);
            }
        } else {
            // App doesn't have access to the device's location at all. Make full request
            // for permission.
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    },
                    101);
        }

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    //Toast.makeText(getActivity(), "" + currentLocation, Toast.LENGTH_SHORT).show();
                    Log.e("loc", currentLocation.toString());
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(5);
                    double latitude = Double.parseDouble(df.format(currentLocation.getLatitude()));
                    double longitude = Double.parseDouble(df.format(currentLocation.getLongitude()));
                    LocationAddress locationAddress = new LocationAddress();
                    try {
                        locationAddress.getAddressFromLocation(latitude, longitude,
                                getActivity(), new GeocoderHandler());

                    }catch(Exception e ){
                        Log.e("exception in geocoder", e.getMessage());
                    }
                } else {
                    Toast.makeText(getActivity(), "Turn on GPS", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = "loading...";
            }
            locality.setText(locationAddress);
            Log.e("user address", locationAddress);
        }
    }
}
