package com.example.dashin.CustomerModule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dashin.CustomerModule.models.SmartSearchModel;
import com.example.dashin.R;

import java.util.ArrayList;
import java.util.List;

public class SmartSearch extends ArrayAdapter<SmartSearchModel> implements Filterable {

    public static ArrayList<SmartSearchModel> messList = new ArrayList<>();     //One that contains filtered values
    public static ArrayList<SmartSearchModel> messValues = new ArrayList<>();   //One that contains all values

    public SmartSearch(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.smart_search_layout, null);
        TextView title =  v.findViewById(R.id.title);
        TextView area = v.findViewById(R.id.area);
        title.setText(messList.get(position).getTITLE());
        if (messList.get(position).getIS_TITLE().equals("1"))
        area.setText(" - "+messList.get(position).getAREA());
        else
            area.setText("");
        return v;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                messList = (ArrayList<SmartSearchModel>)results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<SmartSearchModel> FilteredArrList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    results.count = messValues.size();
                    results.values = messValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < messValues.size(); i++) {
                        String data = messValues.get(i).getTITLE(); //Search by tags or title
                        if (data.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            FilteredArrList.add(messValues.get(i));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}