package com.example.dashin.CustomerModule.fragments;

import android.content.Intent;
import android.net.Uri;
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

import com.example.dashin.CustomerModule.activities.AddressActivity;
import com.example.dashin.CustomerModule.activities.MyOrdersActivity;
import com.example.dashin.LoginModule.activities.FirstPage;
import com.example.dashin.CustomerModule.activities.MainActivity;

import com.example.dashin.LoginModule.activities.LoginActivity;

import com.example.dashin.R;
import com.example.dashin.utils.CircleTransform;
import com.example.dashin.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    RelativeLayout logout,next_setting, my_orders,address;
    TextView cName,cEmail;
    String userId;
    ImageView userImage;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.profile_layout, container, false);

        cName = view.findViewById(R.id.profile_name);
        cEmail = view.findViewById(R.id.profile_email);
        userImage=view.findViewById(R.id.userImage);
        try {
            userId = Constants.mAuth.getCurrentUser().getUid();
        }
        catch (NullPointerException e)
        {
            userId="123456";
            e.printStackTrace();
        }

        cName.setText(Constants.CurrentUser.getName());
        cEmail.setText(Constants.CurrentUser.getEmail());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        storageRef.child(Constants.CurrentUser.getContact()+"/"+Constants.CurrentUser.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                //Picasso.get().load(uri).transform(new CircleTransform()).into(profilepic);
                Picasso.get().load(uri).transform(new CircleTransform()).into(userImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

//        Log.d("TAG","ID: "+userId);
//        DocumentReference documentReference = Constants.mFirestore.collection("customer").document(userId);
//        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()){
//                    String name = documentSnapshot.getString("name");
//                    String email = documentSnapshot.getString("email");
//
//                    cName.setText(name);
//                    cEmail.setText(email);
//                }else {
//                    Toast.makeText(getActivity(),"Data not fetched",Toast.LENGTH_SHORT);
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getActivity(),"Error! Data not fetched",Toast.LENGTH_SHORT);
//                Log.d("TAG",e.toString());
//            }
//        });

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

        address=view.findViewById(R.id.my_address_layout);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });

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
