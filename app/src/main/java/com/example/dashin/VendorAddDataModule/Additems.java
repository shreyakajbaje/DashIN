package com.example.dashin.VendorAddDataModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dashin.R;
import com.example.dashin.VendorAddDataModule.adapter.CategoriesRecyclerView;
import com.example.dashin.VendorAddDataModule.adapter.ItemDataRecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class Additems extends AppCompatActivity {

    public long ItemArraySize;
    FloatingActionButton addButton;
    RecyclerView listViewOfCategories;
    String CAT_ID;
    TextView textView;
    ItemDataRecyclerView ItemRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additems);
        addButton=findViewById(R.id.add_button_in_menu_items);
        CAT_ID=CategoriesRecyclerView.currID.getCAT_ID();
        textView=findViewById(R.id.title_of_category);
        textView.setText(CategoriesRecyclerView.currID.getCAT_NAME());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addField(CAT_ID);
            }
        });
        ItemRecyclerView=new ItemDataRecyclerView(makeCategoriesRecyclerView(CAT_ID));
        RecyclerView recyclerView= findViewById(R.id.items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ItemRecyclerView);
        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("\tITEM SETUP");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setBackgroundColor(getResources().getColor(R.color.pink));
        Drawable drawable= getResources().getDrawable(R.mipmap.back_button_foreground);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 160, 160, true));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(newdrawable);

    }
    void addField(String CAT_ID)
    {
        Map<String,Object> m = new HashMap();
        m.put("ITEM_NAME","");
        m.put("ITEM_PRICE",0);
        m.put("INT_ITEM_ID","ITEM-"+(ItemArraySize+1));
        m.put("ITEM_DESC","");
        m.put("CAT_ID",CAT_ID);
        FirebaseFirestore.getInstance().collection("VENDOR-MENU/9998887776/"+CAT_ID).document("ITEM-"+(ItemArraySize+1)).set(m);
        FirebaseFirestore.getInstance().collection("VENDOR-MENU").document("9998887776").update("ITEM-COUNT",++ItemArraySize);
    }
    FirestoreRecyclerOptions<ItemData> makeCategoriesRecyclerView(String CAT_ID)
    {
        Query query = FirebaseFirestore.getInstance().collection("VENDOR-MENU/9998887776/"+CAT_ID);
        FirebaseFirestore.getInstance().collection("VENDOR-MENU").document("9998887776").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.getLong("ITEM-COUNT")!=null)
                ItemArraySize=documentSnapshot.getLong("ITEM-COUNT");
                else
                ItemArraySize=0;
            }
        });
        FirestoreRecyclerOptions<ItemData> options =  new FirestoreRecyclerOptions
                .Builder<ItemData>()
                .setQuery(query,ItemData.class)
                .build();
        return options;
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
    @Override
    protected void onStart() {
        super.onStart();
        ItemRecyclerView.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        ItemRecyclerView.stopListening();
    }
}
