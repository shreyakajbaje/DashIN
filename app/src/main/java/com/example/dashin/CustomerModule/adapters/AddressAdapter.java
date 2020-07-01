package com.example.dashin.CustomerModule.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.CustomerModule.models.Address;
import com.example.dashin.PaymentModule.PaymentScreen;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;



public class AddressAdapter extends FirestoreRecyclerAdapter<Address, AddressAdapter.discountHolder> {
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ObservableSnapshotArray<Address> mSnapshots;
    Boolean isPaymentAddress;
    public AddressAdapter(@NonNull FirestoreRecyclerOptions<Address> options, Context context,Boolean isPaymentAddress) {
        super(options);
        this.context=context;
        mSnapshots=options.getSnapshots();
        this.isPaymentAddress=isPaymentAddress;
    }


    @Override
    protected void onBindViewHolder(@NonNull final discountHolder holder, final int position, @NonNull final Address model) {
        holder.name.setText("Type : "+model.getName());
        holder.location.setText("Location (Lat,Lng) : "+model.getLocation().get(0)+","+model.getLocation().get(1));
        holder.address.setText(model.getAddress());
        holder.addressPerson.setText(model.getAddress_person());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                db.collection("customer/"+ Constants.CurrentUser.getContact() +"/del-locs").document(mSnapshots.getSnapshot(holder.getAdapterPosition()).getId())
                                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context,"Address Deleted !",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
        if (isPaymentAddress)
        {
            holder.addressLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PaymentScreen.class);
                    Constants.order.setDELIVERY_ADDRESS(model.getAddress());
                    Constants.order.setPERSON_NAME_ADDRESS(model.getAddress_person());
                    Constants.order.setCUST_LOC(model.getLocation());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public discountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_row,parent,false);
        return new discountHolder(v);
    }

    class discountHolder extends RecyclerView.ViewHolder
    {
        TextView name,location,addressPerson,address;
        ImageView delete;
        LinearLayout addressLayout;
        public discountHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            location=itemView.findViewById(R.id.location);
            delete=itemView.findViewById(R.id.deleteAddress);
            address=itemView.findViewById(R.id.address);
            addressPerson=itemView.findViewById(R.id.address_person);
            addressLayout=itemView.findViewById(R.id.addressLayout);
        }
    }

}
