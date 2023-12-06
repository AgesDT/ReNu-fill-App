package com.example.renu_fill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {

    EditText emailLog, passLog;
    Button loginButton, unRegisteredButton;
    FirebaseFirestore firestore;

    public static String currAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLog = findViewById(R.id.emailLog);
        passLog = findViewById(R.id.passLog);
        loginButton = findViewById(R.id.loginButton);
        unRegisteredButton = findViewById(R.id.unRegisteredButton);

        firestore = FirebaseFirestore.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputLogEmail = emailLog.getText().toString();
                String inputLogPassword = passLog.getText().toString();
                if(inputLogEmail.length() == 0 || inputLogPassword.length() == 0){
                    Toast.makeText(getApplicationContext(), "Fill your data", Toast.LENGTH_SHORT).show();
                }else{
                    firestore.collection("accounts")
                            .whereEqualTo("email", inputLogEmail)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (!task.getResult().isEmpty()) {

                                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                            currAcc = document.getId();

                                            Intent intent = new Intent(login.this, product.class);
                                            intent.putExtra("documentId", currAcc);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Email not found", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error searching for email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        unRegisteredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, register.class));
            }
        });
    }
}