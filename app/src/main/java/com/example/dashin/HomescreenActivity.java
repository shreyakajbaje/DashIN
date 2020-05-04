package com.example.dashin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class HomescreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        ImageView profile = findViewById(R.id.profile_btn);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomescreenActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        final BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setSelectedItemId(R.id.nav_home);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_cart:
                        Intent a = new Intent(HomescreenActivity.this,RestaurantCart.class);
                        startActivity(a);
                        break;
                    case R.id.nav_home:
                        break;
                    case  R.id.nav_profile:
                        Intent a1 = new Intent(HomescreenActivity.this,SettingsActivity.class);
                        startActivity(a1);
                        break;
                    case  R.id.nav_search:
                        Intent a2 = new Intent(HomescreenActivity.this,SearchActivity.class);
                        startActivity(a2);
                        break;
                }
                return true;
            }
        });

        RelativeLayout ly = findViewById(R.id.resturant1);

        ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomescreenActivity.this,RestaurantActivity.class);
                startActivity(intent);
            }
        });

        ImageView img1 = findViewById(R.id.res_img1);
        ImageView img2 = findViewById(R.id.res_img2);
        ImageView img3 = findViewById(R.id.res_img3);
        ImageView img4 = findViewById(R.id.res_img4);
        ImageView img5 = findViewById(R.id.res_img5);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img1.setClipToOutline(true);
            img2.setClipToOutline(true);
            img3.setClipToOutline(true);
            img4.setClipToOutline(true);
            img5.setClipToOutline(true);
        }

        ImageView imageView = findViewById(R.id.img1);
        imageView.setImageBitmap(getRoundedCornerImage(R.drawable.image6,R.drawable.rectangle8));

        ImageView imageView1 = findViewById(R.id.img2);
        imageView1.setImageBitmap(getRoundedCornerImage(R.drawable.image8,R.drawable.rectangle9));

        ImageView imageView2 = findViewById(R.id.img3);
        imageView2.setImageBitmap(getRoundedCornerImage(R.drawable.image5,R.drawable.rectangle9));
    }

    Bitmap getRoundedCornerImage(int icon1,int icon2){
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