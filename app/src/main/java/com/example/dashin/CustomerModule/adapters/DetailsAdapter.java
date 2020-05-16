package com.example.dashin.CustomerModule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dashin.utils.DatabaseLogActivity;
import com.example.dashin.CustomerModule.models.Details;
import com.example.dashin.R;

import java.util.List;


public class DetailsAdapter extends ArrayAdapter<Details> {


    public DetailsAdapter(@NonNull Context context, int resource, @NonNull List<Details> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int some_para= OrdersAdapter.pos;
        Details user = DatabaseLogActivity.details.get(some_para).get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_item, parent, false);
        }
        TextView ItemName = convertView.findViewById(R.id.BillOrderName);
        String M = user.ITEM_NAME;
        if(M.length()>30)
            M=M.substring(0,27)+"...";
        ItemName.setText(M+" x "+user.N);
        TextView ItemPrice_into_count = convertView.findViewById(R.id.BillOrderPriceAndCount);
        ItemPrice_into_count.setText("₹"+user.ITEM_COST+" x "+user.N);
        TextView ItemSubtotal = convertView.findViewById(R.id.SubTotal);
        ItemSubtotal.setText("₹"+String.valueOf(user.SUB_TOTAL)+".00");
        System.out.println("₹"+user.getSUB_TOTAL()+".00");
        return convertView;
    }
}
