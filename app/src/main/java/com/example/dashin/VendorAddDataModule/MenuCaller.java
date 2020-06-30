package com.example.dashin.VendorAddDataModule;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.dashin.R;

public class MenuCaller extends AppCompatActivity {

    Button edit,add,manage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_caller);
        edit=findViewById(R.id.Edit_items);
        add=findViewById(R.id.draft);
        manage=findViewById(R.id.Manage_time_table);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuCaller.this,CategoriesActivity.class));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuCaller.this, SelectItems.class));
            }
        });
    }
}