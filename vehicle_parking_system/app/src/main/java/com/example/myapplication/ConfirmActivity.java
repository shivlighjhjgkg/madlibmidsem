package com.example.myapplication;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ConfirmActivity extends AppCompatActivity {

    TextView textDetails;
    Button btnConfirm, btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        textDetails = findViewById(R.id.textDetails);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnEdit = findViewById(R.id.btnEdit);

        // Get data from Intent
        String type = getIntent().getStringExtra("type");
        String vehicleNo = getIntent().getStringExtra("vehicleNo");
        String rcNo = getIntent().getStringExtra("rcNo");

        String details = "Vehicle Type: " + type +
                "\nVehicle No: " + vehicleNo +
                "\nRC No: " + rcNo;

        textDetails.setText(details);

        // Confirm button
        btnConfirm.setOnClickListener(v -> {
            int serial = new Random().nextInt(10000);

            Toast.makeText(this,
                    "Parking Confirmed! Serial No: " + serial,
                    Toast.LENGTH_LONG).show();
        });

        // Edit button
        btnEdit.setOnClickListener(v -> finish());
    }
}