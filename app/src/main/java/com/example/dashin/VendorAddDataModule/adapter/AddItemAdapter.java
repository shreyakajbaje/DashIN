package com.example.dashin.VendorAddDataModule.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashin.R;
import com.example.dashin.VendorAddDataModule.ItemData;
import com.example.dashin.VendorAddDataModule.SelectItems;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class AddItemAdapter extends FirestoreRecyclerAdapter<ItemData, AddItemAdapter.TickedListHolder> {


    public AddItemAdapter(@NonNull FirestoreRecyclerOptions<ItemData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final TickedListHolder holder, int position, @NonNull final ItemData model) {
        holder.name.setText(model.getITEM_NAME());
        holder.price.setText("₹ "+String.valueOf(model.getITEM_PRICE()));
        holder.getItemId();
        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.selected)
                {
                    holder.selected=false;
                    holder.tick.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.empty_circle, 0);
                    SelectItems.menu.get(model.getCAT_ID()).remove(model);
                }
                else
                {
                    holder.selected=true;
                    holder.tick.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ok_filled_circle, 0);
                    SelectItems.menu.get(model.getCAT_ID()).add(model);
                }
                System.out.println(SelectItems.menu);
            }
        });
    }

    @NonNull
    @Override
    public TickedListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tick_mark,parent,false);
        return new AddItemAdapter.TickedListHolder(view);
    }

    class TickedListHolder extends RecyclerView.ViewHolder {
        TextView name,price;
        Button tick;
        boolean selected;
        public TickedListHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.item_name);
            price=itemView.findViewById(R.id.price);
            tick=itemView.findViewById(R.id.tick);
            selected=false;
        }
    }
}
