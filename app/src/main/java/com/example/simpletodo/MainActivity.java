package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> items;
    private Button btnAdd;
    private EditText etItem;
    private RecyclerView rvItems;
    private ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the title on the Main Activity screen
        getSupportActionBar().setTitle(R.string.to_do_items);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        // Loading todo items from the data file
        loadToDoItems();

        // Set focus on the edit text box
        etItem.requestFocus();

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click at position " + position);

                // Create an intent of the EditActivity
                Intent intent = new Intent(MainActivity.this, EditActivity.class);

                // Pass the data which is being edited
                intent.putExtra(Constants.KEY_ITEM_POSITION, position);
                intent.putExtra(Constants.KEY_ITEM_TEXT, items.get(position));

                // Display the edit activity
                startActivityForResult(intent, Constants.EDIT_TEXT_CODE);
            }
        };

        ItemsAdapter.OnLongClickListener longClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                Log.d("MainActivity", "Long click at position " + position);

                // Delete the item from the data model
                items.remove(position);

                // Notify the Items adapter
                itemsAdapter.notifyItemRemoved(position);

                // Show message
                Toast.makeText(getApplicationContext(), "To-Do item removed", Toast.LENGTH_SHORT).show();

                // Saving todo items to the data file
                saveToDoItems();
            }
        };

        itemsAdapter = new ItemsAdapter(items, onClickListener, longClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the data from the text box
                String toDoItem = etItem.getText().toString();

                // Add the item to the data model
                items.add(toDoItem);

                // Notify the Items adapter about the added item
                itemsAdapter.notifyItemInserted(items.size()-1);

                // Clear the text box
                etItem.setText("");

                // Show message
                Toast.makeText(getApplicationContext(), "New To-Do item added", Toast.LENGTH_SHORT).show();

                // Saving todo items to the data file
                saveToDoItems();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.EDIT_TEXT_CODE) {
            // Retrieve the updated todo item
            String updatedItem = data.getStringExtra(Constants.KEY_ITEM_TEXT);

            // Extract the original position of the edited item from the position key
            int position = data.getExtras().getInt(Constants.KEY_ITEM_POSITION);

            // Update the model at the given position with the updated item data
            items.set(position, updatedItem);

            // Notify the Items Adapter
            itemsAdapter.notifyItemChanged(position);

            // Persist the final data
            saveToDoItems();

            // Show message
            Toast.makeText(getApplicationContext(), "Item updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    // Load todo-data file
    private File getDataFile() {
        return new File(getFilesDir(), "todo-data.txt");
    }

    // Reading todo items from the file into the data model
    private void loadToDoItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error while loading todo-data file", e);
            items = new ArrayList<>();
        }
    }

    // Writing the todo items to the data file
    private void saveToDoItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error while writing todo items to the todo-data file", e);
        }
    }
}