package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ResultActivity extends AppCompatActivity {

    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textResult = findViewById(R.id.textResult);

        String movie = getIntent().getStringExtra("movie");
        String theatre = getIntent().getStringExtra("theatre");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String ticketType = getIntent().getStringExtra("ticketType");

        int seats = new Random().nextInt(50) + 1;

        String result = "Movie Ticket Details:\n\n" +
                "Movie: " + movie +
                "\nTheatre: " + theatre +
                "\nDate: " + date +
                "\nTime: " + time +
                "\nTicket Type: " + ticketType +
                "\nAvailable Seats: " + seats;

        textResult.setText(result);
    }
}