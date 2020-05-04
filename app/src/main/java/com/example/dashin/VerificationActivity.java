package com.example.dashin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    EditText et1,et2,et3,et4,et5,et6;
    TextView setphn;
    Button pgnext;
    String st;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_verify_otp);

        pgnext = findViewById(R.id.nexthome);
        pgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(VerificationActivity.this,MainActivity.class));
                startActivity(intent);
            }
        });

        setphn = findViewById(R.id.receivedno);
        st = getIntent().getExtras().getString("Value");
        setphn.setText(st);


    }


}
