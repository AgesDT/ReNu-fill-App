package com.example.renu_fill;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class product extends AppCompatActivity {

    EditText vol;
    Button nextButton;
    public int purId;
    public static String prevAcc, barcode;
    public static int capacity;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        vol = findViewById(R.id.volumeInput);
        nextButton = findViewById(R.id.nextButton);

        // Database initialize
        DB = new DBHelper(product.this);

        // Get account id by counting all data from the table
        Cursor res = DB.getData("purchases");
        purId = res.getCount() + 201;

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String capacityString = vol.getText().toString();
                capacity= Integer.parseInt(capacityString);

                barcode = login.currAcc+'#'+purId;
                Toast.makeText(getApplicationContext(), barcode, Toast.LENGTH_SHORT).show();

                int temp = Integer.parseInt(login.currAcc);
                DB.insertpurchase(barcode, purId, temp, capacity);
                startActivity(new Intent(product.this, instruction.class));

            }
        });
    }
}
