package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerSource, spinnerDestination;
    DatePicker datePicker;
    ToggleButton toggleTrip;
    Button btnSubmit, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerSource = findViewById(R.id.spinnerSource);
        spinnerDestination = findViewById(R.id.spinnerDestination);
        datePicker = findViewById(R.id.datePicker);
        toggleTrip = findViewById(R.id.toggleTrip);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);

        // Spinner Data
        String[] cities = {"Delhi", "Mumbai", "Bangalore", "Chennai"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                cities
        );

        spinnerSource.setAdapter(adapter);
        spinnerDestination.setAdapter(adapter);

        // SUBMIT BUTTON
        btnSubmit.setOnClickListener(v -> {

            String source = spinnerSource.getSelectedItem().toString();
            String destination = spinnerDestination.getSelectedItem().toString();

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();

            String date = day + "/" + month + "/" + year;

            String tripType = toggleTrip.isChecked() ? "Round Trip" : "One Way";

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("source", source);
            intent.putExtra("destination", destination);
            intent.putExtra("date", date);
            intent.putExtra("tripType", tripType);

            startActivity(intent);
        });

        // RESET BUTTON
        btnReset.setOnClickListener(v -> {

            spinnerSource.setSelection(0);
            spinnerDestination.setSelection(0);
            toggleTrip.setChecked(false);

            // Reset Date to current
            Calendar calendar = Calendar.getInstance();
            datePicker.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        });
    }
}