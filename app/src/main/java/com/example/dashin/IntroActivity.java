package com.example.dashin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout layoutDot;
    private TextView[] dotstv;
    private int[] layouts;
    private Button btnskip;
    private Button btnnext;
    private MyPagerAdapter pagerAdapter;
    private ImageView start_btn;
    private LinearLayout ly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.pagerview);
        layoutDot = findViewById(R.id.dot_layout);
        btnnext = findViewById(R.id.btn_next);
        btnskip = findViewById(R.id.btn_skip);
        start_btn = findViewById(R.id.getstart);
        ly = findViewById(R.id.dot_layout);

        /*IntroAdapter adapter = new IntroAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);*/

        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this,WelcomeActivity.class);
                startActivity(intent);
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentpage = viewPager.getCurrentItem()+1;
                if (currentpage < layouts.length){
                    viewPager.setCurrentItem(currentpage);
                }
            }
        });

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this,WelcomeActivity.class);
                startActivity(intent);
            }
        });

        layouts = new int[]{R.layout.slide_1,R.layout.slide_2,R.layout.slide_3};
        pagerAdapter = new MyPagerAdapter(layouts,getApplicationContext());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == layouts.length-1) {
                    btnnext.setVisibility(View.GONE);
                    btnskip.setVisibility(View.GONE);
                    ly.setVisibility(View.GONE);
                    start_btn.setVisibility(View.VISIBLE);
                }else {
                    start_btn.setVisibility(View.GONE);
                    btnnext.setText("Next");
                    ly.setVisibility(View.VISIBLE);
                    btnnext.setVisibility(View.VISIBLE);
                    btnskip.setVisibility(View.VISIBLE);
                }
                setDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setDotStatus(0);
    }


    private  void setDotStatus(int page){
        layoutDot.removeAllViews();
        dotstv = new TextView[layouts.length];
        for(int i=0;i<dotstv.length;i++){
            dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226;"));
            dotstv[i].setTextSize(70);
            dotstv[i].setTextColor(Color.parseColor("#a9b4bb"));
            layoutDot.addView(dotstv[i]);
        }

        if (dotstv.length>0){
            dotstv[page].setTextColor(Color.parseColor("#A9A9A9"));
        }
    }

}