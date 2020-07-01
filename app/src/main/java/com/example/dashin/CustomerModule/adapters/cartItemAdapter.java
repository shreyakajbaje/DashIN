package com.example.dashin.CustomerModule.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.R;
import com.example.dashin.CustomerModule.models.cartItem;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class cartItemAdapter extends FirestoreRecyclerAdapter<cartItem,cartItemAdapter.cartItemHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private int itemTotalPrice=0;
    private boolean is1stTime=true;
    private ObservableSnapshotArray<cartItem> mSnapshots;
    private Context context;
    private setBill setBill;
    public cartItemAdapter(@NonNull FirestoreRecyclerOptions<cartItem> options,Context context) {
        super(options);
        this.context=context;
        mSnapshots=options.getSnapshots();
    }

    @Override
    protected void onBindViewHolder(@NonNull final cartItemHolder holder, final int position, @NonNull final cartItem model) {
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
                db.collection("customer/"+ Constants.CurrentUser.getContact() +"/cart").document(getSnapshots().getSnapshot(holder.getAdapterPosition()).getId()).update("quantity",(model.getQuantity()+1))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                itemTotalPrice+=model.getPrice();

                                setBill.setBillAmounts(itemTotalPrice,getItemCount());
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
                db.collection("customer/"+ Constants.CurrentUser.getContact() +"/cart").document(getSnapshots().getSnapshot(holder.getAdapterPosition()).getId()).update("quantity",(model.getQuantity()-1))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            itemTotalPrice-=model.getPrice();

                            setBill.setBillAmounts(itemTotalPrice,getItemCount());

                        }
                    });


            }
        });
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("customer/"+ Constants.CurrentUser.getContact() +"/cart").document(getSnapshots().getSnapshot(holder.getAdapterPosition()).getId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        is1stTime=false;
                        itemTotalPrice-=(model.getPrice()*model.getQuantity());

                        setBill.setBillAmounts(itemTotalPrice,getItemCount());
                        Toast.makeText(context,"Item deleted !",Toast.LENGTH_SHORT).show();
                        db.collection("customer/"+ Constants.CurrentUser.getContact() +"/cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                QuerySnapshot qs=task.getResult();
                                if (qs.size()==0)
                                {
                                    db.collection("customer").document(Constants.CurrentUser.getContact()).update("cart_mess_name","");
                                    Constants.CurrentUser.setCart_mess_name("");
                                }
                            }
                        });

                    }
                });
            }
        });
        setBill.setBillAmounts(itemTotalPrice,getItemCount());

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
        ImageView increaseItem,decreaseItem,vegNonveg,deleteItem;
        public cartItemHolder(@NonNull View itemView) {
            super(itemView);
            deleteItem=itemView.findViewById(R.id.itemDelete);
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
         void setBillAmounts(int totalItemPrice,int size);
    }
    public void setBillListner(setBill setBill)
    {
        this.setBill=setBill;
    }
    @Override
    public int getItemCount() {
        return mSnapshots.isListening(this) ? mSnapshots.size() : 0;
    }
    @Override
    public void onDataChanged() {
        setBill.setBillAmounts(itemTotalPrice,getItemCount());
    }
}
