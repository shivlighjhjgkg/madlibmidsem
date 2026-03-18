package com.example.myapplication;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleMode;
    ImageView modeImage;
    Button changeModeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleMode = findViewById(R.id.toggleMode);
        modeImage = findViewById(R.id.modeImage);
        changeModeBtn = findViewById(R.id.changeModeBtn);

        // Toggle button listener
        toggleMode.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                modeImage.setImageResource(R.drawable.wifi_icon);
                Toast.makeText(this, "Current Mode: Wi-Fi", Toast.LENGTH_SHORT).show();
            } else {
                modeImage.setImageResource(R.drawable.mobile_data_icon);
                Toast.makeText(this, "Current Mode: Mobile Data", Toast.LENGTH_SHORT).show();
            }
        });

        // Change Mode Button
        changeModeBtn.setOnClickListener(v -> {

            // Switch toggle state
            toggleMode.setChecked(!toggleMode.isChecked());

        });
    }
}
