package com.example.dashin.CustomerModule.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.CustomerModule.models.Discount;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

public class OffersAdapter extends FirestoreRecyclerAdapter<Discount, OffersAdapter.OffersHolder> {


    private OffersAdapter.ClickListener listener;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ObservableSnapshotArray<Discount> mSnapshots;


    public OffersAdapter(@NonNull FirestoreRecyclerOptions<Discount> options, Context context, OffersAdapter.ClickListener listener ) {
        super(options);
        this.listener = listener;
        this.context=context;
        mSnapshots=options.getSnapshots();

     //   Log.e("Offers", "adapter constr");

    }

    @Override
    public void onError(FirebaseFirestoreException e) {
        Log.e("Adapter error", e.getMessage());
    }

    @Override
    protected void onBindViewHolder(@NonNull final OffersAdapter.OffersHolder holder, final int position, @NonNull final Discount model) {


        holder.food.setText("Food : "+model.getFoods());
        holder.duration.setText("Duration : "+model.getDuration());
        holder.expiry.setText("Expiry: "+model.getExpiry());
        holder.code.setText(model.getCode());
        holder.value.setText(model.getOffp()+ " %OFF");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                db.collection("vendors/"+ Constants.mAuth.getCurrentUser().getPhoneNumber() +"/discounts")
                                        .document(mSnapshots.getSnapshot(holder.getAdapterPosition()).getId())
                                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context,"Discount Deleted !",Toast.LENGTH_SHORT).show();
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
        Log.e("Offers", "bindviewholder");
    }


    @Override
    public int getItemCount() {
        return super.getItemCount() ;
    }

    @NonNull
    @Override
    public OffersAdapter.OffersHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.swiggy_offer_item, parent, false);
        //Log.e("Offers", "createviewholder");
        return new OffersAdapter.OffersHolder(v, listener);
    }

    class OffersHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView food,code,value,expiry,duration;
        ImageView delete;

        private WeakReference<ClickListener> listenerRef;

        public OffersHolder(View v, OffersAdapter.ClickListener listener){
            super(v);
            listenerRef = new WeakReference<>(listener);
            v.setOnClickListener(this);
            food=itemView.findViewById(R.id.discountFood);
            code=itemView.findViewById(R.id.discountCode);
            delete=itemView.findViewById(R.id.deleteDiscount);
            value=itemView.findViewById(R.id.discountValue);
            expiry=itemView.findViewById(R.id.discountExpiry);
            duration=itemView.findViewById(R.id.discountDuration);

            Log.e("Offers", "holder constr");


        }

        @Override
        public void onClick(View v) {

//            Discount model = getItem(getAdapterPosition());
//            Log.e("hihih", model.getTitle());
//            final Intent intent;
//            intent =  new Intent(context, ReadOfferss.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//            intent.putExtra("Offers", model.getTitle());
//            intent.putExtra("by", model.getOwner());
//            intent.putExtra("source", source);
//
//            if(!bookn.equals("")) {
//                intent.putExtra("Offersbook", bookn);
//            }else{
//                intent.putExtra("Offersbook", model.getOwner());
//            }
//            context.startActivity(intent);
        }


    }


    public interface ClickListener {
        void onPositionClicked(int position);
    }



}
