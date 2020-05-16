package com.example.dashin.adapters;

import android.content.Context;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.R;
import com.example.dashin.classes.ModelMess;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

public class OffersAdapter extends FirestoreRecyclerAdapter<ModelMess, OffersAdapter.OffersHolder> {


    private OffersAdapter.ClickListener listener;


    public OffersAdapter(@NonNull FirestoreRecyclerOptions<ModelMess> options, OffersAdapter.ClickListener listener ) {
        super(options);
        this.listener = listener;

        Log.e("Offers", "adapter constr");

    }

    @Override
    public void onError(FirebaseFirestoreException e) {
        Log.e("Adapter error", e.getMessage());
    }

    @Override
    protected void onBindViewHolder(@NonNull final OffersAdapter.OffersHolder holder, final int position, @NonNull final ModelMess model) {

        holder.name.setText(model.getBUSI_NAME());
        holder.rating.setText(String.valueOf(model.getRATING()));

        //set front pic here
        if(model.getFRONT_PIC()!=null) {
            Constants.mStorage.getReference().child(model.getOWNER()).child(model.getFRONT_PIC()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(uri!=null) {
                        Picasso.get()
                                .load(uri)
                                .into(holder.front_pic);
                    }
                }
            });
        }
        //calculate open status here

        Log.e("Offers", "bindviewholder");
    }


    @Override
    public int getItemCount() {
        return super.getItemCount() ;
    }

    @NonNull
    @Override
    public OffersAdapter.OffersHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        Log.e("Offers", "createviewholder");
        return new OffersAdapter.OffersHolder(v, listener);
    }

    class OffersHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView name, rating;
        ImageView front_pic;
        Context context;

        private WeakReference<ClickListener> listenerRef;

        public OffersHolder(View v, OffersAdapter.ClickListener listener){
            super(v);
            listenerRef = new WeakReference<>(listener);
            v.setOnClickListener(this);
            context = itemView.getContext();
            name = v.findViewById(R.id.busi_name);
            rating = v.findViewById(R.id.rating);
            front_pic = v.findViewById(R.id.front_pic);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                ViewOutlineProvider provider = new ViewOutlineProvider() {
//                    @Override
//                    public void getOutline(View view, Outline outline) {
//                        int curveRadius = 15;
//                        outline.setRoundRect(0, 0, view.getWidth(), (view.getHeight()), curveRadius);
//                    }
//                };
//                front_pic.setOutlineProvider(provider);
//                front_pic.setClipToOutline(true);
//            }

            Log.e("Offers", "holder constr");


        }

        @Override
        public void onClick(View v) {

//            ModelMess model = getItem(getAdapterPosition());
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
