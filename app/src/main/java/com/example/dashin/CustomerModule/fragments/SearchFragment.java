package com.example.dashin.CustomerModule.fragments;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dashin.CustomerModule.activities.MessActivity;
import com.example.dashin.CustomerModule.adapters.MessAdapter;
import com.example.dashin.CustomerModule.models.ModelMess;
import com.example.dashin.utils.Constants;
import com.example.dashin.utils.DatabaseLogActivity;
import com.example.dashin.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    RelativeLayout relativeLayout;
    AutoCompleteTextView searchView;
    ArrayAdapter<String> adapter;
    MessAdapter messAdapter;
    RecyclerView recyclerView;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.search_layout, container, false);
        relativeLayout = view.findViewById(R.id.restaurant_after_search);
        searchView = view.findViewById(R.id.search);
        recyclerView=view.findViewById(R.id.searchResults);
        recyclerView.setVisibility(View.INVISIBLE);
        setSearchBarResults();
        setUpMessRecyclerView();
        return view;
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
                adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,DatabaseLogActivity.TitleOfHotels);
                searchView.setAdapter(adapter);
                searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), MessActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        // This display list of hotels according to search text. Filters are yet to be applied.
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH)
                {
                    if(!(v.getText().toString()==null||v.getText().toString()=="")) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });

        // This is to hide the list od searched hotels if search text is blank
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) recyclerView.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

    }
    private void setUpMessRecyclerView() {
        Log.e("ModelBooks", "setting up recycler view");
        Query query = Constants.mFirestore.collection("VENDORS");

        FirestoreRecyclerOptions<ModelMess> options = new FirestoreRecyclerOptions.Builder<ModelMess>()
                .setQuery(query, ModelMess.class)
                .build();

        messAdapter = new MessAdapter(options, new MessAdapter.ClickListener() {
            @Override public void onPositionClicked(int position) {
                // callback performed on click
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(messAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        messAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        messAdapter.stopListening();
    }
}
