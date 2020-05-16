package com.example.dashin.CustomerModule.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dashin.CustomerModule.activities.MainActivity;
import com.example.dashin.PaymentModule.PaymentScreen;
import com.example.dashin.R;


public class CartFragment extends Fragment {

    ImageView imageView;
    

    public CartFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.restaurant_cart, container, false);

        ImageView dinebtn = view.findViewById(R.id.dine_in);
        dinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentScreen.class);
                startActivity(intent);
            }
        });

        ImageView backpg = view.findViewById(R.id.back_page);
        backpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        final int[] count = {1};
        final TextView txtCount = view.findViewById(R.id.item_no1);
        ImageView buttonInc = view.findViewById(R.id.add_item1);
        ImageView buttonDec = view.findViewById(R.id.remove_item1);

        buttonInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]++;
                txtCount.setText(String.valueOf(count[0]));
            }
        });

        buttonDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]--;
                if (count[0] >= 0){
                    txtCount.setText(String.valueOf(count[0]));
                }
                else {
                    count[0] = 0;
                }
            }
        });

        final int[] count1 = {1};
        final TextView txtCount1 = view.findViewById(R.id.item_no2);
        ImageView buttonInc1 = view.findViewById(R.id.add_item2);
        ImageView buttonDec1 = view.findViewById(R.id.remove_item2);

        buttonInc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1[0]++;
                txtCount1.setText(String.valueOf(count1[0]));
            }
        });

        buttonDec1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1[0]--;
                if (count1[0] >= 0){
                    txtCount1.setText(String.valueOf(count1[0]));
                }
                else {
                    count1[0] = 0;
                }

            }
        });

        final int[] count2 = {1};
        final TextView txtCount2 = view.findViewById(R.id.item_no3);
        ImageView buttonInc2 = view.findViewById(R.id.add_item3);
        ImageView buttonDec2 = view.findViewById(R.id.remove_item3);

        buttonInc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2[0]++;
                txtCount2.setText(String.valueOf(count2[0]));
            }
        });

        buttonDec2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2[0]--;
                if (count2[0] >= 0){
                    txtCount2.setText(String.valueOf(count2[0]));
                }
                else {
                    count2[0] = 0;
                }

            }
        });

        imageView = view.findViewById(R.id.alertbox);
        imageView.setImageResource(R.drawable.cupon_popup_txt);

        CountDownTimer timer = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                imageView.setVisibility(View.INVISIBLE); //(or GONE)
            }
        }.start();



        return view;
    }
}
