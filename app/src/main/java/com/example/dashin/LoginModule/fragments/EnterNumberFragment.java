package com.example.dashin.LoginModule.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.dashin.CustomerModule.activities.MainActivity;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;


public class EnterNumberFragment extends Fragment {

    private ImageView nextpage;
    TextView signup;
    private Drawable hanim;
    EditText phno;
    String st;
    public static String phone;
    public EnterNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        if(Constants.mAuth.getCurrentUser() != null){
            //start prof
            getActivity().finish();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }

        final TextView textView = view.findViewById(R.id.hello_gujrati);
        final String[] array = {"Hello", "नमस्कार","નમસ્તે","ਸਤ ਸ੍ਰੀ ਅਕਾਲ","ಹಲೋ","வணக்கம்","হ্যালো","नमस्कार"};
        textView.post(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                textView.setText(array[i]);
                i++;
                if (i == 8)
                    i = 0;
                textView.postDelayed(this, 1000);
            }
        });

        phno = view.findViewById(R.id.phone);
        signup = view.findViewById(R.id.signuphere);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        nextpage = view.findViewById(R.id.nextpg);
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int retval = onSubmitClicked(nextpage);
                if (retval == 1){
                    Bundle bundle = new Bundle();
                    bundle.putString("Number", "+91" + phno.getText().toString());
                    VerifyOPTFragment v1 = new VerifyOPTFragment();
                    v1.setArguments(bundle);
                    FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                    ft1.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                    ft1.addToBackStack(null);
                    ft1.replace(R.id.main_frame_1, v1, "");
                    ft1.commit();

                }
            }
        });


        return view;
    }

    public int onSubmitClicked(View v)
    {

        String num = phno.getText().toString();
        if(TextUtils.isEmpty(num) || num.length() < 10)
        {
            phno.setError("You must have 10 characters as your contact number");
            return 0;
        }
        return 1;
    }

}

