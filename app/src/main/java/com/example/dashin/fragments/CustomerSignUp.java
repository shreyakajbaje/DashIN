package com.example.dashin.fragments;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dashin.LoginActivity;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.dashin.utils.Constants.mAuth;
import static com.example.dashin.utils.Constants.mFirestore;

public class CustomerSignUp extends Fragment {

    public static final String TAG = "TAG";
    Button register;
    EditText cName,cEmail,cPhone,cCity,cClg,cPassword;
    String userID;

    public CustomerSignUp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_sign_up, container, false);

        cName = view.findViewById(R.id.name);
        cEmail = view.findViewById(R.id.txt_email);
        cPhone = view.findViewById(R.id.phno);
        cCity = view.findViewById(R.id.city);
        cClg = view.findViewById(R.id.college_name);
        register = view.findViewById(R.id.buttonRegister);
        cPassword = view.findViewById(R.id.txt_password);

        /*if (mAuth.getCurrentUser() != null){
            Toast.makeText(getActivity(),"Customer already registered! Log in!",Toast.LENGTH_LONG).show();
            CustomerLogin cs = new CustomerLogin();
            FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
            ft1.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
            ft1.addToBackStack(null);
            ft1.replace(R.id.first_page, cs, "");
            ft1.commit();
        }*/

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = cName.getText().toString().trim();
                final String email = cEmail.getText().toString().trim();
                final String phno = cPhone.getText().toString().trim();
                final String city = cCity.getText().toString().trim();
                final String clg = cClg.getText().toString().trim();
                final String pass = cPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pass)){
                    Toast.makeText(getActivity(),"Error email pass!",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(name) && TextUtils.isEmpty(phno)){
                    Toast.makeText(getActivity(),"Error name phno!",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(name) && TextUtils.isEmpty(city) && TextUtils.isEmpty(clg)){
                    Toast.makeText(getActivity(),"Error name city clg!",Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = mFirestore.collection("CUSTOMER").document(userID);
                            Map<String, Object> doc = new HashMap<>();
                            doc.put("cName",name);
                            doc.put("cEmail",email);
                            doc.put("cNumber",phno);
                            doc.put("cCity",city);
                            doc.put("cCollege",clg);
                            doc.put("cPassword",pass);
                            documentReference.set(doc).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(),"Register successful! Now sign in!",Toast.LENGTH_LONG).show();
                                    Log.d("TAG","onSuccess: user profile is created for "+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure: "+e.toString());
                                }
                            });
                            startActivity(new Intent(getActivity(),LoginActivity.class));
                        }else {
                            Toast.makeText(getActivity(),"Error while uploading data!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        return view;
    }

}