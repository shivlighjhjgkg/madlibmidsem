package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class ToggleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);

        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        Button btnNextToggle = findViewById(R.id.btnNextToggle);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(ToggleActivity.this, "Toggle is ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ToggleActivity.this, "Toggle is OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNextToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()) {
                    Intent intent = new Intent(ToggleActivity.this, CheckboxActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ToggleActivity.this, "Please turn Toggle ON to proceed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}