package com.example.dashin.CustomerModule.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dashin.CustomerModule.fragments.CartFragment;
import com.example.dashin.CustomerModule.fragments.HomeFragment;
import com.example.dashin.CustomerModule.fragments.ProfileFragment;
import com.example.dashin.CustomerModule.fragments.SearchFragment;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView botnavview;
    HomeFragment home_fragment;
    SearchFragment search_fragment;
    public static SearchFragment forReference_searchFragment;
    ProfileFragment profile_fragment;
    CartFragment cart_fragment;
    public static FragmentManager f;
    boolean isHome=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("fragments", "oncreate");
        ActionBar ab = getSupportActionBar();
        f=getSupportFragmentManager();
        botnavview=findViewById(R.id.bottom_nav);
        home_fragment=new HomeFragment();
        search_fragment=new SearchFragment();
        forReference_searchFragment=search_fragment;
        cart_fragment=new CartFragment();
        profile_fragment=new ProfileFragment();
        service();
        setFragment(home_fragment);
        Log.e("fragments", "oncreate");
        botnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_search:
                        isHome=false;
                        setFragment(search_fragment);
                        return true;

                    case R.id.nav_cart:
                        isHome=false;
                        setFragment(cart_fragment);
                        return true;

                    case R.id.nav_profile:
                        isHome=false;
                        setFragment(profile_fragment);
                        return true;

                    default:
                        Log.e("Fragment", "home");
                        isHome=true;
                        setFragment(home_fragment);
                        return true;
                }
            }
        });


    }



    private void setFragment(Fragment f)
    {
        Log.e("Fragment", "home");
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.main_frame, f, "");
        ft1.commit();
    }
    private void service()
    {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d( "", "getInstanceId failed", task.getException());
                            Toast.makeText(MainActivity.this, "getInstanceId failed "+task.getException(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = "Token : "+token;
                        Log.d("token", msg);
                        FirebaseFirestore db= FirebaseFirestore.getInstance();
                        DocumentReference Ref=db.collection("customer").document(Constants.mAuth.getCurrentUser().getPhoneNumber());
                        Ref.update("fcm-token",token);
                        //Toast.makeText(Activity_Navigation.this, msg, Toast.LENGTH_SHORT).show();
                    }

                });
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("MyNotification","MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        //topic wise messages
//        FirebaseMessaging.getInstance().subscribeToTopic("inland")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg="Successfull!";
//                        if (!task.isSuccessful())
//                        {
//                            msg="Failed!";
//                        }
//                    }
//                });
    }

    @Override
    public void onBackPressed() {

        if (!isHome)
        {
            isHome=true;
            botnavview.setSelectedItemId(R.id.nav_home);
            setFragment(home_fragment);
        }
        else
        {
            finishAffinity();
            System.exit(0);
        }
    }
}
