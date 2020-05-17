package com.example.dashin.CustomerModule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.CustomerModule.models.SingleEntityOfOrders;
import com.example.dashin.utils.DatabaseLogActivity;
import com.example.dashin.CustomerModule.models.Details;
import com.example.dashin.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;


public class DetailsAdapter extends FirestoreRecyclerAdapter<Details, DetailsAdapter.DetailsHolder> {

    class DetailsHolder extends RecyclerView.ViewHolder{
        TextView HoldMyItem;
        TextView HoldMyMul;
        TextView HoldMyTotal;
        public DetailsHolder(@NonNull final View itemView) {
            super(itemView);
            HoldMyItem=(TextView)itemView.findViewById(R.id.BillOrderName);
            HoldMyMul=(TextView)itemView.findViewById(R.id.BillOrderPriceAndCount);
            HoldMyTotal=(TextView)itemView.findViewById(R.id.SubTotal);
        }
    }

    public DetailsAdapter(@NonNull FirestoreRecyclerOptions<Details> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DetailsAdapter.DetailsHolder holder, int position, @NonNull Details model) {
        holder.HoldMyTotal.setText("₹"+model.getSUB_TOT()+".00");
        holder.HoldMyItem.setText(model.getITEM_NAME()+" x "+model.getN());
        holder.HoldMyMul.setText("₹"+model.getITEM_COST()+" x "+model.getN());
    }

    @Override
    public DetailsAdapter.DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item,parent,false);
        return new DetailsHolder(view);
    }
}
