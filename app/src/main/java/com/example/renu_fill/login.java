package com.example.renu_fill;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText emailLog, passLog;
    Button loginButton, unRegisteredButton;

    public static String currAcc;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Form initialize
        emailLog = findViewById(R.id.emailLog);
        passLog = findViewById(R.id.passLog);
        loginButton = findViewById(R.id.loginButton);
        unRegisteredButton = findViewById(R.id.unRegisteredButton);
        DB = new DBHelper(login.this);
        // Login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get email & pass form inputs
                String inputLogEmail = emailLog.getText().toString();
                String inputLogPassword = passLog.getText().toString();


                // Check if the form still empty or filled with inputs
                if (inputLogEmail.length() == 0 || inputLogPassword.length() == 0) {
                    // Empty form
                    Toast.makeText(getApplicationContext(), "Fill your data", Toast.LENGTH_SHORT).show();
                } else {
                    // Filled form

                    // Check if already registered
                    Boolean checking = DB.isUserRegistered(inputLogEmail);
                    if(checking==true) {

                        currAcc = DB.findAccID(inputLogEmail);
                        Toast.makeText(getApplicationContext(), currAcc, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(login.this, product.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Account not registered", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        // Users that dont have an account can go to register with this button (intent to register page)
        unRegisteredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, register.class));
            }
        });
    }
}
