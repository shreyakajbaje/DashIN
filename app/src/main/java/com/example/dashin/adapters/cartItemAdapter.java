package com.example.dashin.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.R;
import com.example.dashin.mess_cart;
import com.example.dashin.utils.cartItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class cartItemAdapter extends FirestoreRecyclerAdapter<cartItem,cartItemAdapter.cartItemHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private int itemTotalPrice=0;
    private boolean is1stTime=true;
    private ObservableSnapshotArray<cartItem> mSnapshots;
    private Context context;
    public cartItemAdapter(@NonNull FirestoreRecyclerOptions<cartItem> options,Context context) {
        super(options);
        this.context=context;
        mSnapshots=options.getSnapshots();
    }

    @Override
    protected void onBindViewHolder(@NonNull cartItemHolder holder, final int position, @NonNull final cartItem model) {
//        if(position==0)
//        {
//            itemTotalPrice=0;
//        }
        holder.name.setText(model.getName());
        holder.qty.setText(""+model.getQuantity());
        holder.price.setText("₹"+model.getPrice());
        if (model.isVEG())
            holder.vegNonveg.setImageResource(R.drawable.veg_icon);
        else
            holder.vegNonveg.setImageResource(R.drawable.non_veg_icon);
        if (is1stTime)
            itemTotalPrice+=(model.getPrice()*model.getQuantity());
        holder.increaseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is1stTime=false;
                //Toast.makeText(context,"+"+position,Toast.LENGTH_SHORT).show();
                db.collection("CUSTOMER/8682259087/Cart").document(getSnapshots().getSnapshot(position).getId()).update("Quantity",(model.getQuantity()+1))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                itemTotalPrice+=model.getPrice();

                                ((mess_cart)context).setBillAmounts(itemTotalPrice);
                            }
                        });

            }
        });
        holder.decreaseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is1stTime=false;
                if (model.getQuantity()-1<1)
                {
                    return;
                }
                //Toast.makeText(context,"-"+position,Toast.LENGTH_SHORT).show();
                db.collection("CUSTOMER/8682259087/Cart").document(getSnapshots().getSnapshot(position).getId()).update("Quantity",(model.getQuantity()-1))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            itemTotalPrice-=model.getPrice();

                            ((mess_cart)context).setBillAmounts(itemTotalPrice);

                        }
                    });


            }
        });
            ((mess_cart)context).setBillAmounts(itemTotalPrice);

       // Toast.makeText(context,"in",Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public cartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        return new cartItemHolder(v);
    }

    class cartItemHolder extends RecyclerView.ViewHolder
    {
        TextView name,qty,price;
        ImageView increaseItem,decreaseItem,vegNonveg;
        public cartItemHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.itemName);
            qty=itemView.findViewById(R.id.itemQuantity);
            price=itemView.findViewById(R.id.itemPrice);
            increaseItem=itemView.findViewById(R.id.add_item);
            decreaseItem=itemView.findViewById(R.id.remove_item);
            vegNonveg=itemView.findViewById(R.id.vegNonveg);
        }
    }
    public interface setBill
    {
         void setBillAmounts(int totalItemPrice);
    }
    @Override
    public int getItemCount() {
        return mSnapshots.isListening(this) ? mSnapshots.size() : 0;
    }

}
