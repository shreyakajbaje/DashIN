package com.example.dashin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dashin.R;

public class Signup_options extends Fragment {

    Button signin,signup;
    RadioGroup radioGroup;
    public Signup_options() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_options, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);
        signup = view.findViewById(R.id.signup_page);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.pg_customer) {
                    CustomerSignUp cs = new CustomerSignUp();
                    FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                    ft1.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                    ft1.addToBackStack(null);
                    ft1.replace(R.id.first_page, cs, "");
                    ft1.commit();
                }
            }
        });

        radioGroup = view.findViewById(R.id.radioGroup);
        signin = view.findViewById(R.id.signin_page);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.pg_customer) {
                    CustomerLogin cs = new CustomerLogin();
                    FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                    ft1.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                    ft1.addToBackStack(null);
                    ft1.replace(R.id.first_page, cs, "");
                    ft1.commit();
                }
            }
        });
        return view;
    }

}
