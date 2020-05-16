package com.example.dashin.CustomerModule.adapters;

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
import com.example.dashin.CustomerModule.models.ModelMess;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

public class MessAdapter extends FirestoreRecyclerAdapter<ModelMess, MessAdapter.MessHolder> {


    private MessAdapter.ClickListener listener;


    public MessAdapter(@NonNull FirestoreRecyclerOptions<ModelMess> options, MessAdapter.ClickListener listener ) {
        super(options);
        this.listener = listener;

        Log.e("Mess", "adapter constr");

    }

    @Override
    public void onError(FirebaseFirestoreException e) {
        Log.e("Adapter error", e.getMessage());
    }

    @Override
    protected void onBindViewHolder(@NonNull final MessAdapter.MessHolder holder, final int position, @NonNull final ModelMess model) {

        Log.e("model", model.getBUSI_NAME());
        holder.name.setText(model.getBUSI_NAME());
        holder.description.setText(model.getBUSI_DESCRIPTION());
        holder.costing.setText(String.valueOf(model.getCOSTING()));
        holder.open_from.setText(model.getOPEN_FROM());
        holder.open_till.setText(model.getOPEN_TILL());
        holder.rating.setText(Double.toString(model.getRATING()));

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


        Log.e("Mess", "bindviewholder");
    }


    @Override
    public int getItemCount() {
        return super.getItemCount() ;
    }

    @NonNull
    @Override
    public MessAdapter.MessHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mess_row, parent, false);
        Log.e("Mess", "createviewholder");
        return new MessAdapter.MessHolder(v, listener);
    }

    class MessHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView name, description, costing, open_from, open_till, open_status, rating;
        ImageView front_pic;
        Context context;

        private WeakReference<ClickListener> listenerRef;

        public MessHolder(View v, MessAdapter.ClickListener listener){
            super(v);
            listenerRef = new WeakReference<>(listener);
            v.setOnClickListener(this);
            context = itemView.getContext();
            name = v.findViewById(R.id.busi_name);
            description = v.findViewById(R.id.busi_description);
            costing = v.findViewById(R.id.costing);
            open_from = v.findViewById(R.id.open_from);
            open_till = v.findViewById(R.id.open_till);
            rating = v.findViewById(R.id.rating);
            open_status = v.findViewById(R.id.open_status);


            front_pic = v.findViewById(R.id.front_pic);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                ViewOutlineProvider provider = new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        int curveRadius = 15;
                        outline.setRoundRect(0, 0, view.getWidth(), (view.getHeight()), curveRadius);
                    }
                };
                front_pic.setOutlineProvider(provider);
                front_pic.setClipToOutline(true);
            }

            Log.e("Mess", "holder constr");


        }

        @Override
        public void onClick(View v) {

//            ModelMess model = getItem(getAdapterPosition());
//            Log.e("hihih", model.getTitle());
//            final Intent intent;
//            intent =  new Intent(context, ReadMesss.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//            intent.putExtra("Mess", model.getTitle());
//            intent.putExtra("by", model.getOwner());
//            intent.putExtra("source", source);
//
//            if(!bookn.equals("")) {
//                intent.putExtra("Messbook", bookn);
//            }else{
//                intent.putExtra("Messbook", model.getOwner());
//            }
//            context.startActivity(intent);
        }


    }


    public interface ClickListener {
        void onPositionClicked(int position);
    }



}
