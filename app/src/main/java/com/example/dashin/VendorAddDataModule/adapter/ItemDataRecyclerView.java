package com.example.dashin.VendorAddDataModule.adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.R;
import com.example.dashin.VendorAddDataModule.ItemData;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ItemDataRecyclerView extends FirestoreRecyclerAdapter<ItemData,ItemDataRecyclerView.ItemsHolder> {
    public ItemDataRecyclerView(@NonNull FirestoreRecyclerOptions<ItemData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ItemsHolder holder, int position, @NonNull final ItemData model) {
        holder.price.setText(null);
        holder.price.setHint(" ₹ "+model.getITEM_PRICE());
        holder.name.setText(model.getITEM_NAME());
        holder.desc.setText(model.getITEM_DESC());
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> data = new HashMap<>();
                data.put("ITEM_NAME", holder.name.getText().toString());
                data.put("ITEM_DESC",holder.desc.getText().toString());
                if(holder.price.getText()==null || holder.price.getText().toString().equals(""))
                data.put("ITEM_PRICE",model.getITEM_PRICE());
                else
                data.put("ITEM_PRICE",Integer.parseInt(holder.price.getText().toString()));
                data.put("INT_ITEM_ID", model.getINT_ITEM_ID());
                FirebaseFirestore.getInstance().collection("VENDOR-MENU/9998887776/"+CategoriesRecyclerView.currID.getCAT_ID()).document(model.getINT_ITEM_ID()).update(data);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setMessage("Deleting this item...").setTitle("Are you sure?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseFirestore.getInstance().collection("VENDOR-MENU/9998887776/"+CategoriesRecyclerView.currID.getCAT_ID()).document(model.getINT_ITEM_ID()).delete();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return new ItemDataRecyclerView.ItemsHolder(view);
    }

    class ItemsHolder extends RecyclerView.ViewHolder{
        EditText name,price,desc;
        Button save,delete;
        public ItemsHolder(@NonNull final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.item_name);
            price=itemView.findViewById(R.id.menu_item_price);
            desc=itemView.findViewById(R.id.item_desc);
            save=itemView.findViewById(R.id.save_item);
            delete=itemView.findViewById(R.id.deleteItem);
        }
    }
}
