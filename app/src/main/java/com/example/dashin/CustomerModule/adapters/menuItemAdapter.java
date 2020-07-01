package com.example.dashin.CustomerModule.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.R;
import com.example.dashin.CustomerModule.models.menuItem;
import com.example.dashin.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class menuItemAdapter extends FirestoreRecyclerAdapter<menuItem,menuItemAdapter.menuHolder> {
    private StorageReference mStorageRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context context;
    private String mess;
    public menuItemAdapter(@NonNull FirestoreRecyclerOptions<menuItem> options,Context context,String mess) {
        super(options);
        this.context=context;
        this.mess=mess;
    }


    @Override
    protected void onBindViewHolder(@NonNull final menuHolder holder, final int position, @NonNull final menuItem model) {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Log.e("call","adpter");
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.price.setText("₹ "+model.getPrice());
        Picasso.get().load(model.getImage()).resize(300,200).into(holder.image);
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("customer").document(Constants.CurrentUser.getContact()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                        if (task1.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot1 = task1.getResult();
                            if (documentSnapshot1.get("cart_mess_name")==null||documentSnapshot1.get("cart_mess_name")=="")
                            {
                                db.collection("customer/"+ Constants.CurrentUser.getContact()+"/cart").document(getSnapshots().getSnapshot(holder.getAdapterPosition()).getId()).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    if(documentSnapshot != null && documentSnapshot.exists()) {
                                                        Toast.makeText(context,"Item already added in cart",Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        Map<String, Object> docData = new HashMap<>();
                                                        docData.put("name", model.getName());
                                                        docData.put("quantity",1);
                                                        docData.put("price",model.getPrice());
                                                        docData.put("veg",model.isVEG());
                                                        docData.put("type",model.getType());
                                                        db.collection("customer/"+ Constants.CurrentUser.getContact()+"/cart").document(getSnapshots().getSnapshot(holder.getAdapterPosition()).getId()).set(docData);
                                                        db.collection("customer").document(Constants.CurrentUser.getContact()).update("cart_mess_name",mess);
                                                        Toast.makeText(context,"Item added in cart",Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }
                                        });
                            }
                            else if (documentSnapshot1.get("cart_mess_name").equals(mess))
                            {
                                db.collection("customer/"+ Constants.CurrentUser.getContact()+"/cart").document(getSnapshots().getSnapshot(holder.getAdapterPosition()).getId()).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    if(documentSnapshot != null && documentSnapshot.exists()) {
                                                        Toast.makeText(context,"Item already added in cart",Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        Map<String, Object> docData = new HashMap<>();
                                                        docData.put("name", model.getName());
                                                        docData.put("quantity",1);
                                                        docData.put("price",model.getPrice());
                                                        docData.put("veg",model.isVEG());
                                                        docData.put("type",model.getType());
                                                        db.collection("customer/"+ Constants.CurrentUser.getContact()+"/cart").document(getSnapshots().getSnapshot(holder.getAdapterPosition()).getId()).set(docData);
                                                        //db.collection("customer").document(Constants.CurrentUser.getContact()).update("cart_mess_name",mess);
                                                        Toast.makeText(context,"Item added in cart",Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }
                                        });
                            }
                            else
                            {
                                Toast.makeText(context,"You cant add items from different mess",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


            }
        });
    }

    @NonNull
    @Override
    public menuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.menuitem_layout,parent,false);
        return new menuHolder(v);
    }

    class menuHolder extends RecyclerView.ViewHolder
    {
        TextView name,description,price;
        ImageView addButton,image;
        public menuHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.menuName);
            description=itemView.findViewById(R.id.menuDescription);
            price=itemView.findViewById(R.id.menuPrice);
            addButton=itemView.findViewById(R.id.addButton);
            image=itemView.findViewById(R.id.menuImage);
        }
    }


}
