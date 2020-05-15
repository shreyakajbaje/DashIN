package com.example.dashin.fragments;

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

import com.example.dashin.R;
import com.example.dashin.RestaurantActivity;

public class SearchFragment extends Fragment {

    RelativeLayout relativeLayout;

    private static final String[] restaurant = new String[]{"Kulkarni mess","Relax Veg"};

    public SearchFragment() {
        // Required empty public constructor
    }

 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.search_layout, container, false);

        relativeLayout = view.findViewById(R.id.restaurant_after_search);

        AutoCompleteTextView searchView = view.findViewById(R.id.search);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.search_autocompletelayout,R.id.res1,restaurant);
        searchView.setAdapter(adapter);

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                startActivity(intent);
            }
        });
        
        return view;
    }
}
