package com.example.dashin.CustomerModule.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;

import com.example.dashin.CustomerModule.activities.mess_activity;
import com.example.dashin.utils.DatabaseLogActivity;
import com.example.dashin.R;
import com.example.dashin.RestaurantActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    RelativeLayout relativeLayout;
    AutoCompleteTextView searchView;
    ArrayAdapter<String> adapter;

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
        setSearchBarResults();
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
                        Intent intent = new Intent(getActivity(), mess_activity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
