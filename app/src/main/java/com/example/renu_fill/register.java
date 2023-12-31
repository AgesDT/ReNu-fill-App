package com.example.renu_fill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class register extends AppCompatActivity {

    EditText emailReg, passReg;
    Button registerButton, registeredButton;

    public int accId;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Form initialize
        emailReg = findViewById(R.id.emailReg);
        passReg = findViewById(R.id.passReg);
        registerButton = findViewById(R.id.registerButton);
        registeredButton = findViewById(R.id.registeredButton);

        // Database initialize
        DB = new DBHelper(this);

        // Get account id by counting all data from the table
        Cursor res = DB.getData("accounts");
        accId = res.getCount() + 101;


        // Register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get email & pass form inputs
                String inputRegEmail = emailReg.getText().toString();
                String inputRegPassword = passReg.getText().toString();

                // Check if the form still empty or filled with inputs
                if (inputRegEmail.length() == 0 || inputRegPassword.length() == 0) {
                    // Empty form
                    Toast.makeText(getApplicationContext(), "Fill your data", Toast.LENGTH_SHORT).show();

                } else {
                    // Filled form

                    // Check if account already registered
                    Boolean checking = DB.isUserRegistered(inputRegEmail);
                    if(checking==true){
                        // Account already registered
                        Toast.makeText(getApplicationContext(), "Account already registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(register.this, login.class));
                    }
                    else{
                        // Account is not registered

                        // Send data account to database
                        DB.insertuserdata(accId, inputRegEmail, inputRegPassword);

                        // Pop up message and intent to login page
                        Toast.makeText(getApplicationContext(), "Login to proceed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(register.this, login.class));
                    }

                }
            }
        });

        // Users that already have an account can go to login with this button (another intent to login page)
        registeredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, login.class));
            }
        });
    }
}
