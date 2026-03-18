package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerVehicle;
    EditText editVehicleNo, editRCNo;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerVehicle = findViewById(R.id.spinnerVehicle);
        editVehicleNo = findViewById(R.id.editVehicleNo);
        editRCNo = findViewById(R.id.editRCNo);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Spinner Data
        String[] vehicles = {"Car", "Bike", "Truck", "Bus"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                vehicles
        );

        spinnerVehicle.setAdapter(adapter);

        btnSubmit.setOnClickListener(v -> {

            String type = spinnerVehicle.getSelectedItem().toString();
            String vehicleNo = editVehicleNo.getText().toString();
            String rcNo = editRCNo.getText().toString();

            Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
            intent.putExtra("type", type);
            intent.putExtra("vehicleNo", vehicleNo);
            intent.putExtra("rcNo", rcNo);

            startActivity(intent);
        });
    }
}