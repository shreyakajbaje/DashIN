package com.example.dashin.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.dashin.LoginActivity;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    RelativeLayout logout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.profile_layout, container, false);

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() == null){
                            //Do anything here which needs to be done after signout is complete
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                        else {
                        }
                    }
                };

                Constants.mAuth.addAuthStateListener(authStateListener);
                Constants.mAuth.signOut();
            }
        });

        return view;
    }
}
