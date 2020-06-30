package com.example.dashin.CustomerModule.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.CustomerModule.activities.DetailedBillAndRepeat;
import com.example.dashin.CustomerModule.activities.MyOrdersActivity;
import com.example.dashin.CustomerModule.models.Details;
import com.example.dashin.CustomerModule.models.SingleEntityOfOrders;
import com.example.dashin.utils.DatabaseLogActivity;
import com.example.dashin.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class OrdersAdapter extends FirestoreRecyclerAdapter<SingleEntityOfOrders, OrdersAdapter.OrdersHolder> {
public static SingleEntityOfOrders current;
public static String pos;
int m;
    @Override
    protected void onBindViewHolder(@NonNull final OrdersHolder holder, final int position, @NonNull final SingleEntityOfOrders model) {
        m=DatabaseLogActivity.OrderArraySize;
        DatabaseLogActivity.details=new ArrayList<>(m);
        for (int i=0;i<m;i++)
        {
            DatabaseLogActivity.details.add(new ArrayList<Details>());
        }
        String date=model.getTIME().toDate().toLocaleString();
        DatabaseLogActivity.setOrdersPreviewString(model.getFROM(),m-position,holder.HoldMyValues);
        holder.HoldMyDate.setText(date);
        holder.HoldMyTitle.setText(model.getBUSI_NAME());
        holder.HoldMyAmount.setText("₹"+String.valueOf(model.getAMOUNT()));
        holder.HoldMyType.setText(model.getTYPE());
        holder.HoldMyAddress.setText(model.getBUSI_ADD());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current=model;
                pos="ORDER-"+(m-position);
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), DetailedBillAndRepeat.class));
            }
        });
        final Transformation transformation = new RoundedCornersTransformation(17, 1);
        if(model.isLIKED())
        holder.likeButton.setLiked(true);

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                DatabaseLogActivity.firebasePointer.collection("CUSTOMER/"+model.getFROM()+"/MY-ORDERS").document("ORDER-"+(m-position)).update("LIKED",true);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                DatabaseLogActivity.firebasePointer.collection("CUSTOMER/"+model.getFROM()+"/MY-ORDERS").document("ORDER-"+(m-position)).update("LIKED",false);
            }
        });
        Picasso.get().load("https://content3.jdmagicbox.com/comp/def_content/tiffin_services/default-tiffin-services-9.jpg?clr=254141").transform(transformation).resize(120, 120).centerCrop().into(holder.HoldMyPhoto);
    }

    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.new_one_entity_layout,parent,false);
        System.out.println("I'm here in Create view Holder");
        return new OrdersHolder(view);
    }

    class OrdersHolder extends RecyclerView.ViewHolder{
        TextView HoldMyDate;
        TextView HoldMyTitle;
        TextView HoldMyValues;
        TextView HoldMyType;
        TextView HoldMyAmount;
        TextView HoldMyAddress;
        ImageView HoldMyPhoto;
        LikeButton likeButton;

        public OrdersHolder(@NonNull final View itemView) {
            super(itemView);
            HoldMyAmount=(TextView)itemView.findViewById(R.id.HoldMyAmount);
            HoldMyTitle=(TextView)itemView.findViewById(R.id.HoldMyTitle);
            HoldMyType=(TextView)itemView.findViewById(R.id.HoldMyType);
            HoldMyDate=(TextView)itemView.findViewById(R.id.HoldMyDate);
            HoldMyValues=(TextView)itemView.findViewById(R.id.HoldMyOrder);
            HoldMyPhoto=(ImageView)itemView.findViewById(R.id.MessPhoto);
            HoldMyAddress=(TextView)itemView.findViewById(R.id.HoldMyAddress);
            likeButton=itemView.findViewById(R.id.liked);
        }
    }
    public OrdersAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
        System.out.println("I'm here");
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        TextView t = MyOrdersActivity.noResults;
        RecyclerView r = MyOrdersActivity.recyclerView;
        if(getItemCount()==0)
        {
            t.setVisibility(View.VISIBLE);
            r.setVisibility(View.INVISIBLE);
        }
        else
        {
            t.setVisibility(View.INVISIBLE);
            r.setVisibility(View.VISIBLE);
        }
    }
}

