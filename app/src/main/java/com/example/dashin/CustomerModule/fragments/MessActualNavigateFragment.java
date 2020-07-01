package com.example.dashin.CustomerModule.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.dashin.R;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;


import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.ui.v5.OnNavigationReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessActualNavigateFragment extends Fragment implements OnNavigationReadyCallback, NavigationListener,
        ProgressChangeListener {
    public MessActualNavigateFragment(double lat1,double long1,double lat2,double long2) {
        ORIGIN_LATITUDE=lat1;
        ORIGIN_LONGITUDE=long1;
        DESTINATION_LATITUDE=lat2;
        DESTINATION_LONGITUDE=long2;
    }
    private static double ORIGIN_LONGITUDE = 74.2070;
    private static double ORIGIN_LATITUDE = 19.5761;
    private static double DESTINATION_LONGITUDE = 73.8567;
    private static double DESTINATION_LATITUDE = 18.5204;

    private NavigationView navigationView;
    private DirectionsRoute directionsRoute;

    @Override
    public View onCreateView(  LayoutInflater inflater,   ViewGroup container,
                               Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity(),getString(R.string.mapbox_access_token));
        return inflater.inflate(R.layout.fragment_mess_navigate_actual, container, false);
    }

    @Override
    public void onViewCreated(  View view,   Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateNightMode();
        navigationView = view.findViewById(R.id.navigation_view_fragment);
        navigationView.onCreate(savedInstanceState);
        navigationView.initialize(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        navigationView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationView.onResume();
    }

    @Override
    public void onSaveInstanceState(  Bundle outState) {
        navigationView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(  Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            navigationView.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        navigationView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        navigationView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        navigationView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        navigationView.onDestroy();
    }

    @Override
    public void onNavigationReady(boolean isRunning) {
        Point origin = Point.fromLngLat(ORIGIN_LONGITUDE, ORIGIN_LATITUDE);
        Point destination = Point.fromLngLat(DESTINATION_LONGITUDE, DESTINATION_LATITUDE);
        fetchRoute(origin, destination);
    }

    @Override
    public void onCancelNavigation() {
        navigationView.stopNavigation();
        stopNavigation();
    }

    @Override
    public void onNavigationFinished() {
        // no-op
    }

    @Override
    public void onNavigationRunning() {
        // no-op
    }

    @Override
    public void onProgressChange(Location location, RouteProgress routeProgress) {
        boolean isInTunnel = routeProgress.inTunnel();
        boolean wasInTunnel = wasInTunnel();
        if (isInTunnel) {
            if (!wasInTunnel) {
                updateWasInTunnel(true);
                updateCurrentNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        } else {
            if (wasInTunnel) {
                updateWasInTunnel(false);
                updateCurrentNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
            }
        }
    }

    @SuppressLint("WrongConstant")
    private void updateNightMode() {
        if (wasNavigationStopped()) {
            updateWasNavigationStopped(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
            getActivity().recreate();
        }
    }

    private void fetchRoute(Point origin, Point destination) {
        NavigationRoute.builder(getContext())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                        directionsRoute = response.body().routes().get(0);
                        startNavigation();
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

                    }
                });
    }

    private void startNavigation() {
        if (directionsRoute == null) {
            return;
        }
        NavigationViewOptions options = NavigationViewOptions.builder()
                .directionsRoute(directionsRoute)
                .shouldSimulateRoute(false)
                .navigationListener(MessActualNavigateFragment.this)
                .progressChangeListener(this)
                .build();
        navigationView.startNavigation(options);
    }

    private void stopNavigation() {
        FragmentActivity activity = getActivity();
        if (activity != null ) {
//            FragmentNavigationActivity fragmentNavigationActivity = (FragmentNavigationActivity) activity;
//            fragmentNavigationActivity.showPlaceholderFragment();
//            fragmentNavigationActivity.showNavigationFab();
            updateWasNavigationStopped(true);
            updateWasInTunnel(false);
        }
    }

    private boolean wasInTunnel() {
        Context context = getActivity();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("WasInTunnel", false);
    }

    private void updateWasInTunnel(boolean wasInTunnel) {
        Context context = getActivity();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("WasInTunnel", wasInTunnel);
        editor.apply();
    }

    private void updateCurrentNightMode(int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        getActivity().recreate();
    }

    private boolean wasNavigationStopped() {
        Context context = getActivity();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("NavigationStopped", false);
    }

    public void updateWasNavigationStopped(boolean wasNavigationStopped) {
        Context context = getActivity();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("NavigationStopped", wasNavigationStopped);
        editor.apply();
    }
}
