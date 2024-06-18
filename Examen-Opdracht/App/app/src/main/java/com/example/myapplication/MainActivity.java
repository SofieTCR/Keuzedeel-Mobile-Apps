package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText serialNumber;
    private EditText password;
    private Button loginButton;

    // Dummy accounts for testing purposes
    private String[][] dummyAccounts = {
            {"00011", "password"},
            {"00022", "password"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serialNumber = findViewById(R.id.serialNumber);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputSerial = serialNumber.getText().toString();
                String inputPassword = password.getText().toString();

                if (validateLogin(inputSerial, inputPassword)) {
                    // Login successful, redirect to MapActivity
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("serialNumber", inputSerial);
                    startActivity(intent);
                    finish();
                } else {
                    // Login failed
                    Toast.makeText(MainActivity.this, "Invalid Serial Number or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateLogin(String serial, String password) {
        for (String[] account : dummyAccounts) {
            if (account[0].equals(serial) && account[1].equals(password)) {
                return true;
            }
        }
        return false;
    }
}