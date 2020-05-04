package com.example.dashin;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView nextpage;
    private Drawable hanim;
    String st;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_welcome);

        /*ImageView hello = findViewById(R.id.hello_gujrati);
        hello.setBackgroundResource(R.drawable.hello_animation);

        hanim = hello.getBackground();
        if (hanim instanceof Animatable) {
            ((Animatable)hanim).start();
        }*/

        final TextView textView = findViewById(R.id.hello_gujrati);
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

        final EditText phno = findViewById(R.id.phone);


        nextpage = findViewById(R.id.nextpg);
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int retval = onSubmitClicked(nextpage);
                if (retval == 1){
                    Intent intent = new Intent(WelcomeActivity.this,VerificationActivity.class);
                    st = phno.getText().toString();
                    intent.putExtra("Value",st);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public int onSubmitClicked(View v)
    {
        EditText editText;
        editText = findViewById(R.id.phone);

        String num = editText.getText().toString();
        if(TextUtils.isEmpty(num) || num.length() < 10)
        {
            editText.setError("You must have 10 characters as your contact number");
            return 0;
        }
        return 1;
    }
}
