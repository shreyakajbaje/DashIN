package com.example.dashin.utils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dashin.CustomerModule.adapters.OrdersAdapter;
import com.example.dashin.CustomerModule.models.Details;
import com.example.dashin.CustomerModule.models.SingleEntityOfOrders;
import com.example.dashin.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

// This was a test activity. So it is never called in the program. Although IMPORTANT to note that, this class contains most of the important methods
// required in Activities(MyOrdersActivity, DetailedBillAndReOrderActivity, SearchBarActivity). So please don't delete this.

public class DatabaseLogActivity extends AppCompatActivity {

    //  This is the main firebase pointer
    public static FirebaseFirestore firebasePointer;
    static ArrayList<SingleEntityOfOrders> ordersPreview = new ArrayList<>();
    public static String name;
    public static String number;
    public static String email;
    public static String college;
    public static String city;
    public static int OrderArraySize;
    public static int count;
    public static ArrayList<String> TitleOfHotels;
    public static ArrayList<String> IdOfHotels;
    public static ArrayList<ArrayList<Details>> details= new ArrayList<>();
    //  To refer application context as global declaration
    static Context applicationContext;
    static int i;
    //  This is defined for use in function checkCustomerExists()
    static boolean result = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_log);
        applicationContext = getApplicationContext();
    }

    public static void startSession() {
        firebasePointer = FirebaseFirestore.getInstance();
    }

    public static void setOrdersPreviewString(String number, final int b, final TextView textView) {
        final String[] m = {""};
        firebasePointer.collection("CUSTOMER/" + number + "/MY-ORDERS/ORDER-" + b + "/DETAILS").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            return;
                        } else {
                            int count = 0;
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (int j = 0; j < documentSnapshots.size(); j++) {
                                m[0] = m[0] + (list.get(j).getString("ITEM_NAME")+" x "+list.get(j).getLong("N").toString()+ ", ");
                            }
                            m[0] = m[0].substring(0, m[0].length() - 2);
                            textView.setText(m[0]);
                        }
                    }
                });
        }

    public static FirestoreRecyclerOptions<SingleEntityOfOrders> makeRecyclerView(String number)
    {
        ordersPreview=new ArrayList<>();
        count=0;
        Query query = firebasePointer.collection("CUSTOMER/"+number+"/MY-ORDERS").orderBy("TIME",Query.Direction.DESCENDING) ;
                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                OrderArraySize=snapshots.size();
        }});
        FirestoreRecyclerOptions<SingleEntityOfOrders> options = new FirestoreRecyclerOptions
                .Builder<SingleEntityOfOrders>()
                .setQuery(query,SingleEntityOfOrders.class)
                .build();
        return options;
    }
    public static FirestoreRecyclerOptions<Details> makeRecyclerView(String number,String orderID)
    {
        Query query = firebasePointer.collection("CUSTOMER/"+number+"/MY-ORDERS/"+orderID+"/DETAILS").orderBy("ITEM_NUM",Query.Direction.ASCENDING) ;

        FirestoreRecyclerOptions<Details> options = new FirestoreRecyclerOptions
                .Builder<Details>()
                .setQuery(query,Details.class)
                .build();
        return options;
    }

    public static void setDetails(TextView messName,TextView messAddress,TextView delOrTake,TextView Amount)
    {
        messName.setText(OrdersAdapter.current.getBUSI_NAME());
        messAddress.setText(OrdersAdapter.current.getBUSI_ADD());
        /*
        if(OrdersAdapter.current.getSTATUS()==-1){
            delOrTake.setText("This order was cancelled");
        }
        else if(OrdersAdapter.current.getD_R_INDEX().equals("NULL")){
            delOrTake.setText("This order was a take-away");
        }
        else if(!OrdersAdapter.current.getD_R_INDEX().equals("NULL")){
            delOrTake.setText("This order was a Home-delivery");
        }
        */
        Amount.setText("₹"+String.valueOf(OrdersAdapter.current.getAMOUNT()));
    }

}
