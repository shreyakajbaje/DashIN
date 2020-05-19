package com.example.dashin.CustomerModule.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Path;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.dashin.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.joda.time.DateTime;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class MessNavigateFragment extends Fragment implements PermissionsListener {
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationComponent locationComponent;
    private boolean isInTrackingMode;
    private NavigationMapRoute navigationMapRoute;
    private static final String TAG="MessNavigationFragment";
    private com.mapbox.geojson.Point Origin,End;
    private View view;
    private MapboxNavigation navigation;
    private DirectionsRoute currentRoute;
    public MessNavigateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity(),getString(R.string.mapbox_access_token));
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_mess_navigate, container, false);
        navigation= new MapboxNavigation(getContext(),getString(R.string.mapbox_access_token));
        mapView=view.findViewById(R.id.MapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapBoxMap) {
                mapboxMap = mapBoxMap;
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                        enableLocationComponent(style);
                        Origin= Point.fromLngLat( 74.2070,19.5761);
                        End=Point.fromLngLat(73.8567,18.5204);
                        getRoute(Origin,End);
                    }
                });
            }
        });
        Button startNavigation = view.findViewById(R.id.startNavigation);
        startNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a NavigationLauncherOptions object to package everything together
//                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
//                        .directionsRoute(currentRoute)
//                        .shouldSimulateRoute(true)
//                        .build();
//
//// Call this method with Context from within an Activity
//                NavigationLauncher.startNavigation(getActivity(), options);

                MessActualNavigateFragment messActualNavigateFragment = new MessActualNavigateFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.NavRelativeLayout, messActualNavigateFragment);
                fragmentTransaction.commit();

            }
        });
        return view;
    }
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle ) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {

// Create and customize the LocationComponent's options
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(getContext())
                    .elevation(5)
                    .accuracyAlpha(.6f)
                    .accuracyColor(Color.RED)
                    .build();

// Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(getContext(), loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build();

// Activate with options
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

// Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

// Add the location icon click listener
            locationComponent.addOnLocationClickListener(new OnLocationClickListener() {
                @SuppressWarnings( {"MissingPermission"})
                @Override
                public void onLocationComponentClick() {
                    if (locationComponent.getLastKnownLocation() != null) {
                        Toast.makeText(getContext(), String.format("18.5204,73.8567",
                                locationComponent.getLastKnownLocation().getLatitude(),
                                locationComponent.getLastKnownLocation().getLongitude()), Toast.LENGTH_LONG).show();
                    }
                }
            });

// Add the camera tracking listener. Fires if the map camera is manually moved.
            locationComponent.addOnCameraTrackingChangedListener(new OnCameraTrackingChangedListener() {
                @Override
                public void onCameraTrackingDismissed() {
                    isInTrackingMode = false;
                }

                @Override
                public void onCameraTrackingChanged(int currentMode) {

                }
            });

            view.findViewById(R.id.back_to_camera_tracking_mode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isInTrackingMode) {
                        isInTrackingMode = true;
                        locationComponent.setCameraMode(CameraMode.TRACKING);
                        locationComponent.zoomWhileTracking(16f);
                        Toast.makeText(getContext(), "Tracking enabled",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Tracking already enabled",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            permissionsManager = new PermissionsManager(MessNavigateFragment.this);
            permissionsManager.requestLocationPermissions(getActivity());
        }

    }

    private void getRoute(Point origin, Point end) {
        NavigationRoute.builder(getContext())
                .accessToken(getString(R.string.mapbox_access_token))
                .origin(origin)
                .destination(end)
                .build().getRoute(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response.body()==null)
                {
                    Log.e("error","body null");
                    return;
                }
                else if(response.body().routes().size()==0)
                {
                    Log.e("error","body size 0");
                    return;
                }
                currentRoute = response.body().routes().get(0);
                if (navigationMapRoute != null)
                {
                    navigationMapRoute.removeRoute();

                }
                else
                {
                    navigationMapRoute = new NavigationMapRoute(navigation,mapView,mapboxMap);
                    Log.e("error","in");
                }
                navigationMapRoute.addRoute(currentRoute);

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
      //  Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            //Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            //finish();
        }
    }
    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mapView.onDestroy();
    }

}
