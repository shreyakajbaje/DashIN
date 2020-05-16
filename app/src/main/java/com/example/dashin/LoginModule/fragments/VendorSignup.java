package com.example.dashin.LoginModule.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dashin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorSignup extends Fragment {

    public VendorSignup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vendor_signup, container, false);
    }
}
