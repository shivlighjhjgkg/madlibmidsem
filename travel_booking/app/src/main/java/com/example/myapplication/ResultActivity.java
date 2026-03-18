package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textResult = findViewById(R.id.textResult);

        String source = getIntent().getStringExtra("source");
        String destination = getIntent().getStringExtra("destination");
        String date = getIntent().getStringExtra("date");
        String tripType = getIntent().getStringExtra("tripType");

        String result = "Travel Ticket Details:\n\n" +
                "Source: " + source +
                "\nDestination: " + destination +
                "\nDate: " + date +
                "\nTrip Type: " + tripType;

        textResult.setText(result);
    }
}