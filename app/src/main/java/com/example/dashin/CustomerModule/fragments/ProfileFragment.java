package com.example.dashin.CustomerModule.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dashin.CustomerModule.activities.MyOrdersActivity;
import com.example.dashin.CustomerModule.activities.MainActivity;
import com.example.dashin.LoginModule.activities.LoginActivity;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;


public class ProfileFragment extends Fragment {

    RelativeLayout logout,next_setting, my_orders;
    TextView cName,cEmail;
    String userId;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.profile_layout, container, false);
        cName = view.findViewById(R.id.profile_name);
        cEmail = view.findViewById(R.id.profile_email);
        userId = Constants.mAuth.getCurrentUser().getUid();


        Log.d("TAG","ID: "+userId);
        DocumentReference documentReference = Constants.mFirestore.collection("CUSTOMER").document(userId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name = documentSnapshot.getString("cName");
                    String email = documentSnapshot.getString("cEmail");

                    cName.setText(name);
                    cEmail.setText(email);
                }else {
                    Toast.makeText(getActivity(),"Data not fetched",Toast.LENGTH_SHORT);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Error! Data not fetched",Toast.LENGTH_SHORT);
                Log.d("TAG",e.toString());
            }
        });

        next_setting = view.findViewById(R.id.settings_layout);
        next_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment s = new SettingsFragment();
                FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                ft1.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                ft1.addToBackStack(null);
                ft1.replace(R.id.main_frame, s, "");
                ft1.commit();
            }
        });

        ImageView back = view.findViewById(R.id.profile_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });

        my_orders=view.findViewById(R.id.my_orders_layout);
        my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyOrdersActivity.class));
            }
        });

        return view;
    }
}
