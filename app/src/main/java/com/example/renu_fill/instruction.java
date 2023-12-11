package com.example.renu_fill;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class instruction extends AppCompatActivity {

    Button generateButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference validateRef = database.getReference().child("data").child("validate"); // Reference to the "validate" child

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        generateButton = findViewById(R.id.generateButton);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call a method to update the data in the "validate" child
                updateDataInValidateChild();

                // Start the qrcode activity
                startActivity(new Intent(instruction.this, qrcode.class));
            }
        });
    }

    private void updateDataInValidateChild() {
        // Set the new value for the "validate" child
        int newValue = product.capacity; // Set the new value

        // Update the data in the "validate" child
        validateRef.setValue(newValue);
    }
}
