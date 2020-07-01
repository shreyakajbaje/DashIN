package com.example.dashin.CustomerModule.fragments;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dashin.CustomerModule.activities.MainActivity;
import com.example.dashin.CustomerModule.activities.MessActivity;
import com.example.dashin.CustomerModule.adapters.MessAdapter;
import com.example.dashin.CustomerModule.adapters.SmartSearch;
import com.example.dashin.CustomerModule.adapters.TagsAdapter;
import com.example.dashin.CustomerModule.models.ModelMess;
import com.example.dashin.CustomerModule.models.SmartSearchModel;
import com.example.dashin.utils.Constants;
import com.example.dashin.utils.DatabaseLogActivity;
import com.example.dashin.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {

    RelativeLayout relativeLayout;
    AutoCompleteTextView searchView;
    SmartSearch adapter;
    MessAdapter messAdapter;
    RecyclerView recyclerView;
    TextView t1,t2,t3,t4,clear_text;
    public static Query query;
    boolean created=false;
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
            if (TagsAdapter.caller != null) {
                if (TagsAdapter.caller.equals("Called from HomeActivity")) {
                    setUpMessRecyclerView(TagsAdapter.tagName, 0);
                    System.out.println("Called from Home");
                    recyclerView.setVisibility(View.VISIBLE);
                }
            } else {
                setUpMessRecyclerView();
                recyclerView.setVisibility(View.INVISIBLE);
            }
        TextView t=view.findViewById(R.id.live_loc_text);
            t.setText(HomeFragment.locationAddress);
        setSearchBarResults();
        t1=view.findViewById(R.id.search1);
        t2=view.findViewById(R.id.search2);
        t3=view.findViewById(R.id.search3);
        t4=view.findViewById(R.id.search4);
        clear_text=view.findViewById(R.id.clear);
        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t4.setText("");
                addToRecentlySearched("");
            }
        });
        setRecentlySearched();
        setRecentSearchText();
        return view;
    }

    void setSearchBarResults()
    {
        SmartSearch.messValues=new ArrayList<>();
        DatabaseLogActivity.startSession();
        adapter=new SmartSearch(getContext(),R.layout.smart_search_layout,SmartSearch.messValues);
        DatabaseLogActivity.firebasePointer.collection("MESS-LIST").orderBy("IS_TITLE").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> m = queryDocumentSnapshots.getDocuments();
                for (int i=0;i<m.size();i++){
                    SmartSearchModel temp=new SmartSearchModel(
                            m.get(i).getString("TITLE"),
                            m.get(i).getString("IS_TITLE"),
                            m.get(i).getString("AREA"),
                            m.get(i).getLong("TIMES")
                    );
                    SmartSearch.messValues.add(temp);
                    if((i+1)%50==0)
                        adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();
                adapter.getFilter().filter(null);
                searchView.setAdapter(adapter);
            }
        });
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                    if(SmartSearch.messList.get(position).getIS_TITLE().equals("1"))
                    addToRecentlySearched(SmartSearch.messList.get(position).getTITLE());
                    setUpMessRecyclerView(SmartSearch.messList.get(position).getTITLE(), Integer.parseInt(SmartSearch.messList.get(position).getIS_TITLE()));
                    searchView.setText(SmartSearch.messList.get(position).getTITLE());
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter("#");
            }
        });
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_SEARCH)
                {
                    if(!(v.getText().toString().equals(null)||v.getText().toString().equals(""))) {
                        ArrayList<String> filterValues = new ArrayList<String>();
                        for(int i=0;i<SmartSearch.messList.size();i++)
                        filterValues.addAll(getVariations(SmartSearch.messList.get(i).getTITLE()));
                        System.out.println(filterValues);
                        if(!filterValues.isEmpty()) {
                            setUpMessRecyclerView(filterValues);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter.getFilter().filter("#");
                        }
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



    private void setUpMessRecyclerView(ArrayList<String> list) {
        Log.e("ModelBooks", "setting up recycler view");
        if(list.size()>10)
        list.subList(10,list.size()-1).clear();
        list.trimToSize();
        System.out.println(list);
        query = Constants.mFirestore.collection("vendors").whereArrayContainsAny("facilities",list);    // .orderBy("VIEWS"); can be added to query
                                                                                                                    // in each case. once field is generated.

        FirestoreRecyclerOptions<ModelMess> options = new FirestoreRecyclerOptions.Builder<ModelMess>()
                .setQuery(query, ModelMess.class)
                .build();
        messAdapter = new MessAdapter(options, new MessAdapter.ClickListener() {
            @Override public void onPositionClicked(int position) {
                // callback performed on click
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(messAdapter);
        messAdapter.startListening();
    }



    public void setUpMessRecyclerView(String smart,int state) {
        Log.e("ModelBooks", "setting up recycler view");
        if(state==0)
        query = Constants.mFirestore.collection("vendors").whereArrayContainsAny("facilities",Arrays.asList(smart));
        else {
            query = Constants.mFirestore.collection("vendors").whereIn("busi_name", getVariations(smart));
        }
        // .orderBy("VIEWS"); can be added to query
        // in each case. once field is generated.
        FirestoreRecyclerOptions<ModelMess> options = new FirestoreRecyclerOptions.Builder<ModelMess>()
                .setQuery(query, ModelMess.class)
                .build();
        messAdapter = new MessAdapter(options, new MessAdapter.ClickListener() {
            @Override public void onPositionClicked(int position) {
                // callback performed on click
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(messAdapter);
        messAdapter.startListening();
        TagsAdapter.caller="";
    }



    private void setUpMessRecyclerView() {
        Log.e("ModelBooks", "setting up recycler view");
        query = Constants.mFirestore.collection("VENDORS");
        // .orderBy("VIEWS"); can be added to query
        // in each case. once field is generated.

        FirestoreRecyclerOptions<ModelMess> options = new FirestoreRecyclerOptions.Builder<ModelMess>()
                .setQuery(query, ModelMess.class)
                .build();
        messAdapter = new MessAdapter(options, new MessAdapter.ClickListener() {
            @Override public void onPositionClicked(int position) {
                // callback performed on click
                Intent intent = new Intent(getActivity(), MessActivity.class);
                startActivity(intent);

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(messAdapter);
        messAdapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(messAdapter!=null)
        messAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(messAdapter!=null)
            messAdapter.stopListening();
    }
    void addToRecentlySearched(String newString)
    {
        t4.setText(t3.getText().toString());
        t3.setText(t2.getText().toString());
        t2.setText(t1.getText().toString());
        t1.setText(newString);
        Map<String,Object> update = new HashMap<>();
        update.put("HOTEL-NAME-1",t1.getText().toString());
        update.put("HOTEL-NAME-2",t2.getText().toString());
        update.put("HOTEL-NAME-3",t3.getText().toString());
        update.put("HOTEL-NAME-4",t4.getText().toString());
        FirebaseFirestore.getInstance().collection("/CUSTOMER/"+Constants.mAuth.getCurrentUser().getPhoneNumber()+"/RECENTLY-SEARCHED").document("/SEARCHES").update(update);
    }
    void setRecentlySearched()
    {
        FirebaseFirestore.getInstance().collection("/CUSTOMER/"+Constants.mAuth.getCurrentUser().getPhoneNumber()+"/RECENTLY-SEARCHED").document("/SEARCHES").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.getString("HOTEL-NAME-4")!=null)
                t4.setText(documentSnapshot.getString("HOTEL-NAME-4"));
                if(documentSnapshot.getString("HOTEL-NAME-3")!=null)
                t3.setText(documentSnapshot.getString("HOTEL-NAME-3"));
                if(documentSnapshot.getString("HOTEL-NAME-2")!=null)
                t2.setText(documentSnapshot.getString("HOTEL-NAME-2"));
                if(documentSnapshot.getString("HOTEL-NAME-1")!=null)
                t1.setText(documentSnapshot.getString("HOTEL-NAME-1"));
            }
        });
    }

    void setRecentSearchText(){
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(t1.getText().toString().equals(null)||t1.getText().toString().equals("")))
                addToSearchList(t1.getText().toString());
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(t2.getText().toString().equals(null)||t2.getText().toString().equals("")))
                addToSearchList(t2.getText().toString());
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(t3.getText().toString().equals(null)||t3.getText().toString().equals("")))
                addToSearchList(t3.getText().toString());
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(t4.getText().toString().equals(null)||t4.getText().toString().equals("")))
                addToSearchList(t4.getText().toString());
            }
        });
    }
    void addToSearchList(String text){
        setUpMessRecyclerView(text,1);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.getFilter().filter("#");
    }

    static List<String> getVariations(String smart)
    {
        String smart1="",smart2="",arr[],smart3;
        smart3=smart.toLowerCase();
        arr=smart.split("\\s");
        for(String s:arr)
        {
            s.replaceFirst(String.valueOf(s.charAt(0)),String.valueOf(s.charAt(0)).toUpperCase());
            smart1=smart1+s+" ";
        }
        smart2=smart.toUpperCase();
        return Arrays.asList(smart,smart1,smart2,smart3,arr[0]);
    }

}
