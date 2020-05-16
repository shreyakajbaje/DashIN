package com.example.dashin.CustomerModule.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dashin.R;

public class BookingScreen extends AppCompatActivity {

    TextView showPopupBtn,booktime;
    ImageView closePopupBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    ImageView img1,img2,img3,img4,seat_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookingscreen_activity);

        showPopupBtn = findViewById(R.id.book_time);
        linearLayout1 = findViewById(R.id.linearlayout1);

        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) BookingScreen.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup_layout,null);

                closePopupBtn = customView.findViewById(R.id.set_time);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });

        img1 = findViewById(R.id.count_one);
        img2 = findViewById(R.id.count_two);
        img3 = findViewById(R.id.count_three);
        img4 = findViewById(R.id.count_four);
        seat_no = findViewById(R.id.seat_display);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_no.setImageResource(R.drawable.single_seat);
                img1.setImageResource(R.drawable.one_selected);
                img2.setImageResource(R.drawable.unselected_two);
                img3.setImageResource(R.drawable.unselected_three);
                img4.setImageResource(R.drawable.unselected_four);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_no.setImageResource(R.drawable.double_seat);
                img1.setImageResource(R.drawable.unselected_one);
                img2.setImageResource(R.drawable.two_selected);
                img3.setImageResource(R.drawable.unselected_three);
                img4.setImageResource(R.drawable.unselected_four);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_no.setImageResource(R.drawable.triple_seat);
                img1.setImageResource(R.drawable.unselected_one);
                img2.setImageResource(R.drawable.unselected_two);
                img3.setImageResource(R.drawable.three_selected);
                img4.setImageResource(R.drawable.unselected_four);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat_no.setImageResource(R.drawable.four_seat);
                img1.setImageResource(R.drawable.unselected_one);
                img2.setImageResource(R.drawable.unselected_two);
                img3.setImageResource(R.drawable.unselected_three);
                img4.setImageResource(R.drawable.four_selected);
            }
        });

    }
}
