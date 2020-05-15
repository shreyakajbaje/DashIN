package com.example.dashin.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dashin.LoginActivity;
import com.example.dashin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import static com.example.dashin.utils.Constants.mAuth;
import static com.example.dashin.utils.Constants.mFirestore;

public class CustomerLogin extends Fragment {

    private EditText email,password;
    private Button signin;

    public CustomerLogin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_login, container, false);

        email = view.findViewById(R.id.signin_email);
        password = view.findViewById(R.id.signin_password);
        signin = view.findViewById(R.id.buttonSignin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cEmail = email.getText().toString();
                String cPassword = password.getText().toString();

                if (!TextUtils.isEmpty(cEmail) && !TextUtils.isEmpty(cPassword)){
                    mAuth.signInWithEmailAndPassword(cEmail,cPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getActivity(),"Log in Successful!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }else {
                                Toast.makeText(getActivity(),"Error can not sign in!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getActivity(),"Email and Password can not be empty!",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
