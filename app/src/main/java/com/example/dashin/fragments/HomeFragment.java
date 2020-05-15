package com.example.dashin.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dashin.R;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.home_screen, container, false);

        ImageView img1 = view.findViewById(R.id.res_img1);
        ImageView img2 = view.findViewById(R.id.res_img2);
        ImageView img3 = view.findViewById(R.id.res_img3);
        ImageView img4 = view.findViewById(R.id.res_img4);
        ImageView img5 = view.findViewById(R.id.res_img5);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img1.setClipToOutline(true);
            img2.setClipToOutline(true);
            img3.setClipToOutline(true);
            img4.setClipToOutline(true);
            img5.setClipToOutline(true);
        }

        ImageView imageView = view.findViewById(R.id.img1);
        imageView.setImageBitmap(getRoundedCornerImage(R.drawable.image6,R.drawable.rectangle8));

        ImageView imageView1 = view.findViewById(R.id.img2);
        imageView1.setImageBitmap(getRoundedCornerImage(R.drawable.image8,R.drawable.rectangle9));

        ImageView imageView2 = view.findViewById(R.id.img3);
        imageView2.setImageBitmap(getRoundedCornerImage(R.drawable.image5,R.drawable.rectangle9));


        return view;
    }

    Bitmap getRoundedCornerImage(int icon1, int icon2){
        // get the back image
        Bitmap backImage= BitmapFactory.decodeResource( getResources(), icon2);

        // Get the front image

        Bitmap originalImg=BitmapFactory.decodeResource( getResources(),icon1);

        // Convert the image to mutable bitmap for later editing


        Bitmap mutableBitmap = backImage.copy( Bitmap.Config.ARGB_8888, true);

        // Create Canvas object for the mutable image

        Canvas canvas = new Canvas(mutableBitmap);

        // Create paint object

        final Paint paint = new Paint();

        paint.setColor(Color.parseColor("#44aa77")) ;

        //  Draw the front image on the back image

        canvas.drawBitmap(originalImg, 25, 18, paint);

        return mutableBitmap;
    }


}
