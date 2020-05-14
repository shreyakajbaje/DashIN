package com.example.dashin.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dashin.HomescreenActivity;
import com.example.dashin.ProfileActivity;
import com.example.dashin.R;
import com.example.dashin.adapters.MessAdapter;
import com.example.dashin.classes.HttpDataHandler;
import com.example.dashin.classes.LocationAddress;
import com.example.dashin.classes.ModelMess;
import com.example.dashin.services.AppLocationService;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class HomeFragment extends Fragment {

    MessAdapter adapter;
    RecyclerView recyclerView;
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

        ImageView profile = view.findViewById(R.id.profile_btn);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        setUpLocation();

        locality = view.findViewById(R.id.locality_tv);
        recyclerView = view.findViewById(R.id.rv);
        setUpMessRecyclerView(view);
        setUpOffersRecyclerView(view);
        setUpFoodRecyclerView(view);
        
        return view;
    }


    private void setUpFoodRecyclerView(View view) {

        ImageView imageView = view.findViewById(R.id.img1);
        imageView.setImageBitmap(getRoundedCornerImage(R.drawable.image6,R.drawable.rectangle8));
        ImageView imageView1 = view.findViewById(R.id.img2);
        imageView1.setImageBitmap(getRoundedCornerImage(R.drawable.image8,R.drawable.rectangle9));
        ImageView imageView2 = view.findViewById(R.id.img3);
        imageView2.setImageBitmap(getRoundedCornerImage(R.drawable.image5,R.drawable.rectangle9));

    }

    private void setUpOffersRecyclerView(View view) {
    }

    private void setUpMessRecyclerView(View view) {
        Log.e("ModelBooks", "setting up recycler view");
        Query query =
                Constants.mFirestore.collection("Vendor");

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
    }

    @Override
    public void onStop() {
        Log.e("ModelBooks", "on stop");
        super.onStop();
        adapter.stopListening();
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
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getActivity(), new GeocoderHandler());

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
