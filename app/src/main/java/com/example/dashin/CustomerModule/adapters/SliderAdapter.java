package com.example.dashin.CustomerModule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dashin.R;
import com.example.dashin.utils.CircleTransform;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<String> mainList;

    public SliderAdapter(Context context, List<String> imagesList) {
        this.context = context;
        this.mainList=imagesList;
    }



    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_adapter, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(final SliderAdapterVH viewHolder, int position) {

       // Picasso.with(context.getApplicationContext()).load(mainList.get(position)).into(viewHolder.imageViewBackground);
        Picasso.get().load(mainList.get(position)).into(viewHolder.imageViewBackground);

    }

    @Override
    public int getCount() {
        return mainList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;


        public SliderAdapterVH(final View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv);

            this.itemView = itemView;
        }
    }
}