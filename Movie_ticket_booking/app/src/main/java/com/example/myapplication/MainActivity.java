package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerMovie, spinnerTheatre;
    DatePicker datePicker;
    TimePicker timePicker;
    ToggleButton toggleTicket;
    Button btnBook, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        spinnerMovie = findViewById(R.id.spinnerMovie);
        spinnerTheatre = findViewById(R.id.spinnerTheatre);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        toggleTicket = findViewById(R.id.toggleTicket);
        btnBook = findViewById(R.id.btnBook);
        btnReset = findViewById(R.id.btnReset);

        // Spinner Data with "Select" prompt
        String[] movies = {"Select Movie", "Avengers", "Inception", "Interstellar", "The Dark Knight"};
        String[] theatres = {"Select Theatre", "PVR Cinemas", "INOX", "Cinepolis", "Carnival Cinemas"};

        ArrayAdapter<String> movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, movies);
        spinnerMovie.setAdapter(movieAdapter);

        ArrayAdapter<String> theatreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, theatres);
        spinnerTheatre.setAdapter(theatreAdapter);

        // Premium Logic: Update button state whenever Toggle or TimePicker changes
        View.OnClickListener updateButtonState = v -> validatePremiumTime();
        toggleTicket.setOnClickListener(updateButtonState);
        
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> validatePremiumTime());

        // Book Now Button Click
        btnBook.setOnClickListener(v -> {
            if (validateInputs()) {
                String movie = spinnerMovie.getSelectedItem().toString();
                String theatre = spinnerTheatre.getSelectedItem().toString();
                String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                String time = String.format("%02d:%02d", timePicker.getHour(), timePicker.getMinute());
                String ticketType = toggleTicket.isChecked() ? "Premium" : "Standard";

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("movie", movie);
                intent.putExtra("theatre", theatre);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("ticketType", ticketType);
                startActivity(intent);
            }
        });

        // Reset Button Click
        btnReset.setOnClickListener(v -> {
            spinnerMovie.setSelection(0);
            spinnerTheatre.setSelection(0);
            toggleTicket.setChecked(false);
            
            Calendar c = Calendar.getInstance();
            datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            
            timePicker.setHour(c.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(c.get(Calendar.MINUTE));
            
            btnBook.setEnabled(true);
            Toast.makeText(MainActivity.this, "Fields Reset", Toast.LENGTH_SHORT).show();
        });
    }

    private void validatePremiumTime() {
        int hour = timePicker.getHour();
        if (toggleTicket.isChecked() && hour < 12) {
            btnBook.setEnabled(false);
            Toast.makeText(this, "Premium tickets only available for shows after 12:00 PM", Toast.LENGTH_SHORT).show();
        } else {
            btnBook.setEnabled(true);
        }
    }

    private boolean validateInputs() {
        if (spinnerMovie.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a movie", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinnerTheatre.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a theatre", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}