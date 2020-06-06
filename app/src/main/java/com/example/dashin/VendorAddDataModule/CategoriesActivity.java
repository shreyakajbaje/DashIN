package com.example.dashin.VendorAddDataModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.dashin.R;
import com.example.dashin.VendorAddDataModule.adapter.CategoriesRecyclerView;
import com.example.dashin.utils.DatabaseLogActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CategoriesActivity extends AppCompatActivity {

    RecyclerView listViewOfCategories;
    CategoriesRecyclerView categoriesRecyclerView;
    static long ArraySize;
    FloatingActionButton addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        addButton=(FloatingActionButton)findViewById(R.id.add_button_in_categories);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addField("9998887776");
            }
        });
        categoriesRecyclerView=new CategoriesRecyclerView(makeCategoriesRecyclerView());
        RecyclerView recyclerView= findViewById(R.id.categories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoriesRecyclerView);
        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("\tMENU SETUP");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setBackgroundColor(getResources().getColor(R.color.pink));
        Drawable drawable= getResources().getDrawable(R.mipmap.back_button_foreground);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 160, 160, true));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(newdrawable);
    }
    void addField(String number)
    {
        Map<String,Object> m = new HashMap();
        m.put("CAT_NAME","");
        m.put("IS_THALI",false);
        m.put("CAT_ID","CAT-"+(ArraySize+1));
        FirebaseFirestore.getInstance().collection("VENDOR-MENU/9998887776/CAT").document("CAT-"+(ArraySize+1)).set(m);
        FirebaseFirestore.getInstance().collection("VENDOR-MENU").document("9998887776").update("CAT-COUNT",++ArraySize);
    }
    FirestoreRecyclerOptions<CategoryDataClass> makeCategoriesRecyclerView()
    {
        Query query = FirebaseFirestore.getInstance().collection("VENDOR-MENU/9998887776/CAT");
        FirebaseFirestore.getInstance().collection("VENDOR-MENU").document("9998887776").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArraySize=documentSnapshot.getLong("CAT-COUNT");
            }
        });
        FirestoreRecyclerOptions<CategoryDataClass> options =  new FirestoreRecyclerOptions
                .Builder<CategoryDataClass>()
                .setQuery(query,CategoryDataClass.class)
                .build();
        return options;
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoriesRecyclerView.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        categoriesRecyclerView.stopListening();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
