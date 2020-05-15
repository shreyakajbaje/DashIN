package com.example.dashin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class SearchBarTool extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> searchViewOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar_tool);
        final List<String> names = new ArrayList<>();
        searchView=(SearchView)findViewById(R.id.Search_bar);
        listView=(ListView)findViewById(R.id.Search_list_view);
        androidx.appcompat.widget.Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("\tSEARCH");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setBackgroundColor(getResources().getColor(R.color.pink));
        Drawable drawable= getResources().getDrawable(R.mipmap.back_button_foreground);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 160, 160, true));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        setSearchBarResults();

    }
    void setSearchBarResults()
    {
        DatabaseLogActivity.TitleOfHotels=new ArrayList<>();
        DatabaseLogActivity.IdOfHotels =new ArrayList<>();
        DatabaseLogActivity.startSession();
        DatabaseLogActivity.firebasePointer.collection("MESS-LIST").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> m = queryDocumentSnapshots.getDocuments();
                for (int i=0;i<m.size();i++){
                    DatabaseLogActivity.TitleOfHotels.add(m.get(i).getString("TITLE"));
                    DatabaseLogActivity.IdOfHotels.add(m.get(i).getString("PHONE"));
                }
                searchViewOptions=new ArrayAdapter<String>(SearchBarTool.this,android.R.layout.simple_list_item_1,DatabaseLogActivity.TitleOfHotels);
                listView.setAdapter(searchViewOptions);
                listView.setVisibility(View.INVISIBLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       int index=DatabaseLogActivity.TitleOfHotels.indexOf(String.valueOf(parent.getItemAtPosition(position)));

                       Toast.makeText(SearchBarTool.this, DatabaseLogActivity.IdOfHotels.get(index),Toast.LENGTH_LONG).show();
                    }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        if(DatabaseLogActivity.TitleOfHotels.contains(query)){
                            searchViewOptions.getFilter().filter(query);
                        }else{
                            Toast.makeText(SearchBarTool.this, "No Match found",Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        searchViewOptions.getFilter().filter(newText);
                        if(newText==null||newText.equals(""))
                        {
                            listView.setVisibility(View.INVISIBLE);
                        }
                        else {
                            listView.setVisibility(View.VISIBLE);
                        }
                        return false;
                    }
                });
            }
        });
    }
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
