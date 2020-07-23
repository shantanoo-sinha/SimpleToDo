package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<String> items;
    private OnClickListener onClickListener;
    private OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> items, OnClickListener onClickListener, OnLongClickListener longClickListener) {
        this.items = items;
        this.onClickListener = onClickListener;
        this.longClickListener = longClickListener;
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

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
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

            // Bind the click listener
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Notify the listener about which item was clicked
                    onClickListener.onItemClicked(getAdapterPosition());
                }
            });

            // Bind the long click listener
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // Notify the listener about which item was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
