package com.example.dashin.PaymentModule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dashin.CustomerModule.activities.MainActivity;
import com.example.dashin.CustomerModule.activities.selectAddress;
import com.example.dashin.CustomerModule.models.Details;
import com.example.dashin.CustomerModule.models.SingleEntityOfOrders;
import com.example.dashin.Discount;
import com.example.dashin.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;


public class checksum extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    String custid="", orderId="", mid="",MerchantKey="";
    SharedPreferences pref;
    private FirebaseFirestore db;
    private CollectionReference messRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Intent intent = getIntent();
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        orderId = genOrderId();
        custid = "CUST"+Constants.order.getTO().replace("+","");
        db= FirebaseFirestore.getInstance();
        //Toast.makeText(checksum.this,getIntent().getStringExtra("MID")+getIntent().getStringExtra("MKEY"),Toast.LENGTH_SHORT).show();

        //MerchantKey=getIntent().getStringExtra("MKEY");
        //mid=getIntent().getStringExtra("MID");

        //MerchantKey="S@IoR&bs#nOCsY2G";
        MerchantKey=getIntent().getStringExtra("MKEY");
        mid=getIntent().getStringExtra("MID");
      //  mid = "lpsvEw59219385907682"; /// your marchant ID

        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        // vollye , retrofit, asynch

    }
    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {
        private ProgressDialog dialog = new ProgressDialog(checksum.this);
        //private String orderId , mid, custid, amt;
        String url ="https://dashin-f110e.web.app/generate_checksum";
        String varifyurl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+orderId;
        String CHECKSUMHASH ="";
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }
        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(checksum.this);
            String param=
                    null;
            try {
                param = "MID="+mid+
                        "&ORDER_ID=" + orderId+
                        "&CUST_ID="+custid+
                        "&CHANNEL_ID=WAP&TXN_AMOUNT="+getIntent().getIntExtra("price",100)+"&WEBSITE=WEBSTAGING"+
                        "&CALLBACK_URL="+ varifyurl+"&INDUSTRY_TYPE_ID=Retail&MERCHANT_KEY="+ URLEncoder.encode(MerchantKey,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
            Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {
                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            PaytmPGService Service = PaytmPGService.getStagingService();
            // when app is ready to publish use production service
             //]
            // PaytmPGService  Service = PaytmPGService.getProductionService();
            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values

            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT",""+getIntent().getIntExtra("price",100));
            paramMap.put("WEBSITE", "WEBSTAGING");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,Service.mCertificate);
            // start payment service call here
            Service.startPaymentTransaction(checksum.this, true, true,
                    checksum.this  );
        }
    }
    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksum ", " respon true " + bundle.toString());
        if(!bundle.get("STATUS").equals("TXN_FAILURE")) {
            Constants.order.setTRAN_ID(bundle.getString("TXNID"));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            Constants.order.setTIME(date);

            //order placement
            db.collection("customer/"+Constants.order.getTO()+"/my-orders").document(orderId).set(Constants.order);
            db.collection("vendors/"+Constants.order.getFROM()+"/orders").document(orderId).set(Constants.order);
            HashMap<String,String> loc = new HashMap();
            loc.put("lat",Constants.order.getBUSI_LOC().get(0));
            loc.put("long",Constants.order.getBUSI_LOC().get(1));
            db.collection("customer/"+Constants.order.getTO()+"/my-orders/"+orderId+"/order-loc").document("loc").set(loc);
            db.collection("vendors/"+Constants.order.getFROM()+"/orders/"+orderId+"/order-loc").document("loc").set(loc);
            HashMap<String ,String > stages = new HashMap<>();
            stages.put("status","IA");
            db.collection("customer/"+Constants.order.getTO()+"/my-orders/"+orderId+"/order-loc").document("stage1").set(stages);
            db.collection("vendors/"+Constants.order.getFROM()+"/orders/"+orderId+"/order-loc").document("stage1").set(stages);
            db.collection("customer/"+Constants.order.getTO()+"/my-orders/"+orderId+"/order-loc").document("stage2").set(stages);
            db.collection("vendors/"+Constants.order.getFROM()+"/orders/"+orderId+"/order-loc").document("stage2").set(stages);
            db.collection("customer/"+Constants.order.getTO()+"/my-orders/"+orderId+"/order-loc").document("stage3").set(stages);
            db.collection("vendors/"+Constants.order.getFROM()+"/orders/"+orderId+"/order-loc").document("stage3").set(stages);
            db.collection("customer/"+Constants.order.getTO()+"/cart")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                 for (QueryDocumentSnapshot document : task.getResult()) {
                                    Details item = new Details();
                                    item.setITEM_CAT(document.getString("type"));
                                    item.setITEM_COST(document.getLong("price"));
                                    item.setITEM_NAME(document.getString("name"));
                                    item.setN(document.getLong("quantity"));
                                    item.setSUB_TOT(document.getLong("price")*document.getLong("quantity"));
                                    item.setITEM_ID(document.getId());
                                    db.collection("customer/"+Constants.order.getTO()+"/my-orders/"+orderId+"/details").document(document.getId()).set(item);
                                    db.collection("vendors/"+Constants.order.getFROM()+"/orders/"+orderId+"/details").document(document.getId()).set(item);
                                    db.collection("customer/"+Constants.order.getTO()+"/cart").document(document.getId()).delete();
                                }

                                Constants.order=new SingleEntityOfOrders();
                            } else {

                            }
                        }
                    });



            Toast.makeText(checksum.this,"Transaction successful. Order Placed!",Toast.LENGTH_LONG).show();
            Intent i = new Intent(checksum.this, MainActivity.class);
            startActivity(i);
        }
        else {
            Toast.makeText(checksum.this,"Payment failure !!",Toast.LENGTH_LONG).show();
            Intent i = new Intent(checksum.this, PaymentScreen.class);
            startActivity(i);
        }
    }
    @Override
    public void networkNotAvailable() {
    }
    @Override
    public void clientAuthenticationFailed(String s) {
    }
    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum ", " ui fail respon  "+ s );
    }
    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
    }
    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(checksum.this,"Payment failure b !!",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(checksum.this,PaymentScreen.class);
        startActivity(intent);
        Log.e("checksum ", " cancel call back respon  " );
    }
    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum ", "  transaction cancel " );
    }
    public String genOrderId() {
        Random r = new Random(System.currentTimeMillis());
        return "ORDER" + (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);
    }
}