package com.example.dashin.CustomerModule.activities;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.CustomerModule.adapters.AddressAdapter;
import com.example.dashin.CustomerModule.models.Address;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AddressActivity extends AppCompatActivity {

    private RecyclerView addressView;
    private FirebaseFirestore db;
    private CollectionReference addressRef;
    private AddressAdapter AddressAdapter;
    private FloatingActionButton addAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        addressView=findViewById(R.id.AddressView);
        db= FirebaseFirestore.getInstance();

        addressRef=db.collection("customer/"+ Constants.CurrentUser.getContact() +"/del-locs");
        Query query = addressRef.orderBy("name",Query.Direction.ASCENDING);;

        FirestoreRecyclerOptions<Address> options=new FirestoreRecyclerOptions.Builder<Address>()
                .setQuery(query,Address.class)
                .build();
        AddressAdapter = new AddressAdapter(options,this,false);
        addressView.setLayoutManager(new LinearLayoutManager(this));
        addressView.setAdapter(AddressAdapter);

        addAddress=findViewById(R.id.addAddress);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LatLng[] pos = new LatLng[1];
                final String[] strLatitude = new String[1];
                final String[] strLongitude = new String[1];

                AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(AddressActivity.this).inflate(R.layout.add_address_form, viewGroup, false);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                ImageView close=dialogView.findViewById(R.id.close);
                final EditText addressName=dialogView.findViewById(R.id.addressName);
                final TextView addressLocation=dialogView.findViewById(R.id.addressLocation);

                final Dialog dialog = new Dialog(AddressActivity.this);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                /////make map clear
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

                dialog.setContentView(R.layout.dialogmap);////your custom content

                MapView mMapView = dialog.findViewById(R.id.mapView);
                MapsInitializer.initialize(AddressActivity.this);

                mMapView.onCreate(dialog.onSaveInstanceState());
                mMapView.onResume();


                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap googleMap) {
                        double lat=18.52;
                        double lng=73.85;
                        if(!addressLocation.getText().toString().equals("Select Location"))
                        {
                            //String[] separated = addressLocation.getText().toString().split(",");
                            lat=Double.parseDouble(strLatitude[0]);
                            lng=Double.parseDouble(strLongitude[0]);
                        }

                        LatLng posisiabsen = new LatLng(lat,lng);
                        pos[0] =posisiabsen;////your lat lng
                        googleMap.addMarker(new MarkerOptions().position(posisiabsen).title("Location"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(posisiabsen));
                        googleMap.getUiSettings().setZoomControlsEnabled(true);
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
                            public void onMapClick(LatLng point){

                                pos[0] =point;
                                googleMap.clear();
                                googleMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude,point.longitude)).title("Location"));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(point.latitude,point.longitude)));
                            }
                        });

                        dialog.findViewById(R.id.setLoc2).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addressLocation.setText(pos[0].latitude + "," + pos[0].longitude);
                                strLatitude[0] = String.valueOf(pos[0].latitude);
                                strLongitude[0] = String.valueOf(pos[0].longitude);
//                                        Toast.makeText(Activity_CMA_WHA.this,
//                                                pos.latitude + ", " + pos.longitude,
//                                                Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        String apiKey = "AIzaSyAuMK7p26Wq1BQ7JBVr3cIP2G_NZpDAP5M";

                        /**
                         * Initialize Places. For simplicity, the API key is hard-coded. In a production
                         * environment we recommend using a secure mechanism to manage API keys.
                         */
                        if (!Places.isInitialized()) {
                            Places.initialize(getApplicationContext(), apiKey);
                        }

// Create a new Places client instance.
                        // PlacesClient placesClient = Places.createClient(getApplicationContext());
                        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

                        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

                        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                            @Override
                            public void onPlaceSelected(Place place) {
                                // TODO: Get info about the selected place.
                                Log.i( "a","Place: " + place.getName() + ", " + place.getId()+","+place.getLatLng());

                                //Toast.makeText(Activity_CMA_WHA.this,""+queriedLocation.longitude+" "+queriedLocation.latitude,Toast.LENGTH_SHORT).show();
                                //searchLocation(place,googleMap);
                                LatLng latLng = new LatLng(place.getLatLng().latitude,place.getLatLng().longitude);
                                pos[0]=latLng;
                                googleMap.clear();
                                googleMap.addMarker(new MarkerOptions().position(latLng).title("loc"));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                            }

                            @Override
                            public void onError(Status status) {
                                // TODO: Handle the error.
                                Log.i( "a","An error occurred: " + status);
                            }
                        });


                    }
                });
                addressLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.show();
                    }
                });
                ImageView submit=dialogView.findViewById(R.id.submit);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (addressName.getText().toString().equals(""))
                        {
                            Toast.makeText(AddressActivity.this,"Enter Address Name",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (addressLocation.getText().toString().equals("Select Location"))
                        {
                            Toast.makeText(AddressActivity.this,"Select Address Location",Toast.LENGTH_SHORT).show();
                            return;
                        }
                       
                        HashMap<String,Object> Address = new HashMap<>();
                        Address.put("name",addressName.getText().toString());
                        String loc[]=addressLocation.getText().toString().split(",");
                        ArrayList<String> tmp=new ArrayList<>();
                        tmp.add(loc[0]);
                        tmp.add(loc[1]);
                        Address.put("location",tmp);
                        addressRef.add(Address).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(AddressActivity.this,"Address Added !",Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertDialog.dismiss();
                                        
                    }
                });
            }
        });


    }
    @Override
    public void onStart()
    {
        super.onStart();
        AddressAdapter.startListening();
    }
    @Override
    public void onStop()
    {
        super.onStop();
        AddressAdapter.stopListening();
    }

}
