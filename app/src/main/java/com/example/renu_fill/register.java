package com.example.renu_fill;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    EditText emailReg, passReg;
    Button registerButton, registeredButton;

    public int accId;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailReg = findViewById(R.id.emailReg);
        passReg = findViewById(R.id.passReg);
        registerButton = findViewById(R.id.registerButton);
        registeredButton = findViewById(R.id.registeredButton);
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("accounts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int documentCount = task.getResult().size();
                    // Set the id to the count + 1
                    accId = documentCount + 101;
                } else {
                    Log.e(TAG, "Error getting document count", task.getException());
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputRegEmail = emailReg.getText().toString();
                String inputRegPassword = passReg.getText().toString();
                if(inputRegEmail.length() == 0 || inputRegPassword.length() == 0){
                    Toast.makeText(getApplicationContext(), "Fill your data", Toast.LENGTH_SHORT).show();

                }else{

                    Map<String, Object> accounts = new HashMap<>();
                    accounts.put("email", inputRegEmail);
                    accounts.put("password", inputRegPassword);

                    firestore.collection("accounts").document(String.valueOf(accId)).set(accounts);

                    Toast.makeText(getApplicationContext(), "Login to proceed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(register.this, login.class));
                }

            }
        });

        registeredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, login.class));
            }
        });


    }
}