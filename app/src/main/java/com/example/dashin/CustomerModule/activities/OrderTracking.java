package com.example.dashin.CustomerModule.activities;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class OrderTracking extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseFirestore db;
    private boolean isFirstTime=true;
    Marker mk;
    TextView oredrStatus;
    boolean isMarkerRotating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        isMarkerRotating = false;
        oredrStatus=findViewById(R.id.orderStatus);
        db=FirebaseFirestore.getInstance();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//getIntent().getStringExtra("orderId")
        db.collection("customer/"+ "+919422224222"+"/my-orders/"+ "ORDER100007671"+"/order-loc").document("loc")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Listen failed: " + e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            if (isFirstTime)
                            {
                                //Add marker and move camera
                                mMap.clear();
                                mMap.setPadding(0,0,0,0);

                                LatLng loc = new LatLng(Double.parseDouble(snapshot.getString("lat")), Double.parseDouble(snapshot.getString("long")));
                                // create marker
                                MarkerOptions marker = new MarkerOptions().position(loc).title("Delivery");

                                // Changing marker icon
                                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.delivery_boy));

                                // adding marker
                                mk=mMap.addMarker(marker);
                                //mMap.addMarker(new MarkerOptions().position(loc).title("Order Live Location"));
                                // mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 18.0f));
                                isFirstTime=false;
                            }
                            else
                            {
                                LatLng loc = new LatLng(Double.parseDouble(snapshot.getString("lat")), Double.parseDouble(snapshot.getString("long")));
                                animateMarker(mk,loc,false);
                            }

                        }
                    }
                });

        db.collection("customer/"+ "+919422224222"+"/my-orders/"+ "ORDER100007671"+"/order-loc").document("stage1")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Listen failed: " + e);
                            return;
                        }
                        if (snapshot != null && snapshot.exists()) {
                            if (snapshot.getString("status").equals("C"))
                            {
                                oredrStatus.setText("Order Status : Order Placed");
                            }
                            db.collection("customer/"+ "+919422224222"+"/my-orders/"+ "ORDER100007671"+"/order-loc").document("stage2")
                                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot snapshot2, @Nullable FirebaseFirestoreException e2) {
                                            if (e2 != null) {
                                                System.err.println("Listen failed: " + e2);
                                                return;
                                            }
                                            if (snapshot2 != null && snapshot2.exists()) {
                                                if (snapshot2.getString("status").equals("C"))
                                                {
                                                    oredrStatus.setText("Order Status : Order On the Way");
                                                }
                                                db.collection("customer/"+ "+919422224222"+"/my-orders/"+ "ORDER100007671"+"/order-loc").document("stage3")
                                                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onEvent(@Nullable DocumentSnapshot snapshot3, @Nullable FirebaseFirestoreException e3) {
                                                                if (e3 != null) {
                                                                    System.err.println("Listen failed: " + e3);
                                                                    return;
                                                                }
                                                                if (snapshot3 != null && snapshot3.exists()) {
                                                                    if (snapshot3.getString("status").equals("C"))
                                                                    {
                                                                        oredrStatus.setText("Order Status : Order Delivered");
                                                                    }

                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });

                        }
                    }
                });

    }
    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        float bearing = (float) bearingBetweenLocations(marker.getPosition(), toPosition);
        rotateMarker(marker, bearing);
//        if (marker.getPosition().longitude<toPosition.longitude)
//        {
//            marker.setRotation(90);
//        }
//        else if (marker.getPosition().longitude>toPosition.longitude)
//        {
//            marker.setRotation(270);
//        }
//        if (marker.getPosition().latitude<toPosition.latitude)
//        {
//            marker.setRotation(0);
//        }
//        else if (marker.getPosition().latitude>toPosition.latitude)
//        {
//            marker.setRotation(180);
//        }
//        preLoc=toPosition;
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;
        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(toPosition, 18.0f));
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }
    private double bearingBetweenLocations(LatLng latLng1,LatLng latLng2) {

        double PI = 3.14159;
        double lat1 = latLng1.latitude * PI / 180;
        double long1 = latLng1.longitude * PI / 180;
        double lat2 = latLng2.latitude * PI / 180;
        double long2 = latLng2.longitude * PI / 180;

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;

        return brng;
    }
    private void rotateMarker(final Marker marker, final float toRotation) {
        if(!isMarkerRotating) {
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            final float startRotation = marker.getRotation();
            final long duration = 2000;

            final Interpolator interpolator = new LinearInterpolator();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    isMarkerRotating = true;

                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = interpolator.getInterpolation((float) elapsed / duration);

                    float rot = t * toRotation + (1 - t) * startRotation;

                    float bearing =  -rot > 180 ? rot / 2 : rot;

                    marker.setRotation(bearing);

                    if (t < 1.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    } else {
                        isMarkerRotating = false;
                    }
                }
            });
        }
    }
}