package com.example.dashin.CustomerModule.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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
import com.example.dashin.CustomerModule.activities.OrderTracking;
import com.example.dashin.CustomerModule.models.Details;
import com.example.dashin.CustomerModule.models.SingleEntityOfOrders;
import com.example.dashin.CustomerModule.models.cartItem;
import com.example.dashin.utils.Constants;
import com.example.dashin.utils.DatabaseLogActivity;
import com.example.dashin.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnSuccessListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;


public class OrdersAdapter extends FirestoreRecyclerAdapter<SingleEntityOfOrders, OrdersAdapter.OrdersHolder> {
public static SingleEntityOfOrders current;
public static String pos;
public ObservableSnapshotArray<SingleEntityOfOrders> mSnapshots;
int m;
    Context context;
    @Override
    protected void onBindViewHolder(@NonNull final OrdersHolder holder, final int position, @NonNull final SingleEntityOfOrders model) {
        m=DatabaseLogActivity.OrderArraySize;
        DatabaseLogActivity.details=new ArrayList<>(m);
        for (int i=0;i<m;i++)
        {
            DatabaseLogActivity.details.add(new ArrayList<Details>());
        }
        String date=model.getTIME();
        DatabaseLogActivity.setOrdersPreviewString(model.getTO(),m-position,holder.HoldMyValues,getSnapshots().getSnapshot(holder.getAdapterPosition()).getId());
        holder.HoldMyDate.setText(date);
        holder.HoldMyTitle.setText(model.getBUSI_NAME());
        holder.HoldMyAmount.setText("₹"+String.valueOf(model.getAMOUNT()));
        holder.HoldMyType.setText(model.getTYPE());
        holder.HoldMyAddress.setText(model.getBUSI_ADD());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current=model;
                pos=getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), DetailedBillAndRepeat.class));
            }
        });
        final Transformation transformation = new RoundedCornersTransformation(17, 1);
        if(model.isLIKED())
        holder.likeButton.setLiked(true);

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                DatabaseLogActivity.firebasePointer.collection("customer/"+model.getTO()+"/my-orders").document(getSnapshots().getSnapshot(holder.getAdapterPosition()).getId()).update("liked",true);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                DatabaseLogActivity.firebasePointer.collection("customer/"+model.getTO()+"/my-orders").document(getSnapshots().getSnapshot(holder.getAdapterPosition()).getId()).update("liked",false);
            }
        });
        if(model.getFRONT_PIC()!=null) {
            Constants.mStorage.getReference().child(model.getFROM()).child(model.getFRONT_PIC()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(uri!=null) {
                        Picasso.get()
                                .load(uri)
                                .transform(transformation)
                                .resize(120, 120)
                                .centerCrop()
                                .into(holder.HoldMyPhoto);
                    }
                }
            });
        }
        holder.liveTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,OrderTracking.class);
                intent.putExtra("orderId",getSnapshots().getSnapshot(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
       // Picasso.get().load("https://content3.jdmagicbox.com/comp/def_content/tiffin_services/default-tiffin-services-9.jpg?clr=254141").transform(transformation).resize(120, 120).centerCrop().into(holder.HoldMyPhoto);
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
        Button liveTrack;

        public OrdersHolder(@NonNull final View itemView) {
            super(itemView);
            context = itemView.getContext();
            HoldMyAmount=(TextView)itemView.findViewById(R.id.HoldMyAmount);
            HoldMyTitle=(TextView)itemView.findViewById(R.id.HoldMyTitle);
            HoldMyType=(TextView)itemView.findViewById(R.id.HoldMyType);
            HoldMyDate=(TextView)itemView.findViewById(R.id.HoldMyDate);
            HoldMyValues=(TextView)itemView.findViewById(R.id.HoldMyOrder);
            HoldMyPhoto=(ImageView)itemView.findViewById(R.id.MessPhoto);
            HoldMyAddress=(TextView)itemView.findViewById(R.id.HoldMyAddress);
            likeButton=itemView.findViewById(R.id.liked);
            liveTrack=itemView.findViewById(R.id.liveTrack);
        }

    }
    public OrdersAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
        System.out.println("I'm here");
        mSnapshots=options.getSnapshots();
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

