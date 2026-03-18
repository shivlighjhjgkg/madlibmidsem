package com.example.myapplication;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    SeekBar seekBar;
    Switch mySwitch;
    TextView textAge;
    Button btnSubmit;

    int age = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        seekBar = findViewById(R.id.seekBar);
        mySwitch = findViewById(R.id.mySwitch);
        textAge = findViewById(R.id.textAge);
        btnSubmit = findViewById(R.id.btnSubmit);

        // SEEK BAR LOGIC
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                age = progress;
                textAge.setText("Age: " + age);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // BUTTON CLICK
        btnSubmit.setOnClickListener(v -> {

            // RADIO BUTTON
            int selectedId = radioGroup.getCheckedRadioButtonId();
            String gender = "Not Selected";

            if (selectedId != -1) {
                RadioButton selected = findViewById(selectedId);
                gender = selected.getText().toString();
            }

            // SWITCH
            String switchStatus = mySwitch.isChecked() ? "ON" : "OFF";

            // FINAL OUTPUT
            String result = "Gender: " + gender +
                    "\nAge: " + age +
                    "\nNotifications: " + switchStatus;

            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        });
    }
}