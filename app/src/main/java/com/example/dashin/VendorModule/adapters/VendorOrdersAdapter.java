package com.example.dashin.VendorModule.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.CustomerModule.activities.DetailedBillAndRepeat;
import com.example.dashin.CustomerModule.models.Details;
import com.example.dashin.R;
import com.example.dashin.VendorModule.models.ModelOrder;
import com.example.dashin.utils.Constants;
import com.example.dashin.utils.DatabaseLogActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;



public class VendorOrdersAdapter extends FirestoreRecyclerAdapter<ModelOrder, VendorOrdersAdapter.OrdersHolder> {
public static ModelOrder current;
public static String pos;
int m;
    @Override
    protected void onBindViewHolder(@NonNull final OrdersHolder holder, final int position, @NonNull final ModelOrder model) {
        m=DatabaseLogActivity.OrderArraySize;
        DatabaseLogActivity.details=new ArrayList<>(m);
        for (int i=0;i<m;i++)
        {
            DatabaseLogActivity.details.add(new ArrayList<Details>());
        }

        holder.time.setText(String.valueOf(model.getTIME().getSeconds()));
        holder.amount.setText("₹" + model.getAMOUNT());

        if(model.getORDER_ID()!=-1) {
            holder.sub_ll.setVisibility(View.GONE);
            holder.order_id.setText(String.valueOf(model.getORDER_ID()));
            holder.method.setText(model.getMETHOD());
        }

        else{
            holder.order_ll.setVisibility(View.GONE);
            holder.subscription.setText(String.valueOf(model.getSUBSCRIPTION()));
            if(model.isPARCEL())
                holder.type.setText("Parcel");
            else
                holder.type.setText("Pre-order");
        }

        String docId = getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
        Constants.mFirestore.collection("VENDORS/" + "6KzwVrPx4KUaVtqYaA0ou7FmWVI3" + "/Orders/" + docId + "/DETAILS")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            return;
                        } else {
                            StringBuffer buffer = new StringBuffer("");
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot doc : documentSnapshots) {
                                buffer.append(doc.getString("Name")+" x "+doc.getLong("Quantity").toString()+ ", ");
                            }
                            holder.details.setText(buffer.toString());
                        }
                    }
                });



    }

    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_oders_item,parent,false);
        System.out.println("I'm here in Create view Holder");
        return new OrdersHolder(view);
    }

    class OrdersHolder extends RecyclerView.ViewHolder{
        TextView time, order_id, method, subscription, type, amount, details;
        Button accept, reject;
        LinearLayout order_ll,sub_ll;

        public OrdersHolder(@NonNull final View itemView) {
            super(itemView);
            time=(TextView)itemView.findViewById(R.id.time);
            order_id=(TextView)itemView.findViewById(R.id.order_id);
            method=(TextView)itemView.findViewById(R.id.method);
            subscription=(TextView)itemView.findViewById(R.id.subscription);
            type=(TextView)itemView.findViewById(R.id.type);
            amount=(TextView) itemView.findViewById(R.id.amount);
            details=(TextView)itemView.findViewById(R.id.order_details);
            accept=itemView.findViewById(R.id.order_accept_btn);
            reject=itemView.findViewById(R.id.order_reject_btn);
            order_ll=itemView.findViewById(R.id.order_LL);
            sub_ll=itemView.findViewById(R.id.sub_LL);

        }
    }
    public VendorOrdersAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
        System.out.println("I'm here");
    }

}

