package com.example.renu_fill;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class product extends AppCompatActivity {

    EditText vol;
    Button nextButton;
    FirebaseFirestore firestore;
    public int purId;
    public static String prevAcc;
    public static String currBarcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        vol = findViewById(R.id.volumeInput);
        nextButton = findViewById(R.id.nextButton);
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("purchases").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int documentCount = task.getResult().size();
                    // Set the id to the count + 1
                    purId = documentCount + 201;
                } else {
                    Log.e(TAG, "Error getting document count", task.getException());
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set data untuk di passing
                String volume = vol.getText().toString();
                String barcode = login.currAcc+ "#" + purId;
                currBarcode = barcode;
                String status = "Pending";

                Map<String, Object> purchases = new HashMap<>();
                purchases.put("accountID", login.currAcc);
                purchases.put("puchaseID", purId);
                purchases.put("capacity", volume);
                purchases.put("status", status);

                // Set the document ID explicitly using purId
                firestore.collection("purchases").document(barcode).set(purchases)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + purId);
                                } else {
                                    Log.e(TAG, "Error adding document", task.getException());
                                }
                            }
                        });
                startActivity(new Intent(product.this, instruction.class));
                if (prevAcc == login.currAcc) {
                    // Increment purId for each additional purchase
                    purId++;
                }
                prevAcc = login.currAcc;
            }
        });
    }
}
