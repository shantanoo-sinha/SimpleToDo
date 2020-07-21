package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    List<String> items;

    public ItemsAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use Layout Inflator to inflate a view
        View toDoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        // Wrap into View Holder and return
        return new ViewHolder(toDoView);
    }

    // Binds the data to an existing View Holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the item at a position
        String item = this.items.get(position);

        // Bind the item into the view holder
        holder.bind(item);
    }

    // Returns the number of items
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to access each item in the list of items
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // Update the inside view with the data
        public void bind(String item) {
            tvItem.setText(item);
        }
    }
}
