package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private EditText etItemUpdate;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Set the title on the Edit Activity screen
        getSupportActionBar().setTitle(R.string.update_item);

        etItemUpdate = findViewById(R.id.etUpdateItem);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Load the selected item in the Edit Activity screen
        etItemUpdate.setText(getIntent().getStringExtra(Constants.KEY_ITEM_TEXT));
        etItemUpdate.requestFocus();

        // Bind the OnClick listener on the Update button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the Intent for the results
                Intent intent = new Intent();

                // Pass the updated data
                intent.putExtra(Constants.KEY_ITEM_TEXT, etItemUpdate.getText().toString());
                intent.putExtra(Constants.KEY_ITEM_POSITION, getIntent().getExtras().getInt(Constants.KEY_ITEM_POSITION));

                // Set the result of the Intent
                setResult(RESULT_OK, intent);

                // Finish, close the edit screen & go back
                finish();
            }
        });
    }
}