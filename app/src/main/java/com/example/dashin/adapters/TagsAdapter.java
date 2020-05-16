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
import com.example.dashin.classes.ModelTag;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

public class TagsAdapter extends FirestoreRecyclerAdapter<ModelTag, TagsAdapter.TagsHolder> {


    private TagsAdapter.ClickListener listener;


    public TagsAdapter(@NonNull FirestoreRecyclerOptions<ModelTag> options, TagsAdapter.ClickListener listener ) {
        super(options);
        this.listener = listener;

        Log.e("Tags", "adapter constr");

    }

    @Override
    public void onError(FirebaseFirestoreException e) {
        Log.e("Adapter error", e.getMessage());
    }

    @Override
    protected void onBindViewHolder(@NonNull final TagsAdapter.TagsHolder holder, final int position, @NonNull final ModelTag model) {

        holder.tag.setText(model.getTag());

        //set front pic here
        if(model.getImage()!=null) {
            Constants.mStorage.getReference().child("Tags").child(model.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(uri!=null) {
                        Picasso.get()
                                .load(uri)
                                .into(holder.image);
                    }
                }
            });
        }
        //calculate open status here


        Log.e("Tags", "bindviewholder");
    }


    @Override
    public int getItemCount() {
        return super.getItemCount() ;
    }

    @NonNull
    @Override
    public TagsAdapter.TagsHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sorting_item, parent, false);
        Log.e("Tags", "createviewholder");
        return new TagsAdapter.TagsHolder(v, listener);
    }

    class TagsHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView tag;
        ImageView image;
        Context context;

        private WeakReference<ClickListener> listenerRef;

        public TagsHolder(View v, TagsAdapter.ClickListener listener){
            super(v);
            listenerRef = new WeakReference<>(listener);
            v.setOnClickListener(this);
            context = itemView.getContext();
            tag = v.findViewById(R.id.sort_tv);
            image = v.findViewById(R.id.sort_iv);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                ViewOutlineProvider provider = new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        int curveRadius = 15;
                        outline.setRoundRect(0, 0, view.getWidth(), (view.getHeight()), curveRadius);
                    }
                };
                image.setOutlineProvider(provider);
                image.setClipToOutline(true);
            }

            Log.e("Tags", "holder constr");


        }

        @Override
        public void onClick(View v) {

//            ModelTags model = getItem(getAdapterPosition());
//            Log.e("hihih", model.getTitle());
//            final Intent intent;
//            intent =  new Intent(context, ReadTagss.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//            intent.putExtra("Tags", model.getTitle());
//            intent.putExtra("by", model.getOwner());
//            intent.putExtra("source", source);
//
//            if(!bookn.equals("")) {
//                intent.putExtra("Tagsbook", bookn);
//            }else{
//                intent.putExtra("Tagsbook", model.getOwner());
//            }
//            context.startActivity(intent);
        }


    }


    public interface ClickListener {
        void onPositionClicked(int position);
    }



}
