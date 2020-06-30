package com.example.dashin.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress {
    private static final String TAG = "LocationAddress";


    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder;
                String result = null;
                try {
                     geocoder= new Geocoder(context, Locale.getDefault());

                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);

                    if (addressList != null && addressList.size() > 0) {

                        Address address = addressList.get(0);
                        Log.e("in locAddr", addressList.get(0).getAddressLine(0).toString());
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append(",");
                        }
//                        sb.append(address.getLocality()).append(", ");
//                        sb.append(address.getPostalCode());
                      //  sb.append(address.getCountryName());
                        sb.append(addressList.get(0).getAddressLine(0));
                        result = sb.toString();


                    }
                }
                catch (NullPointerException e)
                {

                }
                catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
//                        result = "Latitude: " + latitude + " Longitude: " + longitude +
//                                "\n\nAddress:\n" + result;
                        bundle.putString("address", result);
                        Log.e("in locAddr",result);
                        message.setData(bundle);

                    }
                    else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Loading...";
                        bundle.putString("address", result);
                        Log.e("in locAddr", result);

                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}
